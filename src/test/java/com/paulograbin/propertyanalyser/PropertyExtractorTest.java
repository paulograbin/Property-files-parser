package com.paulograbin.propertyanalyser;

import org.junit.Before;
import org.junit.Test;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class PropertyExtractorTest {

    private PropertyExtractor propertyExtractor;
    private PropertyFinderHelper helper;

    @Before
    public void setUp() {
        propertyExtractor = new PropertyExtractor();
        helper = new PropertyFinderHelper();
    }


    @Test
    public void givenFourLines__whenEveryOneIsValid__thenShouldHaveFourProperties() {
        var properties = propertyExtractor.processFile(makeStreamOfStrings("a=a", "name=Paulo", "c=testForPropertyC", "d=testForPropertyD"), "file_1");

        assertEquals(4, properties.size());

        assertTrue(helper.givenPropertySet(properties).containsProperty("a").withValue("a"));
        assertTrue(helper.givenPropertySet(properties).containsProperty("name").withValue("Paulo"));
        assertTrue(helper.givenPropertySet(properties).containsProperty("c").withValue("testForPropertyC"));
        assertTrue(helper.givenPropertySet(properties).containsProperty("d").withValue("testForPropertyD"));
    }

    @Test
    public void givenFourLines__whenEveryOneIsValidAndOneHasTwoValues__thenShouldHaveFourProperties() {
        var properties = propertyExtractor.processFile(makeStreamOfStrings(), "file_1");

        assertEquals(4, properties.size());

        assertTrue(helper.givenPropertySet(properties).containsProperty("a").withValue("a"));
        assertTrue(helper.givenPropertySet(properties).containsProperty("b").withValue("b"));
        assertTrue(helper.givenPropertySet(properties).containsProperty("c").withValue("c"));
        assertTrue(helper.givenPropertySet(properties).containsProperty("a").withValue("adas"));
    }

    @Test
    public void givenTwoLines__whenOneIsValidAndOneIsComment__thenMustHaveOneProperty() {
        var properties = propertyExtractor.processFile(makeStreamOfStrings("a=a", "#b=c"), "file_1");

        assertEquals(1, properties.size());

        assertTrue(helper.givenPropertySet(properties).containsProperty("a").withValue("a"));
    }

    @Test
    public void givenFourLines__whenOneHasNoEqualSign__mustHaveThreeProperties() {
        final Stream<String> a = Stream.of("a=a", "b=b", "c=c", "d");

        var properties = propertyExtractor.processFile(a, "teste");

        assertEquals(3, properties.size());

        assertTrue(helper.givenPropertySet(properties).containsProperty("a").withValue("a"));
        assertTrue(helper.givenPropertySet(properties).containsProperty("b").withValue("b"));
        assertTrue(helper.givenPropertySet(properties).containsProperty("c").withValue("c"));
    }

    private Stream<String> makeStreamOfStrings() {
        return Stream.of("a=a", "b=b", "c=c", "a=adas");
    }

    private Stream<String> makeStreamOfStrings(String ... lines) {
        return Stream.of(lines);
    }
}
