from django.db import models
from django.urls import reverse
from righteous.db.utils import get_random_uuid

# Create your models here.


class Category(models.Model):
    category_name = models.CharField(max_length=50, unique=True)
    description = models.TextField(max_length=300, blank=True)

    is_available = models.BooleanField(default=False)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    class Meta:
        verbose_name = 'category'
        verbose_name_plural = 'categories'

    def __str__(self):
        return self.category_name

    def get_url(self):
        return reverse('products_by_category', args=[self.pk])

    def get_subcategories(self):
        return self.subcategory_set.all()


class Subcategory(models.Model):
    subcategory_name = models.CharField(max_length=50, unique=True)
    category = models.ForeignKey(Category, on_delete=models.CASCADE)

    is_available = models.BooleanField(default=False)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    class Meta:
        verbose_name = 'subcategory'
        verbose_name_plural = 'subcategories'

    def __str__(self):
        return self.subcategory_name

    def get_url(self):
        return reverse('products_by_subcategory', args=[self.category.pk, self.pk])
