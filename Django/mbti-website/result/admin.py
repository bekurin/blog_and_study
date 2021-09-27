from django.contrib import admin
from .models import Result, ResultInfo

# Register your models here.

class ResultAdmin(admin.ModelAdmin):
    list_display = ('number', 'name', 'count')

class ResultInfoAdmin(admin.ModelAdmin):
    list_display = ('sub_title', 'title', 'description_1', 'description_2', 'result_image')

admin.site.register(Result, ResultAdmin)
admin.site.register(ResultInfo, ResultInfoAdmin)