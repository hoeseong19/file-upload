package me.hskwon.fileupload.infrastructure;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.hypersistence.tsid.TSID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class CloudinaryImageStorage {
    private final Cloudinary cloudinary;

    public CloudinaryImageStorage(@Value("${cloudinary.url}") String cloudinaryUrl) {
        this.cloudinary = new Cloudinary(cloudinaryUrl);
        this.cloudinary.config.secure = true;
    }

    public String upload(byte[] content) {
        String id = TSID.Factory.getTsid().toString();

        try {
            Map result = cloudinary.uploader().upload(content,
                    ObjectUtils.asMap("public_id", id));

            return result.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
