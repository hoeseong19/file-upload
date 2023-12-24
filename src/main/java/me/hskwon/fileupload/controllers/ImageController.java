package me.hskwon.fileupload.controllers;

import me.hskwon.fileupload.dtos.UsernameDto;
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

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public String postImage(
            @RequestPart UsernameDto usernameDto,
            @RequestPart MultipartFile image
    ) throws IOException {
        String username = usernameDto.username();

        if (image == null || image.isEmpty()) {
            return "No Image";
        }

        return this.imageStorage.upload(username, image.getBytes());
    }
}
