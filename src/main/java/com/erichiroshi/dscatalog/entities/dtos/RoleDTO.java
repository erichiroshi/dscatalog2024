package com.erichiroshi.dscatalog.entities.dtos;

import java.io.Serializable;

/**
 * DTO for {@link com.erichiroshi.dscatalog.entities.Role}
 */
public record RoleDTO(
        Long id,
        String authority) implements Serializable {
}