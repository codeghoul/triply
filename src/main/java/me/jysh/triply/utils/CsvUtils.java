package me.jysh.triply.utils;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.jysh.triply.dtos.VehicleModelEntry;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CsvUtils {

    public static <T> List<T> multipartFileToEntry(MultipartFile file, Class<T> type) throws IOException {
        final CsvMapper csvMapper = new CsvMapper();
        final CsvSchema schema = csvMapper.schemaFor(type).withHeader();
        final MappingIterator<T> iterator = csvMapper.readerFor(type).with(schema).readValues(file.getInputStream());
        return iterator.readAll();
    }
}
