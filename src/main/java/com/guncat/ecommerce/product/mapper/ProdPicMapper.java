package com.guncat.ecommerce.product.mapper;

import com.guncat.ecommerce.product.domain.entity.ProdPic;
import com.guncat.ecommerce.product.dto.ProdPicDTO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = false))
public interface ProdPicMapper {

    ProdPicMapper INSTANCE = Mappers.getMapper(ProdPicMapper.class);

    ProdPicDTO toDTO(ProdPic prodPic);

    List<ProdPicDTO> toDTOs(List<ProdPic> prodPics);
}
