package com.erichiroshi.dscatalog.controllers.exceptions;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FieldMessage implements Serializable {

	private String fieldName;
	private String message;
}
