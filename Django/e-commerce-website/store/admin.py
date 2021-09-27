from django.contrib import admin

from .models import (Brand, NewProductProxy, PopularProductProxy, Product,
                     RecommendProductProxy, Variation, ReviewRating)

# Register your models here.


class ProductAdmin(admin.ModelAdmin):
    list_display = ('product_name', 'eng_name', 'slug', 'price', 'stock',
                    'product_status', 'is_available', 'created_at', 'updated_at')
    prepopulated_fields = {'slug': ('eng_name',)}


class BrandAdmin(admin.ModelAdmin):
    list_display = ('brand_name', 'eng_name', 'slug',
                    'is_available', 'created_at', 'updated_at')
    prepopulated_fields = {'slug': ('eng_name',)}


class VariationAdmin(admin.ModelAdmin):
    list_display = ('product', 'variation_category',
                    'variation_value', 'is_active')
    list_editable = ('is_active',)
    list_filter = ('product', 'variation_category', 'variation_value')


admin.site.register(ReviewRating)
admin.site.register(Product, ProductAdmin)
admin.site.register(PopularProductProxy, ProductAdmin)
admin.site.register(NewProductProxy, ProductAdmin)
admin.site.register(RecommendProductProxy, ProductAdmin)
admin.site.register(Brand, BrandAdmin)
admin.site.register(Variation, VariationAdmin)
