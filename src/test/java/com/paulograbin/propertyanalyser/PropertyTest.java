package com.paulograbin.propertyanalyser;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class PropertyTest {

    Property property;

    @Before
    public void setUp() {
        property = new Property("testName",
                "testEnvironment",
                "testValue",
                "testName=testValue");
    }

    @Test
    public void givenAllAttributes__whenCallingToString__mustContainAllAttributes() {
        final String propertyToString = property.toString();

        assertTrue(propertyToString.contains("testName"));
        assertTrue(propertyToString.contains("testEnvironment"));
        assertTrue(propertyToString.contains("testValue"));
        assertTrue(propertyToString.contains("testName=testValue"));
    }
}
