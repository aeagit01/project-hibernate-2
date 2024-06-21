package com.javarush.converter;

import static org.junit.jupiter.api.Assertions.*;

import com.javarush.domain.Rating;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class RatingConverterTest {

    @ParameterizedTest
    @ValueSource(
            strings = {"G", "PG", "PG-13", "R", "NC-17"}
    )
    public void testConvertToDatabaseColumn(String param) {
        RatingConverter ratingConverter = new RatingConverter();
        Rating expectedRating = Rating.valueOf(param.replace("-", "_"));
        Rating actualRating = ratingConverter.convertToEntityAttribute(param);
        assertEquals(expectedRating, actualRating);
    }

    @Test
    public void testConvertToEntityAttribute_ValueNotFound() {
        RatingConverter ratingConverter = new RatingConverter();
        Rating actualRating = ratingConverter.convertToEntityAttribute("XYZ");
        assertNull(actualRating);
    }
}