from django.urls import path
from . import views

urlpatterns = [
    path('foods/', views.FoodListCreateAPIView.as_view(), name='food-list'),
    path('foods/<int:pk>/', views.FoodDetailAPIView.as_view(), name='food-detail'),
    path('food_recipe/', views.FoodRecipeCreateAPIView.as_view(), name='food-recipe-list'),
    path('food_recipe/<int:pk>/', views.FoodRecipeDatailAPIView.as_view(), name='food-recipe-detail'),
    path('food_recipe/<int:food_pk>/recipe/', views.FoodRecipeCreateAPIView.as_view(), name='recipe-list'),
    path('chef/', views.ChefCreateListAPIView.as_view(), name='chef-list'),
    path('chef/<int:pk>/', views.ChefDetailAPIView.as_view(), name='chef-detail'),
]