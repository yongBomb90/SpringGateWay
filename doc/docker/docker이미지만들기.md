1. 네트워크 만들기
   1. docker network create --gateway 172.18.0.1 --subnet 172.18.0.0/16 ecommerce-network
2. Rabbit MQ
   1. docker run -d --name rabbitmq --network ecommerce-network -p 15672:15672 -p 5672:5672 -p 15671:15671 -p 5671:5671 -p 4369:4369 -e RABBITMQ_DEFAULT_USER=guest -e RABBITMQ_DEFAULT_PASS=guest rabbitmq:management
   2. docker ps -a 프로세스 확인
3. config 서버 시작
   1. docker run -d -p 8888:8888 --network ecommerce-network -e "spring.rabbitmq.host=rabbitmq" -e "spring.profiles.active=default" --name config-service yongbomb/config-service:1.0
4. discovey 서버 시작
   1. docker run -d -p 8761:8761 --network ecommerce-network -e "spring.cloud.config.uri=http://config-service:8888" --name discovery-service yongbomb/discovery-service:1.0
5. API Gateway 서버 시작
   1. docker run -d -p 8000:8000 --network ecommerce-network -e "spring.cloud.config.uri=http://config-service:8888" -e "spring.rabbitmq.host=rabbitmq" -e "eureka.client.serviceUrl.defaultZone=http://discovery-service:8761/eureka/" --name apigateway-service yongbomb/apigateway-service:1.0
6. mariaDB 서버 시작
   1. docker run -d -p 3306:3306  --network ecommerce-network --name mariadb yongbomb/my-mariadb:1.0
7. kafka 서버 시작
   1. https://github.com/wurstmeister/kafka-docker - 도커 컴포즈
   2. 해당 컴포즈로 build
8. zipkin
   1. docker run -d -p 9411:9411 --network ecommerce-network --name zipkin openzipkin/zinkin
9. prometheous
   1. docker run -d -p 9090:9090 --network ecommerce-network --name prometheus -v /Users/bag-yongbeom/develop/git/Inflearn_msa/doc/docker/prometeous/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus
10. grafana
    1. docker run -d -p 3000:3000 --network ecommerce-network --name grafana grafana/grafana
11. user-service
    1. docker run -d --network ecommerce-network --name user-service -e "spring.cloud.config.uri=http://config-service:8888" -e "spring.rabbitmq.host=rabbitmq" \-e "spring.zipkin.base-url=http://zipkin:9411" -e "eureka.client.serviceUrl.defaultZone=http://discovery-service:8761/eureka/" -e "logging.file=/api-logs/users-ws.log" yongbomb/user-service
12. order-service
    1. docker run -d --network ecommerce-network --name order-service -e "spring.zipkin.base-url=http://zipkin:9411" -e "eureka.client.serviceUrl.defaultZone=http://discovery-service:8761/eureka/" -e "spring.datasource.url=jdbc:mariadb://mariadb:3306/mydb" -e "logging.file=/api-logs/orders-ws.log" yongbomb/order-service
13. catalog-service
    1. docker run -d --network ecommerce-network --name catalog-service -e "eureka.client.serviceUrl.defaultZone=http://discovery-service:8761/eureka/" -e "logging.file=/api-logs/catalogs-ws.log" yongbomb/catalog-service
