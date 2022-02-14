1. 네트워크 만들기
   1. docker network create --gateway 172.18.0.1 --subnet 172.18.0.0/16 ecommerce-network
2. Rabbit MQ
   1. docker run -d --name rabbitmq --network ecommerce-network -p 15672:15672 -p 5672:5672 -p 15671:15671 -p 5671:5671 -p 4369:4369 -e RABBITMQ_DEFAULT_USER=guest -e RABBITMQ_DEFAULT_PASS=guest rabbitmq:management
   2. docker ps -a 프로세스 확인
3. config 서버 시작
   1. docker run -d -p 8888:8888 --network ecommerce-network -e "spring.rabbitmq.host=rabbitmq" -e "spring.profiles.active=default" --name config-service yongbomb/config-service:1.0
4. discovey 서버 시작
   1. docker run -d -p 8761:8761 --network ecommerce-network -e "spring.cloud.config.uri=http://config-service:8888" --name discovery-service yongbomb/discovery-service:1.0