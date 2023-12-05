package me.hskwon.fileupload.infrastructure;

import io.hypersistence.tsid.TSID;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class LocalImageStorage {
    public void upload(byte[] content) {
        String id = TSID.Factory.getTsid().toString();
        File file = new File("data/%s.%s".formatted(id, "jpg"));

        try (OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
