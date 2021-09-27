from django.shortcuts import render
from .models import Result, ResultInfo

# Create your views here.
def result(request):
    if request.method == "GET":
        type = request.GET['type']
        result = Result.objects.get(name=type)
        results = Result.objects.all()
        result_info = ResultInfo.objects.get(result__name=type)

        total_count = 0
        for item in results:
            total_count += item.count

        result.count += 1
        result.save()

        percentage = result.count / total_count * 100

        context = {
            'result_info': result_info,
            'percentage': round(percentage, 2),
        }
    return render(request, 'result.html', context)


def result_set(request):
    result_info = ResultInfo.objects.all()

    context = {
        'result_info': result_info,
    }
    return render(request, 'result_set.html', context)