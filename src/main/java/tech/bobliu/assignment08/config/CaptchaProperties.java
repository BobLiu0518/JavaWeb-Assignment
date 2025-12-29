package tech.bobliu.assignment08.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app.captcha")
@Data
public class CaptchaProperties {
    private List<CaptchaItem> list = new ArrayList<>();

    @Data
    public static class CaptchaItem {
        private String filename;
        private String result;
    }
}
