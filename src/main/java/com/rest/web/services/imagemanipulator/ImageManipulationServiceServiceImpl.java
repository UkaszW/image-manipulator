package com.rest.web.services.imagemanipulator;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.RescaleOp;
import java.awt.image.WritableRaster;

@Service
public class ImageManipulationServiceServiceImpl implements ImageManipulationService {

    @Override
    public BufferedImage rotate(BufferedImage image, Double angle) {
        int width = image.getWidth();
        int height = image.getHeight();
        double toRad = Math.toRadians(angle);
        int hPrime = (int) (width * Math.abs(Math.sin(toRad)) + height * Math.abs(Math.cos(toRad)));
        int wPrime = (int) (height * Math.abs(Math.sin(toRad)) + width * Math.abs(Math.cos(toRad)));

        BufferedImage rotatedImage = new BufferedImage(wPrime, hPrime, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = rotatedImage.createGraphics();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, wPrime, hPrime);  // fill entire area
        g.translate(wPrime / 2, hPrime / 2);
        g.rotate(toRad);
        g.translate(-width / 2, -height / 2);
        g.drawImage(image, 0, 0, null);
        g.dispose();  // release used resources before g is garbage-collected
        return rotatedImage;
    }

    @Override
    public BufferedImage grayscale(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);

        op.filter(image, result);

        return result;
    }

    @Override
    public BufferedImage sepia(BufferedImage image, int sepiaIntensity) {
        BufferedImage sepia = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        // Play around with this.  20 works well and was recommended
        //   by another developer. 0 produces black/white image
        int sepiaDepth = 20;

        int width = image.getWidth();
        int height = image.getHeight();

        WritableRaster raster = sepia.getRaster();

        // We need 3 integers (for R,G,B color values) per pixel.
        int[] pixels = new int[width * height * 3];
        image.getRaster().getPixels(0, 0, width, height, pixels);

        //  Process 3 ints at a time for each pixel.  Each pixel has 3 RGB
        //    colors in array
        for (int i = 0; i < pixels.length; i += 3) {
            int r = pixels[i];
            int g = pixels[i + 1];
            int b = pixels[i + 2];

            int gry = (r + g + b) / 3;
            r = g = b = gry;
            r = r + (sepiaDepth * 2);
            g = g + sepiaDepth;

            if (r > 255) {
                r = 255;
            }
            if (g > 255) {
                g = 255;
            }
            if (b > 255) {
                b = 255;
            }

            // Darken blue color to increase sepia effect
            b -= sepiaIntensity;

            // normalize if out of bounds
            if (b < 0) {
                b = 0;
            }
            if (b > 255) {
                b = 255;
            }

            pixels[i] = r;
            pixels[i + 1] = g;
            pixels[i + 2] = b;
        }
        raster.setPixels(0, 0, width, height, pixels);

        return sepia;
    }

    @Override
    public BufferedImage invert(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb, true);
                int r = 255 - color.getRed();
                int g = 255 - color.getGreen();
                int b = 255 - color.getBlue();

                color = new Color(r, g, b, color.getAlpha());
                result.setRGB(x, y, color.getRGB());
            }
        }

        return result;
    }

    public BufferedImage changeBrightness(BufferedImage image, float val) {
        RescaleOp brighterOp = new RescaleOp(val, 0, null);
        return brighterOp.filter(image, null); //filtering
    }

    @Override
    public BufferedImage resize(BufferedImage image, int width, int height) {
        return Scalr.resize(image, width, height);
    }
}
