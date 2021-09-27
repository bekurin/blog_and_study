from .models import LikeProduct


def likes(request):
    like_count = 0
    products = []
    if 'admin' in request.path:
        return {}
    else:
        try:
            if request.user.is_authenticated:
                like_items = LikeProduct.objects.filter(user=request.user)
                for like_item in like_items:
                    products.append(like_item.product)
                like_count = like_items.count()
        except LikeProduct.DoesNotExist:
            like_count = 0
            products = []
    return dict(like_count=like_count, like_items=products)
