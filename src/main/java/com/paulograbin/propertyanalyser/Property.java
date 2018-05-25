package com.paulograbin.propertyanalyser;

import java.util.HashMap;
import java.util.Map;


class Property {

    private final String name;
    private final String environmentName;
    private final String value;
    private final String originalLine;

    private final Map<String, String> valuePerEnvironment;

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

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", environmentName='" + environmentName + '\'' +
                ", value='" + value + '\'' +
                ", originalLine='" + originalLine + '\'' +
                ", valuePerEnvironment=" + valuePerEnvironment +
                '}';
    }
}
