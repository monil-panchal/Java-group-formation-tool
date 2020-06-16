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
        emailConfig = new EmailConfigImpl();
        databaseConfiguration = new DefaultDatabaseConfiguration();
    }

    public static SystemConfig getInstance() {
        if (instance == null)
            instance = new SystemConfig();
        return instance;
    }

    public IStorageConfig getStorageConfig() {
        return storageConfig;
    }

    public void setStorageConfig(IStorageConfig storageConfig) {
        this.storageConfig = storageConfig;
    }

    public EmailConfig getEmailConfig(){
        return emailConfig;
    }

    public void setEmailConfig(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }

    public IDatabaseConfiguration getDatabaseConfiguration() {
        return databaseConfiguration;
    }

    public void setDatabaseConfiguration(IDatabaseConfiguration databaseConfiguration) {
        this.databaseConfiguration = databaseConfiguration;
    }
}
