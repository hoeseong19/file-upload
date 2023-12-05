package me.hskwon.fileupload.dtos;

import org.springframework.web.multipart.MultipartFile;

public record UploadImageDto(
        MultipartFile image
) {
}
