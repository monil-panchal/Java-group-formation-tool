package com.assessme;

import com.assessme.config.StorageConfig;
import com.assessme.service.StorageService;

public class SystemConfig {

    private static SystemConfig instance;
    private StorageConfig storageConfig;

    public SystemConfig() {
        storageConfig = new StorageConfig();
    }

    public static SystemConfig getInstance() {
        if(instance == null)
            instance = new SystemConfig();
        return instance;
    }

    public StorageConfig getStorageConfig() {
        return storageConfig;
    }
}
