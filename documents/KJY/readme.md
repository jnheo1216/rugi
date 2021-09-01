## 개발환경 구성

### 1. 아나콘다 설치 및 기본 설정

#### 일반 콘솔에서 Conda사용하기

``source C:/Users/wns31/anaconda3/etc/profile.d/conda.sh``

#### 설치 명령어

```bash
conda create -n sub01 python=3.7

conda activate sub01

conda install pytorch torchvision torchaudio cudatoolkit=10.1 -c pytorch

pip install tensorflow-gpu
```



---
### 2. 이미지 분류기 구현

> 동일 디렉토리의 req1.py에 관련 내용을 옮겨두었습니다.






---

### 3. Image Captioning 실행 및 결과 확인

> 현재 Req.3-1까지는 진행된 상태이나 Req3-2, 3-3을 진행하던 중 많은 오류로 인해 진행이 막혀있습니다.