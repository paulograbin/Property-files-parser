package com.paulograbin.propertyanalyser;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class PropertyMergerTest {

    private PropertyMerger merger;
    private Map<String, Property> dictionary;

    @Before
    public void setUp() {
        merger = new PropertyMerger();
        dictionary = new HashMap<>();
    }

    @Test
    public void multipleCallsWithSameParametersMustNotModify() {
        merger.addToDictionary(dictionary, makeStream());
        assertEquals(4, dictionary.size());


        merger.addToDictionary(dictionary, makeStream());
        assertEquals(4, dictionary.size());
    }

    public Stream<Property> makeStream() {
        Property a = new Property("a", "10", "a", "a=a");
        Property b = new Property("b", "10", "b", "b=b");
        Property c = new Property("c", "10", "c", "c=c");
        Property d = new Property("d", "10", "d", "d=d");

        return Stream.of(a, b,c ,d);
    }
}
