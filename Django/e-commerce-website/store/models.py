from django.db import models
from righteous.db.utils import get_directory_path
from righteous.db.models import ProductTypeChoices, VariationCategoryChoices
from django.db.models import Avg, Count
from category.models import Category, Subcategory
from accounts.models import Account
from django.urls import reverse

# Create your models here.


class Brand(models.Model):
    brand_name = models.CharField(max_length=100, unique=True)
    eng_name = models.CharField(max_length=100, unique=True)
    slug = models.SlugField(max_length=100, unique=True, blank=True)
    description = models.TextField()
    image = models.ImageField(
        upload_to=get_directory_path, max_length=255, blank=True, null=True)
    is_available = models.BooleanField(default=False)

    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    def __str__(self):
        return self.brand_name

    def get_url(self):
        return reverse('brand_detail', args=[self.id])


class ProductManager(models.Manager):
    def get_queryset(self):
        return super().get_queryset()

    def available(self, **kwargs):
        return self.filter(is_available=True, **kwargs)


class Product(models.Model):
    product_name = models.CharField(max_length=200, unique=True)
    eng_name = models.CharField(max_length=200, unique=True)
    slug = models.SlugField(max_length=200, unique=True, blank=True)

    description = models.TextField(max_length=300, blank=True)
    price = models.PositiveIntegerField()
    stock = models.IntegerField()
    images = models.ImageField(upload_to=get_directory_path, max_length=255)
    sale = models.PositiveSmallIntegerField(default=0)
    delivery_period = models.PositiveSmallIntegerField()
    delivery_cost = models.PositiveSmallIntegerField(default=0)

    is_available = models.BooleanField(default=False)
    product_status = models.CharField(
        max_length=30, choices=ProductTypeChoices.choices, default=ProductTypeChoices.NEW)

    brand = models.ForeignKey(Brand, on_delete=models.CASCADE)
    category = models.ForeignKey(Category, on_delete=models.CASCADE)
    subcategory = models.ForeignKey(Subcategory, on_delete=models.CASCADE)

    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    def __str__(self):
        return self.product_name

    def save(self, *args, **kwargs):
        self.category = self.subcategory.category
        super().save(*args, **kwargs)

    def get_url(self):
        return reverse('product_detail', args=[self.category.id, self.subcategory.id, self.slug])

    def get_sale_price(self):
        return int(self.price * (1-(self.sale / 100)))

    def get_likes_count(self):
        return self.likeproduct_set.all().count()

    def get_order_count(self):
        return self.orderproduct_set.filter(product=self).count()

    def get_average_review(self):
        reviews = ReviewRating.objects.filter(
            product=self, status=True).aggregate(average=Avg('rating'))
        avg = 0
        if reviews is not None:
            avg = reviews['average']
        return 0 if avg is None else avg

    def get_review_count(self):
        reviews = ReviewRating.objects.filter(
            product=self, status=True).aggregate(count=Count('id'))
        count = 0
        if reviews is not None:
            count = reviews['count']
        return count


class VariationManager(models.Manager):
    def colors(self):
        return super(VariationManager, self).filter(variation_category=VariationCategoryChoices.COLOR, is_active=True)

    def sizes(self):
        return super(VariationManager, self).filter(variation_category=VariationCategoryChoices.SIZE, is_active=True)


class Variation(models.Model):
    product = models.ForeignKey(Product, on_delete=models.CASCADE)
    variation_category = models.CharField(
        max_length=50, choices=VariationCategoryChoices.choices)
    variation_value = models.CharField(max_length=100)
    is_active = models.BooleanField(default=True)

    created_at = models.DateTimeField(auto_now=True)

    objects = VariationManager()

    def __str__(self):
        return self.variation_value


class PopularProductManager(ProductManager):
    def get_queryset(self):
        return super().get_queryset().filter(product_status=Product.ProductTypeChoices.POPULAR)


class PopularProductProxy(Product):
    objects = PopularProductManager()

    class Meta:
        verbose_name = 'Popular Product'
        verbose_name_plural = 'Popular Products'
        proxy = True

    def save(self, *args, **kwargs):
        self.product_status = Product.ProductTypeChoices.POPULAR
        super().save(*args, **kwargs)


class NewProductManager(ProductManager):
    def get_queryset(self):
        return super().get_queryset().filter(product_status=Product.ProductTypeChoices.NEW)


class NewProductProxy(Product):
    objects = NewProductManager()

    class Meta:
        verbose_name = 'New Product'
        verbose_name_plural = 'New Products'
        proxy = True

    def save(self, *args, **kwargs):
        self.product_status = Product.ProductTypeChoices.NEW
        super().save(*args, **kwargs)


class RecommendManager(ProductManager):
    def get_queryset(self):
        return super().get_queryset().filter(product_status=Product.ProductTypeChoices.RECOMMEND)


class RecommendProductProxy(Product):
    objects = RecommendManager()

    class Meata:
        verbose_name = 'Recommend Product'
        verbose_name_plural = 'Recommend Products'
        proxy = True

    def save(self, *args, **kwargs):
        self.product_status = Product.ProductTypeChoices.RECOMMEND
        super().save(*args, **kwargs)


class SaleManager(ProductManager):
    def get_queryset(self):
        return super().get_queryset().filter(product_status=Product.ProductTypeChoices.SALE)


class SaleProductProxy(Product):
    objects = SaleManager()

    class Meata:
        verbose_name = 'Sale Product'
        verbose_name_plural = 'Sale Products'
        proxy = True

    def save(self, *args, **kwargs):
        self.product_status = Product.ProductTypeChoices.SALE
        super().save(*args, **kwargs)


class ReviewRating(models.Model):
    product = models.ForeignKey(Product, on_delete=models.CASCADE)
    user = models.ForeignKey(Account, on_delete=models.CASCADE)
    subject = models.CharField(max_length=100, blank=True)
    rating = models.FloatField()
    ip = models.CharField(max_length=20, blank=True)
    status = models.BooleanField(default=True)

    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    def __str__(self):
        return self.subject
