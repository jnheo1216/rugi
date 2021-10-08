from django.shortcuts import render
from .apps import Synthesizer, plot_graph_and_save_audio
from .apps import trans_file_name
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

    makedirs('audio/')
    
    synthesizer = Synthesizer()
    synthesizer.load('tts/ttskss/logdir-tacotron/kss+moon_2021-09-28_10-49-34', 2, None)
    file_name = synthesizer.synthesize(texts=[text],base_path="audio/",speaker_ids=[0],
                                   attention_trim=True,base_alignment_path=None,isKorean=True)[0]
    handle_upload_mp3(f'audio/{file_name}.wav')
    return Response(status=status.HTTP_200_OK)

def handle_upload_mp3(f):
    s3_client = boto3.client('s3',
                            aws_access_key_id=ACCESS_KEY_ID,
                            aws_secret_access_key=ACCESS_SECRET_KEY)
    response = s3_client.upload_file(
        f, BUCKET_NAME, f)