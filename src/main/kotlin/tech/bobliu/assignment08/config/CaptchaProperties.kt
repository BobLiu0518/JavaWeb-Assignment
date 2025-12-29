package tech.bobliu.assignment08.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import tech.bobliu.assignment08.service.CaptchaService

@Configuration
@ConfigurationProperties(prefix = "app.captcha")
class CaptchaProperties {
    var list: List<CaptchaItem> = mutableListOf()

    class CaptchaItem {
        var filename: String = ""
        var result: String = ""
    }
}
