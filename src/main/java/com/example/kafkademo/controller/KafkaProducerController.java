package com.example.kafkademo.controller;

import com.example.kafkademo.model.LicenseType;
import com.example.kafkademo.service.KafkaProducerService;
import com.example.kafkademo.service.LicenseTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class KafkaProducerController {
    private final KafkaProducerService producerService;
    private final LicenseTypeService service;

    public KafkaProducerController(KafkaProducerService
                                           producerService, LicenseTypeService service) {
        this.producerService = producerService;
        this.service = service;
    }

    @GetMapping("/send")
    public String sendMessage(@RequestParam("message") String message) {
        producerService.sendMessage("test-topic", message);
        return "Message sent to Kafka: " + message;
    }

    @GetMapping("/send2")
    public String sendMessage2(@RequestParam("message") String message) {
        producerService.sendMessage("test-topic2", message);
        return "Message sent to Kafka topics 2: " + message;
    }

    @PostMapping
    public ResponseEntity<LicenseType> create(@RequestBody LicenseType
                                                      licenseType) {
        LicenseType saved = service.create(licenseType);
        producerService.sendMessage("license-topic",
                saved.getLicenseTypeCode() + "," + saved.getMinistryCode() + "," +
                        saved.isCanBeUsedPartially());
        return ResponseEntity.ok(saved);
    }
    @PutMapping("/{code}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable String code)
    {
        service.deactivate(code);
        return ResponseEntity.noContent().build();
    }

}
