package tech.bobliu.assignment08.controller

import jakarta.servlet.http.HttpSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import tech.bobliu.assignment08.model.User
import tech.bobliu.assignment08.service.ThreadService

@Controller
@RequestMapping("/threads")
class ThreadController @Autowired constructor(private val threadService: ThreadService) {

    @GetMapping
    fun listThreads(model: Model, session: HttpSession): String {
        model.addAttribute("threads", threadService.getAllThreads())
        model.addAttribute("user", session.getAttribute("user"))
        return "threads"
    }

    @PostMapping
    fun createThread(
        @RequestParam title: String,
        @RequestParam content: String,
        session: HttpSession
    ): String {
        val user = session.getAttribute("user") as? User ?: return "redirect:/auth/login"
        threadService.createThread(title, content, user)
        return "redirect:/threads"
    }

    @GetMapping("/{id}")
    fun viewThread(@PathVariable id: Long, model: Model, session: HttpSession): String {
        val thread = threadService.getThread(id)
        if (thread != null) {
            model.addAttribute("thread", thread)
            model.addAttribute("user", session.getAttribute("user"))
            return "thread"
        }
        return "redirect:/threads"
    }
    
    @PostMapping("/{id}/reply")
    fun reply(
        @PathVariable id: Long,
        @RequestParam content: String,
        @RequestParam(required = false) replyTo: Long?,
        session: HttpSession
    ): String {
        val user = session.getAttribute("user") as? User ?: return "redirect:/auth/login"
        threadService.reply(id, content, user, replyTo)
        return "redirect:/threads/$id"
    }
}
