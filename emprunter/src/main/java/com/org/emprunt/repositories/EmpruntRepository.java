package com.org.emprunt.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.emprunt.entities.Emprunter;


@Repository
public interface EmpruntRepository extends JpaRepository<Emprunter, Long> {
    List<Emprunter> findByUserId(Long userId);

    List<Emprunter> findByBookId(Long bookId);
}
