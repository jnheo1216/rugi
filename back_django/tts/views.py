import os
from rest_framework import response

from rest_framework.decorators import api_view
from gtts import gTTS
import requests
from rest_framework import status
from rest_framework.response import Response
# import scipy.io.wavfile as swavfile
import boto3
# from image_caption.views import index_kr

ACCESS_KEY_ID = os.environ.get("ACCESS_KEY_ID")
ACCESS_SECRET_KEY = os.environ.get("ACCESS_SECRET_KEY")
BUCKET_NAME = os.environ.get("BUCKET_NAME")

# @api_view(['POST'])
def tts(caption):
    # print(index_kr)
    # caption = caption.data['caption']
    print("@caption: ",caption)

    tts = gTTS(
        text=caption,
        lang="ko", slow=False
    )
    save_path = f'audio/sample.mp3'
    # tts save OSError: [Errno 22] Invalid argument: 'audio/오늘 뭐먹지?.mp3'
    tts.save(save_path)
    handle_upload_mp3(save_path)
    # response = play(save_path)

    return Response(status=status.HTTP_200_OK)

# S3 업로드
def handle_upload_mp3(f):
    s3_client = boto3.client('s3',
                            aws_access_key_id=ACCESS_KEY_ID,
                            aws_secret_access_key=ACCESS_SECRET_KEY)
    response = s3_client.upload_file(
        f, BUCKET_NAME, f)

# # 파일 리턴
# def play(file_name):
#     with open(f'{file_name}', 'rb') as f:
#         response = Response(f.read(), status=206)
#         response['content_type'] = 'audio/wav'
#         print("@ response done")
#         return response
