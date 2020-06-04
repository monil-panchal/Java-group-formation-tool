package com.assessme.service;

import com.opencsv.exceptions.CsvException;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

//String rootLocation = "tmp/uploadFiles";
public interface StorageService {
    void init() throws IOException;
    String store(MultipartFile file) throws IOException;
    Stream<Path> loadAll();
    Path load(String fileName);
    List<String[]> storeAndParseAll(MultipartFile file) throws CsvException, IOException;
}

