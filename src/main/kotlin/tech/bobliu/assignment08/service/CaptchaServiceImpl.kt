package tech.bobliu.assignment08.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import tech.bobliu.assignment08.config.CaptchaProperties
import java.io.InputStream

@Service
class CaptchaServiceImpl @Autowired constructor(
    private val captchaProperties: CaptchaProperties
) : CaptchaService {

    private val captchaList = captchaProperties.list.map { 
        CaptchaService.Captcha(it.filename, it.result) 
    }

    override fun getNextCaptcha(lastIndex: Int?): Pair<CaptchaService.Captcha, Int> {
        var captchaIndex: Int
        do {
            captchaIndex = (Math.random() * captchaList.size).toInt()
        } while (lastIndex != null && captchaIndex == lastIndex)

        return Pair(captchaList[captchaIndex], captchaIndex)
    }

    override fun getCaptchaImage(filename: String): InputStream {
        val resource = ClassPathResource("captcha/$filename")
        return resource.inputStream
    }
}
