package com.assessme;

import com.assessme.config.*;

public class SystemConfig {

    private static SystemConfig instance;
    private IStorageConfig storageConfig;
    private EmailConfig emailConfig;

    public SystemConfig() {
        storageConfig = new StorageConfig();
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

    public EmailConfig getEmailConfig(){
        return emailConfig;
    }
}
