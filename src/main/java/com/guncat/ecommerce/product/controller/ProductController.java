package com.guncat.ecommerce.product.controller;

import com.guncat.ecommerce.product.dto.ProductDTO;
import com.guncat.ecommerce.product.dto.ProductPagingRequestDTO;
import com.guncat.ecommerce.product.service.IF_ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final IF_ProductService productService;

    @GetMapping("")
    public String product(Model model) {
        List<ProductDTO> prodList = productService.getProductsByPaging(
                new ProductPagingRequestDTO(null, null, null,
                        null, null, null)
        ).getContent();
        System.out.println("\n\n" + prodList + "\n\n");
        model.addAttribute("productDTO", prodList);
        return "product/product";
    }

    @GetMapping("/details/{prodCode}")
    public String productDetails(@PathVariable String prodCode, Model model) {
        ProductDTO productDTO = productService.getProductByProdCode(prodCode);
        model.addAttribute("productDTO", productDTO);
        return "product/prod-details";
    }
}
