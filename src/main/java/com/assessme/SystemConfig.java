package com.assessme;

import com.assessme.config.EmailConfig;
import com.assessme.config.EmailConfigImpl;
import com.assessme.db.connection.DefaultDatabaseConfiguration;
import com.assessme.db.connection.IDatabaseConfiguration;

public class SystemConfig {

    private static SystemConfig instance;
    private EmailConfig emailConfig;
    private IDatabaseConfiguration databaseConfiguration;

    public SystemConfig() {
        emailConfig = new EmailConfigImpl();
        databaseConfiguration = new DefaultDatabaseConfiguration();
    }

    public static SystemConfig getInstance() {
        if (instance == null) {
            instance = new SystemConfig();
        }
        return instance;
    }


    public EmailConfig getEmailConfig() {
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
