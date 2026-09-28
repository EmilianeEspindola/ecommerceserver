package br.edu.utfpr.pb.pw44s.ecommerceserver.mapper;

import br.edu.utfpr.pb.pw44s.ecommerceserver.dto.OrderItemResponseDTO;
import br.edu.utfpr.pb.pw44s.ecommerceserver.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderItemMapper {
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    OrderItemResponseDTO toDto(OrderItem orderItem);
}
