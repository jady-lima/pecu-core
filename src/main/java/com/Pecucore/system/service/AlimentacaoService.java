package com.Pecucore.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.Pecucore.system.dto.AlimentacaoLoteRequestDTO;
import com.Pecucore.system.dto.AlimentacaoRequestDTO;
import com.Pecucore.system.model.Alimentacao;
import com.Pecucore.system.model.Animal;
import com.Pecucore.system.model.TipoAlimentacao;
import com.Pecucore.system.repository.AlimentacaoRepository;
import java.time.LocalDate;
import java.util.List;

@Service
public class AlimentacaoService {

    @Autowired
    private AlimentacaoRepository alimentacaoRepository;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private LoteService loteService;

    @Transactional
    public Alimentacao create(AlimentacaoRequestDTO dados) {
        Animal animal = animalService.getAnimalAtivoByBrinco(dados.brinco());
        return registrar(animal, dados.tipo(), dados.quantidade(), dados.data());
    }

    @Transactional
    public List<Alimentacao> createEmLote(AlimentacaoLoteRequestDTO dados) {

        loteService.getLoteById(dados.loteId());

        List<Animal> animais = animalService.getAnimaisAtivosByLoteId(dados.loteId());
        if (animais.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "O lote não possui animais ativos");
        }

        return animais.stream().map(animal -> registrar(animal, dados.tipo(), dados.quantidade(), dados.data())).toList();
    }

    public List<Alimentacao> getHistorico(int brinco) {
        Animal animal = animalService.getAnimalByBrinco(brinco);
        return alimentacaoRepository.findByAnimalIdOrderByDataAscIdAsc(animal.getId());
    }

    private Alimentacao registrar(Animal animal, TipoAlimentacao tipo, double quantidade, LocalDate data) {
        animalService.validarDataRegistro(animal, data);

        Alimentacao alimentacao = new Alimentacao();
        alimentacao.setTipo(tipo);
        alimentacao.setQuantidade(quantidade);
        alimentacao.setData(data);
        alimentacao.setAnimal(animal);

        return alimentacaoRepository.save(alimentacao);
    }
}
