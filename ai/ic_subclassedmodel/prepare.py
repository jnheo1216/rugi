import collections
import random
import json

import config


def prepare_image():
  with open(config.annotation_file, 'r') as f:
      annotations = json.load(f)
      
  image_path_to_caption = collections.defaultdict(list)
  for val in annotations['annotations']:
    caption = f"<start> {val['caption']} <end>"
    image_path = config.PATH + 'COCO_train2014_' + '%012d.jpg' % (val['image_id'])
    image_path_to_caption[image_path].append(caption)

  image_paths = list(image_path_to_caption.keys())
  random.shuffle(image_paths)

  # Select the first 6000 image_paths from the shuffled set.
  # Approximately each image id has 5 captions associated with it, so that will
  # lead to 30,000 examples.
  train_image_paths = image_paths[:6000]
  # print(len(train_image_paths))

  train_captions = []
  img_name_vector = []

  for image_path in train_image_paths:
    caption_list = image_path_to_caption[image_path]
    train_captions.extend(caption_list)
    img_name_vector.extend([image_path] * len(caption_list))
  
  return train_captions, img_name_vector