from django.db import models

# Create your models here.
class Chef(models.Model):
    first_name = models.CharField(max_length=50)
    last_name = models.CharField(max_length=50)

    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    def get_full_name(self):
        return f'{self.first_name} {self.last_name}'


class Food(models.Model):
    author = models.ForeignKey(Chef, on_delete=models.CASCADE)
    food_name = models.CharField(max_length=50)
    food_image = models.ImageField(upload_to='media/foods/', max_length=255)
    description = models.TextField(max_length=200, blank=True, null=True)
    slug = models.SlugField(blank=True, null=True)

    active = models.BooleanField(default=False)

    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)


class FoodRecipe(models.Model):
    food = models.ForeignKey(Food, on_delete=models.CASCADE)
    order = models.PositiveSmallIntegerField()
    description = models.TextField(max_length=300)

    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)


