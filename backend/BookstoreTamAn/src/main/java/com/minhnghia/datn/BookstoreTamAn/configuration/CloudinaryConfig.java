package com.minhnghia.datn.BookstoreTamAn.configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dqqz6bgry",
                "api_key", "932548139173876",
                "api_secret", "1Y8G9SUzECs05eG-rSgXaDj9fdE",
                "secure", true
        ));
    }
}
