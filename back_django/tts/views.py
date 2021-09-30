# from .apps import Synthesize
from .forms import UploadFileForm
# from somewhere import handle_uploaded_file

from io import BytesIO
from rest_framework.decorators import api_view
from gtts import gTTS
import requests
from rest_framework import status
from rest_framework.response import Response
import scipy.io.wavfile as swavfile


# pip install gtts
@api_view(['POST'])
def tts(caption):
    caption = caption.data['caption']
    print("@@@@@@@",caption)

    form = UploadFileForm(caption.FILES)
    # response = requests.get(caption)
    tts = gTTS(
        text="안녕하세요",
        lang="ko", slow=False
    )
    # handle_uploaded_file(caption.FILES[tts])
    tts.save('sample.mp3')
    print("tts done")
    return Response(status=status.HTTP_200_OK)