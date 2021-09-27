from django.db import models

# Create your models here.
class Journalist(models.Model):
    EMOTION = (
        ('New', 'new'),
        ('Happy', 'happy'),
        ('Sad', 'sad'),
        ('Calm', 'calm'),
        ('Nervous', 'nervous'),
        ('Etc', 'etc')
    )

    first_name = models.CharField(max_length=60)
    last_name = models.CharField(max_length=60)
    emotion = models.CharField(max_length=10, choices=EMOTION, default='New')


class Article(models.Model):
    author = models.ForeignKey(Journalist, on_delete=models.CASCADE)
    title = models.CharField(max_length=120)
    description = models.CharField(max_length=120)
    body = models.TextField(max_length=500)
    location = models.CharField(max_length=50)
    publication_date = models.DateField()

    active = models.BooleanField(default=False)

    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    def __str__(self):
        return f'{self.author} {self.title}'