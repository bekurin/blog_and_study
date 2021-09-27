from django.shortcuts import render
from store.models import Brand, Product
from category.models import Category


def home(request):
    products = Product.objects.filter(
        is_available=True).order_by('-created_at')
    categories = Category.objects.filter(
        is_available=True).order_by('-created_at')
    brands = Brand.objects.filter(is_available=True).order_by('-created_at')

    context = {
        'products': products,
        'categories': categories,
        'brands': brands
    }
    return render(request, 'home.html', context)
