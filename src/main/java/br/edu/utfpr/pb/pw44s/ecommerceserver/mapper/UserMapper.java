package br.edu.utfpr.pb.pw44s.ecommerceserver.mapper;

import br.edu.utfpr.pb.pw44s.ecommerceserver.dto.UserDTO;
import br.edu.utfpr.pb.pw44s.ecommerceserver.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    User toEntity(UserDTO dto);
}