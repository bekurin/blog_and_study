from django.http.response import HttpResponse
from rest_framework import status, generics
from rest_framework.response import Response
from rest_framework.generics import get_object_or_404

from recipes.models import Food, FoodRecipe, Chef
from recipes.api.serializers import FoodSerializer, FoodRecipeSerializer, ChefSerializer
from recipes.api.permissions import IsAdminUserReadOnly, IsRecipeAuthorOrReadOnly


class FoodListCreateAPIView(generics.ListCreateAPIView):
    queryset = Food.objects.all().order_by('-created_at')
    serializer_class = FoodSerializer
    permission_classes = [IsAdminUserReadOnly]


class FoodDetailAPIView(generics.RetrieveUpdateDestroyAPIView):
    queryset = Food.objects.all()
    serializer_class = FoodSerializer
    permission_classes = [IsAdminUserReadOnly]

class FoodRecipeCreateAPIView(generics.ListCreateAPIView):
    queryset = FoodRecipe.objects.all().order_by('order')
    serializer_class = FoodRecipeSerializer
    permission_classes = [IsRecipeAuthorOrReadOnly]

    def get(self, request, *args, **kwargs):
        food_pk = self.kwargs.get('food_pk')
        if food_pk:
            queryset = FoodRecipe.objects.filter(food__pk=food_pk).order_by('order')
            serializer = FoodRecipeSerializer(queryset, many=True)
            return Response(serializer.data)
        return super().get(request, *args, **kwargs)


class FoodRecipeDatailAPIView(generics.RetrieveUpdateDestroyAPIView):
    queryset = FoodRecipe.objects.all()
    serializer_class = FoodRecipeSerializer
    permission_classes = [IsRecipeAuthorOrReadOnly]


class ChefCreateListAPIView(generics.ListCreateAPIView):
    queryset = Chef.objects.all().order_by('-updated_at')
    serializer_class = ChefSerializer
    permission_classes = [IsAdminUserReadOnly]


class ChefDetailAPIView(generics.RetrieveUpdateDestroyAPIView):
    queryset = Chef.objects.all()
    serializer_class = ChefSerializer
    permission_classes = [IsAdminUserReadOnly]