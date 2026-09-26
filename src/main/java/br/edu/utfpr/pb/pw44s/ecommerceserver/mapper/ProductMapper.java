package br.edu.utfpr.pb.pw44s.ecommerceserver.mapper;

import br.edu.utfpr.pb.pw44s.ecommerceserver.dto.ProductDTO;
import br.edu.utfpr.pb.pw44s.ecommerceserver.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = CategoryMapper.class)
public interface ProductMapper {
    ProductDTO toDto(Product entity);
}