package me.hskwon.fileupload.controllers;

import me.hskwon.fileupload.dtos.UploadImageDto;
import me.hskwon.fileupload.infrastructure.LocalImageStorage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/images")
public class ImageController {
    private final LocalImageStorage imageStorage;

    public ImageController(LocalImageStorage imageStorage) {
        this.imageStorage = imageStorage;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public String postImage(@ModelAttribute UploadImageDto uploadImageDto) throws IOException {
        MultipartFile multipartFile = uploadImageDto.image();

        if (multipartFile == null || multipartFile.isEmpty()) {
            return "No Image";
        }

        return this.imageStorage.upload(multipartFile.getBytes());
    }
}
