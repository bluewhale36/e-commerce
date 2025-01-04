package com.guncat.ecommerce.admin.product.domain.vo;

import com.guncat.ecommerce.common.vo.JudgeModificationVO;
import com.guncat.ecommerce.product.dto.ProductDTO;
import lombok.Getter;
import lombok.ToString;
import org.junit.Assert;

@Getter
@ToString(callSuper = true)
public class JudgeProductModificationVO extends JudgeModificationVO<ProductDTO> {

    private final boolean isProdContModified;
    private final boolean isProdPicModified;


    public JudgeProductModificationVO(ProductDTO existing, ProductDTO modified) {
        super(existing, modified);
        this.isProdContModified = isProdContModified(existing, modified);
        this.isProdPicModified = isProdPicModified(existing, modified);
    }

    @Override
    protected boolean isBasicDataModified(ProductDTO existing, ProductDTO modified) {

        Assert.assertEquals("You must judge modifications only with same prod-code data.",
                existing.getProdCode(), modified.getProdCode());

        if (!existing.getProdCnt().equals(modified.getProdCnt())) {
            return true;
        } else if (!existing.getProdCategory().equals(modified.getProdCategory())) {
            return true;
        } else if (!existing.getProdKind().equals(modified.getProdKind())) {
            return true;
        } else if (!existing.getProdName().equals(modified.getProdName())) {
            return true;
        } else if (!existing.getProdPrice().equals(modified.getProdPrice())) {
            return true;
        } else if (!existing.getDiscntRate().equals(modified.getDiscntRate())) {
            return true;
        } else if (isProdContModified() || isProdPicModified()) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean isEnumStatusModified(ProductDTO existing, ProductDTO modified) {
        if (!existing.getProdStatus().equals(modified.getProdStatus())) {
            return true;
        } else if (!existing.getIsEnabled().equals(modified.getIsEnabled())) {
            return true;
        }
        return false;
    }

    private boolean isProdContModified(ProductDTO existing, ProductDTO modified) {
        return false;
    }

    private boolean isProdPicModified(ProductDTO existing, ProductDTO modified) {
        return false;
    }
}
