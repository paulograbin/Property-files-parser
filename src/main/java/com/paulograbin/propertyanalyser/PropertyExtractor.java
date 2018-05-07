package com.paulograbin.propertyanalyser;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.springframework.util.StringUtils.hasText;

@Component
public class PropertyExtractor {

    private static final String COMMENT_CHARACTER = "#";

    private String environmentName = "";
    private Map<String, Property> propertiesDictionary = new HashMap<>();

    public PropertyExtractor() {
    }

    public void processFile(File file) {
        try {
            environmentName = getEnvironmentIdFromFileName(file.getName());

            final Stream<String> lines = Files.lines(file.toPath());

            Consumer<String> c = new Consumer<String>() {
                @Override
                public void accept(String line) {
                    if (hasText(line)) {
                        try {
                            final Property property = extractPropertyFromTextLine(line);

                            if (propertyAlreadyProcessed(property)) {
                                appendNewValue(property);

                            } else {
                                addPropertyForTheFirstTime(property);
                            }
                        } catch (RuntimeException e) {
                            System.err.println("Error trying to extract property from line " + line + ". Are you sure this is a property?");
                        }
                    }
                }

                private void appendNewValue(Property property) {
                    final Property currentProp = propertiesDictionary.get(property.getName());

                    currentProp.addEnvironmentValue(environmentName, property.getValue());
                }

                private boolean propertyAlreadyProcessed(Property property) {
                    return propertiesDictionary.containsKey(property.getName());
                }


                private void addPropertyForTheFirstTime(Property property) {
                    propertiesDictionary.put(property.getName(), property);
                }
            };

            lines.filter(l -> !lineIsCommentary(l)).forEach(c);
        } catch (RuntimeException e) {
            System.out.println("Deu merda aqui...");
            e.printStackTrace();

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Map<String, Property> getPropertiesDictionary() {
        return propertiesDictionary;
    }

    private String getEnvironmentIdFromFileName(String name) {
        final String[] split = name.split("__");

        return name;
    }

    private boolean lineIsCommentary(String line) {
        return line.startsWith(COMMENT_CHARACTER);
    }

    private Property extractPropertyFromTextLine(String line) {
        line = line.trim();
        final int i = findEqualSignPosition(line);

        final String propertyName = extractPropertyName(line, i);

        String propertyValue = "";
        if (lineHasPropertyValue(line, i)) {
            propertyValue = extractPropertyValue(line, i);
        }

        Property p = new Property(propertyName, environmentName, propertyValue);
        p.addEnvironmentValue(environmentName, propertyValue);

        return p;
    }

    private String extractPropertyValue(String line, int i) {
        return line.substring(i+1, line.length());
    }

    private boolean lineHasPropertyValue(String line, int i) {
        return hasText(extractPropertyValue(line, i));
    }

    private String extractPropertyName(String line, int i) {
        return line.substring(0, i);
    }

    private int findEqualSignPosition(String line) {
        final int equalSignPosition = line.indexOf("=");

        if(equalSignPosition == -1) {
            throw new RuntimeException("No equal sign found in line");
        }

        return equalSignPosition;
    }
}
