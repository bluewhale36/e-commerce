package com.guncat.ecommerce.common.vo;

import lombok.Getter;
import lombok.ToString;
import org.junit.Assert;

@Getter
@ToString
public abstract class JudgeModificationVO<T> {

    private final boolean isBasicDataModified;
    private final boolean isEnumStatusModified;

    public JudgeModificationVO(T existing, T modified) {
        Assert.assertNotNull("parameters must not be null", existing);
        Assert.assertNotNull("parameters must not be null", modified);

        this.isBasicDataModified = isBasicDataModified(existing, modified);
        this.isEnumStatusModified = isEnumStatusModified(existing, modified);
    }

    protected abstract boolean isBasicDataModified(T existing, T modified);
    protected abstract boolean isEnumStatusModified(T existing, T modified);


}
