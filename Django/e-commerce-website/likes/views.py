from django.shortcuts import render, redirect
from store.models import Product
from .models import LikeProduct
from django.contrib.auth.decorators import login_required
import requests

# Create your views here.


@login_required(login_url='login')
def like(request):
    current_user = request.user

    if current_user.is_authenticated:
        like_products = LikeProduct.objects.filter(user=current_user)
    else:
        like_products = None

    context = {
        'like_products': like_products
    }
    return render(request, 'likes/wishlist.html', context)


@login_required(login_url='login')
def add_like(request, product_id):
    current_user = request.user
    product = Product.objects.get(id=product_id)

    url = request.META.get('HTTP_REFERER')
    if current_user.is_authenticated and not LikeProduct.objects.filter(user=current_user, product=product).exists():
        like_product = LikeProduct.objects.create(
            user=current_user,
            product=product,
        )
        like_product.save()
    try:
        return redirect(url)
    except:
        return redirect('home')


@login_required(login_url='login')
def remove_like(request, product_id):
    current_user = request.user
    product = Product.objects.get(id=product_id)

    url = request.META.get('HTTP_REFERER')
    if current_user.is_authenticated and LikeProduct.objects.filter(user=current_user, product=product).exists():
        like_product = LikeProduct.objects.get(
            user=current_user, product=product)
        like_product.delete()
    try:
        return redirect(url)
    except:
        return redirect('home')
