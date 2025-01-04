package com.guncat.ecommerce.admin.product.controller;

import com.guncat.ecommerce.common.dto.PagingResponseDTO;
import com.guncat.ecommerce.product.dto.ProductDTO;
import com.guncat.ecommerce.product.dto.ProductPagingRequestDTO;
import com.guncat.ecommerce.product.enums.ProdCategory;
import com.guncat.ecommerce.product.enums.ProdKind;
import com.guncat.ecommerce.product.service.IF_ProductService;
import com.guncat.ecommerce.security.annotation.AuthenticationPrincipalUserCode;
import lombok.RequiredArgsConstructor;
import org.junit.Assert;
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
//        System.out.println(productPagingRequestDTO);
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

    @PutMapping("/details/{prodCode}")
    public void updateProduct(@PathVariable String prodCode, @AuthenticationPrincipalUserCode String userCode,
                              @ModelAttribute ProductDTO productDTO) {
        Assert.assertEquals("You should not modify product data without being in its detail page.",
                prodCode, productDTO.getProdCode());

        Assert.assertEquals("You should not modify product data because you did not registered.",
                userCode, productDTO.getRegisteredAdminCode());

        System.out.println("\n\nUPDATE\n\n");
        System.out.println(productDTO);

        productService.updateProductInfo(productDTO);
    }
}
