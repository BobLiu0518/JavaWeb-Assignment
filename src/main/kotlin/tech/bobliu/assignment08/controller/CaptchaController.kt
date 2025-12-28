package tech.bobliu.assignment08.controller

import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpSession
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import java.io.InputStream

@Controller
class CaptchaController {

    data class Captcha(val filename: String, val result: String)

    private val captchaList = listOf(
        Captcha("captcha1.jpg", "1"),
        Captcha("captcha2.jpg", "0"),
        Captcha("captcha3.jpg", "2x"),
        Captcha("captcha4.jpg", "1"),
        Captcha("captcha5.jpg", "-1"),
        Captcha("captcha6.jpg", "4"),
        Captcha("captcha7.jpg", "2"),
        Captcha("captcha8.jpg", "48"),
        Captcha("captcha9.jpg", "0"),
        Captcha("captcha10.jpg", "4")
    )

    @GetMapping("/captcha")
    fun getCaptcha(session: HttpSession, response: HttpServletResponse) {
        response.contentType = "image/jpeg"
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate")
        response.setHeader("Pragma", "no-cache")
        response.setDateHeader("Expires", 0)

        var captchaIndex: Int
        val lastIndex = session.getAttribute("captchaIndex") as? Int
        do {
            captchaIndex = (Math.random() * captchaList.size).toInt()
        } while (lastIndex != null && captchaIndex == lastIndex)

        val captcha = captchaList[captchaIndex]
        session.setAttribute("captchaIndex", captchaIndex)
        session.setAttribute("captchaResult", captcha.result)

        val resource = ClassPathResource("captcha/${captcha.filename}")
        resource.inputStream.use { input ->
            input.transferTo(response.outputStream)
        }
    }
}
