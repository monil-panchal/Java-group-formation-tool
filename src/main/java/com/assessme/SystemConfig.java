package com.assessme;

import com.assessme.config.*;
import com.assessme.db.connection.DefaultDatabaseConfiguration;
import com.assessme.db.connection.IDatabaseConfiguration;

public class SystemConfig {

    private static SystemConfig instance;
    private IStorageConfig storageConfig;
    private EmailConfig emailConfig;
    private IDatabaseConfiguration databaseConfiguration;

    public SystemConfig() {
        storageConfig = new StorageConfig();
        emailConfig = new EmailConfig();
        databaseConfiguration = new DefaultDatabaseConfiguration();
    }

    public static SystemConfig getInstance() {
        if (instance == null)
            instance = new SystemConfig();
        return instance;
    }
    public IDatabaseConfiguration getDatabaseConfiguration() {
        return databaseConfiguration;
    }

    public IStorageConfig getStorageConfig() {
        return storageConfig;
    }

    public EmailConfig getEmailConfig(){
        return emailConfig;
    }
}
