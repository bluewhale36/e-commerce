package com.guncat.ecommerce.product.mapper;

import com.guncat.ecommerce.admin.product.dto.ProductRegisterDTO;
import com.guncat.ecommerce.product.domain.entity.ProdCont;
import com.guncat.ecommerce.product.domain.entity.ProdPic;
import com.guncat.ecommerce.product.domain.entity.Product;
import com.guncat.ecommerce.product.dto.ProdContDTO;
import com.guncat.ecommerce.product.dto.ProdPicDTO;
import com.guncat.ecommerce.product.dto.ProductDTO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = false))
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "prodContList", target = "prodContList")
    @Mapping(source = "prodPicList", target = "prodPicList")
    ProductDTO toDTO(Product product);

    default List<ProdContDTO> mapProdContList(List<ProdCont> prodConts) {
        return ProdContMapper.INSTANCE.toDTOs(prodConts);
    }

    default List<ProdPicDTO> mapProdPicList(List<ProdPic> prodPics) {
        return ProdPicMapper.INSTANCE.toDTOs(prodPics);
    }

    List<ProductDTO> toDTOs(List<Product> products);


}
