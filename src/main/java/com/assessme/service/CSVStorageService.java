package com.assessme.service;

import com.assessme.SystemConfig;
import com.assessme.config.StorageConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;


public class CSVStorageService implements StorageService{
    private Logger logger = LoggerFactory.getLogger(CSVStorageService.class);
    private static CSVStorageService instance;
    Path rootLocation;
    Path createdDirectoryPath;

    public CSVStorageService() {
        StorageConfig config = SystemConfig.getInstance().getStorageConfig();
        this.rootLocation = Paths.get(config.getLocation());
        init();
    }

    public static CSVStorageService getInstance(){
        if(instance == null)
            instance = new CSVStorageService();
        return instance;
    }

    @Override
    public void init() {
        if(!Files.exists(rootLocation)){
            try {
                createdDirectoryPath = Files.createDirectories(rootLocation);
                logger.info(String.format("Directory created: %s", rootLocation));
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Can't Create Directory for uploading files");
            }
        }
    }

    public String store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        logger.info(String.format("File Name: %s", fileName));
        String filePrefix = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String newFileName = filePrefix+"_"+fileName;
        logger.info(String.format("NewFile Name: %s", newFileName));
        try {
            if (file.isEmpty()) {
                logger.error("File is Empty");
                throw new IOException("File is Empty, Not storing file");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(newFileName),
                        StandardCopyOption.REPLACE_EXISTING);
                logger.info("Returning File Name");
                return newFileName;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Failed to store file " + newFileName);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public Path load(String fileName) {
        return rootLocation.resolve(fileName);
    }
}
