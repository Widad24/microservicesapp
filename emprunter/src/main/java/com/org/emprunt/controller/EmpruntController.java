package com.org.emprunt.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.org.emprunt.DTO.EmpruntDetailsDTO;
import com.org.emprunt.entities.Emprunter;
import com.org.emprunt.service.EmpruntService;

@RestController
@RequestMapping("/api/emprunts")
public class EmpruntController {

    private final EmpruntService service;

    public EmpruntController(EmpruntService service) {
        this.service = service;
    }

    //
    @PostMapping
    public ResponseEntity<Emprunter> createEmprunt(@RequestBody Map<String, Long> request) {
        Long userId = request.get("userId");
        Long bookId = request.get("bookId");
        Emprunter emprunt = service.createEmprunt(userId, bookId);
        return ResponseEntity.ok(emprunt);
    }

    @PostMapping("/{userId}/{bookId}")
    public Emprunter emprunterBook(
            @PathVariable Long userId,
            @PathVariable Long bookId) {
        return service.createEmprunt(userId, bookId);
    }

    @GetMapping
    public List<EmpruntDetailsDTO> getAllEmprunts() {
        return service.getAllEmprunts();
    }
}