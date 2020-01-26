package com.rest.web.services.imagemanipulator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "manipulation")
@RequiredArgsConstructor
public class ManipulationEndpoint {

    private static final Double ROTATE_ANGLE = 90d;

    private final ImageRepository imageRepository;
    private final ImageManipulationService imageManipulationService;

    @PostMapping("/rotate/{id}")
    public Image rotate(@PathVariable Long id) {
        Image image = imageRepository.findById(id).orElseThrow(NoSuchElementException::new);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(image.getContent());
        try {
            BufferedImage rotatedImage = imageManipulationService.rotate(
                    ImageIO.read(new ByteArrayInputStream(image.getContent())), ROTATE_ANGLE);

            image.setContent(
                    FileUtils.bufferedImageToBytesArray(rotatedImage, FileUtils.getExtensionType(image.getName())));
            imageRepository.save(image);
        } catch (Exception e) {
            log.error("Error occurred: ", e);
        }
        return image;
    }



}
