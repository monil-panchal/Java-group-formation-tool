package com.assessme;

import com.assessme.config.*;

public class SystemConfig {

    private static SystemConfig instance;
    private IStorageConfig storageConfig;
    private IDatabaseConfig databaseConfig;
    private EmailConfig emailConfig;

    public SystemConfig() {
        storageConfig = new StorageConfig();
        databaseConfig = new DatabaseConfig();
        emailConfig = new EmailConfig();
    }

    public static SystemConfig getInstance() {
        if (instance == null)
            instance = new SystemConfig();
        return instance;
    }

    public IStorageConfig getStorageConfig() {
        return storageConfig;
    }

    public IDatabaseConfig getDatabaseConfig() {
        return databaseConfig;
    }


    public EmailConfig getEmailConfig(){
        return emailConfig;
    }
}
