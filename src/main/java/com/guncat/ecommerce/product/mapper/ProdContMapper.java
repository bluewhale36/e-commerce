package com.guncat.ecommerce.product.mapper;

import com.guncat.ecommerce.product.domain.entity.ProdCont;
import com.guncat.ecommerce.product.dto.ProdContDTO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = false))
public interface ProdContMapper {

    ProdContMapper INSTANCE = Mappers.getMapper(ProdContMapper.class);

    List<ProdContDTO> toDTOs(List<ProdCont> prodConts);

}
