# TTS(Text To Speech)

## 1. 개발 환경 구성



### 필수 패키지 설치

웹 서버 구성을 위한 django 패키지 설치

```python
pip install django
```



TTS 모델 학습을 위한 모듈 설치. requirements.txt에 이미 작성되어 있음.

```shell
librosa==0.6.3
unidecode == 1.1.1
inflect == 4.1.0
tqdm == 4.48.2
numba == 0.48
```



## 2. TTS(Text To Speech) 모델 다운



### 음성 데이터셋 

### The LJ Speech Dataset : 학습에 사용되는 LJ 음성 데이터셋

link : https://keithito.com/LJ-Speech-Dataset/



### Pre-trained Model 

Tacotron2 : 어느정도 학습이 진행된 Tacotron2 모델 

link : https://drive.google.com/file/d/1IUWQHB2cFQXekNuo-yCoKrY5wD8nNpnV/view?usp=sharing

WaveGlow : 학습이 완료된 Waveglow 모델

link : https://drive.google.com/file/d/1lVP5gQoB-fi6aydKuxjVE64qMexGX2yL/view?usp=sharing



# 기능 명세



## Req. 1. 데이터 전처리 및 파라미터 구성



### Req. 1-1 음성 데이터 전처리 과정 살펴보기 (melspec_example.ipynb)

작성된 melspec_example.ipynb을 실행하며 waveform과 mel-spectrogram의 차이에 대해서 살펴보고, mel-spectrogram으로 변환 과정 이해 (Tacotron의 mel-spectrogram 변환 과정과 차이가 있음)



아래 링크에서 진행.

link : https://colab.research.google.com/drive/1YRX9ZXKQFS8h_IJCkx3RiWAt7sp8jbga?usp=sharing



### melspec_example.ipynb

### 음성 파일 다운로드

```python
import gdown
url = "https://drive.google.com/file/d/15Oc0D-BsyDQ3nQfM2203Qt4jbQ6DKXSA/view?usp=sharing"
output = 'sample.wav' # sample.wav 파일 다운로드
gdown.download(url,output,quiet=False)
```

From: https://drive.google.com/file/d/15Oc0D-BsyDQ3nQfM2203Qt4jbQ6DKXSA/view?usp=sharing 

To: /content/sample.wav

위 코드를 실행시키면 colab의 /content 위치에 sample.wav 파일이 다운로드 된다. 



```python
from google.colab import drive
drive.mount('/content/gdrive')
```

google drive를 마운트 시켜주고



```python
output = '/content/gdrive/My Drive/Audio/a2s/water/201165.wav'
```



```python
import numpy as np
import librosa, librosa.display 
import matplotlib.pyplot as plt

FIG_SIZE = (15,10)
```

```python
from scipy import signal
# 음성 파일 load
sig, sr = librosa.load(output, sr=22050)

```

위 블럭의 코드를 실행시키면 아래와 같은 error가 발생하는데 아직 해결중.

```
/usr/local/lib/python3.7/dist-packages/librosa/core/audio.py:165: UserWarning: PySoundFile failed. Trying audioread instead.
  warnings.warn("PySoundFile failed. Trying audioread instead.")
---------------------------------------------------------------------------
RuntimeError                              Traceback (most recent call last)
/usr/local/lib/python3.7/dist-packages/librosa/core/audio.py in load(path, sr, mono, offset, duration, dtype, res_type)
    148     try:
--> 149         with sf.SoundFile(path) as sf_desc:
    150             sr_native = sf_desc.samplerate

6 frames
RuntimeError: Error opening '/content/gdrive/My Drive/Audio/a2s/water/201165.wav': File contains data in an unknown format.

During handling of the above exception, another exception occurred:

NoBackendError                            Traceback (most recent call last)
/usr/local/lib/python3.7/dist-packages/audioread/__init__.py in audio_open(path, backends)
    114 
    115     # All backends failed!
--> 116     raise NoBackendError()

NoBackendError: 
```



---

2021-09-07



> melspectrogram_ex.ipynb를 colab에서 실행할 때 sample.wav 파일이 정상적으로 열리지 않아 문제가 되었다. 따라서
>
> https://file-examples.com/index.php/sample-audio-files/sample-wav-download/ 경로에서 sample wav파일을 다운받아 Google drive에 위치시켜 준 후 진행하였다.



![image-20210907210949335](C:\Users\multicampus\Desktop\projects\third\S05P21B106\documents\HK\sub02.assets\image-20210907210949335.png)

​																					그림 1. sample.wav 에서 추출한 Waveform

​																					

```python
#(생략)
real_part, imag_part = fft.unbind(-1)
magnitude = torch.sqrt(real_part ** 2 + imag_part ** 2)
mel_output = torch.matmul(mel_basis, magnitude)
log_mel_spec = torch.log10(torch.clamp(mel_output, min=1e-5))
mel=log_mel_spec[0].numpy()
print(mel.shape, mel.min(),mel.max(),mel.mean())
#(생략)

plt.figure(figsize=FIG_SIZE)
#display
librosa.display.specshow(mel, y_axis='mel', sr=sr, hop_length=hop_length, x_axis='time')
plt.colorbar(format='%+2.0f dB')
plt.title('Mel-Spectrogram')
plt.tight_layout()
plt.show()
```



![image-20210907212551264](C:\Users\multicampus\Desktop\projects\third\S05P21B106\documents\HK\sub02.assets\image-20210907212551264.png)

​																				그림 2. Waveform을 변환한 Mel-Spectogram



### 푸리에 변환으로 스펙트럼 구하기

```python
# 단순 퓨리에 변환으로 스펙트럼 구하기
fft = np.fft.fft(sig)

# 복소공간 값 절댓갑 취해서, magnitude 구하기
magnitude = np.abs(fft) 

# Frequency 값 만들기
f = np.linspace(0,sr,len(magnitude))

# 푸리에 변환을 통과한 specturm은 대칭구조로 나와서 high frequency 부분 절반을 날려고 앞쪽 절반만 사용한다.
left_spectrum = magnitude[:int(len(magnitude)/2)]
left_f = f[:int(len(magnitude)/2)]

plt.figure(figsize=FIG_SIZE)
plt.plot(left_f, left_spectrum)
plt.xlabel("Frequency")
plt.ylabel("Magnitude")
plt.title("Power spectrum")
```

![image-20210907213254884](C:\Users\multicampus\Desktop\projects\third\S05P21B106\documents\HK\sub02.assets\image-20210907213254884.png)



### STFT(Short Time Fourier Transform)를 사용하여 Spectogram 생성하기

```python

# STFT를 사용하여 spectrogram 생성하기

hop_length = 512  # 전체 frame 수
n_fft = 2048  # frame 하나당 sample 수

# calculate duration hop length and window in seconds
hop_length_duration = float(hop_length)/sr
n_fft_duration = float(n_fft)/sr

# STFT
stft = librosa.stft(sig, n_fft=n_fft, hop_length=hop_length)

# 복소공간 값 절댓값 취하기
magnitude = np.abs(stft)

# magnitude > Decibels 
log_spectrogram = librosa.amplitude_to_db(magnitude)

# display spectrogram
plt.figure(figsize=FIG_SIZE)
librosa.display.specshow(log_spectrogram, sr=sr, hop_length=hop_length)
plt.xlabel("Time")
plt.ylabel("Frequency")
plt.colorbar(format="%+2.0f dB")
plt.title("Spectrogram (dB)")
```



![image-20210907213502578](C:\Users\multicampus\Desktop\projects\third\S05P21B106\documents\HK\sub02.assets\image-20210907213502578.png)



### Mel-spectogram 생성하기

```python
# Mel spectrogram 생성하기
n_mels = 80

# mel spectrogram
mel_basis = librosa.filters.mel(sr, n_fft, n_mels)  # (n_mels, 1+n_fft//2)
mel = np.dot(mel_basis, magnitude)  # (n_mels, t)

# to decibel
mel = 20 * np.log10(np.maximum(1e-5, mel))

# normalize
mel = np.clip((mel - 20 + 100) / 100, 1e-8, 1)

print(mel.shape, mel.min(),mel.max(),mel.mean())

plt.figure(figsize=FIG_SIZE)
librosa.display.specshow(mel, y_axis='mel', sr=sr, hop_length=hop_length, x_axis='time')
plt.colorbar(format='%+2.0f dB')
plt.title('Mel-Spectrogram')
plt.tight_layout()
plt.show()
```

![image-20210907213604276](C:\Users\multicampus\Desktop\projects\third\S05P21B106\documents\HK\sub02.assets\image-20210907213604276.png)



### MFCC확인

```python
# MFCCs

# extract 13 MFCCs
MFCCs = librosa.feature.mfcc(sig, sr, n_fft=n_fft, hop_length=hop_length, n_mfcc=13)

# display MFCCs
plt.figure(figsize=FIG_SIZE)
librosa.display.specshow(MFCCs, sr=sr, hop_length=hop_length)
plt.xlabel("Time")
plt.ylabel("MFCC coefficients")
plt.colorbar()
plt.title("MFCCs")

# show plots
plt.show()
```

![image-20210907213648496](C:\Users\multicampus\Desktop\projects\third\S05P21B106\documents\HK\sub02.assets\image-20210907213648496.png)

---

2021-09-08

### 화면 목록 정리

[Notion]: https://recondite-sweatshirt-73b.notion.site/List-of-Screen-3975e9aa343f4c44a0a01454708029c6



### Wire Frame 작업

![image-20210908210727979](C:\Users\multicampus\Desktop\projects\third\S05P21B106\documents\HK\sub02.assets\image-20210908210727979-1631103095447.png)





