package com.guncat.ecommerce.common.converter;

import com.guncat.ecommerce.common.enums.IsDefault;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * {@link com.guncat.ecommerce.common.enums.IsDefault} Enum 객체와 JPA Record 를 변환하는 Converter.
 *
 * @see IsDefault
 */
@Converter(autoApply = true)
public class IsDefaultConverter implements AttributeConverter<IsDefault, Boolean> {

    /**
     * 매개변수의 객체를 DB Column 으로 Convert.
     *
     * @param isDefault {@link IsDefault} 객체.
     * @return 매개변수가 {@link IsDefault#DEFAULT} 일 경우 {@link Boolean#TRUE} 반환.<br/>
     * 매개변수가 {@code null} 이거나, {@link IsDefault#NOT_DEFAULT} 일 경우 {@link Boolean#FALSE} 반환.
     */
    @Override
    public Boolean convertToDatabaseColumn(IsDefault isDefault) {
        return isDefault != null && isDefault.toBoolean();
    }

    /**
     * 매개변수의 DB Column 값을 {@link IsDefault} 객체로 Convert.
     *
     * @param aBoolean {@link Boolean} 객체.
     * @return {@link IsDefault#fromBoolean(Boolean)}
     */
    @Override
    public IsDefault convertToEntityAttribute(Boolean aBoolean) {
        return IsDefault.fromBoolean(aBoolean);
    }
}
