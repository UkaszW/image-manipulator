package com.rest.web.services.imagemanipulator;

import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;

@Service
public class ImageManipulationServiceServiceImpl implements ImageManipulationService {

    @Override
    public BufferedImage rotate(BufferedImage image, Double angle) {
        int w = image.getWidth();
        int h = image.getHeight();
        double toRad = Math.toRadians(angle);
        int hPrime = (int) (w * Math.abs(Math.sin(toRad)) + h * Math.abs(Math.cos(toRad)));
        int wPrime = (int) (h * Math.abs(Math.sin(toRad)) + w * Math.abs(Math.cos(toRad)));

        BufferedImage rotatedImage = new BufferedImage(wPrime, hPrime, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = rotatedImage.createGraphics();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, wPrime, hPrime);  // fill entire area
        g.translate(wPrime/2, hPrime/2);
        g.rotate(toRad);
        g.translate(-w/2, -h/2);
        g.drawImage(image, 0, 0, null);
        g.dispose();  // release used resources before g is garbage-collected
        return rotatedImage;
    }
}
