package tech.bobliu.assignment08.service

import java.io.InputStream

interface CaptchaService {
    data class Captcha(val filename: String, val result: String)
    
    fun getNextCaptcha(lastIndex: Int?): Pair<Captcha, Int>
    fun getCaptchaImage(filename: String): InputStream
}
