from typing import OrderedDict

from django.http.request import HttpRequest
from django.http.response import HttpResponse
from category.models import Category, Subcategory
from django.contrib import messages
from django.core.paginator import Paginator
from django.db.models import Q
from django.shortcuts import get_object_or_404, redirect, render
from righteous.db.forms import ReviewForm
from orders.models import OrderProduct

from .models import Brand, Product, ReviewRating

# Create your views here.


def store(request, category_slug=None, subcategory_slug=None):
    if category_slug != None and subcategory_slug == None:
        categories = get_object_or_404(Category, pk=category_slug)
        products = Product.objects.filter(
            category=categories, is_available=True)
    elif subcategory_slug != None:
        categories = get_object_or_404(Subcategory, pk=subcategory_slug)
        products = Product.objects.filter(
            subcategory=categories, is_available=True)
    else:
        products = Product.objects.filter(
            is_available=True).order_by('-created_at')

    paginator = Paginator(products, 1)

    page = request.GET.get('page')
    list_kind = request.GET.get('list_kind')

    paged_products = paginator.get_page(page)
    product_count = products.count()

    if list_kind == 'large':
        template_name = 'store/product_list-large.html'
    else:
        template_name = 'store/product_list-grid.html'

    context = {
        'products': paged_products,
        'product_count': product_count,
        'categories': categories,
    }

    return render(request, template_name, context)


def search(request):
    if 'keyword' in request.GET:
        keyword = request.GET['keyword']
        if keyword:
            products = Product.objects.filter(Q(description__icontains=keyword) | Q(
                product_name__icontains=keyword)).order_by('-created_at')
            product_count = products.count()

        list_kind = request.GET.get('list_kind')

        if list_kind == 'large':
            template_name = 'store/product_list-large.html'
        else:
            template_name = 'store/product_list-grid.html'

        context = {
            'products': products,
            'product_count': product_count
        }
        return render(request, template_name, context)


def product_detail(request, category_slug, subcategory_slug, product_slug):
    try:
        single_product = Product.objects.get(
            category__id=category_slug, subcategory__id=subcategory_slug, slug=product_slug)
    except Exception as e:
        raise e

    if request.user.is_authenticated:
        try:
            order_product = OrderProduct.objects.filter(
                user=request.user, product_id=single_product.id).exists()
        except:
            order_product = None
    else:
        order_product = None

    reviews = ReviewRating.objects.filter(
        product__id=single_product.id, status=True)

    context = {
        'single_product': single_product,
        'reviews': reviews,
        'order_product': order_product
    }
    return render(request, 'store/product_detail.html', context)


def submit_review(request, product_id):
    url = request.META.get('HTTP_REFERER')
    if request.method == 'POST':
        try:
            reviews = ReviewRating.objects.get(
                user=request.user, product__id=product_id)
            form = ReviewForm(request.POST, instance=reviews)
            form.save()
            messages.success(request, '감사합니다. 리뷰가 업데이트되었습니다.')
            return redirect(url)
        except:
            form = ReviewForm(request.POST)
            if form.is_valid():
                data = ReviewRating()
                data.subject = form.cleaned_data['subject']
                data.rating = form.cleaned_data['rating']
                data.ip = request.META.get('REMOTE_ADDR')
                data.product_id = product_id
                data.user = request.user
                data.save()
                messages.success(request, '소중한 리뷰 감사합니다.')
                return redirect(url)


def brand_detail(request, brand_id):
    brand = Brand.objects.get(id=brand_id)
    products = Product.objects.filter(brand=brand, is_available=True)
    product_count = products.count()

    context = {
        'brand': brand,
        'products': products,
        'product_count': product_count,
    }
    return render(request, 'store/brand_detail.html', context)
