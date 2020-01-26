package com.rest.web.services.imagemanipulator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtils {

    private static final Pattern EXTENSION_PATTERN = Pattern.compile("(?:\\.([^.]+))?$");

    public static String getExtensionType(String fileName) {
        Matcher matcher = EXTENSION_PATTERN.matcher(fileName);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new IllegalStateException("Not find extension type in filename: " + fileName);
        }
    }

    public static byte[] bufferedImageToBytesArray(BufferedImage image, String imageType) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, imageType, outputStream);
        return outputStream.toByteArray();
    }
}
