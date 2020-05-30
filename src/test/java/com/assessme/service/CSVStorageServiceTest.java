package com.assessme.service;

import com.assessme.config.StorageConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CSVStorageServiceTest {

    private StorageService storageService;

    @BeforeEach
    void init() throws IOException {
        storageService = CSVStorageService.getInstance();
        storageService.init();
    }
    @Test
    void testEmptyFileStore() {
        assertThrows(IOException.class, () -> {
                    storageService.store(
                            new MockMultipartFile("foo", "foo.txt",
                                    MediaType.TEXT_PLAIN_VALUE, "".getBytes()));
                }
        );
    }

    @Test
    void storeAndLoad() throws IOException {
        String uploadedFileName = storageService.store(new MockMultipartFile("foo", "foo.txt", MediaType.TEXT_PLAIN_VALUE,
                "Hello, World".getBytes()));
        assertThat(storageService.load(uploadedFileName)).exists();
    }

}
