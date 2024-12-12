package com.guncat.ecommerce.product.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Table(name = "prod_cont")
public class ProdCont {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prodContCode;

    @Column(name = "cont_prod_code")
    private String contProdCode;

    @Column
    private String prodContName;
}
