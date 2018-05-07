package com.paulograbin.propertyanalyser;

import java.util.HashMap;
import java.util.Map;


public class Property {

    private String name;
    private String environmentName;
    private String value;
    private Map<String, String> valuePerEnvironment;

    public Property(String name, String environmentName, String value) {
        this.name = name;
        this.environmentName = environmentName;
        this.value = value;

        this.valuePerEnvironment = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public String getEnvironmentName() {
        return environmentName;
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

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", environmentName='" + environmentName + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
