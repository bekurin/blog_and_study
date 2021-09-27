from django.urls import path
from . import views

urlpatterns = [
    path('', views.like, name='like'),
    path('add_like/<int:product_id>/', views.add_like, name='add_like'),
    path('remove_like/<int:product_id>/',
         views.remove_like, name='remove_like'),
]
