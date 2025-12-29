package tech.bobliu.assignment08.controller

import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import tech.bobliu.assignment08.service.CaptchaService
import java.io.InputStream

@Controller
class CaptchaController @Autowired constructor(private val captchaService: CaptchaService) {

    @GetMapping("/captcha")
    fun getCaptcha(session: HttpSession, response: HttpServletResponse) {
        response.contentType = "image/jpeg"
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate")
        response.setHeader("Pragma", "no-cache")
        response.setDateHeader("Expires", 0)

        val lastIndex = session.getAttribute("captchaIndex") as? Int
        val (captcha, captchaIndex) = captchaService.getNextCaptcha(lastIndex)

        session.setAttribute("captchaIndex", captchaIndex)
        session.setAttribute("captchaResult", captcha.result)

        captchaService.getCaptchaImage(captcha.filename).use { input ->
            input.transferTo(response.outputStream)
        }
    }
}
