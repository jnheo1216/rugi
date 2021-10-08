FROM python:3
WORKDIR /app
RUN pip install gunicorn greenlet gevent gunicorn[gevent]
COPY requirements.txt /app
RUN pip install -r requirements.txt
COPY gunicorn.conf.py /app
COPY .env /app
COPY . .
EXPOSE 8081
ENTRYPOINT gunicorn django_backend.wsgi:application -c gunicorn.conf.py
