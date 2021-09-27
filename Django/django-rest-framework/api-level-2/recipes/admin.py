from django.contrib import admin
from django.db import models
from .models import Chef, Food, FoodRecipe

# Register your models here.
class FoodRecipeInline(admin.TabularInline):
    model = FoodRecipe
    extra = 0
        
class FoodInline(admin.TabularInline):
    model = Food
    extra = 0

class FoodAdmin(admin.ModelAdmin):
    list_display = ('author', 'food_name', 'description', 'created_at', 'updated_at', 'active')
    prepopulated_fields = {'slug': ('food_name',)}
    inlines = [FoodRecipeInline]

class ChefAdmin(admin.ModelAdmin):
    list_display = ('get_full_name', 'created_at', 'updated_at')
    inlines = [FoodInline]


admin.site.register(Chef, ChefAdmin)
admin.site.register(Food, FoodAdmin)
admin.site.register(FoodRecipe)