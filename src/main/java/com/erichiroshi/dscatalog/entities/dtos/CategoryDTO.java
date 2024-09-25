package com.erichiroshi.dscatalog.entities.dtos;

import java.io.Serializable;

/**
 * DTO for {@link com.erichiroshi.dscatalog.entities.Category}
 */
public record CategoryDTO(
		Long id,
		String name) implements Serializable {

}