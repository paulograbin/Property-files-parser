package com.paulograbin.propertyanalyser;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.util.StringUtils.hasText;


@Component
public class PropertyExtractor {

    private static final String COMMENT_CHARACTER = "#";
    private static final int STRING_BEGIN_INDEX = 0;

    private String environmentName = "";


    public List<Property> processFile(File file) {
        try {
            return this.processFile(Files.lines(file.toPath()), file.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<Property> processFile(Stream<String> lines, String fileName) {
        try {
            environmentName = getEnvironmentIdFromFileName(fileName);

            return lines.filter(l -> !lineIsCommentary(l))
                    .filter(StringUtils::hasText)
                    .map(this::extractPropertyFromTextLine)
                    .collect(Collectors.toList());

        } catch (RuntimeException e) {
            System.out.println("Deu merda aqui...");
        }

        return Collections.emptyList();
    }

    private Property extractPropertyFromTextLine(String line) {
        line = line.trim();
        var i = findEqualSignPosition(line);

        var propertyName = extractPropertyName(line, i);

        var propertyValue = "";
        if (lineHasPropertyValue(line, i)) {
            propertyValue = extractPropertyValue(line, i);
        }

        var extractedProperty = new Property(propertyName, environmentName, propertyValue, line);
        extractedProperty.addEnvironmentValue(environmentName, propertyValue);

        return extractedProperty;
    }


    private String getEnvironmentIdFromFileName(String name) {
        final String[] split = name.split("__");

        return name;
    }

    private boolean lineIsCommentary(String line) {
        return line.startsWith(COMMENT_CHARACTER);
    }

    private String extractPropertyValue(String line, int i) {
        return line.substring(i+1, line.length());
    }

    private boolean lineHasPropertyValue(String line, int i) {
        return hasText(extractPropertyValue(line, i));
    }

    private String extractPropertyName(String line, int equalSignIndex) {
        return line.substring(STRING_BEGIN_INDEX, equalSignIndex);
    }

    private int findEqualSignPosition(String line) {
        var equalSignPosition = line.indexOf("=");

        if(equalSignPosition == -1) {
            throw new RuntimeException("No equal sign found in line");
        }

        return equalSignPosition;
    }
}
