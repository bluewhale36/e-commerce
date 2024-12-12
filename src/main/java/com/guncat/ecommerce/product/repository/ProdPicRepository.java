package com.guncat.ecommerce.product.repository;

import com.guncat.ecommerce.product.domain.entity.ProdPic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdPicRepository extends JpaRepository<ProdPic, Long> {
}
