Mac OS RabbitMQ 설치 방법

``brew update``  
``brew install rabbitmq``   
``brew service start rabbitmq``  
`` export PATH=$PATH:/usr/local/sbin ``  
`` rabbitmq-server ``

15672 포트

window  
Erlang 다운로드 및 설치
- https://erlang.org/downloads/23.1

- OTP 23.1 Windows 640bit Binary File

RabbitMQ 다운로드 및 설치
- https://rabbitmq.com/install-windows.html#installer

- rabbitmq-server-3.8.11.exe

Management Plugin 설치
C:\> rabbitmq-plugins enable rabbitmq_management