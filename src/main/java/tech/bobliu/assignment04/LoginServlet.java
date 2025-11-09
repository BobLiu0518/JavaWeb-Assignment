package tech.bobliu.assignment04;

import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    public void init() { }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");

        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        if(username.isEmpty()) {
            response.sendRedirect("./login.jsp");
            return;
        }

        session.setAttribute("username", username);
        response.sendRedirect("./index.jsp");
    }

    public void destroy() {
    }
}