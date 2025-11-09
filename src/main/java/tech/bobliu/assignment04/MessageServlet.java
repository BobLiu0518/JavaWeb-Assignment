package tech.bobliu.assignment04;

import java.io.*;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "messageServlet", value = "/message")
public class MessageServlet extends HttpServlet {
    public void init() {
        ServletContext context = getServletContext();
        context.setAttribute("messages", new StringBuffer());
    }

    private String getUser(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        if(username == null) {
            response.setStatus(401);
        }
        return username;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");

        String username = getUser(request, response);
        if(username == null) {
            return;
        }

        ServletContext context = getServletContext();
        StringBuffer messages = (StringBuffer) context.getAttribute("messages");

        PrintWriter out = response.getWriter();
        out.print(!messages.isEmpty() ? messages.toString() : "还没有消息，快来发送消息吧");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");

        String username = getUser(request, response);
        if(username == null) {
            return;
        }

        ServletContext context = getServletContext();
        StringBuffer messages = (StringBuffer) context.getAttribute("messages");

        String message = request.getReader().readLine();
        messages.insert(0, username + "：" + message + "\n");
        context.setAttribute("messages", messages);
    }

    public void destroy() {
    }
}