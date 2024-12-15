package com.guncat.ecommerce.product.domain.entity;

import com.guncat.ecommerce.common.converter.IsEnabledConverter;
import com.guncat.ecommerce.common.enums.IsEnabled;
import com.guncat.ecommerce.product.enums.ProdCategory;
import com.guncat.ecommerce.product.enums.ProdKind;
import com.guncat.ecommerce.product.enums.ProdStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Table(name = "product")
public class Product {

    @Id
    private String prodCode;

    @Column(name = "prod_user_code")
    private String registeredAdminCode;

    @Column
    @Enumerated(EnumType.STRING)
    private ProdStatus prodStatus;

    @Column
    private Long prodCnt;

    @Column
    @Enumerated(EnumType.STRING)
    private ProdCategory prodCategory;

    @Column
    @Enumerated(EnumType.STRING)
    private ProdKind prodKind;

    @Column
    private String prodName;

    @Column
    private Long prodPrice;

    @Column
    private Long discntRate;

    @Column
    @Convert(converter = IsEnabledConverter.class)
    private IsEnabled isEnabled;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "pic_prod_code")
    private List<ProdPic> prodPicList;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "cont_prod_code")
    private List<ProdCont> prodContList;
}
