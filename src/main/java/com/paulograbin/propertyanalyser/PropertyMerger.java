package com.paulograbin.propertyanalyser;

import java.util.Map;
import java.util.stream.Stream;


public class PropertyMerger {

    private Map<String, Property> dictionary;

    public void addToDictionary(Map<String, Property> propertiesDictionary, Stream<Property> extractedPropertiesFromEnvironment) {
        dictionary = propertiesDictionary;

        extractedPropertiesFromEnvironment.forEach(p -> {
            try {
                if (propertyAlreadyProcessed(p)) {
                    appendNewValue(p);
                } else {
                    addPropertyForTheFirstTime(p);
                }
            } catch (RuntimeException e) {
                System.err.println("Error trying to extract property from line " + p.getOriginalLine() + ". Are you sure this is a property?");
            }
        });
    }

    private void appendNewValue(Property property) {
        var currentProperty = dictionary.get(property.getName());

        currentProperty.addEnvironmentValue(property.getEnvironmentName(), property.getValue());
    }

    private boolean propertyAlreadyProcessed(Property property) {
        return dictionary.containsKey(property.getName());
    }

    private void addPropertyForTheFirstTime(Property property) {
        dictionary.put(property.getName(), property);
    }
}
