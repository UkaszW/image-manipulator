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

        BufferedImage rotated = new BufferedImage(w, h, image.getType());
        Graphics2D graphic = rotated.createGraphics();
        graphic.rotate(Math.toRadians(angle), w/2, h/2);
        graphic.drawImage(image, null, 0, 0);
        graphic.dispose();
        return rotated;
    }
}
