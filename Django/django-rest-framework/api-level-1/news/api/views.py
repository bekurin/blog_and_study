from rest_framework import status
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework.generics import get_object_or_404

from news.models import Article, Journalist
from news.api.serializers import ArticleSerializer, JournalistSerializer


class ArticleListCreateAPIView(APIView):

    def get(self, request):
        articles = Article.objects.filter(active=True)
        serializer = ArticleSerializer(articles, many=True)
        return Response(serializer.data)

    def post(self, request):
        serializer = ArticleSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status.HTTP_201_CREATED)
        return Response(serializer.errors, status.HTTP_400_BAD_REQUEST)


class ArticleDetailAPIView(APIView):

    def get_object(self, pk):
        article = get_object_or_404(Article, pk=pk)
        return article

    def get(self, request, pk):
        article = self.get_object(pk)
        serializer = ArticleSerializer(article)
        return Response(serializer.data)

    def put(self, request, pk):
        article = self.get_object(pk)
        serializer = ArticleSerializer(article)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status.HTTP_201_CREATED)
        return Response(serializer.errors, status.HTTP_400_BAD_REQUEST)

    def delete(self, request, pk):
        article = self.get_object(pk)
        article.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)


class JournalistListCreateAPIView(APIView):

    def get(self, request):
        journalists = Journalist.objects.all()
        serializer = JournalistSerializer(journalists, many=True, context={'request':request})
        return Response(serializer.data)

    def post(self, request):
        serializer = JournalistSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status.HTTP_201_CREATED)
        return Response(serializer.errors, status.HTTP_400_BAD_REQUEST)


class JournalistDetailAPIView(APIView):

    def get_object(self, pk):
        journalist = get_object_or_404(Journalist, pk=pk)
        return journalist

    def get(self, request, pk):
        journalist = self.get_object(pk)
        serializer = JournalistSerializer(journalist, context={'request':request})
        return Response(serializer.data)

    def put(self, request, pk):
        journalist = self.get_object(pk)
        serializer = JournalistSerializer(journalist, data=request.data, context={'request':request})
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status.HTTP_201_CREATED)
        return Response(serializer.errors, status.HTTP_400_BAD_REQUEST)

    def delete(self, request, pk):
        journalist = self.get_object(pk)
        journalist.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)
        

