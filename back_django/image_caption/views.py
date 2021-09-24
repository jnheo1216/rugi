from django.shortcuts import render
from django.http import HttpResponse
from .apps import DeepConfig
from PIL import Image



# Create your views here.
def index(request):
    deepconf = DeepConfig()

    path = 'image_caption\deep\img2.jpg'
    img = Image.open(path)
    img.load()
    
    encod_img = deepconf.newEncodeImage(img)
    caption = deepconf.newGenerateCaption(encod_img)


    return HttpResponse(caption)
