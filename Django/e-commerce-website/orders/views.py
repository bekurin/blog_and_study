from django.http.response import JsonResponse
from store.models import Product
from django.shortcuts import redirect, render
from carts.models import Cart, CartItem
from righteous.db.forms import OrderForm
from orders.models import Order, OrderProduct, Payment

import datetime
import json

from django.template.loader import render_to_string
from django.core.mail import EmailMessage, send_mail

# Create your views here.


def payment(request):
    body = json.loads(request.body)
    order = Order.objects.get(
        user=request.user, is_ordered=False, order_number=body['orderID'])

    payment = Payment(user=request.user, payment_id=body['transID'],
                      payment_method=body['payment_method'], amount_paid=order.order_total, status=body['status'])
    payment.save()

    order.payment = payment
    order.is_ordered = True
    order.save()

    cart_items = CartItem.objects.filter(user=request.user)

    for item in cart_items:
        order_product = OrderProduct()
        order_product.order = order
        order_product.payment = payment
        order_product.user = request.user
        order_product.product = item.product
        order_product.quantity = item.quantity
        order_product.product_price = item.product.get_sale_price()
        order_product.ordered = True
        order_product.save()

        cart_item = CartItem.objects.get(id=item.id)
        product_variation = cart_item.variations.all()

        order_product = OrderProduct.objects.get(id=order_product.id)
        order_product.variations.set(product_variation)
        order_product.save()

        product = Product.objects.get(id=item.product_id)
        product.stock -= item.quantity
        product.save()

    CartItem.objects.filter(user=request.user).delete()

    mail_subject = '주문해주셔서 감사합니다!'
    message = render_to_string('orders/order_received_email.html', {
        'user': request.user,
        'order': order
    })
    to_email = request.user.email
    send_email = EmailMessage(mail_subject, message, to=[to_email])
    send_email.send()

    data = {
        'order_number': order.order_number,
        'transID': payment.payment_id
    }
    return JsonResponse(data)


def order_complete(request):
    order_number = request.GET.get('order_number')
    transID = request.GET.get('transID')

    try:
        order = Order.objects.get(order_number=order_number, is_ordered=True)
        ordered_products = OrderProduct.objects.filter(order_id=order.id)

        subtotal = 0

        for i in ordered_products:
            subtotal += i.product_price * i.quantity

        payment = Payment.objects.get(payment_id=transID)

        context = {
            'order': order,
            'ordered_products': ordered_products,
            'order_number': order.order_number,
            'transID': payment.payment_id,
            'payment': payment,
            'subtotal': subtotal,
        }
        return render(request, 'orders/order_complete.html', context)
    except (Payment.DoesNotExist, Order.DoesNotExist):
        return redirect('home')


def place_order(request):
    current_user = request.user

    cart_items = CartItem.objects.filter(user=current_user, is_active=True)
    cart_count = cart_items.count()

    if cart_count <= 0:
        return redirect('home')

    total, sale_total, quantity = 0, 0, 0

    for cart_item in cart_items:
        total += cart_item.product.price * cart_item.quantity
        sale_total += (cart_item.product.price *
                       (cart_item.product.sale / 100)) * cart_item.quantity
        quantity += cart_item.quantity

    if request.method == 'POST':
        form = OrderForm(request.POST)

        if form.is_valid():
            data = Order()
            data.user = current_user
            data.first_name = form.cleaned_data['first_name']
            data.last_name = form.cleaned_data['last_name']
            data.phone = form.cleaned_data['phone']
            data.address_line_1 = form.cleaned_data['address_line_1']
            data.address_line_2 = form.cleaned_data['address_line_2']
            data.order_note = form.cleaned_data['order_note']
            data.order_total = int(total - sale_total)
            data.ip = request.META.get('REMOTE_ADDR')
            data.save()

            yr = int(datetime.date.today().strftime('%Y'))
            mt = int(datetime.date.today().strftime('%m'))
            dt = int(datetime.date.today().strftime('%d'))
            d = datetime.date(yr, mt, dt)
            current_date = d.strftime('%Y%m%d')
            order_number = current_date + str(data.id)
            data.order_number = order_number
            data.save()

            order = Order.objects.get(
                user=current_user, is_ordered=False, order_number=order_number)

            context = {
                'order': order,
                'cart_items': cart_items,
                'total': total,
                'sale_total': int(sale_total),
                'discount_total': int(total - sale_total)
            }
        return render(request, 'orders/payments.html', context)
    else:
        return redirect('checkout')
