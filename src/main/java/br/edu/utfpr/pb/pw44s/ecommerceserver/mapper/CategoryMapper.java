package br.edu.utfpr.pb.pw44s.ecommerceserver.mapper;

import br.edu.utfpr.pb.pw44s.ecommerceserver.dto.CategoryDTO;
import br.edu.utfpr.pb.pw44s.ecommerceserver.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    CategoryDTO toDto(Category entity);
}