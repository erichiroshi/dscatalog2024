package com.erichiroshi.dscatalog.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.erichiroshi.dscatalog.entities.User;
import com.erichiroshi.dscatalog.entities.dtos.UserDTO;
import com.erichiroshi.dscatalog.entities.dtos.UserInsertDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {
		RoleMapper.class })
public interface UserMapper {

	User toEntity(UserDTO userDto);

	User toEntity(UserInsertDTO userInsertDto);

	UserDTO toDto(User user);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "id", ignore = true)
	User partialUpdate(UserDTO userDto, @MappingTarget User user);

}