## 개발환경 구성 (0830)

> ### :snake: Anaconda
>
> 설치: https://www.anaconda.com/products/individual
>
> 가성환경 생성 및 활성화
>
> ```
> conda create -n [name] python=3.7 # 가상환경 생성
> conda activate [name] # 가상환경 활성화
> conda install pytorch torchvision torchaudio -c pytorch # 프레임워크 및 라이브러리 설치
> ```
>
> ### :book: colab을 통한 pytorch 이미지 분류기 구현
>
> https://colab.research.google.com/drive/1p7jF-Hl07EMu7rfG5AbcfAEpManNFv5E?usp=sharing
>
> 

## 데이터셋 및 전처리

> ```python
> import torch
> import torchvision
> import torchvision.transforms as transforms
> import matplotlib.pyplot as plt
> import numpy as np
> ```
>
> ​	**torch**는 pytorch의 가장 상위에 있는 package로써 라이브러리에서 이것저것 불러오기 위해 import
>
> ​	**torchvision**은 Computer vision에서 사용하는 각종 테크닉들을 torch와 연동하여 구현한 라이브러리
>
> 
>
> ```python
> # [0,1] 범위의 데이터셋을 [-1, -1] 범위의 값으로 normalize하도록 transform 정의
> transform = transforms.Compose(
>     [transforms.ToTensor(),
>      transforms.Normalize((0.5, 0.5, 0.5), (0.5, 0.5, 0.5))])
> 
> 
> # torchvision으로 CIFAR10 trainset load, trainset dataloader 정의
> trainset = torchvision.datasets.CIFAR10(root='./data', train=True,
>                                         download=True, transform=transform)
> trainloader = torch.utils.data.DataLoader(trainset, batch_size=128,
>                                           shuffle=True, num_workers=2)
> 
> # torchvision으로 CIFAR10 testset load, trainset dataloader 정의
> testset = torchvision.datasets.CIFAR10(root='./data', train=False,
>                                        download=True, transform=transform)
> testloader = torch.utils.data.DataLoader(testset, batch_size=128,
>                                          shuffle=False, num_workers=2)
> # CIFAR10의 10개의 class 정의
> classes = ('plane', 'car', 'bird', 'cat',
>            'deer', 'dog', 'frog', 'horse', 'ship', 'truck')
> ```
>
> 
>
> ### :microscope: 데이터 시각화
>
> `matplotlib`의 imshow 함수를 데이터들을 시각화
>
> ```python
> # Req. 1-1	데이터셋 준비 및 전처리
> # 이미지를 시각화하는 함수
> def visualize(img):
>     # pass
>     # [-1, -1] 범위로 normalize된 데이터를 [0,1] 범위로 unnormalize
>     img = img / 2 + 0.5
>     
>     # img를 numpy값으로 변환
>     npimg = img.numpy()
> 
>     # plt.imshow함수로 시각화
>     plt.imshow(np.transpose(npimg, (1, 2, 0)))
> 
> # 트레이닝 데이터를 랜덤 샘플
> dataiter = iter(trainloader)
> images, labels = dataiter.next() ## image
> 
> # show images
> visualize(torchvision.utils.make_grid(images))
> 
> # print labels
> print(' '.join('%5s' % classes[labels[j]] for j in range(4)))
> ```
>
> 

## 분류기 모델 설계

> CNN을 사용하여 모델 설계
> torch.nn 라이브러리를 사용하여 뉴럴넷 구현
>
> ```python
> import torch.nn as nn
> import torch.nn.functional as F
> 
> # Req. 1-2	분류기 모델 설계
> class Classifier(nn.Module):
>     def __init__(self):
>         super(Classifier, self).__init__()
>         
>         # 이미지 3-channel 입력
>         self.conv1 = nn.Conv2d(3, 16, 5)
>         self.pool = nn.MaxPool2d(2, 2)
> 
>         # 최종 10개의 class에 대한 확률
>         self.fc1 = nn.Linear(16*14*14, 128)
>         self.fc2 = nn.Linear(128, 10)
>     
> 
>     def forward(self, x):
>        
>         x = self.pool(F.relu(self.conv1(x)))
>         x = x.view(-1, 16*14*14)
>         x = F.relu(self.fc1(x))
>         x = self.fc2(x)
>         
>         return x
> 
> classifier = Classifier()
> ```
>
> 

## Loss function

> ```python
> import torch.optim as optim
> 
> # Req. 1-3	Loss function 및 optimizer정의
> 
> # loss function
> loss_func = nn.CrossEntropyLoss()
> # optimizer
> optim = torch.optim.SGD(Net.parameters(), lr=0.001, momentum=0)
> ```
>
> 

## Model Training (진행중)

> ```python
> # Req. 1-4	모델 학습
> epochs = 100  #define epochs
> 
> # 1) for문으로 epochs 만큼 반복
> for epoch in range(epochs):  # epochs 횟수만큼 반복
>     
>     # loss값 누적 
>     running_loss = 0.0
>     
>     # 2) for문으로 trainset이 저장되어 있는 trainloader에서 배치 사이즈 만큼씩 샘플링하여 data load
>     for i, data in enumerate(trainloader, 0):
>         
>         # 3) load한 data에서 input 값과 label로 분리하여 저장
>         inputs, labels = data
>        
>         # 4) 각각의 값을 device에 올린다 (GPU or CPU)
>         inputs, labels = inputs.cuda(), labels.cuda()
> 
>         # 5) optimizer에서 gradient 값 0으로 초기화
>         optimizer.zero_grad()
> 
>         # 6) model에 input값을 입력하여 forward propagation
>         outputs = net(input)
> 
>         # 7)  loss function으로 예측값과 label 비교
>         loss = loss_func(outputs, labels)
>         
>         # 8) loss 값 backpropagation 하여 gradient 계산
> 
>         
>         # 9) 계산된 gradient를 모두 parameter에 적용
> 
> 
>         # 10) loss 값을 합하여 일정 주기(ex.2000 batch) 마다 평균 loss 값 출력 후 초기화
> 
> 
> # 12) torch.save로 학습이 마친 이후 모델 저장        
> 
> 
> print('Finished Training')
> ```
>
> (0831) 자료를 더 찾아보고, 이해를 필요로 함

