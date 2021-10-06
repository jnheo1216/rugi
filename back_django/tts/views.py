import os
from rest_framework import response

from rest_framework.decorators import api_view
from gtts import gTTS
import requests
from rest_framework import status
from rest_framework.response import Response

import boto3
from datetime import datetime

ACCESS_KEY_ID = "AKIATD5L7CMI2SALOZHB"
ACCESS_SECRET_KEY = "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"
BUCKET_NAME = "ssafy-bucket"

# @api_view(['POST'])
def tts(caption):

    print("@ caption : ", caption)
    tts = gTTS(text=caption, lang="ko", slow=False)
    makedirs("audio/")
    print("5 1")
    file_name=get_time()
    file_dir = f"audio/{file_name}.mp3"
    print("5 2")
    tts.save(file_dir)
    print("5 3")
    handle_upload_mp3(file_dir)
    print("5 4")
    # response = play(file_dir)
    print("5 5")
    return file_dir


# S3 업로드
def handle_upload_mp3(file_dir):
    print("5 3 1")
    print("ACCESS_KEY_ID : ", ACCESS_KEY_ID)
    print("ACCESS_SECRET_KEY : ", ACCESS_SECRET_KEY)
    
    s3_client = boto3.client(
        "s3", aws_access_key_id=ACCESS_KEY_ID, aws_secret_access_key=ACCESS_SECRET_KEY
    )
    with open(file_dir, 'rb') as data:
        response = s3_client.upload_fileobj(data, BUCKET_NAME, file_dir)
    print("5 3 3 filename : " , file_dir)
    print("response : ", response)
    print("5 3 4")


def get_time():
    return datetime.now().strftime("%Y-%m-%d_%H-%M-%S")


# # 파일 리턴
# def play(file_name):
#     with open(f'{file_name}', 'rb') as f:
#         response = Response(f.read(), status=206)
#         response['content_type'] = 'audio/wav'
#         print("@ response done")
#         return response

# audio 디렉토리 생성
def makedirs(path):
    if not os.path.exists(path):
        os.makedirs(path)
