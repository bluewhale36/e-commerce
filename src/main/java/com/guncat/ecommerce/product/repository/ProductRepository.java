package com.guncat.ecommerce.product.repository;

import com.guncat.ecommerce.common.enums.IsEnabled;
import com.guncat.ecommerce.product.domain.entity.Product;
import com.guncat.ecommerce.product.enums.ProdCategory;
import com.guncat.ecommerce.product.enums.ProdStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    Page<Product> findByProdNameContaining(String productName, Pageable pageable);

    @Query("select p from Product p where p.prodName like %:prodName% and p.prodStatus in :prodStatus")
    Page<Product> findByProdNameContainingAndProdStatusIn(String prodName, String[] prodStatus, Pageable pageable);

    @Query("select p from Product p where p.prodName like %:prodName% and p.isEnabled in :isEnabled")
    Page<Product> findByProdNameContainingAndIsEnabledIn(String prodName, String[] isEnabled, Pageable pageable);

    @Query("select p from Product p where p.prodName like %:prodName% and p.prodCategory in :prodCategory")
    Page<Product> findByProdNameContainingAndProdCategoryIn(String prodName, String[] prodCategory, Pageable pageable);

    @Query("update Product p set p.isEnabled = :isEnabled, p.prodStatus = :prodStatus where p.prodCode = :prodCode")
    @Transactional
    @Modifying(clearAutomatically = true)
    void updateIsEnabledAndProdStatus(String prodCode, IsEnabled isEnabled, ProdStatus prodStatus);
}
