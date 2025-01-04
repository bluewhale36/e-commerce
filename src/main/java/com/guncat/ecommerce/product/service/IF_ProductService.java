package com.guncat.ecommerce.product.service;

import com.guncat.ecommerce.admin.product.dto.ProductDTOForAdmin;
import com.guncat.ecommerce.admin.product.dto.ProductRegisterDTO;
import com.guncat.ecommerce.common.dto.PagingResponseDTO;
import com.guncat.ecommerce.product.dto.ProdContDTO;
import com.guncat.ecommerce.product.dto.ProdPicDTO;
import com.guncat.ecommerce.product.dto.ProductDTO;
import com.guncat.ecommerce.product.dto.ProductPagingRequestDTO;
import com.guncat.ecommerce.users.dto.UsersDTO;
import com.guncat.ecommerce.users.dto.UsersPagingRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IF_ProductService {

    void saveNewProduct(ProductRegisterDTO productRegiDTO) throws IOException;

    public ProductDTO getProductByProdCode(String prodCode);

    public ProductDTOForAdmin getProductByProdCodeForAdmin(String prodCode);

    public PagingResponseDTO<List<ProductDTO>> getProductsByPaging(ProductPagingRequestDTO productPagingRequestDTO);

    public void updateProductInfo(ProductDTO modified);
}
