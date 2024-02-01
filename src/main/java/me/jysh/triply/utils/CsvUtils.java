package me.jysh.triply.utils;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.IOException;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * Utility class for handling CSV operations.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CsvUtils {

  /**
   * Converts the contents of a CSV file provided as a MultipartFile into a list of objects of the
   * specified type.
   *
   * @param file The CSV file as a MultipartFile.
   * @param type The class type of the objects to be created from the CSV file.
   * @param <T>  The generic type of the objects.
   * @return A list of objects created from the CSV file.
   * @throws IOException If an I/O error occurs while reading the CSV file.
   */
  public static <T> List<T> multipartFileToEntry(MultipartFile file, Class<T> type)
      throws IOException {
    final CsvMapper csvMapper = new CsvMapper();
    final CsvSchema schema = csvMapper.schemaFor(type).withHeader();
    final MappingIterator<T> iterator = csvMapper.readerFor(type).with(schema)
        .readValues(file.getInputStream());
    return iterator.readAll();
  }
}
