from django.shortcuts import render
from result.models import Result

def home(request):
    results = Result.objects.all()

    total_count = 0
    for item in results:
        total_count += item.count
    
    context = {
        'total_count': total_count,
    }
    return render(request, 'home.html', context)