from tensorflow.keras.layers import TextVectorization
from tensorflow import keras

import config
from prepare import load_captions_data, train_val_split
from vectorizing import get_vectorization
from builddataset import make_dataset
from buildmodel import get_cnn_model, TransformerDecoderBlock, TransformerEncoderBlock, ImageCaptioningModel
from predict import generate_caption


# Load the dataset
captions_mapping, text_data = load_captions_data("Flickr8k.token.txt")

# Split the dataset into training and validation sets
train_data, valid_data = train_val_split(captions_mapping)
print("Number of training samples: ", len(train_data))
print("Number of validation samples: ", len(valid_data))


# vectonize
vectorization = get_vectorization(text_data)


# Pass the list of images and the list of corresponding captions
train_dataset = make_dataset(list(train_data.keys()), list(train_data.values()), vectorization)
valid_dataset = make_dataset(list(valid_data.keys()), list(valid_data.values()), vectorization)


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

# Fit the model
caption_model.fit(
    train_dataset,
    epochs=config.EPOCHS,
    validation_data=valid_dataset,
    callbacks=[early_stopping],
)



# save
caption_model.save_weights('model_tuto.hdf5')

generate_caption()
generate_caption()
