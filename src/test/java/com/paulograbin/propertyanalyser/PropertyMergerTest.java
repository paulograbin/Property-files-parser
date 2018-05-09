package com.paulograbin.propertyanalyser;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class PropertyMergerTest {

    private PropertyMerger merger;
    private Map<String, Property> dictionary;

    @Before
    public void setUp() {
        merger = new PropertyMerger();
        dictionary = new HashMap<>();
    }

    @Test
    public void name() {
//        merger.addToDictionary();
    }
}
