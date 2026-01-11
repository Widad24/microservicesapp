package com.example.notification_service.service;

import com.example.notification_service.DTO.EmpruntEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "emprunt-created", groupId = "notification-group")
    public void consumeEmpruntEvent(EmpruntEvent event) {
        System.out.println("========================================");
        System.out.println("📩 NOTIFICATION REÇUE !");
        System.out.println("========================================");
        System.out.println("Type: " + event.getEventType());
        System.out.println("Emprunt ID: " + event.getEmpruntId());
        System.out.println("Utilisateur ID: " + event.getUserId());
        System.out.println("Livre ID: " + event.getBookId());
        System.out.println("Date: " + event.getTimestamp());
        System.out.println("========================================");

        // Ici vous pouvez ajouter la logique d'envoi d'email, SMS, etc.
        sendNotification(event);
    }

    private void sendNotification(EmpruntEvent event) {
        // Simulation d'envoi de notification
        System.out.println("✉️ Notification envoyée à l'utilisateur " + event.getUserId());
        System.out.println("Message: Votre emprunt du livre #" + event.getBookId() + " a été confirmé !");
    }
}