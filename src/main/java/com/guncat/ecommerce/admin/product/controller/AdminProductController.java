package com.guncat.ecommerce.admin.product.controller;

import com.guncat.ecommerce.admin.product.dto.ProductRegisterDTO;
import com.guncat.ecommerce.common.enums.IsEnabled;
import com.guncat.ecommerce.product.enums.ProdCategory;
import com.guncat.ecommerce.product.enums.ProdStatus;
import com.guncat.ecommerce.product.service.IF_ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/products")
public class AdminProductController {

    private final IF_ProductService productService;

    @GetMapping("")
    public String adminProducts(Model model) {
        model.addAttribute("filter_IsEnabled", IsEnabled.values());
        model.addAttribute("filter_ProdStatus", ProdStatus.values());
        model.addAttribute("filter_ProdCategory", ProdCategory.values());
        return "admin/products/products";
    }

    @GetMapping("/new")
    public String newProduct(Model model) {
        model.addAttribute("isEnabled", IsEnabled.values());
        model.addAttribute("prodStatus", ProdStatus.values());
        model.addAttribute("prodCategory", ProdCategory.values());
        return "admin/products/product-new";
    }

    @PostMapping("/new")
    public String saveNewProduct(@ModelAttribute ProductRegisterDTO productRegiDTO) throws IOException {
        productService.saveNewProduct(productRegiDTO);
        return "redirect:/admin/products";
    }

    @GetMapping("/details/{prodCode}")
    public String adminProductDetails(@PathVariable String prodCode, Model model) {
        model.addAttribute("product", productService.getProductByProdCodeForAdmin(prodCode));
        return "admin/products/product-details";
    }

}
