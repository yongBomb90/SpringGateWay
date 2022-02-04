package com.example.orderservice.messagequeue;

import com.example.orderservice.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class OrderProducer {

    private KafkaTemplate<String,String> kafkaTemplate;

    List<Field> fields = Arrays.asList(
            new Field("string", true, "order_id")
            , new Field("string", true, "user_id")
            , new Field("string", true, "product_id")
            , new Field("int32", true, "qty")
            , new Field("int32", true, "unit_price")
            , new Field("int32", true, "total_price")
    );

    Schema schema = Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("orders")
            .build();

    @Autowired
    public OrderProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public OrderDTO send(String topic, OrderDTO orderDTO) {
        Payload payload = Payload.builder()
                .order_id(orderDTO.getOrderId())
                .user_id(orderDTO.getUserId())
                .product_id(orderDTO.getProductId())
                .qty(orderDTO.getQty())
                .unit_price(orderDTO.getUnitPrice())
                .total_price(orderDTO.getTotalPrice())
                .build();

        KafkaOrderDTO kafkaOrderDTO = new KafkaOrderDTO(schema,payload);

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";



        try {
            jsonInString = mapper.writeValueAsString(kafkaOrderDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        kafkaTemplate.send(topic,jsonInString);
        log.info("Kafka order producer sent >> " + kafkaOrderDTO);

        return orderDTO;

    }
}
