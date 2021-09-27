import tensorflow as tf
import numpy as np
import collections
import random
from PIL import Image
import requests
from io import BytesIO

# Find the maximum length of any caption in the dataset
def calc_max_length(tensor):
    return max(len(t) for t in tensor)

def get_tokenize(train_captions, img_name_vector):
  # Choose the top 5000 words from the vocabulary
  tokenizer = tf.keras.preprocessing.text.Tokenizer(num_words=5000,
                                                    oov_token="<unk>",
                                                    filters='!"#$%&()*+.,-/:;=?@[\]^_`{|}~')
  tokenizer.fit_on_texts(train_captions)

  tokenizer.word_index['<pad>'] = 0
  tokenizer.index_word[0] = '<pad>'

  # Create the tokenized vectors
  train_seqs = tokenizer.texts_to_sequences(train_captions)

  # Pad each vector to the max_length of the captions
  # If you do not provide a max_length value, pad_sequences calculates it automatically
  cap_vector = tf.keras.preprocessing.sequence.pad_sequences(train_seqs, padding='post')

  # Calculates the max_length, which is used to store the attention weights
  max_length = calc_max_length(train_seqs)

  img_to_cap_vector = collections.defaultdict(list)
  for img, cap in zip(img_name_vector, cap_vector):
    img_to_cap_vector[img].append(cap)

  # Create training and validation sets using an 80-20 split randomly.
  img_keys = list(img_to_cap_vector.keys())
  random.shuffle(img_keys)

  slice_index = int(len(img_keys)*0.8)
  img_name_train_keys, img_name_val_keys = img_keys[:slice_index], img_keys[slice_index:]

  img_name_train = []
  cap_train = []
  for imgt in img_name_train_keys:
    capt_len = len(img_to_cap_vector[imgt])
    img_name_train.extend([imgt] * capt_len)
    cap_train.extend(img_to_cap_vector[imgt])

  img_name_val = []
  cap_val = []
  for imgv in img_name_val_keys:
    capv_len = len(img_to_cap_vector[imgv])
    img_name_val.extend([imgv] * capv_len)
    cap_val.extend(img_to_cap_vector[imgv])

  return tokenizer, max_length, img_name_train, cap_train, img_name_val, cap_val



def evaluate(image, max_length, attention_features_shape, decoder, encoder, tokenizer):
    attention_plot = np.zeros((max_length, attention_features_shape))
    image_features_extract_model = get_img_feature_extract_model()

    hidden = tf.zeros((1, 512))

    temp_input = tf.expand_dims(load_image(image)[0], 0)
    img_tensor_val = image_features_extract_model(temp_input)
    img_tensor_val = tf.reshape(img_tensor_val, (img_tensor_val.shape[0], -1, img_tensor_val.shape[3]))

    features = encoder(img_tensor_val)

    dec_input = tf.expand_dims([tokenizer.word_index['<start>']], 0)
    result = []

    for i in range(max_length):
        predictions, hidden, attention_weights = decoder(dec_input,
                                                         features,
                                                         hidden)

        attention_plot[i] = tf.reshape(attention_weights, (-1, )).numpy()

        predicted_id = tf.random.categorical(predictions, 1)[0][0].numpy()
        result.append(tokenizer.index_word[predicted_id])

        if tokenizer.index_word[predicted_id] == '<end>':
            return result, attention_plot

        dec_input = tf.expand_dims([predicted_id], 0)

    attention_plot = attention_plot[:len(result), :]
    return result, attention_plot


# def plot_attention(image, result, attention_plot):
#     temp_image = np.array(Image.open(image))

#     fig = plt.figure(figsize=(10, 10))

#     len_result = len(result)
#     for i in range(len_result):
#         temp_att = np.resize(attention_plot[i], (8, 8))
#         grid_size = max(np.ceil(len_result/2), 2)
#         ax = fig.add_subplot(grid_size, grid_size, i+1)
#         ax.set_title(result[i])
#         img = ax.imshow(temp_image)
#         ax.imshow(temp_att, cmap='gray', alpha=0.6, extent=img.get_extent())

#     plt.tight_layout()
#     plt.show()

def load_image(image_path):
    # img = tf.io.read_file(image_path)
    # 이미지 resize & preprocess
    response = requests.get(image_path)
    img = Image.open(BytesIO(response.content))
    img.load()

    # img = tf.image.decode_jpeg(img, channels=3)
    img = tf.image.resize(img, (299, 299))
    img = tf.keras.applications.inception_v3.preprocess_input(img)
    return img, image_path

def get_img_feature_extract_model():
    # 이미지 feature추출
  image_model = tf.keras.applications.InceptionV3(include_top=False, weights='imagenet')
  new_input = image_model.input
  hidden_layer = image_model.layers[-1].output
  image_features_extract_model = tf.keras.Model(new_input, hidden_layer)
  return image_features_extract_model
