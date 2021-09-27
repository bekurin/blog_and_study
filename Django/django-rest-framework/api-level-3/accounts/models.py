from django.db import models
from django.contrib.auth.models import User

# Create your models here.
class Profile(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE)
    avatar = models.ImageField(upload_to='media/profiles', null=True, blank=True)
    age = models.PositiveSmallIntegerField(null=True, blank=True)

    def __str__(self):
        return self.user.username


class ProfileStatus(models.Model):
    STATUS = (
        ('Update', 'update'),
        ('Create', 'create'),
        ('Delete', 'delete'),
        ('Read', 'read'),
    )
    
    user_profile = models.ForeignKey(Profile, on_delete=models.CASCADE)
    status = models.CharField(max_length=10, choices=STATUS)

    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    class Meta:
        verbose_name_plural = 'statuses'\
    
    def __str__(self):
        return str(self.user_profile)