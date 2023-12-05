package me.hskwon.fileupload.infrastructure;

import io.hypersistence.tsid.TSID;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class LocalImageStorage {
    public String upload(String username, byte[] content) {
        String id = TSID.Factory.getTsid().toString();
        File folder = new File("data/%s/".formatted(username));
        File file = new File("data/%s/%s.%s".formatted(username, id, "jpg"));

        if (!folder.exists()) {
            System.out.println(folder.mkdir());
        }

        try (OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(content);

            return "data/%s/%s.%s".formatted(username, id, "jpg");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
