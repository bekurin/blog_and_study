from .models import ReviewRating, Brand


def all_review(request):
    reviews = ReviewRating.objects.all().order_by('-created_at')[:10]
    return dict(reviews=reviews)


def all_brand(request):
    brands = Brand.objects.all()
    return dict(brands=brands)
