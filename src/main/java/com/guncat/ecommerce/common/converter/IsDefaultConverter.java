package com.guncat.ecommerce.common.converter;

import com.guncat.ecommerce.common.enums.IsDefault;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * {@link com.guncat.ecommerce.common.enums.IsDefault} Enum 객체와 JPA Record 를 변환하는 Converter.
 */
@Converter(autoApply = true)
public class IsDefaultConverter implements AttributeConverter<IsDefault, Boolean> {

    @Override
    public Boolean convertToDatabaseColumn(IsDefault isDefault) {
        return isDefault != null && isDefault.toBoolean();
    }

    @Override
    public IsDefault convertToEntityAttribute(Boolean aBoolean) {
        return IsDefault.fromBoolean(aBoolean);
    }
}
