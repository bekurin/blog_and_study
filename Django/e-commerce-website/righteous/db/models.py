from django.db import models


class ProductTypeChoices(models.TextChoices):
    POPULAR = 'popular',
    NEW = 'new',
    RECOMMEND = 'recommend',
    SALE = 'sale'


class VariationCategoryChoices(models.TextChoices):
    COLOR = 'color',
    SIZE = 'size'


class OrderStatusChoices(models.TextChoices):
    NEW = 'new',
    ACCEPTED = 'accepted',
    COMPLETED = 'completed',
    CANCELLED = 'cancelled'
