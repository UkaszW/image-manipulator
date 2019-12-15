package com.rest.web.services.imagemanipulator;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "check")
@RequiredArgsConstructor
public class TestEndpoint {

    private final ImageRepository imageRepository;

    @PostMapping("/upload")
    public Image uploadImage(@RequestParam("myFile") MultipartFile file) throws IOException {
        Image image = new Image(file.getOriginalFilename(), file.getContentType(), file.getBytes());

        return imageRepository.save(image);
    }
}
