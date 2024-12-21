package com.guncat.ecommerce.product.controller;

import com.guncat.ecommerce.product.dto.ProductDTO;
import com.guncat.ecommerce.product.service.IF_ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductRestController {

    private final IF_ProductService productService;

}
