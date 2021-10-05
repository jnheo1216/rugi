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
    tts = gTTS(
        text=caption,
        lang="ko", slow=False
    )
    file_name = f'audio/{get_time()}.mp3'

    tts.save(file_name)
    handle_upload_mp3(file_name)
    # response = play(file_name)

    return file_name

# S3 업로드
def handle_upload_mp3(f):
    s3_client = boto3.client('s3',
                            aws_access_key_id=ACCESS_KEY_ID,
                            aws_secret_access_key=ACCESS_SECRET_KEY)
    response = s3_client.upload_file(
        f, BUCKET_NAME, f)

def get_time():
    return datetime.now().strftime("%Y-%m-%d_%H-%M-%S")

# # 파일 리턴
# def play(file_name):
#     with open(f'{file_name}', 'rb') as f:
#         response = Response(f.read(), status=206)
#         response['content_type'] = 'audio/wav'
#         print("@ response done")
#         return response
