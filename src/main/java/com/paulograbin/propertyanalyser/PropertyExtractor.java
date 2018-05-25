package com.paulograbin.propertyanalyser;

import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.util.StringUtils.hasText;


public class PropertyExtractor {

    private static final String COMMENT_CHARACTER = "#";
    private static final int STRING_BEGIN_INDEX = 0;

    private String environmentName = "";


    public List<Property> processFile(final Stream<String> lines, final String fileName) {
        environmentName = getEnvironmentIdFromFileName(fileName);

        return lines.filter(this::isValidProperty)
                .map(this::extractPropertyFromTextLine)
                .collect(Collectors.toUnmodifiableList());
    }

    private boolean isValidProperty(String s) {
        return !lineIsCommentary(s) && StringUtils.hasText(s) && findEqualSignPosition(s) > 0;
    }

    private Property extractPropertyFromTextLine(String line) {
        line = line.trim();

        Property extractedProperty = new Property("", "", "", "");

        try {
            var equalSignPosition = findEqualSignPosition(line);

            var propertyName = extractPropertyName(line, equalSignPosition);

            var propertyValue = "";
            if (lineHasPropertyValue(line, equalSignPosition)) {
                propertyValue = extractPropertyValue(line, equalSignPosition);
            }

            extractedProperty = new Property(propertyName, environmentName, propertyValue, line);
            extractedProperty.addEnvironmentValue(environmentName, propertyValue);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return extractedProperty;
    }

    private String getEnvironmentIdFromFileName(String name) {
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
        return line.indexOf("=");
    }
}
