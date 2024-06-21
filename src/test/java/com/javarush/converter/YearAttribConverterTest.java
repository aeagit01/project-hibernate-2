package com.javarush.converter;

import org.junit.jupiter.api.Test;

import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;

class YearAttribConverterTest {
    private final YearAttribConverter converter = new YearAttribConverter();

    @Test
    public void testConvertToDatabaseColumn_WhenYearIsNull_ExpectNull() {
        Year year = null;
        Short result = converter.convertToDatabaseColumn(year);
        assertEquals(null, result);
    }

    @Test
    public void testConvertToDatabaseColumn_WhenYearIsNotNull_ExpectShortValue() {
        Year year = Year.of(2024);
        Short result = converter.convertToDatabaseColumn(year);
        assertEquals((short) 2024, result);
    }

    @Test
    public void testConvertToEntityAttribute_WhenYearIsNull_ExpectNull() {
        Short year = null;
        Year result = converter.convertToEntityAttribute(year);
        assertNull(result);
    }

    @Test
    public void testConvertToEntityAttribute_WhenYearIsNotNull_ExpectCorrectYear() {
        Short yearValue = 2024;
        Year expectedYear = Year.of(yearValue);
        Year result = converter.convertToEntityAttribute(yearValue);
        assertEquals(expectedYear, result);
    }
}