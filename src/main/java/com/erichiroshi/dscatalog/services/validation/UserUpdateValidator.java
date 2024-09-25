package com.erichiroshi.dscatalog.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.HandlerMapping;

import com.erichiroshi.dscatalog.controllers.exceptions.FieldMessage;
import com.erichiroshi.dscatalog.entities.User;
import com.erichiroshi.dscatalog.entities.dtos.UserDTO;
import com.erichiroshi.dscatalog.repositories.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserDTO> {

    private final HttpServletRequest request;
    private final UserRepository repository;

    public UserUpdateValidator(HttpServletRequest request, UserRepository repository) {
        this.request = request;
        this.repository = repository;
    }

    @Override
    public boolean isValid(UserDTO dto, ConstraintValidatorContext context) {

        @SuppressWarnings("unchecked")
        var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        long userId = Long.parseLong(uriVars.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        User user = repository.findByEmail(dto.email());
        if (user != null && userId != user.getId()) {
            list.add(new FieldMessage("email", "Email já existe"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}