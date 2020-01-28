package com.rest.web.services.imagemanipulator;

import com.rest.web.services.imagemanipulator.exception.ImageNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "base")
@RequiredArgsConstructor
public class BaseEndpoint {

    private final ImageRepository imageRepository;

    @PostMapping("/upload")
    public Image uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        Image image = new Image(file.getOriginalFilename(), file.getContentType(), file.getBytes());
        return imageRepository.save(image);
    }

    @GetMapping("image/{id}")
    public Image getImageById(@PathVariable Long id) {
        return imageRepository.findById(id).orElse(null);
    }

    @DeleteMapping("image/{id}")
    public void deleteImageById(@PathVariable Long id) {
        imageRepository.findById(id).ifPresent(imageRepository::delete);
    }
}
