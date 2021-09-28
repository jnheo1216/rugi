from django.urls import path

from . import views

app_name = 'image_caption'
urlpatterns = [
    path('', views.index, name='index'),
    path('kr/', views.index_kr, name='index_kr'),
]
