package com.guncat.ecommerce.common.converter;

import com.guncat.ecommerce.common.enums.IsLocked;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * {@link com.guncat.ecommerce.common.enums.IsLocked} Enum 객체와 JPA Record 를 변환하는 Converter.
 * 
 * @see IsLocked
 */
@Converter(autoApply = true)
public class IsLockedConverter implements AttributeConverter<IsLocked, Boolean> {

    /**
     * 매개변수의 객체를 DB Column 으로 Convert.
     * 
     * @param isLocked {@link IsLocked} 객체.
     * @return 매개변수가 {@link IsLocked#LOCKED} 일 경우 {@link Boolean#TRUE} 반환.<br/>
     * 매개변수가 {@code null} 이거나, {@link IsLocked#UNLOCKED} 일 경우 {@link Boolean#FALSE} 반환.
     */
    @Override
    public Boolean convertToDatabaseColumn(IsLocked isLocked) {
        return isLocked != null && isLocked.toBoolean();
    }

    /**
     * 매개변수의 DB Column 값을 {@link IsLocked} 객체로 Convert.
     * 
     * @param aBoolean {@link Boolean} 객체.
     * @return {@link IsLocked#fromBoolean(Boolean)}
     */
    @Override
    public IsLocked convertToEntityAttribute(Boolean aBoolean) {
        return IsLocked.fromBoolean(aBoolean);
    }
}
