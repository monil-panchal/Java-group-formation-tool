package com.assessme;

import com.assessme.config.DatabaseConfig;
import com.assessme.config.IDatabaseConfig;
import com.assessme.config.IStorageConfig;
import com.assessme.config.StorageConfig;
import com.assessme.service.StorageService;

public class SystemConfig {

    private static SystemConfig instance;
    private IStorageConfig storageConfig;
    private IDatabaseConfig databaseConfig;

    public SystemConfig() {
        storageConfig = new StorageConfig();
        databaseConfig = new DatabaseConfig();
    }

    public static SystemConfig getInstance() {
        if(instance == null)
            instance = new SystemConfig();
        return instance;
    }

    public IStorageConfig getStorageConfig() {
        return storageConfig;
    }

    public IDatabaseConfig getDatabaseConfig() {
        return databaseConfig;
    }
}
