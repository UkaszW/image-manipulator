package com.rest.web.services.imagemanipulator;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "manipulation")
@RequiredArgsConstructor
public class ManipulationEndpoint {

    private static final Double ROTATE_ANGLE = 90d;

    private final ImageRepository imageRepository;

    @PostMapping("/rotate/{id}")
    public Image rotate(@PathVariable Long id) {
        Image image = imageRepository.findById(id).orElseThrow(NoSuchElementException::new);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(image.getContent());
        try {
            BufferedImage rotatedImage = rotate(ImageIO.read(new ByteArrayInputStream(image.getContent())), ROTATE_ANGLE);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(rotatedImage, "jpg", outputStream);
            //image.setContent(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private BufferedImage rotate(BufferedImage bimg, double angle) {
        int w = bimg.getWidth();
        int h = bimg.getHeight();

        BufferedImage rotated = new BufferedImage(w, h, bimg.getType());
        Graphics2D graphic = rotated.createGraphics();
        graphic.rotate(Math.toRadians(angle), w/2, h/2);
        graphic.drawImage(bimg, null, 0, 0);
        graphic.dispose();
        return rotated;
    }

}
