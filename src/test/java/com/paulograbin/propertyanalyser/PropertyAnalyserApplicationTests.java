package com.paulograbin.propertyanalyser;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.net.URISyntaxException;
import java.net.URL;
import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PropertyAnalyserApplicationTests {

    private PropertyAnalyserApplication p = new PropertyAnalyserApplication();

    @Before
    public void setUp() {
    }

    @Test
    public void contextLoads() {
        final String s = this.getClass().getResource("/environments").toString();

        AnalysisResult a = p.runAnalysis(s);

        assertNotNull(a);
    }

    @Test
    public void name() {
        final URL resource = this.getClass().getResource("/environments");

        try {
            final File file = new File(resource.toURI());

            System.out.println("Directory " + file.isDirectory());
            System.out.println("File " + file.isFile());

            teste(file);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        System.out.println(resource);
    }
//
    private void teste(File directory) {
        if (directory.isDirectory()) {
            System.out.println(directory.getName() + " is directory...");

            final File[] files = directory.listFiles();

            for (File file : files) {
                teste(file);
            }
        } else {
            System.out.println("Found file " + directory.getName());
        }
    }

    @Test
    @Ignore
    public void name3() {
        assertTrue("aaaaa".matches(".*[a-z]+.*"));
        assertTrue("AAAAAA".matches("^(?=\\w{6})[A-Z0-9]{6}$"));
        assertTrue("AA3AAA".matches("^(?=\\w{6})[A-Z0-9]{6}$"));
        assertTrue("123455".matches("^(?=\\w{6})[A-Z0-9]{6}$"));

        assertFalse("111".matches("^(?=\\w{6})[A-Z0-9]{6}$"));
        assertFalse("aaa".matches("^(?=\\w{6})[A-Z0-9]{6}$"));
        assertFalse("11111a".matches("^(?=\\w{6})[A-Z0-9]{6}$"));
        assertFalse("11111111".matches("^(?=\\w{6})[A-Z0-9]{6}$"));
    }

    @Test
    @Ignore
    public void name2() {
        assertTrue("AAAAAA".matches(".*[a-z]+.*"));
    }
}
