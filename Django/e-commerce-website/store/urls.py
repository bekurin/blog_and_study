from django.urls import path
from . import views

urlpatterns = [
    path('category/<int:category_slug>/',
         views.store, name='products_by_category'),
    path('category/<int:category_slug>/<int:subcategory_slug>/',
         views.store, name='products_by_subcategory'),
    path('category/<int:category_slug>/<int:subcategory_slug>/<slug:product_slug>/',
         views.product_detail, name='product_detail'),

    path('search/', views.search, name='search'),

    path('submit_review/<int:product_id>/',
         views.submit_review, name='submit_review'),

    path('brand_detail/<int:brand_id>/',
         views.brand_detail, name='brand_detail'),
]
