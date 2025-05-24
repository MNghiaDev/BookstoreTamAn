package com.minhnghia.datn.BookstoreTamAn.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "vnp")
@Getter
@Setter
public class VnPayConfig {
    private String payUrl;
    private String returnUrl;
    private String tmnCode;
    private String secretKey;
    private String apiUrl;
}
