from django.shortcuts import render
from .apps import Synthesizer
# from .apps import trans_file_name
from rest_framework import status
from rest_framework.response import Response
from rest_framework.decorators import api_view
import urllib.request
import requests

import argparse
from .ttskss.utils import makedirs
from .ttskss.utils.audio import save_wav, inv_linear_spectrogram, inv_preemphasis, inv_spectrogram_tensorflow

import os
import boto3

ACCESS_KEY_ID = os.environ.get("ACCESS_KEY_ID")
ACCESS_SECRET_KEY = os.environ.get("ACCESS_SECRET_KEY")
BUCKET_NAME = os.environ.get("BUCKET_NAME")

# Create your views here.
@api_view(['POST'])
def tts(text):
    text = text.data['text'] # key text
    print("@ text : ", text)

    makedirs('tts/ttskss/logdir-tacotron/generate')
    
    synthesizer = Synthesizer()
    synthesizer.load('tts/ttskss/logdir-tacotron/kss+moon_2021-09-28_10-49-34', 2, None)
    synthesizer.synthesize(texts=[text],base_path="tts/ttskss/logdir-tacotron/generate",speaker_ids=[0],
                                   attention_trim=True,base_alignment_path=None,isKorean=True)[0]
    # print("@ 파일패쓰 @!", trans_file_name())
    # file_name = trans_file_name()
    # handle_upload_mp3(f'audio/{file_name}')
    return Response(status=status.HTTP_200_OK)

def handle_upload_mp3(f):
    s3_client = boto3.client('s3',
                            aws_access_key_id=ACCESS_KEY_ID,
                            aws_secret_access_key=ACCESS_SECRET_KEY)
    response = s3_client.upload_file(
        f, BUCKET_NAME, f)