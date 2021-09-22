import os

annotation_folder = './annotations/'
annotation_file = annotation_folder+'captions_train2014.json'
image_folder = '/train2014/'
PATH = os.path.abspath('.') + image_folder
# Choose the top 5000 words from the vocabulary
top_k = 5000

EPOCH = 20
BATCH_SIZE = 64
BUFFER_SIZE = 1000
embedding_dim = 256
units = 512
vocab_size = top_k + 1
# num_steps = len(img_name_train) // BATCH_SIZE

# Shape of the vector extracted from InceptionV3 is (64, 2048)
# These two variables represent that vector shape
features_shape = 2048
attention_features_shape = 64