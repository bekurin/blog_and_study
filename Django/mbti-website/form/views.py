from django.shortcuts import render, redirect
from .models import Qusetion, Choice

# Create your views here.
def form(request):
    questions = Qusetion.objects.all()
    choices = Choice.objects.all()

    total_count = questions.count()
    context = {
        'questions': questions,
        'choices': choices,
        'total_count': total_count,
    }
    return render(request, 'form.html', context)


def form_check(request, total_count):
    if request.method == 'POST':
        answers = []
        for i in range(1, total_count+1):
            key = f'answer_{i}'
            value = request.POST[key]
            answers.append(value)
        type = result_check(answers)
    return redirect(f'/result?type={type}')



def result_check(answers):
    type = ""
    step, type_sum = 0, 0
    for i in range(len(answers)):
        if ((i % 3 == 0 and i != 0) or i == len(answers)):
            type += discriminate_type(step, type_sum)
            type_sum = 0
            step += 1
        type_sum += int(answers[i])
    type += discriminate_type(step, type_sum)
    return type
            
            


def discriminate_type(step, type_sum):
    positive_type = ["I", "S", "T", "J"]
    negative_type = ["E", "N", "F", "P"]
    if type_sum > 0:
        return positive_type[step]
    else:
        return negative_type[step]