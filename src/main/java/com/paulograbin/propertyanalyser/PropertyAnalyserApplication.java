package com.paulograbin.propertyanalyser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PropertyAnalyserApplication {

    private final PropertyExtractor propertyExtractor;
    private final EnvironmentLoader environmentLoader;
    private final PropertyMerger propertyMerger;

    private Map<String, Property> propertiesDictionary;
    private int environmentCount = 0;


    public PropertyAnalyserApplication() {
        this.propertiesDictionary = new HashMap<>();

        this.propertyExtractor = new PropertyExtractor();
        this.environmentLoader = new EnvironmentLoader();
        this.propertyMerger = new PropertyMerger();
    }


    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Ooops you forgot some parameters didn't you?");
            System.exit(1);
        }

        PropertyAnalyserApplication p = new PropertyAnalyserApplication();
        final AnalysisResult analysisResult = p.runAnalysis(args[0]);
    }

    public AnalysisResult runAnalysis(String path) {
        environmentLoader.loadPropertiesFilesFromEnvironment(new File(path));

        var propertyFilesFound = environmentLoader.getFilesList();

        computePropertiesInEnvironment(propertyFilesFound);
        logInformation();
        printResults();

        return null;
    }

    private void computePropertiesInEnvironment(List<File> propertyFilesFound) {
        for (var file : propertyFilesFound) {
            System.out.println("Reading properties from file: " + file.getPath());

            try {
                var lines = Files.lines(file.toPath());
                var extractedPropertiesFromEnvironment = propertyExtractor.processFile(lines, file.getName());

                combineCurrentPropertiesWithResult(extractedPropertiesFromEnvironment);

                environmentCount++;
            } catch (IOException e) {
                System.err.println("Problems while parsing file " + file.getName());
            }
        }
    }

    private void printResults() {
        var allPropertiesFound = propertiesDictionary.values();

        var finalEnvironmentCount = environmentCount;
        allPropertiesFound.forEach(p -> {
            if(p.getValuePerEnvironment().values().size() == finalEnvironmentCount) {
                System.out.println("Property " + p.getName() + " is present in all environments with the same value");
                System.out.println(p.getName());
            }
        });
    }

    private void combineCurrentPropertiesWithResult(List<Property> extractedPropertiesFromEnvironment) {
        propertyMerger.addToDictionary(propertiesDictionary, extractedPropertiesFromEnvironment.stream());
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
}
