import matplotlib as plt
import numpy as np
import tensorflow as tf
from tensorflow import keras

import config
from prepare import load_captions_data, train_val_split
from vectorizing import get_vectorization
from builddataset import read_image
from buildmodel import get_cnn_model, TransformerDecoderBlock, TransformerEncoderBlock, ImageCaptioningModel


captions_mapping, text_data = load_captions_data("Flickr8k.token.txt")
vectorization = get_vectorization(text_data)
train_data, valid_data = train_val_split(captions_mapping)

cnn_model = get_cnn_model()
encoder = TransformerEncoderBlock(
    embed_dim=config.EMBED_DIM, dense_dim=config.FF_DIM, num_heads=config.NUM_HEADS
)
decoder = TransformerDecoderBlock(
    embed_dim=config.EMBED_DIM, ff_dim=config.FF_DIM, num_heads=config.NUM_HEADS
)
caption_model = ImageCaptioningModel(
    cnn_model=cnn_model, encoder=encoder, decoder=decoder
)
# Define the loss function
cross_entropy = keras.losses.SparseCategoricalCrossentropy(
    from_logits=True, reduction="none"
)

# EarlyStopping criteria
early_stopping = keras.callbacks.EarlyStopping(patience=3, restore_best_weights=True)

# Compile the model
caption_model.compile(
    optimizer=keras.optimizers.Adam(learning_rate=config.LEARNING_RATE), loss=cross_entropy
)

caption_model.load_weights('model_tuto.hdf5')

######################################################################

vocab = vectorization.get_vocabulary()
index_lookup = dict(zip(range(len(vocab)), vocab))
max_decoded_sentence_length = config.SEQ_LENGTH - 1
valid_images = list(valid_data.keys())


def generate_caption():
    # Select a random image from the validation dataset
    sample_img = np.random.choice(valid_images)

    # Read the image from the disk
    sample_img = read_image(sample_img)
    img = sample_img.numpy().astype(np.uint8)
    plt.imshow(img)
    plt.show()

    # Pass the image to the CNN
    img = tf.expand_dims(sample_img, 0)
    img = caption_model.cnn_model(img)

    # Pass the image features to the Transformer encoder
    encoded_img = caption_model.encoder(img, training=False)

    # Generate the caption using the Transformer decoder
    decoded_caption = "<start> "
    for i in range(max_decoded_sentence_length):
        tokenized_caption = vectorization([decoded_caption])[:, :-1]
        mask = tf.math.not_equal(tokenized_caption, 0)
        predictions = caption_model.decoder(
            tokenized_caption, encoded_img, training=False, mask=mask
        )
        sampled_token_index = np.argmax(predictions[0, i, :])
        sampled_token = index_lookup[sampled_token_index]
        if sampled_token == " <end>":
            break
        decoded_caption += " " + sampled_token

    print("PREDICTED CAPTION:", end=" ")
    print(decoded_caption.replace("<start> ", "").replace(" <end>", "").strip())


# Check predictions for a few samples
generate_caption()
generate_caption()
generate_caption()