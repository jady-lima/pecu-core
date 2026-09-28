package com.Pecucore.system.controller;

import com.Pecucore.system.dto.CustoRequestDTO;
import com.Pecucore.system.dto.CustoResponseDTO;
import com.Pecucore.system.model.Custo;
import com.Pecucore.system.service.CustoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/custos")
@SecurityRequirement(name = "bearerAuth")
public class CustoController {

    @Autowired
    private CustoService custoService;

    @Operation(
            summary = "Registra um custo manual"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Custo registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Animal, lote ou propriedade não encontrado")
    })
    @PostMapping
    public ResponseEntity<CustoResponseDTO> create(
            @RequestBody @Valid CustoRequestDTO dados) {

        Custo custo = custoService.create(dados);

        return ResponseEntity.status(201)
                .body(new CustoResponseDTO(custo));
    }

    @Operation(
            summary = "Consulta todos os custos"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Custos encontrados")
    })
    @GetMapping
    public ResponseEntity<List<CustoResponseDTO>> getAll() {

        List<CustoResponseDTO> custos = custoService.getAll()
                .stream()
                .map(CustoResponseDTO::new)
                .toList();

        return ResponseEntity.ok(custos);
    }
}