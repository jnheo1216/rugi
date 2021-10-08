from django.apps import AppConfig
import tensorflow as tf
import pickle
from .deep.icconfig import get_tokenize
# from tensorflow.keras.preprocessing.sequence import pad_sequences
# from PIL import Image
# import numpy as np


# START = "startseq"
# STOP = "endseq"
# WIDTH = 224
# HEIGHT = 224
# OUTPUT_DIM = 1280
# max_length = 36


class ImageCaptionConfig(AppConfig):
    default_auto_field = 'django.db.models.BigAutoField'
    name = 'image_caption'


class DeepConfig(AppConfig):
    def __init__(self):
        pass
    with open('image_caption/deep/train_captions10k_contour.pkl', 'rb') as f:
        train_captions = pickle.load(f)
    with open('image_caption/deep/img_name_vector10k_contour.pkl', 'rb') as f:
        img_name_vector = pickle.load(f)
    tokenizer, max_length, _, _, _, _ = get_tokenize(train_captions, img_name_vector)
    new_encoder = tf.keras.models.load_model('image_caption/deep/encoder_ic_10k_contour')
    new_decoder = tf.keras.models.load_model('image_caption/deep/decoder_ic_10k_contour')
    
    # with open('image_caption/deep/wordtoidx1652.json', 'rb') as fp:
    #     wordtoidx = pickle.load(fp)
    # with open('image_caption/deep/idxtoword1652.json', 'rb') as fp:
    #     idxtoword = pickle.load(fp)    
    # encode_model = tf.keras.applications.MobileNetV2(weights='imagenet')
    # encode_model = tf.keras.models.Model(encode_model.input, encode_model.layers[-2].output)
    # new_model = tf.keras.models.load_model('image_caption/deep/image-captioning-model-mobilenet.h5')

    # def newEncodeImage(self, img):
    #     img = img.resize((WIDTH, HEIGHT), Image.ANTIALIAS)
    #     x = tf.keras.preprocessing.image.img_to_array(img)
    #     x = np.expand_dims(x, axis=0)
    #     x = tf.keras.applications.mobilenet_v2.preprocess_input(x)
    #     x = self.encode_model.predict(x) 

    #     return x

    # def newGenerateCaption(self, photo):
    #     in_text = START
    #     for i in range(max_length):
    #         sequence = [self.wordtoidx[w] for w in in_text.split() if w in self.wordtoidx]
    #         sequence = pad_sequences([sequence], maxlen=max_length)
    #         yhat = self.new_model.predict([photo,sequence], verbose=0)
    #         yhat = np.argmax(yhat)
    #         word = self.idxtoword[yhat]
    #         in_text += ' ' + word
    #         if word == STOP:
    #             break
    #     final = in_text.split()
    #     final = final[1:-1]
    #     final = ' '.join(final)
    #     return final
