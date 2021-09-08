# :calendar: SUB-PJT2

### :bulb: 개발 환경 구성

> TTS 모델 학습을 위한 모듈 설치
>
> ```
> librosa == 0.6.3
> unidecode == 1.1.1
> tqdm  == 4.48.2
> numba == 0.48
> ```
>
> 
>
> TTS 모델 다운
>
> - LJ Speech Dataset : 학습에 사용 되는 LJ 음성 데이터셋
> - Tacotron2 : 학습이 어느정도 진행된 모델
> - WaveGlow : 학습이 완료된 모델

---

### (09/07)

## 데이터 전처리 및 파라미터 구성

### 음성데이터 전처리 과정 살펴보기

> colab 실습 진행
>
> 기존 sample.wav가 적절하지 않아 다운로드 후 경로 재설정
>
> ```python
> output = '/content/gdrive/My Drive/Audio/test.wav'

### 퓨리에 변환으로 스펙트럼 구하기

- 특정 길이의 음성 조각(frame)이 주파수(frequency) 성분을 얼마만큼 갖고 있는지를 나타내는 스펙트럼을 얻는다.
- 여러 개의 스펙트럼을 시간 축에 나열하면 시간 변화에 따른 스펙트럼의 변화인 스펙트로그램(spectrogram)을 얻게 되고,
- 이는 사람의 귀와 유사한 매커니즘으로 각각의 주파수 성분을 추출하여 정보를 얻는다.

### STFT를 사용하여 Spectrogram 생성하기

- Short-Time Fourier Transform

- 음성의 feature를 계산해주는 하나의 도구 (python에서는 librosa가 이를 위한 라이브러리)

### Mel-spectrogram 생성하기

주파수 대역별 상대적인 헤르즈의 갭에 대한 인지 차이를 사람에 맞게 변환한 것

- `mel-scale` : 사람의 귀를 칼라맵인 스펙트로그램에 반영한 것

### MFCC 확인

- Mel Frequency Cepstral Coefficient

- https://m.blog.naver.com/mylogic/220988857132


---

### (09/08)

- 기획
- API 명세 작성
- TTS  Tacotran 이해