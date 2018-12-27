package com.akulinski.sspws.utils;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TestingUtils {
    public TestingUtils() {
    }

    public String base64() throws IOException {

        File file = new File("target/test-classes/base.txt");
        return IOUtils.toString(new FileInputStream(file), "UTF-8");
    }
}