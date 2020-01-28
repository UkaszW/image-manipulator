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

    private final ImageRepository imageRepository;
    private final ImageManipulationService imageManipulationService;

    @PutMapping("/rotate/{id}")
    public Image rotate(@PathVariable Long id, @RequestParam("angle") String angle) {
        Image image = imageRepository.findById(id).orElseThrow(NoSuchElementException::new);
        try {
            BufferedImage rotatedImage = imageManipulationService.rotate(
                    ImageIO.read(new ByteArrayInputStream(image.getContent())), Double.valueOf(angle));

            image.setContent(
                    FileUtils.bufferedImageToBytesArray(rotatedImage, FileUtils.getExtensionType(image.getName())));
            imageRepository.save(image);
        } catch (Exception e) {
            log.error("Error occurred: ", e);
        }
        return image;
    }

    @PutMapping("/grayscale/{id}")
    public Image grayscale(@PathVariable Long id) {
        Image image = imageRepository.findById(id).orElseThrow(NoSuchElementException::new);
        try {
            BufferedImage grayscale = imageManipulationService.grayscale(
                    ImageIO.read(new ByteArrayInputStream(image.getContent())));

            image.setContent(
                    FileUtils.bufferedImageToBytesArray(grayscale, FileUtils.getExtensionType(image.getName())));
            imageRepository.save(image);
        } catch (Exception e) {
            log.error("Error occurred: ", e);
        }
        return image;
    }

    @PutMapping("/sepia/{id}")
    public Image sepia(@PathVariable Long id, @RequestParam("sepiaIntensity") String sepiaIntensity) {
        Image image = imageRepository.findById(id).orElseThrow(NoSuchElementException::new);
        try {
            BufferedImage rotatedImage = imageManipulationService.sepia(
                    ImageIO.read(new ByteArrayInputStream(image.getContent())), Integer.parseInt(sepiaIntensity));

            image.setContent(
                    FileUtils.bufferedImageToBytesArray(rotatedImage, FileUtils.getExtensionType(image.getName())));
            imageRepository.save(image);
        } catch (Exception e) {
            log.error("Error occurred: ", e);
        }
        return image;
    }

    @PutMapping("/invert/{id}")
    public Image invert(@PathVariable Long id) {
        Image image = imageRepository.findById(id).orElseThrow(NoSuchElementException::new);
        try {
            BufferedImage grayscale = imageManipulationService.invert(
                    ImageIO.read(new ByteArrayInputStream(image.getContent())));

            image.setContent(
                    FileUtils.bufferedImageToBytesArray(grayscale, FileUtils.getExtensionType(image.getName())));
            imageRepository.save(image);
        } catch (Exception e) {
            log.error("Error occurred: ", e);
        }
        return image;
    }

    @PutMapping("/brightness/{id}")
    public Image brightness(@PathVariable Long id, @RequestParam("factor") String factor) {
        Image image = imageRepository.findById(id).orElseThrow(NoSuchElementException::new);
        try {
            BufferedImage rotatedImage = imageManipulationService.changeBrightness(
                    ImageIO.read(new ByteArrayInputStream(image.getContent())), Float.parseFloat(factor));

            image.setContent(
                    FileUtils.bufferedImageToBytesArray(rotatedImage, FileUtils.getExtensionType(image.getName())));
            imageRepository.save(image);
        } catch (Exception e) {
            log.error("Error occurred: ", e);
        }
        return image;
    }

    @PutMapping("/resize/{id}")
    public Image brightness(@PathVariable Long id, @RequestParam("width") String width,
                            @RequestParam("height") String height) {
        Image image = imageRepository.findById(id).orElseThrow(NoSuchElementException::new);
        try {
            BufferedImage rotatedImage = imageManipulationService.resize(
                    ImageIO.read(new ByteArrayInputStream(image.getContent())), Integer.parseInt(width),
                    Integer.parseInt(height));

            image.setContent(
                    FileUtils.bufferedImageToBytesArray(rotatedImage, FileUtils.getExtensionType(image.getName())));
            imageRepository.save(image);
        } catch (Exception e) {
            log.error("Error occurred: ", e);
        }
        return image;
    }

}
