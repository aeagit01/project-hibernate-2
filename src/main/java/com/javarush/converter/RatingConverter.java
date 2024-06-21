package com.javarush.converter;

import com.javarush.domain.Rating;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RatingConverter implements AttributeConverter<Rating, String> {

    @Override
    public String convertToDatabaseColumn(Rating raiting) {
        return raiting.getValue();
    }

    @Override
    public Rating convertToEntityAttribute(String value) {

        for (Rating rating : Rating.values()) {
            if (rating.getValue().equals(value)) {
                return rating;
            }
        }
        return null;
    }
}
