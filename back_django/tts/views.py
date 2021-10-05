import os
from rest_framework import response

from rest_framework.decorators import api_view
from gtts import gTTS
import requests
from rest_framework import status
from rest_framework.response import Response

import boto3
from datetime import datetime

ACCESS_KEY_ID = os.environ.get("ACCESS_KEY_ID")
ACCESS_SECRET_KEY = os.environ.get("ACCESS_SECRET_KEY")
BUCKET_NAME = os.environ.get("BUCKET_NAME")

# @api_view(['POST'])
def tts(caption):

    print("@ caption : ", caption)
    tts = gTTS(text=caption, lang="ko", slow=False)
    makedirs("audio/")
    print("5 1")
    file_name = f"audio/{get_time()}.mp3"
    print("5 2")
    tts.save(file_name)
    print("5 3")
    handle_upload_mp3(file_name)
    print("5 4")
    # response = play(file_name)
    print("5 5")
    return file_name


# S3 업로드
def handle_upload_mp3(f):
    print("5 3 1")
    print("ACCESS_KEY_ID : ", ACCESS_KEY_ID)
    print("ACCESS_SECRET_KEY : ", ACCESS_SECRET_KEY)
    s3_client = boto3.client(
        "s3", aws_access_key_id=ACCESS_KEY_ID, aws_secret_access_key=ACCESS_SECRET_KEY
    )
    print("5 3 3")
    response = s3_client.upload_file(f, BUCKET_NAME, f)
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
