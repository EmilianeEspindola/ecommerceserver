package br.edu.utfpr.pb.pw44s.ecommerceserver.mapper;

import br.edu.utfpr.pb.pw44s.ecommerceserver.dto.AddressDTO;
import br.edu.utfpr.pb.pw44s.ecommerceserver.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Address toEntity(AddressDTO dto);

    AddressDTO toDto(Address address);
}
