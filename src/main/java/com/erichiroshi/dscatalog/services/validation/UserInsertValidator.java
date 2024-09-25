package com.erichiroshi.dscatalog.services.validation;

import java.util.ArrayList;
import java.util.List;

import com.erichiroshi.dscatalog.controllers.exceptions.FieldMessage;
import com.erichiroshi.dscatalog.entities.User;
import com.erichiroshi.dscatalog.entities.dtos.UserInsertDTO;
import com.erichiroshi.dscatalog.repositories.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {

	private final UserRepository repository;

	public UserInsertValidator(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();

		User user = repository.findByEmail(dto.email());
		if (user != null) {
			list.add(new FieldMessage("email", "Email já existe"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		return list.isEmpty();
	}
}