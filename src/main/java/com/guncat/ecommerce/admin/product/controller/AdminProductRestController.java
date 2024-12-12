package com.guncat.ecommerce.admin.product.controller;

import com.guncat.ecommerce.common.dto.PagingResponseDTO;
import com.guncat.ecommerce.product.dto.ProductDTO;
import com.guncat.ecommerce.product.dto.ProductPagingRequestDTO;
import com.guncat.ecommerce.product.enums.ProdCategory;
import com.guncat.ecommerce.product.enums.ProdKind;
import com.guncat.ecommerce.product.service.IF_ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/products")
public class AdminProductRestController {

    private final IF_ProductService productService;

    @GetMapping("/")
    public PagingResponseDTO<List<ProductDTO>> getAdminProductPagingData (@ModelAttribute ProductPagingRequestDTO productPagingRequestDTO) {
        System.out.println(productPagingRequestDTO);
        return productService.getProductsByPaging(productPagingRequestDTO);
    }

    @GetMapping("/new-prodkind")
    public Map<ProdKind, String> getProdKind(@RequestParam("prodCategory") ProdCategory prodCategory) {
        Map<ProdKind, String> returnMap = new HashMap<>();
        for (ProdKind prodKind : prodCategory.getProdKindList()) {
            returnMap.put(prodKind, prodKind.getDescription());
        }
        return returnMap;
    }
}
