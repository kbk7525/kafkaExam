package com.example.hello.service;

import com.example.hello.dto.LogDto;
import com.example.hello.entity.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Producer {

    @Value("${kafka.topic.name}")
    private String topic;
    private final KafkaTemplate<String, Log> kafkaTemplate;

    @Autowired
    public Producer(KafkaTemplate<String, Log> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(Log data) {
        this.kafkaTemplate.send(topic, data);
        log.info("send msg : " + data);
    }
}