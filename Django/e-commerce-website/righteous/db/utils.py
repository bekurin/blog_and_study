import os
from django.utils.text import slugify
import shortuuid


def get_directory_path(instance, filename):
    ext = filename.split('.')[-1]
    if instance:
        brand = None
        uuid = shortuuid.uuid(name=instance.slug)
        try:
            brand = instance.brand
        except:
            pass

        if brand:
            upload_to = f'{brand.slug}/{instance.slug}'
        else:
            upload_to = f'{instance.slug}'
        print(uuid)
        filename = '{}.{}'.format(uuid, ext)
    else:
        uuid = shortuuid.ShortUUID().random(length=10)
        upload_to = ''
        filename = '{}.{}'.format(uuid, ext)

    return os.path.join(upload_to, filename)


def get_random_uuid(size=6):
    uuid = shortuuid.ShortUUID().random(length=size)
    return uuid


def get_unique_slug(instance, new_slug=None, size=6, max_size=30):
    name = instance.eng_name

    if new_slug is None:
        slug = slugify(name)
    else:
        slug = new_slug

    slug = slug[:max_size]
    Klass = instance.__class__
    parent = None

    try:
        parent = instance.parent
    except:
        pass

    if parent is not None:
        qs = Klass.objects.filter(parent=parent, slug=slug)
    else:
        qs = Klass.objects.filter(slug=slug)

    if qs.exists():
        new_slug = slugify(name) + get_random_uuid(name=size)
        return get_unique_slug(instance, new_slug=new_slug)
    return slug
