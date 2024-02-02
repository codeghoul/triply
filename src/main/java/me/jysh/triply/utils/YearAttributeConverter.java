package me.jysh.triply.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.Year;

/**
 * JPA Attribute Converter for converting {@link Year} objects to and from the database column of
 * type {@link Short}. Auto-applies the converter to all entities using the {@link Year} attribute.
 */
@Converter(autoApply = true)
public class YearAttributeConverter implements AttributeConverter<Year, Short> {

  /**
   * Converts a {@link Year} attribute to its database column representation.
   *
   * @param attribute The {@link Year} attribute to be converted.
   * @return The database column representation of the {@link Year} attribute as a {@link Short}.
   */
  @Override
  public Short convertToDatabaseColumn(
      Year attribute) {
    if (attribute != null) {
      return (short) attribute.getValue();
    }
    return null;
  }

  /**
   * Converts a database column value of type {@link Short} to its corresponding {@link Year}
   * attribute.
   *
   * @param dbData The database column value of type {@link Short}.
   * @return The {@link Year} attribute representing the converted database column value.
   */
  @Override
  public Year convertToEntityAttribute(
      Short dbData) {
    if (dbData != null) {
      return Year.of(dbData);
    }
    return null;
  }
}
