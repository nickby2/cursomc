package com.aplication.course.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.aplication.course.domain.enums.TipoCliente;
import com.aplication.course.dto.ClienteNewDTO;
import com.aplication.course.resources.exceptions.FieldMessage;
import com.aplication.course.services.validation.utils.BR;

public class ClienteIncertValidator implements ConstraintValidator<ClienteIncert, ClienteNewDTO> {
	@Override
	public void initialize(ClienteIncert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		if(objDto.getTipo().equals(TipoCliente.PESSOA_FISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("CpfOuCnpj", "CPF inválido"));
		}
		
		if(objDto.getTipo().equals(TipoCliente.PESSOA_JURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("CpfOuCnpj", "CNPJ inválido"));
		}
		
// inclua os testes aqui, inserindo erros na lista
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}