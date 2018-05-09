package com.paulograbin.propertyanalyser;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;


public class PropertyExtractorTest {

    private PropertyExtractor propertyExtractor;

    @Before
    public void setUp() {
        propertyExtractor = new PropertyExtractor();
    }

    @Test
    public void basicTest() {
        var properties = propertyExtractor.processFile(makeStreamOfStrings(), "file_1");

        assertEquals(4, properties.size());
    }

    @Test
    public void lineWithoutEqualSignShouldNotBeProperty() {
        final Stream<String> a = Stream.of("a=a", "b=b", "c=c", "a");

        final List<Property> teste = propertyExtractor.processFile(a, "teste");

        assertEquals(4, teste.size());
    }

    private Stream<String> makeStreamOfStrings() {
        return Stream.of("a=a", "b=b", "c=c", "a=adas");
    }
}
