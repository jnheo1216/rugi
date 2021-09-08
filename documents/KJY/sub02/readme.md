## 개발환경 구성

### 1. 아나콘다 TTS 모듈 추가 설정

> 기존 Sub1에서 구성했던 가상환경에 다음과 같은 내용을 추가 설치했습니다. 웹 서버 구축 설정은 추후 따로 구현할 예정으로, 실습 먼저 진행했습니다.

#### django 패키지 설치

```bash
pip install django
```

#### TTS 관련 모듈 설치 (requirements.txt)

> requirements.txt파일을 TTS 폴더에 생성해 설치했습니다

```
librosa==0.6.3
unidecode==1.1.1
inflect==4.1.0
tqdm==4.48.2
numba==0.48
```

##### TTS 관련 모듈 설치 명령어

```bash
pip install -r requirements.txt
```



#### TTS 관련 모듈 다운로드

> 아래 세 경로에서 TTS 관련 모듈을 다운로드 받아 TTS/downloads 경로에 우선 넣어주었습니다

음성 데이터셋(The LJ Speech Dataset)  : https://keithito.com/LJ-Speech-Dataset/

Pre-trained Model(Tacotron2) : https://drive.google.com/file/d/1IUWQHB2cFQXekNuo-yCoKrY5wD8nNpnV/view

Pre-trained Model(WaveGlow) : https://drive.google.com/file/d/1lVP5gQoB-fi6aydKuxjVE64qMexGX2yL/view





---

### 2. 기능/과제 수행

#### Req.1 데이터 전처리 및 파라미터 구성

##### Req.1-1 음성 데이터 전처리 과정 살펴보기 (melspec_example.ipynb)

> melspectrogram_ex.ipynb 파일을 첨부하였습니다.

원래 있던 sample 파일이 정상적으로 작동하지 않아 아래 경로에서 wav 파일을 다운로드 받아 사용했습니다.

https://file-examples.com/index.php/sample-audio-files/sample-wav-download/

##### Req.1-2 Tacotron 모델 및 파라미터 구성 (model.py, config.yaml)

##### Req.1-3 데이터 셋 및 Pre-trained 모델 다운로드





