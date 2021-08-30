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
