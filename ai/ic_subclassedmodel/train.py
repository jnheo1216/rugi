import tensorflow as tf
import time
import matplotlib as plt
import pickle

import config
from prepare import prepare_image
from img_extract import make_img_np
from tokenizeImg import get_tokenize
from set_dataset import set_data
from buildmodel import CNN_Encoder, RNN_Decoder


def show_flow(loss_plot):
  plt.plot(loss_plot)
  plt.xlabel('Epochs')
  plt.ylabel('Loss')
  plt.title('Loss Plot')
  plt.show()


def loss_function(real, pred, loss_object):
  mask = tf.math.logical_not(tf.math.equal(real, 0))
  loss_ = loss_object(real, pred)

  mask = tf.cast(mask, dtype=loss_.dtype)
  loss_ *= mask

  return tf.reduce_mean(loss_)


@tf.function
def train_step(img_tensor, target, loss_object, encoder, decoder, tokenizer, optimizer):
  loss = 0

  # initializing the hidden state for each batch
  # because the captions are not related from image to image
  hidden = decoder.reset_state(batch_size=target.shape[0])

  dec_input = tf.expand_dims([tokenizer.word_index['<start>']] * target.shape[0], 1)

  with tf.GradientTape() as tape:
      features = encoder(img_tensor)

      for i in range(1, target.shape[1]):
          # passing the features through the decoder
          predictions, hidden, _ = decoder(dec_input, features, hidden)

          loss += loss_function(target[:, i], predictions, loss_object)

          # using teacher forcing
          dec_input = tf.expand_dims(target[:, i], 1)

  total_loss = (loss / int(target.shape[1]))

  trainable_variables = encoder.trainable_variables + decoder.trainable_variables

  gradients = tape.gradient(loss, trainable_variables)

  optimizer.apply_gradients(zip(gradients, trainable_variables))

  return loss, total_loss


############### train ################
train_captions, img_name_vector = prepare_image()
make_img_np(img_name_vector)
tokenizer, max_length, img_name_train, cap_train, img_name_val, cap_val = get_tokenize(train_captions, img_name_vector)
num_steps = len(img_name_train) // config.BATCH_SIZE
dataset = set_data(img_name_train, cap_train)

encoder = CNN_Encoder(config.embedding_dim)
decoder = RNN_Decoder(config.embedding_dim, config.units, config.vocab_size)

optimizer = tf.keras.optimizers.Adam()
loss_object = tf.keras.losses.SparseCategoricalCrossentropy(from_logits=True, reduction='none')

checkpoint_path = "./checkpoints/train"
ckpt = tf.train.Checkpoint(encoder=encoder, decoder=decoder, optimizer=optimizer)
ckpt_manager = tf.train.CheckpointManager(ckpt, checkpoint_path, max_to_keep=5)

start_epoch = 0
if ckpt_manager.latest_checkpoint:
  start_epoch = int(ckpt_manager.latest_checkpoint.split('-')[-1])
  # restoring the latest checkpoint in checkpoint_path
  ckpt.restore(ckpt_manager.latest_checkpoint)

loss_plot = []

EPOCHS = config.EPOCH
for epoch in range(start_epoch, EPOCHS):
    start = time.time()
    total_loss = 0

    for (batch, (img_tensor, target)) in enumerate(dataset):
        batch_loss, t_loss = train_step(img_tensor, target, loss_object, encoder, decoder, tokenizer, optimizer)
        total_loss += t_loss

        if batch % 100 == 0:
            average_batch_loss = batch_loss.numpy()/int(target.shape[1])
            print(f'Epoch {epoch+1} Batch {batch} Loss {average_batch_loss:.4f}')
    # storing the epoch end loss value to plot later
    loss_plot.append(total_loss / num_steps)

    
    ckpt_manager.save()

    print(f'Epoch {epoch+1} Loss {total_loss/num_steps:.6f}')
    print(f'Time taken for 1 epoch {time.time()-start:.2f} sec\n')

###################################################

with open('train_captions_ic.pkl', 'wb') as f:
    pickle.dump(train_captions, f)
with open('img_name_vector.pkl', 'wb') as f:
    pickle.dump(img_name_vector, f)

encoder.save_weights('encoder_weights_ic.hdf5')
decoder.save_weights('decoder_weights_ic.hdf5')
encoder.save('encoder_ic', save_format='tf')
decoder.save('decoder_ic', save_format='tf')






