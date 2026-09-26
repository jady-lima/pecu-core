package com.Pecucore.system.repository;

import com.Pecucore.system.model.Pesagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PesagemRepository extends JpaRepository<Pesagem, Long> {
    Optional<Pesagem> findTopByAnimalIdOrderByDataDesc(Long animalId);
    List<Pesagem> findByAnimalIdOrderByDataAsc(Long animalId);
}
