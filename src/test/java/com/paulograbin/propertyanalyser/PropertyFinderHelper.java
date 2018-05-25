package com.paulograbin.propertyanalyser;

import java.util.List;
import java.util.Set;


public class PropertyFinderHelper {

    private String propertyName;
    private List<Property> properties;


    public PropertyFinderHelper() {
    }

    public PropertyFinderHelper givenPropertySet(List<Property> properties) {
        this.properties = properties;

        return this;
    }

    public PropertyFinderHelper containsProperty(String propertyName) {
        this.propertyName = propertyName;

        return this;
    }

    public boolean withValue(String value) {
        return properties.stream()
                .anyMatch(p -> p.getName().equals(propertyName)
                        && p.getValuePerEnvironment().containsValue(value));
    }

    public boolean withValues(String... values) {
        //TODO finish this, adapting to when one environment has more than one value for a prop

        final Set<String> mustHaveValues = Set.of(values);

        boolean firstResult = properties.stream()
                .anyMatch(p -> p.getName().equals(propertyName));

        boolean secondResult = properties.stream()
                .anyMatch(p -> p.getValuePerEnvironment().values().containsAll(mustHaveValues));

        return firstResult && secondResult;
    }
}
