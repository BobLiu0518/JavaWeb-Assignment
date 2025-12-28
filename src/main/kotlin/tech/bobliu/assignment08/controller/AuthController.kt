package tech.bobliu.assignment08.controller

import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import tech.bobliu.assignment08.service.UserService

@Controller
@RequestMapping("/auth")
class AuthController(private val userService: UserService) {

    @GetMapping("/login")
    fun loginPage(): String {
        return "login"
    }

    @PostMapping("/login")
    fun login(
        @RequestParam username: String,
        @RequestParam password: String,
        @RequestParam captcha: String,
        session: HttpSession,
        model: Model
    ): String {
        val captchaResult = session.getAttribute("captchaResult") as? String
        if (captchaResult == null || captchaResult != captcha) {
            model.addAttribute("error", "验证码错误")
            return "login"
        }

        val user = userService.login(username, password)
        if (user != null) {
            session.setAttribute("user", user)
            return "redirect:/"
        } else {
            model.addAttribute("error", "用户名或密码错误")
            return "login"
        }
    }

    @GetMapping("/register")
    fun registerPage(): String {
        return "register"
    }

    @PostMapping("/register")
    fun register(
        @RequestParam username: String,
        @RequestParam password: String,
        @RequestParam captcha: String,
        session: HttpSession,
        model: Model
    ): String {
        val captchaResult = session.getAttribute("captchaResult") as? String
        if (captchaResult == null || captchaResult != captcha) {
            model.addAttribute("error", "验证码错误")
            return "register"
        }

        try {
            userService.register(username, password)
            return "redirect:/login"
        } catch (e: IllegalArgumentException) {
            model.addAttribute("error", e.message)
            return "register"
        }
    }

    @GetMapping("/logout")
    fun logout(session: HttpSession): String {
        session.invalidate()
        return "redirect:/"
    }
}
