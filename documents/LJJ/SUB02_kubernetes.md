# Kubernetes

> K8s라고도 알려진 쿠버네티스는 컨테이너화된 애플리케이션을 자동으로 배포, 스케일링 및 관리해주는 오픈소스 시스템

https://kubernetes.io/ko/docs/concepts/overview/what-is-kubernetes/#여정-돌아보기

전통적 배포 → 가상머신(개별 OS)

### → 컨테이너(OS 공유)

VM과 유사하지만 격리 속성을 완화하여 애플리케이션 간에 운영체제(OS)를 공유/ 가볍다

자체 파일 시스템, CPU 점유율, 메모리, 프로세스 공간 등이 있다. 기본 인프라와의 종속성을 끊었기 때문에, 클라우드나 OS 배포본에 모두 이식할 수 있음

- 컨테이너 이미지 생성이 보다 쉽고 효율적
- 지속적인 개발, 통합 및 배포: 효율적으로 롤백
- 개발과 운영의 관심사 분리
  - 배포 시점이 아닌 빌드/릴리스 시점에 애플리케이션 컨테이너 이미지를 만들기 때문에, 애플리케이션이 인프라스트럭처에서 분리
- 개발, 테스팅 및 운영 환경에 걸친 일관성
- 클라우드 및 OS 배포판 간 이식성
- 애플리케이션 중심 관리
- 독립적인 단위로 쪼개져서 동적으로 배포되고 관리될 수 있다.
- 리소스 격리: 애플리케이션 성능을 예측할 수 있다.
- 자원 사용량: 리소스 사용량: 고효율 고집적.

### → 쿠버네티스

예를 들어 컨테이너가 다운되면 다른 컨테이너를 다시 시작해야 한다. 이 문제를 시스템에 의해 처리한다면 더 쉽지 않을까?

쿠버네티스는 분산 시스템을 탄력적으로 실행하기 위한 프레임 워크를 제공한다. 애플리케이션의 확장과 장애 조치를 처리하고, 배포 패턴 등을 제공

- Kubernetes can ...

  - **서비스 디스커버리와 로드 밸런싱**

    쿠버네티스는 DNS 이름을 사용하거나 자체 IP 주소를 사용하여 컨테이너를 노출할 수 있다.

    컨테이너에 대한 트래픽이 많으면, 쿠버네티스는 네트워크 트래픽을 로드밸런싱하고 배포

  - **스토리지 오케스트레이션**

    로컬 저장소, 공용 클라우드 공급자 등과 같이 원하는 저장소 시스템을 자동으로 탑재

  - **자동화된 롤아웃과 롤백**

    배포된 컨테이너의 원하는 상태를 서술할 수 있으며 현재 상태를 원하는 상태(desire state)로 설정한 속도에 따라 변경

    *예를 들어 쿠버네티스를 자동화해서 배포용 새 컨테이너를 만들고, 기존 컨테이너를 제거하고, 모든 리소스를 새 컨테이너에 적용할 수 있다.*

  - **자동화된 빈 패킹(bin packing)**

    컨테이너화된 작업을 실행하는데 사용할 수 있는 쿠버네티스 클러스터 노드를 제공

    각 컨테이너가 필요로 하는 CPU와 메모리(RAM)를 쿠버네티스에게 지시

    쿠버네티스는 컨테이너를 노드에 맞추어서 리소스를 가장 잘 사용할 수 있도록 해줌

  - **자동화된 복구(self-healing)**

    쿠버네티스는 실패한 컨테이너를 다시 시작하고, 컨테이너를 교체하며, '사용자 정의 상태 검사'에 응답하지 않는 컨테이너를 죽이고, 서비스 준비가 끝날 때까지 그러한 과정을 클라이언트에 보여주지 않는다.

  - **시크릿과 구성 관리**

    쿠버네티스를 사용하면 암호, OAuth 토큰 및 SSH 키와 같은 중요한 정보를 저장하고 관리 가능

    컨테이너 이미지를 재구성하지 않고 스택 구성에 시크릿을 노출하지 않고도 시크릿 및 애플리케이션 구성을 배포 및 업데이트 가능

쿠버네티스는 전통적인, 모든 것이 포함된 Platform as a Service(PaaS)가 아니다

> 컨테이너 수준에서 운영되기 때문에, PaaS가 일반적으로 제공하는 배포, 스케일링, 로드 밸런싱과 같은 기능을 제공하며, 사용자가 로깅, 모니터링 및 알림 솔루션을 통합할 수 있다. 하지만, 쿠버네티스는 모놀리식(monolithic)이 아니어서, 이런 기본 솔루션이 선택적이며 추가나 제거가 용이하다.

> 쿠버네티스는 독립적이고 조합 가능한 제어 프로세스들로 구성되어 있다. 이 프로세스는 지속적으로 현재 상태를 입력받은 의도한 상태로 나아가도록 한다

### 쿠버네티스 컴포넌트

쿠버네티스를 배포하면 **클러스터**를 얻는다.

쿠버네티스 클러스터는

컨테이너화된 애플리케이션을 실행하는 [노드](https://kubernetes.io/ko/docs/concepts/architecture/nodes/)라고 하는 워커 머신의 집합.

모든 클러스터는 최소 한 개의 워커 노드를 가진다.

**워커 노드**는 애플리케이션의 구성요소인 **파드**를 호스트한다. **컨트롤 플레인**은 워커 노드와 클러스터 내 파드를 **관리**한다.

https://kubernetes.io/ko/docs/concepts/overview/components/

## **컨트롤 플레인 컴포넌트**

컨트롤 플레인 컴포넌트는 클러스터에 관한 전반적인 결정(예를 들어, 스케줄링)을 수행하고 클러스터 이벤트(예를 들어, 디플로이먼트의 `replicas` 필드에 대한 요구 조건이 충족되지 않을 경우 새로운 [파드](https://kubernetes.io/ko/docs/concepts/workloads/pods/)를 구동시키는 것)를 감지하고 반응한다.

### **kube-apiserver**

API 서버는 쿠버네티스 API를 노출하는 쿠버네티스 [컨트롤 플레인](https://kubernetes.io/ko/docs/reference/glossary/?all=true#term-control-plane) 컴포넌트이다. API 서버는 쿠버네티스 컨트롤 플레인의 프론트 엔드이다.

클러스터의 다른 부분 그리고 외부 컴포넌트가 서로 통신할 수 있도록 HTTP API를 제공

쿠버네티스 API를 사용하면 쿠버네티스의 API 오브젝트(예: 파드(Pod), 네임스페이스(Namespace), 컨피그맵(ConfigMap) 그리고 이벤트(Event))를 질의(query)하고 조작할 수 있다.

대부분의 작업은 kubectl 커맨드 라인 인터페이스 또는 API를 사용하는 kubeadm과 같은 다른 커맨드 라인 도구를 통해 수행

[OpenAPI 명세](https://kubernetes.io/ko/docs/concepts/overview/kubernetes-api/#api-specification) 🔗

### **etcd**

모든 클러스터 데이터를 담는 쿠버네티스 뒷단의 저장소로 사용되는 일관성·고가용성 키-값 저장소.

### **kube-scheduler**

[노드](https://kubernetes.io/ko/docs/concepts/architecture/nodes/)가 배정되지 않은 새로 생성된 [파드](https://kubernetes.io/ko/docs/concepts/workloads/pods/) 를 감지하고, 실행할 노드를 선택하는 컨트롤 플레인 컴포넌트.

### **kube-controller-manager**

[컨트롤러](https://kubernetes.io/ko/docs/concepts/architecture/controller/) 프로세스를 실행하는 컨트롤 플레인 컴포넌트.

## **노드 컴포넌트**

노드 컴포넌트는 동작 중인 파드를 유지시키고 쿠버네티스 런타임 환경을 제공하며, 모든 노드 상에서 동작한다.

### **kubelet**

클러스터의 각 [노드](https://kubernetes.io/ko/docs/concepts/architecture/nodes/)에서 실행되는 에이전트. Kubelet은 [파드](https://kubernetes.io/ko/docs/concepts/workloads/pods/)에서 [컨테이너](https://kubernetes.io/ko/docs/concepts/containers/)가 확실하게 동작하도록 관리한다.

### **kube-proxy**

kube-proxy는 클러스터의 각 [노드](https://kubernetes.io/ko/docs/concepts/architecture/nodes/)에서 실행되는 네트워크 프록시로, 쿠버네티스의 [서비스](https://kubernetes.io/docs/concepts/services-networking/service/) 개념의 구현부이다.

### **컨테이너 런타임**

컨테이너 런타임은 컨테이너 실행을 담당하는 소프트웨어이다.

## **애드온**

애드온은 쿠버네티스 리소스([데몬셋](https://kubernetes.io/ko/docs/concepts/workloads/controllers/daemonset), [디플로이먼트](https://kubernetes.io/ko/docs/concepts/workloads/controllers/deployment/) 등)를 이용하여 클러스터 기능을 구현한다. 이들은 클러스터 단위의 기능을 제공하기 때문에 애드온에 대한 네임스페이스 리소스는 `kube-system` 네임스페이스에 속한다.

### **DNS**

여타 애드온들이 절대적으로 요구되지 않지만, 많은 예시에서 필요로 하기 때문에 모든 쿠버네티스 클러스터는 [클러스터 DNS](https://kubernetes.io/ko/docs/concepts/services-networking/dns-pod-service/)를 갖추어야만 한다.

클러스터 DNS는 구성환경 내 다른 DNS 서버와 더불어, 쿠버네티스 서비스를 위해 DNS 레코드를 제공해주는 DNS 서버다.

쿠버네티스에 의해 구동되는 컨테이너는 DNS 검색에서 이 DNS 서버를 자동으로 포함한다.

### **웹 UI (대시보드)**

[대시보드](https://kubernetes.io/ko/docs/tasks/access-application-cluster/web-ui-dashboard/)는 쿠버네티스 클러스터를 위한 범용의 웹 기반 UI다. 사용자가 클러스터 자체뿐만 아니라, 클러스터에서 동작하는 애플리케이션에 대한 관리와 문제 해결을 할 수 있도록 해준다.

### **컨테이너 리소스 모니터링**

[컨테이너 리소스 모니터링](https://kubernetes.io/ko/docs/tasks/debug-application-cluster/resource-usage-monitoring/)은 중앙 데이터베이스 내의 컨테이너들에 대한 포괄적인 시계열 매트릭스를 기록하고 그 데이터를 열람하기 위한 UI를 제공해 준다.

### **클러스터-레벨 로깅**

[클러스터-레벨 로깅](https://kubernetes.io/ko/docs/concepts/cluster-administration/logging/) 메커니즘은 검색/열람 인터페이스와 함께 중앙 로그 저장소에 컨테이너 로그를 저장하는 책임을 진다.



---

#  Kubernets 설치 및 클러스터 생성, 조인

### AWS에 kubectl 설치

쿠버네티스 커맨드 라인 도구인 kubectl을 사용하면, 쿠버네티스 클러스터에 대해 명령을 실행할 수 있다. kubectl을 사용하여 애플리케이션을 배포하고, 클러스터 리소스를 검사 및 관리하며 로그를 볼 수 있다.

### **리눅스에서 curl을 사용하여 kubectl 바이너리 설치**

- learn more

  1. 다음 명령으로 최신 릴리스를 다운로드한다.

     ```bash
     curl -LO "<https://storage.googleapis.com/kubernetes-release/release/$>(curl -s <https://storage.googleapis.com/kubernetes-release/release/stable.txt>)/bin/linux/amd64/kubectl"
     ```

  2. kubectl 바이너리를 실행 가능하게 만든다.

     ```bash
     chmod +x ./kubectl
     ```

  3. 바이너리를 PATH가 설정된 디렉터리로 옮긴다.

     ```bash
     sudo mv ./kubectl /usr/local/bin/kubectl
     ```

  4. 설치한 버전이 최신 버전인지 확인한다.

     ```bash
     kubectl version --client
     ```

     ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/44fe1d81-00c7-4299-881f-cfe911c2ef7c/Untitled.png)

  ## **kubectl 구성 확인**

  kubectl이 쿠버네티스 클러스터를 찾아 접근하려면, [kube-up.sh](https://github.com/kubernetes/kubernetes/blob/master/cluster/kube-up.sh)를 사용하여 클러스터를 생성하거나 Minikube 클러스터를 성공적으로 배포할 때 자동으로 생성되는 [kubeconfig 파일](https://v1-18.docs.kubernetes.io/ko/docs/concepts/configuration/organize-cluster-access-kubeconfig/)이 필요하다. 기본적으로, kubectl 구성은 `~/.kube/config` 에 있다.

  클러스터 상태를 가져와서 kubectl이 올바르게 구성되어 있는지 확인한다.

  `kubectl cluster-info`

  ### 자동완성..

  `kubectl completion bash` : bash-completion먼저 설치

  `echo 'source <(kubectl completion bash)' >>~/.bashrc`

  : ~/.bashrc 파일에서 완성 스크립트를 소싱한다.

  `kubectl completion bash >/etc/bash_completion.d/kubectl`

  완성 스크립트를 /etc/bash_completion.d 디렉터리에 추가한다.

## 시작하기

## **kubeadm**

[kubeadm](https://kubernetes.io/docs/admin/kubeadm/) 도구를 사용하여 쿠버네티스 클러스터를 만들고 관리할 수 있다.

[kubeadm 설치](https://kubernetes.io/ko/docs/setup/production-environment/tools/kubeadm/install-kubeadm/). 설치가 끝나면, [클러스터 생성](https://kubernetes.io/docs/setup/production-environment/tools/kubeadm/create-cluster-kubeadm/)이 가능하다.

## **런타임 설치**

- learn more

  파드에서 컨테이너를 실행하기 위해, 쿠버네티스는 [컨테이너 런타임](https://kubernetes.io/docs/setup/production-environment/container-runtimes)을 사용한다.

  ```bash
  도커와 containerd가 모두 감지되면 도커가 우선시된다. 
  이것이 필요한 이유는 도커 18.09에서 도커만 설치한 경우에도 containerd와 함께 제공되므로 
  둘 다 감지될 수 있기 때문이다. 
  다른 두 개 이상의 런타임이 감지되면, kubeadm은 오류와 함께 종료된다.
  ```

  ~~운영환경 : 컨테이너 런타임~~

  운영 환경: Containerd

  파드에서 컨테이너를 실행하기 위해 쿠버네티스는 컨테이너 런타임을 사용

  > 모든 명령은 root에서 실행

  ## **Containerd**

  이 섹션은 `containerd`를 CRI 런타임으로써 사용하는데 필요한 단계를 담고 있다.

  Containerd를 시스템에 설치하기 위해서 다음의 커맨드들을 사용한다.

  ### **선행 조건**

  ```bash
  cat > /etc/modules-load.d/containerd.conf <<EOF
  overlay
  br_netfilter
  EOF
  
  modprobe overlay
  modprobe br_netfilter
  
  # 요구되는 sysctl 파라미터 설정, 이 설정은 재부팅해도 유지된다.
  cat > /etc/sysctl.d/99-kubernetes-cri.conf <<EOF
  net.bridge.bridge-nf-call-iptables  = 1
  net.ipv4.ip_forward                 = 1
  net.bridge.bridge-nf-call-ip6tables = 1
  EOF
  
  sysctl --system
  ```

  ### 설치

  ```bash
  # 공식 도커 리포지터리에서 containerd.io 패키지를 설치
  # 각 리눅스 배포판에 대한 도커 리포지터리를 설정하고 containerd.io 패키지를 설치하는 방법은 
  # 도커 엔진 설치에서 찾을 수 있다.
  sudo apt-get update
  sudo apt-get install \\
      apt-transport-https \\
      ca-certificates \\
      curl \\
      gnupg \\
      lsb-release
  # Docker의 공식 GPG 키를 시스템에 추가
  curl -fsSL <https://download.docker.com/linux/ubuntu/gpg> | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
  # Docker 설치
  echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
  
  sudo apt-get update
  # containerd.io를 설치
  sudo apt-get install containerd.io docker-ce docker-ce-cli
  
  # containerd 설정
  sudo mkdir -p /etc/containerd
  containerd config default | sudo tee /etc/containerd/config.toml
  
  #containerd 재시작
  sudo systemctl restart containerd
  ```



## **kubeadm, kubelet 및 kubectl 설치**

- learn more

  모든 머신에 다음 패키지들을 설치한다.

  - `kubeadm`: 클러스터를 부트스트랩하는 명령이다.
  - `kubelet`: 클러스터의 모든 머신에서 실행되는 파드와 컨테이너 시작과 같은 작업을 수행하는 컴포넌트이다.
  - `kubectl`: 클러스터와 통신하기 위한 커맨드 라인 유틸리티이다.

  ```bash
  kubeadm은 kubelet 또는 kubectl 을 설치하거나 관리하지 않으므로, 
  kubeadm이 설치하려는 쿠버네티스 컨트롤 플레인의 버전과 일치하는지 확인해야 한다. 
  그렇지 않으면, 예상치 못한 버그 동작으로 이어질 수 있는 버전 차이(skew)가 발생할 위험이 있다. 
  그러나, kubelet과 컨트롤 플레인 사이에 하나의 마이너 버전 차이가 지원되지만, 
  kubelet 버전은 API 서버 버전 보다 높을 수 없다. 
  예를 들어, 1.7.0 버전의 kubelet은 1.8.0 API 서버와 완전히 호환될 수 있지만, 
  그 반대의 경우는 아니다.
  ```

  1. `apt` 패키지 색인을 업데이트하고, 쿠버네티스 `apt` 리포지터리를 사용하는 데 필요한 패키지를 설치한다.

     `sudo apt-get update sudo apt-get install -y apt-transport-https ca-certificates curl`

  2. 구글 클라우드의 공개 사이닝 키를 다운로드 한다.

     `sudo curl -fsSLo /usr/share/keyrings/kubernetes-archive-keyring.gpg https://packages.cloud.google.com/apt/doc/apt-key.gpg`

  3. 쿠버네티스 `apt` 리포지터리를 추가한다.

     `echo "deb [signed-by=/usr/share/keyrings/kubernetes-archive-keyring.gpg] https://apt.kubernetes.io/ kubernetes-xenial main" | sudo tee /etc/apt/sources.list.d/kubernetes.list`

  4. `apt` 패키지 색인을 업데이트하고, kubelet, kubeadm, kubectl을 설치하고 해당 버전을 고정한다.

     ```bash
      sudo apt-get update
      sudo apt-get install -y kubelet kubeadm kubectl
      sudo apt-mark hold kubelet kubeadm kubectl
     ```

     Cgroup 드라이버

     >  kubelet과 도커에 cgroupfs를 사용하고, 나머지 프로세스는 systemd를 사용하도록 노드가 설정된 경우, 리소스가 부족할 때 불안정해지는 사례를 보고했다.
     >
     > 설정 필요.

     ### **`systemd` cgroup 드라이버의 사용**

     `/etc/containerd/config.toml` 의 `systemd` cgroup 드라이버를 `runc` 에서 사용하려면, 다음과 같이 설정한다.

     ```
     [plugins."io.containerd.grpc.v1.cri".containerd.runtimes.runc]
       ...
       [plugins."io.containerd.grpc.v1.cri".containerd.runtimes.runc.options]
         SystemdCgroup = true
     ```

     이 변경 사항을 적용하는 경우 containerd를 재시작한다.

     `sudo systemctl restart containerd`

     ### kubelet cgroup driver 설정

     In v1.22, if the user is not setting the cgroupDriver field under KubeletConfiguration, kubeadm will default it to systemd.

     버전을 확인해보니 1.22.1이어서

     ```bash
     kubelet --version
     Kubernetes v1.22.1
     ```

     > systemd는 cgroupfs을 /sys/fs/cgroup 하위 경로에 Mount합니다. 따라서 systemd를 사용하는 Linux 환경에서는 User가 별도로 cgroupfs을 Mount하지 않았어도 /sys/fs/cgroup 하위 경로에 cgroupfs들이 Mount되어 있습니다.

     따로 설정할 필요가 없었다.

     Docker의 cgroup드라이버 설정은 추후에 나온다.

     

## kubeadm으로 클러스터 생성하기

- A simple way for you to try out Kubernetes, possibly for the first time.
- A way for existing users to automate setting up a cluster and test their application.
- A building block in other ecosystem and/or installer tools with a larger scope.

처음이니까 이 방법(kubeadm)을 채택합니다 😂



### ‼️ 시작하기 전에

**MAC 주소 및 product_uuid가 모든 노드에서 고유한지 확인**

- MAC 주소를 확인: `ip link` 또는 `ifconfig -a`

- product_uuid 확인 : `sudo cat /sys/class/dmi/id/product_uuid`

- RAM : 2GB 이상, CPU : 2CPU 이상

- 클러스터안의 machine들 간에 네트워크 통신이 전부 가능해야한다

  - public, private 망 둘다 이용 가능

- learn more

  ### 🥅 목표

  - Install a single control-plane Kubernetes cluster
  - Install a Pod network on the cluster so that your Pods can talk to each other

  Docker의 cgroup 관리자가 뭔지 확인한다.

  `docker info |grep Cgroup`

  cgroupfs 라면 systemd로 변경해줘야 한다.

  ```bash
  vi /lib/systemd/system/docker.service
  # ExecStart에 --exec-opt native.cgroupdriver=systemd 옵션 추가 후 
  # 도커 재실행
  systemctl daemon-reload
  systemctl restart docker
  ```

  

  ### control-plane node 초기화작업 (개인 lightsail 서버를 발급해서 사용했다)

  including etcd (the cluster database) and the API Server (which the kubectl command line tool communicates with).

  컨트롤 플레인에 해당하는 컴포넌트들은 마스터 노드에서 실행

  마스터 노드 초기화

  ```bash
  kubeadm init --apiserver-advertise-address=10.147.20.3 --pod-network-cidr=10.244.0.0/16 --ignore-preflight-errors=NumCPU
  ```

  ```bash
  --apiserver-advertise-address= # 나의 이더넷어댑터 아이피주소, api서버의 주소이기도 하다. 다른 노드들과 연결이되어있는 IP
  --pod-network-cidr= # 추후에 설치할 CNI에 따라 다르다.
  --ignore-preflight-errors=NumCPU # cpu갯수가 1개라서 에러를 무시하는 옵션
  ```

  성공 하고 나면 `Your Kubernetes control-plane has initialized successfully! 라고 뜨고`

  여러 정보를 주는데, 

  1. `kubectl` 커맨드 사용을 일반 유저가 할수 있게하는명령어
  2. CNI 설치를 해야한다는 이야기
  3. 클러스터에 워커 노드를 조인시킬때 사용할 명령어, 토큰이 포함되어있다.(토큰의 수명은 24시간 이다.)
     1. `kubeadm token list` 발급한 토큰리스트 확인
     2. `kubeadm token create` 만료후에 새로운 토큰 발급
  
  ```bash
  # 일반 유저가 kubectl 명령어 사용할수 있게	
  	mkdir -p $HOME/.kube
    sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
    sudo chown $(id -u):$(id -g) $HOME/.kube/config
  
  # if you are the root user, you can run:
    export KUBECONFIG=/etc/kubernetes/admin.conf
  
  # You should now deploy a pod network to the cluster.
  # Run "kubectl apply -f [podnetwork].yaml" with one of the options listed at:
  #  <https://kubernetes.io/docs/concepts/cluster-administration/addons/>
  
  # 이 부분을 잘 저장해두자. 비밀리에..
  kubeadm join 172.26.3.76:6443 --token 비밀 \\
  	--discovery-token-ca-cert-hash sha256:비밀
  ```

  sysctl net.bridge.bridge-nf-call-iptables=1 명령어를 실행하여 /proc/sys/net/bridge/bridge-nf-call-iptables 의 값을 1로 설정 해야한다.

  이것은 일부 CNI 플러그인이 작동하기 위한 요구 사항이다.

  쿠버네티스 동작 상태 확인

  `kubectl get po -n kube-system`

  지금은 coredns 들이 PENDING 상태 일것이다. 네트워크 에드온을 디플로이 해줘야 작동한다.

  

  ### 파드 네트워크 에드온 설치

  파드끼리 서로 통신하기 위해서는 Container Network Interface (CNI)기반 파드 네트워크 에드온을 설디플로이 해야한다.

  Cluster DNS (CoreDNS) will not start up before a network is installed.

  네트워크가 설치되기 전에는 dns가 시작 되지 않는다.

  - 파드 네트워크들과  마스터의 네트워크 대역이 같지 않도록 주의

    - `kubeadm init` 할때 `-pod-network-cidr=네트워크대역` 옵션을 줘서 네트워크 대역 지정 가능 (https://kubernetes.io/docs/reference/setup-tools/kubeadm/kubeadm-init/ 참고)

    - Calico 192.168.0.0/16

      https://docs.projectcalico.org/getting-started/kubernetes/quickstart

    - Flannel 10.244.0.0./16

  - kubeadm의 기본 세팅은 역할기반접근제어(RBAC)이다. 파드 네트워크도  RBAC 기반 플러그인인이 확인.

  ### Flannel 설치

  `kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/master/Documentation/kube-flannel.yml`

  을 설치한 후

  `kubectl get nodes` 명령어로 마스터노드 설정이 잘됐는지 확인

  `kubectl get po -n kube-system` dns 서버들이 running 중인지 확인

  ‼️ CNI는 하나만 설치해야한다. 여러개 설치해서 문제가 생기면... 이전것과 관련된걸 찾아서 지워줘야한다.
  
  ```bash
  ls /etc/cni/net.d/
  # calico 했다가 flannel로 바꿔서 생김
  10-calico.conflist  10-flannel.conflist  calico-kubeconfig
  ```
  
  상태 확인후 dns 러닝중인거 확인하고나면,
  
  ### cluster join
  
  워커노드로 가서 클러스터 생성후 받은 `kubeadm join` 명령어 입력
  
  ### 워커노드 클러스터 조인 완료 ❤️‍🔥
  
  ➕... https://kubenav.io/
  
  핸드폰으로 쿠버네티스를 관리하는 앱.



## 참고

:link: 쿠버네티스 공식문서

:link: 핀다 테크 블로그 : https://medium.com/finda-tech/overview-8d169b2a54ff

