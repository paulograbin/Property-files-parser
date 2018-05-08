package com.paulograbin.propertyanalyser;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@SpringBootApplication
public class PropertyanalyserApplication {

    private PropertyExtractor propertyExtractor = new PropertyExtractor();
    private EnvironmentLoader environmentLoader = new EnvironmentLoader();

    private Map<String, Property> propertiesDictionary = new HashMap<>();


    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Ooops you forgot some parameters didn't you?");
            System.exit(1);
        }

        PropertyanalyserApplication p = new PropertyanalyserApplication();
        p.init(args[0]);
    }

    public void init(String path) {
        int environmentCount = 0;

        environmentLoader.loadPropertiesFilesFromEnvironment(new File(path));

        final List<File> filesList = environmentLoader.getFilesList();

        for (File file : filesList) {
            System.out.println("Reading properties from file: " + file.getName() + " " + file.getParent());
            final List<Property> properties = propertyExtractor.processFile(file);

            properties.stream().forEach(this::addToDictionary);

            environmentCount++;
        }

        System.out.println(environmentCount + " environments scanned");
        System.out.println(propertiesDictionary.keySet().size() + " properties found");
        System.out.println(propertiesDictionary.values()
                .stream()
                .mapToInt(p -> p.getValuePerEnvironment()
                        .keySet()
                        .size())
                .sum() + " values found"); // TODO make this less awful to the eyes


        final Collection<Property> values = propertiesDictionary.values();

        int finalEnvironmentCount = environmentCount;
        values.forEach(p -> {
            if(p.getValuePerEnvironment().values().size() == finalEnvironmentCount) {
                System.out.println("Property " + p.getName() + " is present in all envs with the same value");
                System.out.println(p.getName());
            }
        });
    }

    private void addToDictionary(Property property) {
        try {
            if (propertyAlreadyProcessed(property)) {
                appendNewValue(property);
            } else {
                addPropertyForTheFirstTime(property);
            }
        } catch (RuntimeException e) {
            System.err.println("Error trying to extract property from line " + property.getOriginalLine() + ". Are you sure this is a property?");
        }
    }

    private void appendNewValue(Property property) {
        final Property currentProp = propertiesDictionary.get(property.getName());

        currentProp.addEnvironmentValue(property.getEnvironmentName(), property.getValue());
    }

    private boolean propertyAlreadyProcessed(Property property) {
        return propertiesDictionary.containsKey(property.getName());
    }

    private void addPropertyForTheFirstTime(Property property) {
        propertiesDictionary.put(property.getName(), property);
    }
}
