package com.paulograbin.propertyanalyser;

import java.util.HashMap;
import java.util.Map;


public class Property {

    private String name;
    private String environmentName;
    private String value;
    private String originalLine;
    private Map<String, String> valuePerEnvironment;

    public Property(String name, String environmentName, String value, String originalLine) {
        this.name = name;
        this.environmentName = environmentName;
        this.value = value;
        this.originalLine = originalLine;

        this.valuePerEnvironment = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public Map<String, String> getValuePerEnvironment() {
        return valuePerEnvironment;
    }

    public void addEnvironmentValue(String environmentName, String propertyValue) {
        valuePerEnvironment.put(environmentName, propertyValue);
    }

    public String getEnvironmentName() {
        return environmentName;
    }

    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    public String getOriginalLine() {
        return originalLine;
    }

    public void setOriginalLine(String originalLine) {
        this.originalLine = originalLine;
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", environmentName='" + environmentName + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
