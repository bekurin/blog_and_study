from rest_framework import serializers
from news.models import Article, Journalist


class ArticleSerializer(serializers.ModelSerializer):

    class Meta:
        model = Article
        exclude = ('id',)


class JournalistSerializer(serializers.ModelSerializer):

    article_set = serializers.HyperlinkedRelatedField(many=True, read_only=True, view_name='article-detail')

    class Meta:
        model = Journalist
        fields = '__all__'