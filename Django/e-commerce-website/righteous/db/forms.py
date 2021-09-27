from store.models import ReviewRating
from django import forms
from orders.models import Order
from accounts.models import Account, UserProfile


class RegistrationsForm(forms.ModelForm):
    password = forms.CharField(widget=forms.PasswordInput(attrs={
        'placeholder': '비밀번호를 입력해주세요.'
    }))

    confirm_password = forms.CharField(widget=forms.PasswordInput(attrs={
        'placeholder': '비밀번호를 입력해주세요.'
    }))

    class Meta:
        model = Account
        fields = ['first_name', 'last_name', 'username',
                  'phone_number', 'email', 'password']

    def __init__(self, *args, **kwargs):
        super(RegistrationsForm, self).__init__(*args, **kwargs)

        self.fields['first_name'].widget.attrs['placeholder'] = '이름을 입력해주세요.'
        self.fields['last_name'].widget.attrs['placeholder'] = '성을 입력해주세요.'
        self.fields['username'].widget.attrs['placeholder'] = '닉네임을 입력해주세요.'
        self.fields['phone_number'].widget.attrs['placeholder'] = '핸드폰 번호를 입력해주세요.'
        self.fields['email'].widget.attrs['placeholder'] = '이메일을 입력해주세요.'

        for field in self.fields:
            self.fields[field].widget.attrs['class'] = 'form-control'

    def clean(self):
        cleaned_data = super(RegistrationsForm, self).clean()
        password = cleaned_data.get('password')
        confirm_password = cleaned_data.get('confirm_password')

        if password != confirm_password:
            raise forms.ValidationError(
                '같은 비밀번호를 입력해주세요.'
            )


class OrderForm(forms.ModelForm):
    class Meta:
        model = Order
        fields = ['first_name', 'last_name', 'phone',
                  'address_line_1', 'address_line_2', 'order_note']


class ReviewForm(forms.ModelForm):
    class Meta:
        model = ReviewRating
        fields = ['subject', 'rating']


class UserProfileForm(forms.ModelForm):
    class Meta:
        model = UserProfile
        fields = ['address_line_1', 'address_line_2']
