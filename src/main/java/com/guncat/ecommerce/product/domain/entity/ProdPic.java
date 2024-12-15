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
@Table(name = "prod_pic")
public class ProdPic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prodPicCode;

    @Column(name = "pic_prod_code")
    private String picProdCode;

    @Column
    private String prodPicName;

    @Column
    private String prodPicCaption;
}
