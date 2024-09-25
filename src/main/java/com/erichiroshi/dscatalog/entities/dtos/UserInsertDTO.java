package com.erichiroshi.dscatalog.entities.dtos;

import java.io.Serializable;
import java.util.Set;

import com.erichiroshi.dscatalog.services.validation.UserInsertValid;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for {@link com.erichiroshi.dscatalog.entities.User}
 */
@UserInsertValid
public record UserInsertDTO(
        Long id,

        @NotBlank(message = "Campo obrigatório")
        String firstName,
        String lastName,

        @Email(message = "Favor entrar um email válido")
        String email,

        String password,
        Set<RoleDTO> roles) implements Serializable {
}