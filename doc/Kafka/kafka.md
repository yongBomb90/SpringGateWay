- 다운 사이트 : https://www.apache.org/dyn/closer.cgi?path=/kafka/3.1.0/kafka_2.13-3.1.0.tgz  
- 실행순서  
  - zookeeper 실행 : ``$KAFKA_HOME/bin/kafka-server-start.sh  $KAFKA_HOME/config/server.properties``
  - Kafka 실행 : ``$KAFKA_HOME/bin/kafka-server-start.sh  $KAFKA_HOME/config/server.properties``
  - 토픽 생성 : ``$KAFKA_HOME/bin/kafka-topics.sh --create --topic quickstart-events --bootstrap-server localhost:9092 
--partitions 1``  
  - 토픽 목록 확인 : ``$KAFKA_HOME/bin/kafka-topics.sh --bootstrap-server localhost:9092 --list``
  - 토픽 상세 확인 : ``$KAFKA_HOME/bin/kafka-topics.sh --describe --topic quickstart-events --bootstrap-server localhost:9092``
  - 프로듀서 생성 : ``$KAFKA_HOME/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic quickstart-events``
  - 컨슈머 생성 : ``$KAFKA_HOME/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic quickstart-events --from-beginning``
  - kafka connect(confluent-community) 실행 : ``$KAFKA_CONNECT_HOME/bin/connect-distributed $KAFKA_CONNECT_HOME/etc/kafka/connect-distributed.properties``
- 데이터 베이스(JDBC) APi
  - 토픽생성 - [POST맨 Kafka](https://galactic-eclipse-898382.postman.co/workspace/MSA~50d313ee-e94b-4f84-8751-c6c009998c24/request/19066531-e3def59a-3f72-4155-a01c-d604f59a2423)
  - 토픽프로듀서입력 - [POST맨 Kafka list](https://galactic-eclipse-898382.postman.co/workspace/MSA~50d313ee-e94b-4f84-8751-c6c009998c24/request/19066531-8f3462f5-314f-4e3b-8c81-11efd2d1a385)

>해당처리는 추후 자세한 내용이 필요할듯 