# from django.shortcuts import render
from .apps import DeepConfig
from .deep.icconfig import evaluate
from rest_framework import status
from rest_framework.response import Response
from rest_framework.decorators import api_view

import urllib.request

# import requests
# from io import BytesIO
# from PIL import Image
from tts.views import tts

# import tensorflow as tf
import json


# Create your views here.
@api_view(["POST"])
def index(request):
    path = request.data["image_path"]
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
    print("Prediction Caption:", " ".join(result))

    caption = " ".join(result)

    # response = requests.get(path)
    # img = Image.open(BytesIO(response.content))
    # img.load()
    # cvtimg = img.convert('RGB')
    # # cvtimg = tf.image.resize(cvtimg, (224, 224))
    # encod_img = ic.newEncodeImage(cvtimg).reshape((1,1280))
    # caption = ic.newGenerateCaption(encod_img)

    data = {"caption": caption}
    tts(caption)

    return Response(data, status=status.HTTP_200_OK)


@api_view(["POST"])
def index_kr(request):
    path = request.data["image_path"]

    ic = DeepConfig()
    max_length = ic.max_length
    tokenizer = ic.tokenizer
    new_encoder = ic.new_encoder
    new_decoder = ic.new_decoder

    result, _ = evaluate(path, max_length, 64, new_decoder, new_encoder, tokenizer)
    print("Prediction Caption:", " ".join(result))

    caption = " ".join(result)

    # response = requests.get(path)
    # img = Image.open(BytesIO(response.content))
    # img.load()
    # cvtimg = img.convert('RGB')
    # encod_img = ic.newEncodeImage(cvtimg)
    # caption = ic.newGenerateCaption(encod_img)

    client_id = "b3HISCq2mJu0enV0Wvog"  # 개발자센터에서 발급받은 Client ID 값
    client_secret = "lRHpaTXbnv"  # 개발자센터에서 발급받은 Client Secret 값
    encText = urllib.parse.quote(caption)
    data = "source=en&target=ko&text=" + encText
    url = "https://openapi.naver.com/v1/papago/n2mt"
    request_trans = urllib.request.Request(url)
    request_trans.add_header("X-Naver-Client-Id", client_id)
    request_trans.add_header("X-Naver-Client-Secret", client_secret)
    response = urllib.request.urlopen(request_trans, data=data.encode("utf-8"))
    rescode = response.getcode()
    if rescode == 200:
        print("1")
        response_body = response.read()
        print("2")
        caption = response_body.decode("utf-8")
        print("3")
        captionjson = json.loads(caption)
        print("4")
        captionjson = captionjson["message"]["result"]["translatedText"]
        print("5")
        audio = tts(caption)
        print("6")
        data = {"caption": captionjson, "audio": audio}
        print("7")
        return Response(data, status=status.HTTP_200_OK)
    else:
        print("8")
        data = {"caption": "do not work"}
        print("9")
        return Response(data, status=status.HTTP_200_OK)
