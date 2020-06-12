package com.assessme.config;

public class StorageConfig implements IStorageConfig{
    private String location = "tmp/uploadDir/csvImports";
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
}
