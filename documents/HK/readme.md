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

## 3) 프레임워크 및 라이브러리 설치
아래의 명령어로 파이토치와 텐서플로우를 설치.
```
conda install pytorch torchvision torchaudio cudatoolkit=10.1 -c pytorch
```
```
pip install tensorflow-gpu
```
---
21-09-01
# req01
## 1. 데이터셋 준비 및 전처리
```python
# [0,1] 범위의 데이터셋을 [-1, -1] 범위의 값으로 normalize하도록 transform 정의
transform = transforms.Compose(
    [transforms.ToTensor(),
     transforms.Normalize((0.5, 0.5, 0.5), (0.5, 0.5, 0.5))])


# torchvision으로 CIFAR10 trainset load, trainset dataloader 정의
trainset = torchvision.datasets.CIFAR10(root='./data', train=True,
                                        download=True, transform=transform)
trainloader = torch.utils.data.DataLoader(trainset, batch_size=128,
                                          shuffle=True, num_workers=2)

# torchvision으로 CIFAR10 testset load, trainset dataloader 정의
testset = torchvision.datasets.CIFAR10(root='./data', train=False,
                                       download=True, transform=transform)
testloader = torch.utils.data.DataLoader(testset, batch_size=128,
                                         shuffle=False, num_workers=2)
# CIFAR10의 10개의 class 정의
classes = ('plane', 'car', 'bird', 'cat',
           'deer', 'dog', 'frog', 'horse', 'ship', 'truck')
```

## 데이터 시각화
```python
# Req. 1-1	데이터셋 준비 및 전처리
# 이미지를 시각화하는 함수
def visualize(img):
    # [-1, -1] 범위로 normalize된 데이터를 [0,1] 범위로 unnormalize
    img = img / 2 + 0.5
    # img를 numpy값으로 변환
    npimg = img.numpy()
    # plt.imshow함수로 시각화
    plt.imshow(np.transpose(npimg, (1, 2, 0)))
    plt.show()

# 트레이닝 데이터를 랜덤 샘플
dataiter = iter(trainloader)
images, labels = dataiter.next() ## image

# show images
visualize(torchvision.utils.make_grid(images))

# print labels
print(' '.join('%5s' % classes[labels[j]] for j in range(4)))
```

## 2. 분류기 모델 설계
```python
import torch.nn as nn
import torch.nn.functional as F

# Req. 1-2	분류기 모델 설계
class Classifier(nn.Module):
    def __init__(self):
        super(Classifier, self).__init__()
        
        # 이미지 3-channel 입력
        self.conv1 = nn.Conv2d(3, 16, 5)
        self.pool = nn.MaxPool2d(2, 2)

        # 최종 10개의 class에 대한 확률
        self.fc1 = nn.Linear(16*14*14, 128)
        self.fc2 = nn.Linear(128, 10)
    

    def forward(self, x):
       
        x = self.pool(F.relu(self.conv1(x)))
        x = x.view(-1, 16*14*14)
        x = F.relu(self.fc1(x))
        x = self.fc2(x)
        
        return x

classifier = Classifier()
```

## 3. Loss function 정의
```python
import torch.optim as optim

# Req. 1-3	Loss function 및 optimizer정의

# loss function
criterion = nn.CrossEntropyLoss()
# optimizer
optimizer = optim.SGD(classifier.parameters(), lr=0.001, momentum=0.9)
```

## 4. 모델 학습시키기
```python
# Req. 1-4	모델 학습
# model을 device에 올린다 (GPU or CPU) 
# 구현 완료 상태
device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
classifier = classifier.to(device)

# Req. 1-4	모델 학습
epochs = 100  #define epochs

# 1) for문으로 epochs 만큼 반복
for epoch in range(epochs):  # epochs 횟수만큼 반복
    
    # loss값 누적 
    running_loss = 0.0
    
    # 2) for문으로 trainset이 저장되어 있는 trainloader에서 배치 사이즈 만큼씩 샘플링하여 data load
    for i, data in enumerate(trainloader, 0):
        
        # 3) load한 data에서 input 값과 label로 분리하여 저장
        inputs, labels = data
       
        # 4) 각각의 값을 device에 올린다 (GPU or CPU)
        inputs = inputs.to(device)
        labels = labels.to(device)

        # 5) optimizer에서 gradient 값 0으로 초기화
        optimizer.zero_grad()

        # 6) model에 input값을 입력하여 forward propagation
        outputs = classifier(inputs)

        # 7)  loss function으로 예측값과 label 비교
        loss = criterion(outputs, labels)
        
        # 8) loss 값 backpropagation 하여 gradient 계산
        loss.backward()
        
        # 9) 계산된 gradient를 모두 parameter에 적용
        optimizer.step()

        # 10) loss 값을 합하여 일정 주기(ex.2000 batch) 마다 평균 loss 값 출력 후 초기화
        running_loss += loss.item()
        if i % 2000 == 1999:
          print('[%d, %5d] loss: %.3f' %
                (epoch + 1, i + 1, running_loss / 2000))
          running_loss = 0.0

# 12) torch.save로 학습이 마친 이후 모델 저장        


print('Finished Training')
```
## 5. 테스트하기
```python
# Req. 1-5	모델 테스트
# 만약 저장한 모델을 load해야 한다면, 모델의 인스턴스를 생성하고, 모델의 weight이 저장되어 있는 .ckpt 파일을 모델에 load
new_classifier = Classifier()
new_classifier.load_state_dict(torch.load('cifar_net.pth'))
new_classifier.to(device)

# 1) 모델을 evaluation 모드로 전환

correct = 0
total = 0
# 2) with torch.no_grad로 gradient 계산을 제외
with torch.no_grad():
    # 3) for문으로 testset에 저장되어 있는 testloader에서 배치 사이즈 만큼씩 샘플링하여 data load
    for data in testloader:
        
         # 4) load한 data에서 input 값과 label로 분리하여 저장
        images, labels = data
       
        # 5) 각각의 값을 device에 올린다 (GPU or CPU)
        images, labels = images.to(device), labels.to(device)

        # 6) model에 input값을 입력하여 forward propagation
        outputs = new_classifier(images)

        # 7) 예측한 값들 중 가장 높은 확률의 class 선택
        _, predicted = torch.max(outputs.data, 1)

        # 8) label과 예측한 class 비교하여 정답 확인
        total += labels.size(0)
        correct += (predicted == labels).sum().item()

# 9) 정답률 출력
print(
    "Accuracy of the network on the 10000 test images: %d %%" % (100 * correct / total)
)
```
