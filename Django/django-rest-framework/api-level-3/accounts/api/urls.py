from django.urls import include, path
from . import views
from rest_framework.routers import DefaultRouter

router = DefaultRouter()
router.register(r'profiles', views.ProfileViewSet)
router.register(r'status', views.ProfileStatusViewSet, basename='status')

urlpatterns = [
    path('', include(router.urls)),
    path('avatar/', views.AvatarUpdateView.as_view(), name='avatar-update')
]
