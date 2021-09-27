from django.contrib import admin
from .models import Category, Subcategory

# Register your models here.
class CategoryAdmin(admin.ModelAdmin):
    list_display = ('category_name', 'is_available', 'created_at', 'updated_at')

admin.site.register(Category, CategoryAdmin)


class SubcategoryAdmin(admin.ModelAdmin):
    list_display = ('subcategory_name', 'category', 'is_available', 'created_at', 'updated_at')

admin.site.register(Subcategory, SubcategoryAdmin)
