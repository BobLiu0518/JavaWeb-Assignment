package tech.bobliu.assignment08.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tech.bobliu.assignment08.model.User;
import tech.bobliu.assignment08.service.UserService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String captcha,
            HttpSession session,
            Model model
    ) {
        String captchaResult = (String) session.getAttribute("captchaResult");
        if (captchaResult == null || !captchaResult.equals(captcha)) {
            model.addAttribute("error", "验证码错误");
            return "login";
        }

        User user = userService.login(username, password);
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/";
        } else {
            model.addAttribute("error", "用户名或密码错误");
            return "login";
        }
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String captcha,
            HttpSession session,
            Model model
    ) {
        String captchaResult = (String) session.getAttribute("captchaResult");
        if (captchaResult == null || !captchaResult.equals(captcha)) {
            model.addAttribute("error", "验证码错误");
            return "register";
        }

        try {
            userService.register(username, password);
            return "redirect:/auth/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
