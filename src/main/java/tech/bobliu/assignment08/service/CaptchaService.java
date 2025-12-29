package tech.bobliu.assignment08.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

public interface CaptchaService {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class Captcha {
        private String filename;
        private String result;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class CaptchaResponse {
        private Captcha captcha;
        private int index;
    }

    CaptchaResponse getNextCaptcha(Integer lastIndex);
    InputStream getCaptchaImage(String filename);
}
