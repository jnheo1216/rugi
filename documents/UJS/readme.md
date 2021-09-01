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

---

## 0831

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

> 
>
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

---

## 09/01

## Model Training 

> 
>
> ```python
> # Req. 1-4	모델 학습
> epochs = 1  #define epochs
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
>         inputs, labels = inputs.to(device), labels.to(device)
> 
> 
>         # 5) optimizer에서 gradient 값 0으로 초기화
>         optim.zero_grad()
> 
>         # 6) model에 input값을 입력하여 forward propagation
>         outputs = classifier(inputs)
> 
>         # 7)  loss function으로 예측값과 label 비교
>         loss = loss_func(outputs, labels)
>         
>         # 8) loss 값 backpropagation 하여 gradient 계산
>         loss.backward()
>         
>         # 9) 계산된 gradient를 모두 parameter에 적용
>         optim.step()
> 
>         # 10) loss 값을 합하여 일정 주기(ex.2000 batch) 마다 평균 loss 값 출력 후 초기화
>         running_loss += loss.item()
>         if i % 2000 == 1999:    # print every 2000 mini-batches
>             print('[%d, %5d] loss: %.3f' %
>                   (epoch + 1, i + 1, running_loss / 2000))
>             running_loss = 0.0
> 
> # 12) torch.save로 학습이 마친 이후 모델 저장        
> PATH = "./cifar_net.pth"
> torch.save(classifier.state_dict(), PATH)
> 
> print('Finished Training')
> ```
>
> ```python
> # Req. 1-5	모델 테스트
> # 만약 저장한 모델을 load해야 한다면, 모델의 인스턴스를 생성하고, 모델의 weight이 저장되어 있는 .ckpt 파일을 모델에 load
> new_classifier = Classifier()
> new_classifier.load_state_dict(torch.load('cifar_net.pth'))
> new_classifier.to(device)
> 
> # 1) 모델을 evaluation 모드로 전환
> 
> correct = 0
> total = 0
> # 2) with torch.no_grad로 gradient 계산을 제외
> with torch.no_grad():
> 
>     # 3) for문으로 testset에 저장되어 있는 testloader에서 배치 사이즈 만큼씩 샘플링하여 data load
>     for data in testloader:
> 
>         
>          # 4) load한 data에서 input 값과 label로 분리하여 저장
>         images, labels = data
> 
>        
>         # 5) 각각의 값을 device에 올린다 (GPU or CPU)
>         images, labels = images.to(device), labels.to(device)
> 
> 
>         # 6) model에 input값을 입력하여 forward propagation
>         outputs = new_classifier(images)
> 
> 
>         # 7) 예측한 값들 중 가장 높은 확률의 class 선택
>         _, predicted = torch.max(outputs.data, 1)
> 
> 
>         # 8) label과 예측한 class 비교하여 정답 확인
>         total += labels.size(0)
>         correct += (predicted == labels).sum().item()
> 
> 
> # 9) 정답률 출력
> print(
>     "Accuracy of the network on the 10000 test images: %d %%" % (100 * correct / total)
> )
> 
> ```
>
> 

## 간단 개념 정리

**CNN**

> 일부 정보만으로도 판별할 수 있는 융통성있는 AI를 만들기 위함?
>
> **Overfitting** 정확하지만 융통성이 없어 오히려 오차가 너무 커져버리는
>
> fully-connected-layer : 너무 정확하게 트레이닝되어 있을 문제점이 있다.
>
> 윤곽을 먼저 생각한다. image의 특징(feature)를 뽑아낸다.
> (필터를 뉴런에 전달을 해준다. [전처리] - overfitting 해결)
>
> 
>
> 효율
>
> ### 1. Stride
>
> 일일히 돌지 않고, 보다 넓은 간격으로 정보를 찾는다?
>
> ### 2. maxpooling
>
> 필터링된 윤곽의 이미지 사이즈를 줄이기 위함
>
> ex) 2x2행렬의 max값을 뽑고 각 max값을 모아 새로운 행렬을 생성한다.
> 이는 후에 cnn(fully-connected-layer)를 통과시킬때 시간방면에 유리

## Colab

> ## 추론 / 학습 재개를 위해 일반 체크포인트(checkpoint) 저장하기 & 불러오기
>
> ### save
>
> ```
> torch.save({
>             'epoch': epoch,
>             'model_state_dict': model.state_dict(),
>             'optimizer_state_dict': optimizer.state_dict(),
>             'loss': loss,
>             ...
>             }, PATH)
> ```
>
> ### load
>
> ```
> model = TheModelClass(*args, **kwargs)
> optimizer = TheOptimizerClass(*args, **kwargs)
> 
> checkpoint = torch.load(PATH)
> model.load_state_dict(checkpoint['model_state_dict'])
> optimizer.load_state_dict(checkpoint['optimizer_state_dict'])
> epoch = checkpoint['epoch']
> loss = checkpoint['loss']
> 
> model.eval()
> # - or -
> model.train()
> ```



## :book: 라이브러리

#### :checkered_flag: numpy

> numpy는 머신러닝 코드 개발 할 경우 벡터, 행렬등을 표현하고 연산하는 라이브러리
>
> ​	**numpy** vs **list**
> ​	머신러닝에서 숫자, 사람, 동물을 인식하기 위해서 이미지 데이터를 행렬로 변환하는것이 중요.
> ​	행렬을 나타내긱 위해서는 리스트를 사용할 수 있지만, 행렬 연산이 직관적이지 않고 오류 가능성이 높다
>
> **벡터 생성**
> vector는 np.array([ . . . ]) 을 사용하여 생성한다.
> 머신러닝 코드 구현 시, 연산을 위해서 vector, matrix 등의 형상(shape), 차원(dimension)을 확인하는것이 필요
>
> **행렬 생성**
> matrix는 vector와 마찬가지로 np.array([ [...], [...], [...], [...], ... ])를 사용하여 생성
> 	형변환 (reshape)
>
> ```python
> D = np.array([[1, 2, 3], [4, 5, 6]])
> # 2x3 행렬
> D.reshape(3,2)
> # D = [[1, 2], [3, 4], [5, 6]]
> # 3x2 행렬
> ```
>
> 
>
> ### 행렬 곱 (dot product)
>
> 행렬 A의 열 벡터와 B 행렬의 행 벡터가 같아야 곱이 가능
> 가능하지 않다면 **reshape** 또는 **transpose(전치행렬)** 등을 사용하여 형 변환을 한 후에 행렬 곱을 해야함
>
> *전치 행렬 : 열은 행으로, 행은 열로
>
> **필요**
>
> 행렬곱 조건을 만족하는 다양한 크기의 행렬을 연속으로 만들고
> 행렬 곱을 연속으로 계산할 수 있게 해주며
> 결과 값을 만들 수 있기 때문에 머신러닝과 이미지 프로세싱에 자주 사용
>
> Broadcast : 크기가 다른 두 행렬간에도 사칙연산을 할 수 있게 해줌
> 차원이 작은 쪽이 큰 쪽의 행단위로 반복적으로 크기를 맞춘 후에 계산
>
> ​	**행렬곱엔 적용이 안됨**
>
> ```python
> # ex)
> A = np.array([[1, 2], [3, 4]])
> b = 5 # ([5, 5], [5, 5])로 인식
> ```
>
> 

#### :checkered_flag: matplotlib

> 그래프를 그리는 라이브러리

### :checkered_flag: Tensor

> NumPy는 훌륭한 프레임워크지만, GPU를 사용하여 수치 연산을 가속화할 수는 없습니다.
> 현대의 심층 신경망에서 GPU는 종종 [50배 또는 그 이상](https://github.com/jcjohnson/cnn-benchmarks) 의 속도 향상을 제공하기 때문에, 안타깝게도 NumPy는 현대의 딥러닝에는 충분치 않습니다.
>
> PyTorch 텐서(Tensor)는 개념적으로 NumPy 배열과 동일합니다: 텐서(Tensor)는 n-차원 배열이며, PyTorch는 이러한 텐서들의 연산을 위한 다양한 기능들을 제공합니다. 
>
> PyTorch 텐서는 GPU를 사용하여 수치 연산을 가속할 수 있습니다. PyTorch 텐서를 GPU에서 실행하기 위해서는 단지 적절한 장치를 지정해주기만 하면 됩니다
