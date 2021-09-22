## tf model on django



apps.py example

```python
from django.apps import AppConfig
import tensorflow as tf
import numpy as np
import pickle
from PIL import Image
from tensorflow.keras.applications.inception_v3 import InceptionV3
from tensorflow.keras.models import Model
from tensorflow.keras.preprocessing.sequence import pad_sequences


WIDTH = 299
HEIGHT = 299
OUTPUT_DIM = 2048
START = "startseq"
STOP = "endseq"
max_length = 34


class IcConfig(AppConfig):
    default_auto_field = 'django.db.models.BigAutoField'
    name = 'ic'


class DeepConfig(AppConfig):

    def __init__(self):
        self.preprocess_input = tf.keras.applications.inception_v3.preprocess_input
        self.encode_model = InceptionV3(weights='imagenet')
        self.encode_model = Model(self.encode_model.input, self.encode_model.layers[-2].output)
        with open('ic\deep\idxtoword1652.json', 'rb') as fp:
            self.idxtoword = pickle.load(fp)
        with open('ic\deep\wordtoidx1652.json', 'rb') as fp:
            self.wordtoidx = pickle.load(fp)
        self.caption_model = tf.keras.models.load_model('ic\deep\image-captioning-model.h5')
    
    def newEncodeImage(self, img):
        img = img.resize((WIDTH, HEIGHT), Image.ANTIALIAS)
        x = tf.keras.preprocessing.image.img_to_array(img)
        x = np.expand_dims(x, axis=0)
        x = self.preprocess_input(x)
        x = self.encode_model.predict(x) 

        return x

    def newGenerateCaption(self, photo):
        in_text = START
        for i in range(max_length):
            sequence = [self.wordtoidx[w] for w in in_text.split() if w in self.wordtoidx]
            sequence = pad_sequences([sequence], maxlen=max_length)
            yhat = self.caption_model.predict([photo,sequence], verbose=0)
            yhat = np.argmax(yhat)
            word = self.idxtoword[yhat]
            in_text += ' ' + word
            if word == STOP:
                break
        final = in_text.split()
        final = final[1:-1]
        final = ' '.join(final)
        return final



```



views.py example

```python
from django.shortcuts import render
from django.http import HttpResponse
from .apps import DeepConfig

from PIL import Image


def index(request):
    deepconf = DeepConfig()

    # 여길 나중에 파일 request로 받아 오는걸로 바꾸자
    # if request.method == 'POST':
    # img = request.FILES['img']
    path = 'ic\deep\img2.jpg'
    img = Image.open(path)
    img.load()

    encod_img = deepconf.newEncodeImage(img)
    caption = deepconf.newGenerateCaption(encod_img)

    return HttpResponse(caption)



```

