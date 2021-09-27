from django.test import TestCase

# Create your tests here.
class ProductTestCase(TestCase):
    pass


class BrandTestCase(TestCase):
    def create_brand(self):
        brand_name = '테스트'
        eng_name = 'test'
        slug = 'test'
        description = '테스트입니다.'