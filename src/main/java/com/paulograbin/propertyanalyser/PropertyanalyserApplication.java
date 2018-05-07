package com.paulograbin.propertyanalyser;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;



@SpringBootApplication
public class PropertyanalyserApplication {

    private PropertyExtractor propertyExtractor = new PropertyExtractor();
    private EnvironmentLoader environmentLoader = new EnvironmentLoader();

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
            propertyExtractor.processFile(file);
            environmentCount++;
        }

        System.out.println(environmentCount + " environments scanned");
        final Map<String, Property> propertiesDictionary = propertyExtractor.getPropertiesDictionary();
        final Collection<Property> values = propertiesDictionary.values();


        int finalEnvironmentCount = environmentCount;
        values.forEach(p -> {
            if(p.getValuePerEnvironment().values().size() == finalEnvironmentCount) {
                System.out.println("Property " + p.getName() + " is present in all envs with the same value");
                System.out.println(p.getName());
            }
        });
    }
}
