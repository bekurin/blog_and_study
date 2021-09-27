from django.db import models
from result.models import Result

# Create your models here.
class Qusetion(models.Model):
    number = models.IntegerField(unique=True)
    content = models.CharField(max_length=150)

    def __str__(self):
        return f'{self.pk}번 문제'

class Choice(models.Model):
    content = models.CharField(max_length=100)
    question = models.ForeignKey(Qusetion, on_delete=models.CASCADE)
    score = models.IntegerField(default=0)

    def __str__(self):
        return f'{self.question} | {self.content}'