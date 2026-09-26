package com.Pecucore.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.Pecucore.system.dto.PesagemRequestDTO;
import com.Pecucore.system.evento.PesoAnimalAlteradoEvento;
import com.Pecucore.system.model.Animal;
import com.Pecucore.system.model.Pesagem;
import com.Pecucore.system.repository.AnimalRepository;
import com.Pecucore.system.repository.PesagemRepository;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class PesagemService {

    @Autowired
    private PesagemRepository pesagemRepository;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private GmdService gmdService;

    @Value("${pesagem.intervalo-recomendado-dias}")
    private int intervaloRecomendadoDias;

    @EventListener
    @Transactional
    public void handlePesoAnimalAlterado(PesoAnimalAlteradoEvento evento) {
        create(new PesagemRequestDTO(evento.animalId(), evento.pesoKg(), evento.data()));
    }

    @Transactional
    public ResultadoPesagem create(PesagemRequestDTO dados) {

        Animal animal = animalService.getAnimalAtivoById(dados.animalId());

        if (animal.getDataNascimento() != null && dados.dataPesagem().isBefore(animal.getDataNascimento())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A data da pesagem não pode ser anterior à data de nascimento do animal"
            );
        }

        Pesagem ultimaPesagem = pesagemRepository.findTopByAnimalIdOrderByDataDescIdDesc(dados.animalId()).orElse(null);

        if(ultimaPesagem != null && dados.dataPesagem().isBefore(ultimaPesagem.getData())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A data da pesagem não pode ser anterior à última pesagem registrada"
            );
        }

        String aviso = null;
        if(ultimaPesagem !=null) {
            long diasEntrePesagens = ChronoUnit.DAYS.between(
                    ultimaPesagem.getData(),
                    dados.dataPesagem()
            );

            if(diasEntrePesagens > intervaloRecomendadoDias){
                aviso = "O intervalo desde a última pesagem (" + diasEntrePesagens
                        + " dias) ultrapassa o recomendado (" + intervaloRecomendadoDias + " dias)";
            }
        }

        Double gmd = gmdService.calculateGmd(ultimaPesagem, dados.pesoAtual(), dados.dataPesagem());

        Pesagem pesagem = new Pesagem();

        pesagem.setPesoKg(dados.pesoAtual());
        pesagem.setGmdCalculado(gmd);
        pesagem.setData(dados.dataPesagem());
        pesagem.setAnimal(animal);

        animal.setPesoAtual(dados.pesoAtual());

        pesagemRepository.save(pesagem);
        animalRepository.save(animal);

        return new ResultadoPesagem(pesagem, aviso);
    }
    
    public List<Pesagem> getHistorico(Long animalId) {

        if (!animalRepository.existsById(animalId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Animal não encontrado"
            );
        }

        return pesagemRepository.findByAnimalIdOrderByDataAscIdAsc(animalId);
    }
}
