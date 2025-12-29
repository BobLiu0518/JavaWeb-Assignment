package tech.bobliu.assignment08.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tech.bobliu.assignment08.model.Thread;
import tech.bobliu.assignment08.model.User;
import tech.bobliu.assignment08.service.ThreadService;

@Controller
@RequestMapping("/threads")
public class ThreadController {

    private final ThreadService threadService;

    @Autowired
    public ThreadController(ThreadService threadService) {
        this.threadService = threadService;
    }

    @GetMapping
    public String listThreads(Model model, HttpSession session) {
        model.addAttribute("threads", threadService.getAllThreads());
        model.addAttribute("user", session.getAttribute("user"));
        return "threads";
    }

    @PostMapping
    public String createThread(
            @RequestParam String title,
            @RequestParam String content,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/auth/login";
        }
        threadService.createThread(title, content, user);
        return "redirect:/threads";
    }

    @GetMapping("/{id}")
    public String viewThread(@PathVariable Long id, Model model, HttpSession session) {
        Thread thread = threadService.getThread(id);
        if (thread != null) {
            model.addAttribute("thread", thread);
            model.addAttribute("user", session.getAttribute("user"));
            return "thread";
        }
        return "redirect:/threads";
    }

    @PostMapping("/{id}/reply")
    public String reply(
            @PathVariable Long id,
            @RequestParam String content,
            @RequestParam(required = false) Long replyTo,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/auth/login";
        }
        threadService.reply(id, content, user, replyTo);
        return "redirect:/threads/" + id;
    }
}
