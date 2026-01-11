package com.org.emprunt.kafka;

import com.org.emprunt.DTO.EmpruntEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final String TOPIC = "emprunt-created";

    private final KafkaTemplate<String, EmpruntEvent> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, EmpruntEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEmpruntEvent(EmpruntEvent event) {
        kafkaTemplate.send(TOPIC, event);
        System.out.println("Event sent to Kafka: " + event);
    }
}