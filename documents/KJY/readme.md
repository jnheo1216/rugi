## 개발환경 구성

### 1. 아나콘다 설치 및 기본 설정

#### 일반 콘솔에서 Conda사용하기

``source C:/Users/wns31/anaconda3/etc/profile.d/conda.sh``

#### 설치 명령어

```bash
conda create -n sub01 python=3.7

conda activate sub01

conda install pytorch torchvision torchaudio cudatoolkit=10.1 -c pytorch

pip install tensorflow-gpu`
```

