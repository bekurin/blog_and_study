from django.contrib import admin
from .models import LikeProduct

# Register your models here.


class LikeProductAdmin(admin.ModelAdmin):
    list_display = ('user', 'product', 'created_at', 'updated_at', 'is_active')


admin.site.register(LikeProduct, LikeProductAdmin)
