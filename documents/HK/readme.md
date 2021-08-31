# JIRA & GitLab 연동

## JIRA Connection

1. 외부 push 메시지 수신을 위한 팀 별 채널 생성
2. GitLab 연동을 위한 Webhook URL 생성
3. 생성한 push 채널에서 연결(connect)하고 이슈를 구독(subscribe)설정 #1

지라 구독이 완료되면 지라에서 변경사항(Events)이 발생할 때 push 채널에 업데이트 됩니다.

## GitLab Connection


1. GitLab에서 Jira 연동
2. GitLab에서 Mattermost 연동

GitLab과 Jira가 연동이 완료되면 GitLab의 commit 정보가 Jira 이슈에 연결 되고 MR이 닫힐 때 이슈도 같이 완료됩니다.
또한 GitLab에 코드가 Push 되거나 Merge될 때 또는 MR이 요청되고 닫힐 때 push 채널에서 빠르게 정보 확인 후 대응이 가능합니다.

---
2021-08-31

# 개발 환경 구성

## 1) 아나콘다 설치
아나콘다의 파이썬 버전은 최신으로 받고 환경 구성할 때 버전은 아래의 설명을 참고.
Link : https://www.anaconda.com/products/individual

## 2) 아나콘다 가상환경 생성 및 활성화
아래 명령어를 통해 아나콘다 가상환경 계정을 세팅. 우분투의 경우  python=3.6 버전을 필요로 함.
```
conda create -n [NAME] python=3.7 #[NAME] : 가상환경 이름 본 개발환경 구성에서는 sub1을 사용
```

다음으로 생성한 계정을 활성화
```
conda activate [NAME]
```

### 3) 프레임워크 및 라이브러리 설치
아래의 명령어로 파이토치와 텐서플로우를 설치.
```
conda install pytorch torchvision torchaudio cudatoolkit=10.1 -c pytorch
```
```
pip install tensorflow-gpu
```
