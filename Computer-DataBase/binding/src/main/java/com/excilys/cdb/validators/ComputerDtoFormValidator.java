package com.excilys.cdb.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.excilys.cdb.dto.ComputerDto;

public class ComputerDtoFormValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return ComputerDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.name.required");
	}

}
