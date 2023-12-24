package me.hskwon.fileupload.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.hskwon.fileupload.infrastructure.LocalImageStorage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ImageController.class)
class ImageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    LocalImageStorage imageStorage;

    @Autowired
    private ObjectMapper objectMapper;

//    @MockBean
//    CloudinaryImageStorage imageStorage;

    @Test
    @DisplayName("postImage")
    void postImage() throws Exception {
        String json = """
                {
                    "username": "hskwon"
                }
                """;

        byte[] jsondata = objectMapper
                .writeValueAsString(json)
                .getBytes(StandardCharsets.UTF_8);

        MockMultipartFile dto = new MockMultipartFile(
                "dto",
                "dto",
                MediaType.APPLICATION_JSON_VALUE,
                jsondata
        );

        String filename = "src/test/resources/files/image.jpg";

        String mockName = "image";
        String mockOriginalFilename = "image.jpg";
        String mockContentType = "image/jpeg";

        MockMultipartFile file = new MockMultipartFile(
                mockName,
                mockOriginalFilename,
                mockContentType,
                new FileInputStream(filename)
        );

        RequestBuilder requestBuilder = multipart("/images")
                .file(file)
                .file(dto);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated());

        verify(imageStorage).upload("hskwon", file.getBytes());
    }
}