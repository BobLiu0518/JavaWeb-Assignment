package tech.bobliu.assignment08.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import tech.bobliu.assignment08.service.CaptchaService;

import java.io.IOException;
import java.io.InputStream;

@Controller
public class CaptchaController {

    private final CaptchaService captchaService;

    @Autowired
    public CaptchaController(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @GetMapping("/captcha")
    public void getCaptcha(HttpSession session, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        Integer lastIndex = (Integer) session.getAttribute("captchaIndex");
        CaptchaService.CaptchaResponse captchaResponse = captchaService.getNextCaptcha(lastIndex);

        session.setAttribute("captchaIndex", captchaResponse.getIndex());
        session.setAttribute("captchaResult", captchaResponse.getCaptcha().getResult());

        try (InputStream input = captchaService.getCaptchaImage(captchaResponse.getCaptcha().getFilename())) {
            input.transferTo(response.getOutputStream());
        }
    }
}
