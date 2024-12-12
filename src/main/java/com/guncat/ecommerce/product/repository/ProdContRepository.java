package com.guncat.ecommerce.product.repository;

import com.guncat.ecommerce.product.domain.entity.ProdCont;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdContRepository extends JpaRepository<ProdCont, Long> {
}
