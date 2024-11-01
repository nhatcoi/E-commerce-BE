package com.example.ecommerceweb.configurations;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class GoogleCredentialsConfig {
    String googleCredentialsPath = System.getProperty("user.dir") + "/google-api-key.json";

    @PostConstruct
    public void init() {
        // Load file từ đường dẫn cấu hình
        File credentialsFile = new File(googleCredentialsPath);

        // Kiểm tra xem file có tồn tại không
        if (credentialsFile.exists()) {
            System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", credentialsFile.getAbsolutePath());
            System.out.println("GOOGLE_APPLICATION_CREDENTIALS set to: " + credentialsFile.getAbsolutePath());
        } else {
            throw new IllegalArgumentException("Google credentials file not found at path: " + googleCredentialsPath);
        }
    }
}
