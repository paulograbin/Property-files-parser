package com.paulograbin.propertyanalyser;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@Component
public class EnvironmentLoader {

    private List<File> filesList = new ArrayList<>();

    public URL loadContext() {
        return this.getClass().getResource("/environments");
    }

    public void loadPropertiesFilesFromEnvironment(File directory) {
        if (directory.isDirectory()) {
            final File[] files = directory.listFiles();

            for (File file : files) {
                loadPropertiesFilesFromEnvironment(file);
            }
        }

        if (shouldScanFile(directory)) {
            filesList.add(directory);
        }
    }

    private static boolean shouldScanFile(File file) {
        return new FilenameFilter() {

            /**
             * Tests if a specified file should be included in a file list.
             *
             * @param dir  the directory in which the file was found.
             * @param name the name of the file.
             * @return <code>true</code> if and only if the name should be
             * included in the file list; <code>false</code> otherwise.
             */
            @Override
            public boolean accept(File dir, String name) {
                return name.contains(".properties");
            }
        }.accept(file.getParentFile(), file.getName());
    }

    public List<File> getFilesList() {
        return filesList;
    }
}
