package tech.bobliu.assignment08.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tech.bobliu.assignment08.config.CaptchaProperties;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    private final List<Captcha> captchaList;

    @Autowired
    public CaptchaServiceImpl(CaptchaProperties captchaProperties) {
        this.captchaList = captchaProperties.getList().stream()
                .map(it -> new Captcha(it.getFilename(), it.getResult()))
                .collect(Collectors.toList());
    }

    @Override
    public CaptchaResponse getNextCaptcha(Integer lastIndex) {
        int captchaIndex;
        do {
            captchaIndex = (int) (Math.random() * captchaList.size());
        } while (lastIndex != null && captchaIndex == lastIndex);

        return new CaptchaResponse(captchaList.get(captchaIndex), captchaIndex);
    }

    @Override
    public InputStream getCaptchaImage(String filename) {
        ClassPathResource resource = new ClassPathResource("captcha/" + filename);
        try {
            return resource.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load captcha image", e);
        }
    }
}
