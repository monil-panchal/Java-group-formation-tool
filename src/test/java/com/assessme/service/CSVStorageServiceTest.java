package com.assessme.service;

import com.assessme.config.StorageConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CSVStorageServiceTest {
    private StorageService storageService;

    @BeforeAll
    void init() throws IOException {
        storageService = CSVStorageService.getInstance();
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
