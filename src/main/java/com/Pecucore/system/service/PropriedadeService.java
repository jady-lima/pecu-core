package com.Pecucore.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.Pecucore.system.dto.PropriedadeRequestDTO;
import com.Pecucore.system.model.Perfil;
import com.Pecucore.system.model.Propriedade;
import com.Pecucore.system.model.Usuario;
import com.Pecucore.system.repository.PropriedadeRepository;
import com.Pecucore.system.repository.UsuarioRepository;

@Service
public class PropriedadeService {

    @Autowired
    private PropriedadeRepository propriedadeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Propriedade create(PropriedadeRequestDTO dados, Usuario usuarioLogado) {

        Usuario dono = definirDono(dados, usuarioLogado);

        Propriedade propriedade = new Propriedade();

        propriedade.setNome(dados.nome());
        propriedade.setLocalizacao(dados.localizacao());
        propriedade.setUsuario(dono);

        return propriedadeRepository.save(propriedade);
    }

    private Usuario definirDono(PropriedadeRequestDTO dados, Usuario usuarioLogado) {

        if (usuarioLogado.getPerfil() == Perfil.ADMIN) {

            if (dados.usuarioId() == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "O usuário dono da propriedade deve ser informado"
                );
            }

            Usuario dono = getUsuarioById(dados.usuarioId());

            if (dono.getPerfil() != Perfil.PRODUTOR) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "A propriedade só pode pertencer a um usuário com perfil PRODUTOR"
                );
            }

            return dono;
        }

        if (dados.usuarioId() != null && !dados.usuarioId().equals(usuarioLogado.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Um produtor só pode cadastrar propriedades em seu próprio nome"
            );
        }

        return getUsuarioById(usuarioLogado.getId());
    }

    private Usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"
                ));
    }

    public Propriedade update(Long id, PropriedadeRequestDTO dados) {

        Propriedade propriedadeExistente =
                propriedadeRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Propriedade não encontrada"
                        ));

        propriedadeExistente.setNome(dados.nome());
        propriedadeExistente.setLocalizacao(dados.localizacao());

        return propriedadeRepository.save(propriedadeExistente);
    }

    public List<Propriedade> getAllPropriedades() {
        return propriedadeRepository.findAll();
    }

    public Propriedade getPropriedadeById(Long id) {
        return propriedadeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Propriedade não encontrada"
                ));
    }

    public void deletePropriedade(Long id) {

        Propriedade propriedadeExistente =
                propriedadeRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Propriedade não encontrada"
                        ));

        propriedadeRepository.delete(propriedadeExistente);
    }
}