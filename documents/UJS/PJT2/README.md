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

---

### (09/09)

- ERD 설계

- Tacotron, Wavlglow 이해 https://pytorch.org/hub/nvidia_deeplearningexamples_waveglow/

> Pre-trained Tacotron2, Waveglow 모델을 torch.hub에서 load
> Tacotron2 입력테스트의 텐서가 주어지면 mel-spectrogram 생성
> Waveglow는 mel-spectrogram에서 사운드를 생성
>
> 
>
>
> LJ Speech 데이터 세트에서 사전 훈련된 WaveGlow 모델 로드
>
>
> ```python
> import torch
> waveglow = torch.hub.load('NVIDIA/DeepLearningExamples:torchhub', 'nvidia_waveglow', model_math='fp32')
> waveglow = waveglow.remove_weightnorm(waveglow)
> waveglow = waveglow.to('cuda')
> waveglow.eval()
> ```
>
> Tacotron2 모델 로드
>
> ```python
> tacotron2 = torch.hub.load('NVIDIA/DeepLearningExamples:torchhub', 'nvidia_tacotron2', model_math='fp32')
> tacotron2 = tacotron2.to('cuda')
> tacotron2.eval()
> ```
>
> utils 메소드로 입력 형식 지정
>
> ```python
> text = "hello world, I missed you so much"
> 
> utils = torch.hub.load('NVIDIA/DeepLearningExamples:torchhub', 'nvidia_tts_utils')
> sequences, lengths = utils.prepare_input_sequence([text])
> ```
>
> 모델 실행
>
> ```python
> with torch.no_grad():
>     mel, _, _ = tacotron2.infer(sequences, lengths)
>     audio = waveglow.infer(mel)
> audio_numpy = audio[0].data.cpu().numpy()
> rate = 22050
> 
> from scipy.io.wavfile import write
> write("audio.wav", rate, audio_numpy)
> ```
>
> 출력
>
> ```python
> from IPython.display import Audio
> Audio(audio_numpy, rate=rate)
> ```

---

(09/10)

### train.py > optimizer, scheduler

```python
optimizer = torch.optim.Adam(model.parameters(), lr=learning_rate,
                                 weight_decay=hparams.weight_decay)
```



### validate()

```python
def validate(model, criterion, valset, iteration, batch_size, n_gpus,
             collate_fn, logger, distributed_run, rank):
    model.eval()
    with torch.no_grad():
        val_sampler = DistributedSampler(valset) if distributed_run else None
        val_loader = DataLoader(valset, sampler=val_sampler, num_workers=1,
                                shuffle=False, batch_size=batch_size,
                                pin_memory=False, collate_fn=collate_fn)

        val_loss = 0.0
        for i, batch in enumerate(val_loader):
            x, y = model.parse_batch(batch)
            y_pred = model(x)
            loss = criterion(y_pred, y)
            if distributed_run:
                reduced_val_loss = reduce_tensor(loss.data, n_gpus).item()
            else:
                reduced_val_loss = loss.item()
            val_loss += reduced_val_loss
        val_loss = val_loss / (i + 1)

    model.train()
    if rank == 0:
        print("Validation loss {}: {:9f}  ".format(iteration, val_loss))
        logger.log_validation(val_loss, model, y, y_pred, iteration)
```



### save_checkpoint()

```python
def save_checkpoint(model, optimizer, learning_rate, iteration, filepath):
    print("Saving model and optimizer state at iteration {} to {}".format(
        iteration, filepath))
    torch.save({'iteration': iteration,
                'state_dict': model.state_dict(),
                'optimizer': optimizer.state_dict(),
                'learning_rate': learning_rate}, filepath)
```

