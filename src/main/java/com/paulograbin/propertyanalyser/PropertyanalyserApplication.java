package com.paulograbin.propertyanalyser;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.HashMap;
import java.util.Map;



@SpringBootApplication
public class PropertyanalyserApplication {

    private final PropertyExtractor propertyExtractor = new PropertyExtractor();
    private final EnvironmentLoader environmentLoader = new EnvironmentLoader();

    private Map<String, Property> propertiesDictionary = new HashMap<>();
    private int environmentCount = 0;


    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Ooops you forgot some parameters didn't you?");
            System.exit(1);
        }

        PropertyanalyserApplication p = new PropertyanalyserApplication();
        p.init(args[0]);
    }

    private void init(String path) {
        environmentLoader.loadPropertiesFilesFromEnvironment(new File(path));

        var propertyFilesFound = environmentLoader.getFilesList();

        for (var file : propertyFilesFound) {
            System.out.println("Reading properties from file: " + file.getName() + " " + file.getParent());
            var extractedPropertiesFromEnvironment = propertyExtractor.processFile(file);

            extractedPropertiesFromEnvironment.stream().forEach(this::addToDictionary);

            environmentCount++;
        }

        logInformation();


        var allPropertiesFound = propertiesDictionary.values();

        var finalEnvironmentCount = environmentCount;
        allPropertiesFound.forEach(p -> {
            if(p.getValuePerEnvironment().values().size() == finalEnvironmentCount) {
                System.out.println("Property " + p.getName() + " is present in all environments with the same value");
                System.out.println(p.getName());
            }
        });
    }

    private void logInformation() {
        System.out.println(environmentCount + " environments scanned");
        System.out.println(propertiesDictionary.keySet().size() + " properties found");
        System.out.println(propertiesDictionary.values()
                .stream()
                .mapToInt(p -> p.getValuePerEnvironment()
                        .keySet()
                        .size())
                .sum() + " values found"); // TODO make this less awful to the eyes
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
        var currentProperty = propertiesDictionary.get(property.getName());

        currentProperty.addEnvironmentValue(property.getEnvironmentName(), property.getValue());
    }

    private boolean propertyAlreadyProcessed(Property property) {
        return propertiesDictionary.containsKey(property.getName());
    }

    private void addPropertyForTheFirstTime(Property property) {
        propertiesDictionary.put(property.getName(), property);
    }
}
