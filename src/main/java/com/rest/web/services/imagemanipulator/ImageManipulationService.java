package com.rest.web.services.imagemanipulator;

import java.awt.image.BufferedImage;

public interface ImageManipulationService {

    BufferedImage rotate(BufferedImage image, Double angle);

    BufferedImage grayscale(BufferedImage image);

    BufferedImage sepia(BufferedImage image, int sepiaIntensity);

    BufferedImage invert(BufferedImage image);

    BufferedImage changeBrightness(BufferedImage image, float val);

    BufferedImage resize(BufferedImage image, int width, int height);
}
