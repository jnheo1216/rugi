import pickle
from PIL import Image
import tensorflow as tf

import config
from tokenizeImg import get_tokenize
from buildmodel import CNN_Encoder, RNN_Decoder
from evaluate import evaluate, plot_attention



with open('train_captions.pkl', 'rb') as f:
    train_captions = pickle.load(f)
with open('img_name_vector.pkl', 'rb') as f:
    img_name_vector = pickle.load(f)

tokenizer, max_length, _, _, _, _ = get_tokenize(train_captions, img_name_vector)

new_encoder = CNN_Encoder(config.embedding_dim)
new_decoder = RNN_Decoder(config.embedding_dim, config.units, config.vocab_size)
# new_encoder.build(input_shape = config.embedding_dim)
# new_decoder.build(input_shape = (config.vocab_size, config.embedding_dim))

# new_encoder.load_weights('encoder_weights.hdf5')
# new_decoder.load_weights('decoder_weights.hdf5')

new_encoder = tf.keras.models.load_model('encoder_ic')
new_decoder = tf.keras.models.load_model('decoder_ic')



my_img_path = './data/img.jpg'
result, attention_plot = evaluate(my_img_path, max_length, config.attention_features_shape, new_decoder, new_encoder, tokenizer)
print('Prediction Caption:', ' '.join(result))
plot_attention(my_img_path, result, attention_plot)

Image.open(my_img_path)