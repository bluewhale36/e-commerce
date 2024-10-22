package com.guncat.ecommerce.common.converter;

import com.guncat.ecommerce.common.enums.IsLocked;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * {@link com.guncat.ecommerce.common.enums.IsLocked} Enum 객체와 JPA Record 를 변환하는 Converter.
 */
@Converter(autoApply = true)
public class IsLockedConverter implements AttributeConverter<IsLocked, Boolean> {

    @Override
    public Boolean convertToDatabaseColumn(IsLocked isLocked) {
        return isLocked != null && isLocked.toBoolean();
    }

    @Override
    public IsLocked convertToEntityAttribute(Boolean aBoolean) {
        return IsLocked.fromBoolean(aBoolean);
    }
}
