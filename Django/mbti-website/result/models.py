from django.db import models

# Create your models here.
class Result(models.Model):
    number = models.IntegerField(unique=True)
    name = models.CharField(max_length=50)
    count = models.IntegerField()

    def __str__(self):
        return self.name

class ResultInfo(models.Model):
    sub_title = models.CharField(max_length=100)
    title = models.CharField(max_length=100)
    description_1 = models.CharField(max_length=200)
    description_2 = models.CharField(max_length=100)
    result_image = models.ImageField(upload_to='reuslt')

    # 전체의 몇 퍼센트인지 계산하기 위한 코드
    result = models.ForeignKey(Result, on_delete=models.CASCADE)

    def __str__(self):
        return self.title