package com.paulograbin.propertyanalyser;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

class EnvironmentLoaderTest {

    EnvironmentLoader environmentLoader;

    @BeforeEach
    void setUp() {
        environmentLoader = new EnvironmentLoader();
    }

    @Test
    void name() throws URISyntaxException {
        File environmentLocation = makeResourcesURL();

        environmentLoader.loadPropertiesFilesFromEnvironment(environmentLocation);
        final List<File> filesList = environmentLoader.getFilesList();

        Assert.assertEquals(3, filesList.size());
    }

    private File makeResourcesURL() throws URISyntaxException {
        final URL url = environmentLoader.loadContext();

        return new File(url.toURI());
    }
}
