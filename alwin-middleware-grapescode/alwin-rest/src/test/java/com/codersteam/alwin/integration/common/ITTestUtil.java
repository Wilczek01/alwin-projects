package com.codersteam.alwin.integration.common;

import com.fasterxml.jackson.core.JsonEncoding;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

public class ITTestUtil {

    public static String expectedResponse(final String name) throws IOException {
        return getResponseFile(getPath(name));
    }

    static String getResponseFile(final String path) throws IOException {
        final ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        final InputStream responseInputStream = classloader.getResourceAsStream(path);
        final StringWriter writer = new StringWriter();
        IOUtils.copy(responseInputStream, writer, JsonEncoding.UTF8.getJavaName());
        return writer.toString();
    }

    private static String getPath(final String name) {
        return "/messages/response/" + name;
    }

    static File getFile(final String name) {
        return new File("src/test/resources" + getPath(name));
    }
}
