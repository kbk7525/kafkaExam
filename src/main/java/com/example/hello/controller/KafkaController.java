package com.example.hello.controller;

import com.example.hello.dto.LogDto;
import com.example.hello.entity.Log;
import com.example.hello.service.MongoService;
import com.example.hello.service.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class KafkaController {
    private final Producer producer;
    private final MongoService mongoService;
    @Autowired
    KafkaController(Producer producer, MongoService mongoService) {
        this.producer = producer;
        this.mongoService = mongoService;
    }

    //kafka에 로그 전송
    @PostMapping("/kafka/send")
    public ResponseEntity<String> message(@RequestBody Log data) {
        producer.send(data);
        return ResponseEntity.ok("success");
    }

    //mongodb에 저장된 전체 로그 조회
    @GetMapping("/logs")
    public ResponseEntity<List<LogDto>> getLogs() {
        return ResponseEntity.ok(mongoService.getData());
    }

    //특정 공격명으로 로그 조회
    @GetMapping("/logs/{attackName}")
    public ResponseEntity<List<LogDto>> getLogsByAttack(@PathVariable String attackName) {
        log.info(attackName);
        return ResponseEntity.ok(mongoService.findAttackName(attackName));
    }
}
