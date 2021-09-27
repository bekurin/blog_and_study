from rest_framework import filters
from rest_framework.views import set_rollback
from accounts.models import Profile, ProfileStatus
from rest_framework import generics, mixins, viewsets
from rest_framework.filters import SearchFilter
from rest_framework.permissions import IsAuthenticated
from rest_framework.viewsets import ModelViewSet

from .permisstions import IsOwnerOrReadOnly, IsOwnProfileOrReadOnly
from .serializers import (ProfileAvatarSerializer, ProfileSerializer,
                          ProfileStatusSerializer)


class AvatarUpdateView(generics.UpdateAPIView):
    serializer_class = ProfileAvatarSerializer
    permission_classes = [IsAuthenticated]

    def get_object(self):
        profile_object = self.request.user_profile
        return profile_object


class ProfileViewSet(mixins.UpdateModelMixin, mixins.ListModelMixin, mixins.RetrieveModelMixin, viewsets.GenericViewSet):
    queryset = Profile.objects.all()
    serializer_class = ProfileSerializer
    permission_classes = [IsAuthenticated, IsOwnProfileOrReadOnly]
    filter_backends = [SearchFilter]
    search_fields = ['user.username']


class ProfileStatusViewSet(ModelViewSet):
    serializer_class = ProfileStatusSerializer
    permission_classes = [IsAuthenticated, IsOwnerOrReadOnly]

    def get_queryset(self):
        queryset = ProfileStatus.objects.all()
        username = self.request.query_params.get('username', None)

        if username is not None:
            queryset = queryset.filter(user_profile__user__username=username)
        return queryset

    def perform_create(self, serializer):
        user_profile = self.request.user.profile
        serializer.save(user_profile=user_profile)