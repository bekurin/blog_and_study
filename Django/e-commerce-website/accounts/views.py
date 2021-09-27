from righteous.settings import EMAIL_USE_TLS
from django.contrib import messages, auth
from django.shortcuts import get_object_or_404, redirect, render
from righteous.db.forms import RegistrationsForm, UserProfileForm
from .models import Account, UserProfile
from carts.models import Cart, CartItem
from carts.views import _cart_id
from django.contrib.auth.decorators import login_required
from orders.models import Order
from righteous.db.models import OrderStatusChoices
from django.db.models import Q

# Verification email
from django.contrib.sites.shortcuts import get_current_site
from django.template.loader import render_to_string
from django.utils.http import urlsafe_base64_encode, urlsafe_base64_decode
from django.utils.encoding import force_bytes
from django.contrib.auth.tokens import default_token_generator
from django.core.mail import EmailMessage, message

import requests


# Create your views here.


def register(request):
    if request.method == 'POST':
        form = RegistrationsForm(request.POST)
        if form.is_valid():
            first_name = form.cleaned_data['first_name']
            last_name = form.cleaned_data['last_name']
            username = form.cleaned_data['username']
            phone_number = form.cleaned_data['phone_number']
            email = form.cleaned_data['email']
            password = form.cleaned_data['password']

            user = Account.objects.create_user(
                first_name=first_name, last_name=last_name, username=username, email=email, password=password, phone_number=phone_number)
            user.save()

            profile = UserProfile.objects.create(user=user)
            profile.save()

            current_site = get_current_site(request)
            mail_subject = '[righteous] 계정 활성화를 위한 이메일입니다.'
            message = render_to_string('accounts/verification_email.html', {
                'user': user,
                'domain': current_site,
                'uid': urlsafe_base64_encode(force_bytes(user.pk)),
                'token': default_token_generator.make_token(user),
            })
            to_email = email
            send_email = EmailMessage(mail_subject, message, to=[to_email])
            send_email.send()
            return redirect('/accounts/login/?command=verification&email='+email)
    else:
        form = RegistrationsForm()
    context = {
        'form': form
    }
    return render(request, 'accounts/register.html', context)


def login(request):
    if request.method == 'POST':
        email = request.POST['email']
        password = request.POST['password']
        user = auth.authenticate(email=email, password=password)

        if user is not None:
            try:
                cart = Cart.objects.get(cart_id=_cart_id(request))
                is_cart_item_exists = CartItem.objects.filter(
                    cart=cart).exists()

                if is_cart_item_exists:
                    cart_item = CartItem.objects.filter(cart=cart)
                    product_variation = []

                    for item in cart_item:
                        variation = item.variations.all()
                        product_variation.append(list(variation))

                    cart_item = CartItem.objects.filter(user=user)
                    ex_var_list = []
                    id = []

                    for item in cart_item:
                        existing_variation = item.variations.all()
                        ex_var_list.append(list(existing_variation))
                        id.append(item.id)

                    for product in product_variation:
                        if product in ex_var_list:
                            index = ex_var_list.index(product)
                            item_id = id[index]
                            item = CartItem.objects.get(id=item_id)
                            item.quantity += 1
                            item.user = user
                            item.save()
                        else:
                            cart_item = CartItem.objects.filter(cart=cart)
                            for item in cart_item:
                                item.user = user
                                item.save()
            except:
                pass

            auth.login(request, user)
            messages.success(request, '로그인에 성공하였습니다.')
            url = request.META.get('HTTP_REFERER')

            try:
                query = requests.utils.urlparse(url).query

                params = dict(x.split('=') for x in query.split('&'))
                if 'next' in params:
                    next_page = params['next']
                    return redirect(next_page)
            except:
                return redirect('home')
        else:
            messages.error(request, '이메일 또는 비밀번호를 확인해주시기 바랍니다.')
            return redirect('login')
    return render(request, 'accounts/login.html')


@login_required(login_url='login')
def logout(request):
    auth.logout(request)
    messages.success(request, "로그아웃하셨습니다.")
    return redirect('login')


def activate(request, uidb64, token):
    try:
        uid = urlsafe_base64_decode(uidb64).decode()
        user = Account._default_manager.get(pk=uid)
    except(TypeError, ValueError, OverflowError, Account.DoesNotExist):
        user = None

    if user is not None and default_token_generator.check_token(user, token):
        user.is_active = True
        user.save()
        messages.success(request, '계정이 활성화되었습니다.')
        return redirect('login')
    else:
        messages.error(request, '유효하지 않은 링크입니다.')
        return redirect('register')


def forgotpassword(request):
    if request.method == 'POST':
        email = request.POST['email']

        if Account.objects.filter(email=email).exists():
            user = Account.objects.get(email__exact=email)

            current_site = get_current_site(request)
            mail_subject = '[righteous] 비밀번호를 재설정하세요.'
            message = render_to_string('accounts/reset_password_email.html', {
                'user': user,
                'domain': current_site,
                'uid': urlsafe_base64_encode(force_bytes(user.id)),
                'token': default_token_generator.make_token(user),
            })
            to_email = email
            send_email = EmailMessage(mail_subject, message, to=[to_email])
            send_email.send()

            messages.success(request, '비밀번호 재설정 이메일을 발송하였습니다.')
            return redirect('login')
        else:
            messages.error(request, '회원이 존재하지 않습니다.')
            return redirect('forgotpassword')
    return render(request, 'accounts/forgot_password.html')


def resetpassword(request):
    if request.method == 'POST':
        password = request.POST['password']
        confirm_password = request.POST['confirm_password']

        if password == confirm_password:
            uid = request.session.get('uid')
            user = Account.objects.get(pk=uid)
            user.set_password(password)
            user.save()

            messages.success(request, '비밀번호 재설정이 완료되었습니다.')
            return redirect('login')
        else:
            messages.error(request, '비밀번호가 같지 않습니다.')
            return redirect('resetpassword')
    else:
        return render(request, 'accounts/reset_password.html')


def resetpassword_validate(request, uidb64, token):
    try:
        uid = urlsafe_base64_decode(uidb64).decode()
        user = Account._default_manager.get(pk=uid)
    except(TypeError, ValueError, OverflowError, Account.DoesNotExist):
        user = None

    if user is not None and default_token_generator.check_token(user, token):
        request.session['uid'] = uid
        messages.success(request, '비밀번호를 재설정해주시기 바랍니다.')
        return redirect('resetpassword')
    else:
        messages.error(request, '유효하지 않은 주소입니다.')
        return redirect('login')


@login_required(login_url='login')
def dashboard(request):
    current_user = request.user
    orders = Order.objects.filter(
        Q(user=current_user, is_ordered=True) & ~Q(status=OrderStatusChoices.COMPLETED))
    order_count = orders.count()
    order_complete_count = Order.objects.filter(
        user=current_user, status=OrderStatusChoices.COMPLETED).count()

    context = {
        'user': current_user,
        'orders': orders,
        'order_count': order_count,
        'order_complete_count': order_complete_count
    }
    return render(request, 'accounts/dashboard.html', context)


def orders(request):
    current_user = request.user

    orders = Order.objects.filter(
        Q(user=current_user, is_ordered=True) & ~Q(status=OrderStatusChoices.COMPLETED)).order_by('-created_at')

    context = {
        'orders': orders
    }
    return render(request, 'accounts/dashboard_order.html', context)
