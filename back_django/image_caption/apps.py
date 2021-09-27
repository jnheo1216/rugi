from django.apps import AppConfig
import tensorflow as tf
import pickle
from .deep.icconfig import get_tokenize


# WIDTH = 299
# HEIGHT = 299
# OUTPUT_DIM = 2048
# START = "startseq"
# STOP = "endseq"
# max_length = 34


class ImageCaptionConfig(AppConfig):
    default_auto_field = 'django.db.models.BigAutoField'
    name = 'image_caption'


class DeepConfig(AppConfig):
    def __init__(self):
        pass
    with open('image_caption/deep/train_captions30k.pkl', 'rb') as f:
        train_captions = pickle.load(f)
    with open('image_caption/deep/img_name_vector30k.pkl', 'rb') as f:
        img_name_vector = pickle.load(f)
    tokenizer, max_length, _, _, _, _ = get_tokenize(train_captions, img_name_vector)
    new_encoder = tf.keras.models.load_model('image_caption/deep/encoder_ic_30k')
    new_decoder = tf.keras.models.load_model('image_caption/deep/decoder_ic_30k')

