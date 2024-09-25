package com.erichiroshi.dscatalog.entities.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import com.erichiroshi.dscatalog.entities.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * DTO for {@link Product}
 */
public record ProductDTO(
        Long id,

        @Size(min = 5, max = 60, message = "Deve ter entre 5 e 60 caracteres")
        @NotBlank(message = "Campo requerido")
        String name,

        @NotBlank(message = "Campo requerido")
        String description,

        @Positive(message = "Preço deve ser um valor positivo")
        BigDecimal price,

        String imgUrl,

        @PastOrPresent(message = "A data do produto não pode ser futura")
        LocalDateTime date,
        
		Set<CategoryDTO> categories) implements Serializable {

}