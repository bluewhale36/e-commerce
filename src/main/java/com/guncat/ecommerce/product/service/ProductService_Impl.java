package com.guncat.ecommerce.product.service;

import com.guncat.ecommerce.admin.product.dto.ProductDTOForAdmin;
import com.guncat.ecommerce.admin.product.dto.ProductRegisterDTO;
import com.guncat.ecommerce.common.dto.PagingResponseDTO;
import com.guncat.ecommerce.common.enums.IsEnabled;
import com.guncat.ecommerce.common.util.FileData;
import com.guncat.ecommerce.product.domain.entity.ProdCont;
import com.guncat.ecommerce.product.domain.entity.ProdPic;
import com.guncat.ecommerce.product.domain.entity.Product;
import com.guncat.ecommerce.product.dto.ProductDTO;
import com.guncat.ecommerce.product.dto.ProductPagingRequestDTO;
import com.guncat.ecommerce.product.enums.ProdCategory;
import com.guncat.ecommerce.product.enums.ProdStatus;
import com.guncat.ecommerce.product.mapper.ProductMapper;
import com.guncat.ecommerce.product.repository.ProdContRepository;
import com.guncat.ecommerce.product.repository.ProdPicRepository;
import com.guncat.ecommerce.product.repository.ProductRepository;
import com.guncat.ecommerce.users.dto.UsersDTO;
import com.guncat.ecommerce.users.service.IF_UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService_Impl implements IF_ProductService {

    private final ProductRepository productRepository;
    private final ProdContRepository prodContRepository;
    private final ProdPicRepository prodPicRepository;

    private final ProductMapper productMapper;

    private final IF_UsersService usersService;

    private final FileData fileData;


    @Override
    public void saveNewProduct(ProductRegisterDTO productRegiDTO) throws IOException {

        String prodCode = UUID.randomUUID().toString();
        String registeredAdminCode = usersService.getUserCodeByUserId(SecurityContextHolder.getContext().getAuthentication().getName());

        List<ProdCont> prodContList = new ArrayList<>();
        List<ProdPic> prodPicList = new ArrayList<>();

        for (String prodContFileName : fileData.uploadFile(productRegiDTO.getProdCont())) {
            ProdCont prodCont = ProdCont.builder()
                    .prodContCode(null)
                    .contProdCode(prodCode)
                    .prodContName(prodContFileName)
                    .build();

            prodContList.add(prodCont);
        }

        for (String prodPicFileName : fileData.uploadFile(productRegiDTO.getProdPic())) {
            ProdPic prodPic = ProdPic.builder()
                    .prodPicCode(null)
                    .picProdCode(prodCode)
                    .prodPicName(prodPicFileName)
                    .prodPicCaption(null)
                    .build();

            prodPicList.add(prodPic);
        }

        Product product = Product.builder()
                .prodCode(prodCode)
                .registeredAdminCode(registeredAdminCode)
                .prodStatus(productRegiDTO.getProdStatus())
                .prodCnt(productRegiDTO.getProdCnt())
                .prodCategory(productRegiDTO.getProdCategory())
                .prodKind(productRegiDTO.getProdKind())
                .prodName(productRegiDTO.getProdName())
                .prodPrice(productRegiDTO.getProdPrice())
                .discntRate(productRegiDTO.getDiscntRate())
                .isEnabled(productRegiDTO.getIsEnabled())
                .build();

        productRepository.save(product);

        prodContRepository.saveAll(prodContList);
        prodPicRepository.saveAll(prodPicList);
    }

    @Override
    public ProductDTO getProductByProdCode(String prodCode) {
        return productMapper.toDTO(productRepository.findById(prodCode).orElseThrow(
                () -> new IllegalArgumentException("No Such Products")
        ));
    }

    @Override
    public ProductDTOForAdmin getProductByProdCodeForAdmin(String prodCode) {
        ProductDTO productDTO = getProductByProdCode(prodCode);
        return ProductDTOForAdmin.builder()
                .productDTO(productDTO)
                .currentAdminUserId(usersService.getUserIdByUserCode(productDTO.getRegisteredAdminCode()))
                .prodCategoryList(List.of(ProdCategory.values()))
                .prodStatusList(List.of(ProdStatus.values()))
                .isEnabledList(List.of(IsEnabled.values()))
                .build();
    }

    @Override
    public PagingResponseDTO<List<ProductDTO>> getProductsByPaging(ProductPagingRequestDTO productPagingRequestDTO) {
        System.out.println(productPagingRequestDTO);

        Pageable pageable = PageRequest.of(
                productPagingRequestDTO.getPageNum(), 25, Sort.by(productPagingRequestDTO.getSortingType())
        );

        Page<Product> productsPage = productRepository.findAll(pageable);

        PagingResponseDTO<List<ProductDTO>> dtoPage = new PagingResponseDTO<>(
                productMapper.toDTOs(productsPage.getContent()), productsPage.getTotalPages(), productsPage.getNumber(),
                productPagingRequestDTO.getSearchType(), productPagingRequestDTO.getSearchKeyword(),
                productPagingRequestDTO.getFilterType(), productPagingRequestDTO.getFilterValue(), productPagingRequestDTO.getSortingType()
        );

        System.out.println(dtoPage);

        return dtoPage;
    }
}
