# from django.shortcuts import render
from .apps import DeepConfig
from .deep.icconfig import evaluate
from rest_framework import status
from rest_framework.response import Response
from rest_framework.decorators import api_view



# Create your views here.
@api_view(['POST'])
def index(request):
    path = request.data['image_path']
    # print(image_path)

    ic = DeepConfig()
    max_length = ic.max_length
    tokenizer = ic.tokenizer
    new_encoder = ic.new_encoder
    new_decoder = ic.new_decoder

    # path = 'image_caption\deep\img2.jpg'
    # img = Image.open(image_path)
    # img.load()

    result, _ = evaluate(path, max_length, 64, new_decoder, new_encoder, tokenizer)
    print('Prediction Caption:', ' '.join(result))

    caption = ' '.join(result)

    data = {
        'caption': caption
    }

    return Response(data, status=status.HTTP_200_OK)
