package com.example.hello.service;

import com.example.hello.entity.Log;
import com.example.hello.repository.LogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Consumer {
    public LogRepository logRepository;

    public Consumer(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @KafkaListener(topics = "test", groupId = "test")
    public void listen(String str) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try{
            Log data = mapper.readValue(str, Log.class);
            log.info("rcv msg : {}", data);
            logRepository.save(data);
        }catch (Exception ex) {
            throw new Exception("error", ex);
        }
    }
}
