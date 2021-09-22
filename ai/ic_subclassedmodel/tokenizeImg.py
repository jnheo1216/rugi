import tensorflow as tf
import collections
import random

import config


# Find the maximum length of any caption in the dataset
def calc_max_length(tensor):
    return max(len(t) for t in tensor)


def get_tokenize(train_captions, img_name_vector):
  # Choose the top 5000 words from the vocabulary
  tokenizer = tf.keras.preprocessing.text.Tokenizer(num_words=config.top_k,
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