- 다운 사이트 : https://www.apache.org/dyn/closer.cgi?path=/kafka/3.1.0/kafka_2.13-3.1.0.tgz
- .\kafka-topics.bat --bootstrap-server localhost:9092 --create --topic quickstart-events --partitions 1
- >kafka-console-producer.bat --broker-list localhost:9092 --topic quickstart-events
  > 
> kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic quickstart-events --from-beginning
> 
> 
> C:\dev\kafka_2.13-3.1.0\confluentinc-kafka-connect-jdbc-10.3.1\lib\kafka-connect-jdbc-10.3.1.jar
> 
> .\connect-distributed.bat ..\..\etc\kafka\connect-distributed.properties
> 