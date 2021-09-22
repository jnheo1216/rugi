import tensorflow as tf

import config


def read_image(img_path, size=config.IMAGE_SIZE):
    img = tf.io.read_file(img_path)
    img = tf.image.decode_jpeg(img, channels=3)
    img = tf.image.resize(img, size)
    img = tf.image.convert_image_dtype(img, tf.float32)
    return img


def make_dataset(images, captions, vectorization):
    img_dataset = tf.data.Dataset.from_tensor_slices(images).map(
        read_image, num_parallel_calls=config.AUTOTUNE
    )
    cap_dataset = tf.data.Dataset.from_tensor_slices(captions).map(
        vectorization, num_parallel_calls=config.AUTOTUNE
    )
    dataset = tf.data.Dataset.zip((img_dataset, cap_dataset))
    dataset = dataset.batch(config.BATCH_SIZE).shuffle(256).prefetch(config.AUTOTUNE)
    return dataset


# # Pass the list of images and the list of corresponding captions
# train_dataset = make_dataset(list(train_data.keys()), list(train_data.values()), vectorization)
# valid_dataset = make_dataset(list(valid_data.keys()), list(valid_data.values()), vectorization)