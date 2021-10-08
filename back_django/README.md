## DjangoRestFramework with tensorflow



POST  :  http://{your-runserver-URL}:8000/image_caption/



form-data:

{'image_path': '{your image src}'}



return:

data = {'caption': 'image captioning result'}