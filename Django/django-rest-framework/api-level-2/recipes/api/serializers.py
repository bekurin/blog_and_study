from rest_framework import serializers
from recipes.models import FoodRecipe, Food, Chef

class FoodRecipeSerializer(serializers.ModelSerializer):
    
    class Meta:
        model = FoodRecipe
        fields = '__all__'


class FoodSerializer(serializers.ModelSerializer):

    foodrecipe_set = serializers.HyperlinkedRelatedField(many=True, read_only=True, view_name='food-recipe-detail') 

    class Meta:
        model = Food
        fields = '__all__'


class ChefSerializer(serializers.ModelSerializer):

    food_set = serializers.HyperlinkedRelatedField(many=True, read_only=True, view_name='food-detail') 

    class Meta:
        model = Chef
        fields = '__all__'