package com.Pecucore.system.repository;

import com.Pecucore.system.model.Alimentacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlimentacaoRepository extends JpaRepository<Alimentacao, Long> {
    List<Alimentacao> findByAnimalIdOrderByDataAscIdAsc(Long animalId);
}
