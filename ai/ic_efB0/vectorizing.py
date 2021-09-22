import tensorflow as tf
from tensorflow.keras.layers import TextVectorization
import re

import config

strip_chars = "!\"#$%&'()*+,-./:;<=>?@[\]^_`{|}~"
strip_chars = strip_chars.replace("<", "")
strip_chars = strip_chars.replace(">", "")


def custom_standardization(input_string):
    lowercase = tf.strings.lower(input_string)
    return tf.strings.regex_replace(lowercase, "[%s]" % re.escape(strip_chars), "")


def get_vectorization(text_data):
    vectorization = TextVectorization(
    max_tokens=config.VOCAB_SIZE,
    output_mode="int",
    output_sequence_length=config.SEQ_LENGTH,
    standardize=custom_standardization,
    )
    vectorization.adapt(text_data)
    return vectorization


# vectorization = TextVectorization(
#     max_tokens=config.VOCAB_SIZE,
#     output_mode="int",
#     output_sequence_length=config.SEQ_LENGTH,
#     standardize=custom_standardization,
# )
# vectorization.adapt(text_data)