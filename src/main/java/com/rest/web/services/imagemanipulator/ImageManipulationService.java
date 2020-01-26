package com.rest.web.services.imagemanipulator;

import java.awt.image.BufferedImage;

public interface ImageManipulationService {

    BufferedImage rotate(BufferedImage image, Double angle);
}
