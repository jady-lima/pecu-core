package com.Pecucore.system.dto;

import com.Pecucore.system.model.StatusAnimal;
import jakarta.validation.constraints.NotNull;

public record AnimalStatusRequestDTO(
        @NotNull(message = "O status precisa ser informado!!")
        StatusAnimal status
) {}
