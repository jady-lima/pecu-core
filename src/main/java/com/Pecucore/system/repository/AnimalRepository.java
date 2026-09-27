package com.Pecucore.system.repository;

import com.Pecucore.system.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Pecucore.system.model.StatusAnimal;

import java.util.List;
import java.util.Optional;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

    Optional<Animal> findByBrinco(int brinco);

    boolean existsByBrinco(int brinco);

    boolean existsByBrincoAndIdNot(int brinco, Long id);

    boolean existsByLoteIdAndStatus(Long loteId, StatusAnimal status);

    List<Animal> findByLoteIdAndStatus(Long loteId, StatusAnimal status);
}
