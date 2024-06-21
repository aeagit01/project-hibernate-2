package com.javarush.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.Year;

import static java.util.Objects.isNull;

@Converter(autoApply = true)
public class YearAttribConverter implements AttributeConverter<Year, Short> {

    @Override
    public Short convertToDatabaseColumn(Year year) {
        return isNull(year) ? null : (short) year.getValue();
    }

    @Override
    public Year convertToEntityAttribute(Short year) {
        return isNull(year) ? null : Year.of(year);
    }
}
