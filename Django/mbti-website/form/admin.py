from django.contrib import admin
from .models import Qusetion, Choice

# Register your models here.

class QusetionAdmin(admin.ModelAdmin):
    list_display = ('number', 'content')

class ChoiceAdmin(admin.ModelAdmin):
    list_display = ('question', 'content', 'score')

admin.site.register(Qusetion, QusetionAdmin)
admin.site.register(Choice, ChoiceAdmin)