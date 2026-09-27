package com.Pecucore.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Pecucore.system.dto.VacinacaoRequestDTO;
import com.Pecucore.system.model.Animal;
import com.Pecucore.system.model.Usuario;
import com.Pecucore.system.model.Vacina;
import com.Pecucore.system.model.Vacinacao;
import com.Pecucore.system.repository.VacinacaoRepository;
import java.util.List;

@Service
public class VacinacaoService {

    @Autowired
    private VacinacaoRepository vacinacaoRepository;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private VacinaService vacinaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CalculadoraCarencia calculadoraCarencia;

    @Transactional
    public Vacinacao create(VacinacaoRequestDTO dados, Usuario usuarioLogado) {
        Animal animal = animalService.getAnimalAtivoByBrinco(dados.brinco());
        animalService.validarDataRegistro(animal, dados.data());

        Vacina vacina = vacinaService.getVacinaById(dados.vacinaId());
        Usuario aplicador = usuarioService.getUsuarioById(dados.aplicadorId());
        Usuario registrador = usuarioService.getUsuarioById(usuarioLogado.getId());
        
        Vacinacao vacinacao = new Vacinacao();
        vacinacao.setAnimal(animal);
        vacinacao.setData(dados.data());
        vacinacao.setVacina(vacina);
        vacinacao.setLoteVacina(dados.loteVacina().trim());
        vacinacao.setDoseAplicada(dados.doseAplicada());
        vacinacao.setObservacoes(dados.observacoes() == null || dados.observacoes().isBlank() ? null : dados.observacoes().trim());
        vacinacao.setUsuarioAplicador(aplicador);
        vacinacao.setUsuarioRegistro(registrador);
        vacinacao.setDataFimCarencia(calculadoraCarencia.calculateDataFimCarencia(dados.data(), vacina.getCarenciaDias()));

        if (vacina.getIntervaloDoseDias() != null) {
            vacinacao.setDataProximaDose(dados.data().plusDays(vacina.getIntervaloDoseDias()));
        }

        return vacinacaoRepository.save(vacinacao);
    }

    public List<Vacinacao> getHistorico(int brinco) {
        Animal animal = animalService.getAnimalByBrinco(brinco);
        return vacinacaoRepository.findByAnimalIdOrderByDataAscIdAsc(animal.getId());
    }
}
