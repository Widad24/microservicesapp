package com.org.emprunt.service;

import com.org.emprunt.DTO.EmpruntDetailsDTO;
import com.org.emprunt.DTO.EmpruntEvent;
import com.org.emprunt.entities.Emprunter;
import com.org.emprunt.feign.BookClient;
import com.org.emprunt.feign.UserClient;
import com.org.emprunt.kafka.KafkaProducerService;
import com.org.emprunt.repositories.EmpruntRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class EmpruntService {

    private final EmpruntRepository repo;
    private final UserClient userClient;
    private final BookClient bookClient;
    private final KafkaProducerService kafkaProducer; // ← AJOUT

    public EmpruntService(EmpruntRepository repo,
            UserClient userClient,
            BookClient bookClient,
            KafkaProducerService kafkaProducer) { // ← AJOUT
        this.repo = repo;
        this.userClient = userClient;
        this.bookClient = bookClient;
        this.kafkaProducer = kafkaProducer; // ← AJOUT
    }

    public Emprunter createEmprunt(Long userId, Long bookId) {

        // 1. Vérifier user existe
        userClient.getUser(userId);

        // 2. Vérifier book existe
        bookClient.getBook(bookId);

        // 3. Créer l'emprunt
        Emprunter b = new Emprunter();
        b.setUserId(userId);
        b.setBookId(bookId);

        // 4. Sauvegarder dans MySQL
        Emprunter saved = repo.save(b);

        // 5. ✨ ENVOYER L'ÉVÉNEMENT KAFKA
        EmpruntEvent event = new EmpruntEvent(
                saved.getId(),
                saved.getUserId(),
                saved.getBookId(),
                "EMPRUNT_CREATED",
                LocalDateTime.now());

        kafkaProducer.sendEmpruntEvent(event);

        return saved;
    }

    public List<EmpruntDetailsDTO> getAllEmprunts() {
        return repo.findAll().stream().map(e -> {

            var user = userClient.getUser(e.getUserId());
            var book = bookClient.getBook(e.getBookId());

            return new EmpruntDetailsDTO(
                    e.getId(),
                    user.getName(),
                    book.getTitle(),
                    e.getEmpruntDate());
        }).collect(Collectors.toList());
    }

}