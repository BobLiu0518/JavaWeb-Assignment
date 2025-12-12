package tech.bobliu.assignment05.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@WebServlet(name = "captchaServlet", value = "/captcha")
public class CaptchaServlet extends HttpServlet {
    private static class Captcha {
        public String filename;
        public String result;

        public Captcha(String filename, String result) {
            this.filename = filename;
            this.result = result;
        }
    }

    private List<Captcha> captchaList;

    public void init() {
        this.captchaList = List.of(
                new Captcha("captcha1.jpg", "1"),
                new Captcha("captcha2.jpg", "0"),
                new Captcha("captcha3.jpg", "2x"),
                new Captcha("captcha4.jpg", "1"),
                new Captcha("captcha5.jpg", "-1"),
                new Captcha("captcha6.jpg", "4"),
                new Captcha("captcha7.jpg", "2"),
                new Captcha("captcha8.jpg", "48"),
                new Captcha("captcha9.jpg", "0"),
                new Captcha("captcha10.jpg", "4")
        );
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("image/jpeg");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        HttpSession session = request.getSession();
        int captchaIndex;
        Integer lastIndex = (Integer) session.getAttribute("captchaIndex");
        do {
            captchaIndex = (int) (Math.random() * captchaList.size());
        } while (lastIndex != null && captchaIndex == lastIndex);

        Captcha captcha = captchaList.get(captchaIndex);
        session.setAttribute("captchaIndex", captchaIndex);
        session.setAttribute("captchaResult", captcha.result);
        try (InputStream is = getServletContext().getResourceAsStream("/WEB-INF/captcha/" + captcha.filename)) {
            OutputStream os = response.getOutputStream();
            is.transferTo(os);
        }
    }
}