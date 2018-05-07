package com.paulograbin.propertyanalyser;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collection;
import java.util.List;
import java.util.Map;



@SpringBootApplication
public class PropertyanalyserApplication {

    PropertyExtractor propertyExtractor = new PropertyExtractor();
    EnvironmentLoader environmentLoader = new EnvironmentLoader();

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
            if(shouldScanFile(file)) {
                System.out.println("Reading properties from file: " + file.getName() + " " + file.getParent());
                propertyExtractor.processFile(file);
                environmentCount++;
            } else {
                // It's not a local.properties file, so skipping it...
            }
        }

        System.out.println(environmentCount + " environment scanned");
        final Map<String, Property> propertiesDictionary = propertyExtractor.getPropertiesDictionary();
        final Collection<Property> values = propertiesDictionary.values();


        int finalEnvironmentCount = environmentCount;
        values.stream().forEach(p -> {

            if(p.getValuePerEnvironment().values().size() == finalEnvironmentCount) {
                System.out.println("Property " + p.getName() + " is present in all envs with the same value");
                System.out.println(p.getName());
            }
        });
    }

    private static boolean shouldScanFile(File file) {
        return new FilenameFilter() {

            /**
             * Tests if a specified file should be included in a file list.
             *
             * @param dir  the directory in which the file was found.
             * @param name the name of the file.
             * @return <code>true</code> if and only if the name should be
             * included in the file list; <code>false</code> otherwise.
             */
            @Override
            public boolean accept(File dir, String name) {
                if(name.contains(".properties"))
                    return true;

                return false;
            }
        }.accept(file.getParentFile(), file.getName());
    }
}
