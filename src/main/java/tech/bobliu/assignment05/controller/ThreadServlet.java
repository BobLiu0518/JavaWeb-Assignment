package tech.bobliu.assignment05.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tech.bobliu.assignment05.model.Thread;
import tech.bobliu.assignment05.model.Threads;
import tech.bobliu.assignment05.model.User;

import java.io.IOException;

@WebServlet(name = "threadServlet", value = "/threads/*", loadOnStartup = 1)
public class ThreadServlet extends HttpServlet {
    public void init() {
        ServletContext servletContext = getServletContext();
        servletContext.setAttribute("threads", new Threads());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        ServletContext servletContext = getServletContext();
        Threads threads = (Threads) servletContext.getAttribute("threads");

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // GET /threads/ 列出所有帖子
                RequestDispatcher rd = servletContext.getRequestDispatcher("/threads.jsp");
                rd.forward(request, response);
            } else {
                // GET /threads/{id} 查看指定帖子
                int threadId = Integer.parseInt(pathInfo.substring(1));
                Thread thread = threads.getThread(threadId);

                request.setAttribute("thread", thread);
                RequestDispatcher rd = servletContext.getRequestDispatcher("/thread.jsp");
                rd.forward(request, response);
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("message", e.getMessage());
            RequestDispatcher rd = servletContext.getRequestDispatcher("/error.jsp");
            rd.forward(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        HttpSession session = request.getSession();
        ServletContext servletContext = getServletContext();
        Threads threads = (Threads) servletContext.getAttribute("threads");
        Thread thread = null;

        try {
            User sender = (User) session.getAttribute("user");
            if (sender == null) {
                throw new IllegalArgumentException("请先登录");
            }

            if (pathInfo == null || pathInfo.equals("/")) {
                // POST /threads/ 创建新帖子
                String title = request.getParameter("title");
                String content = request.getParameter("content");

                if (title == null || content == null) {
                    throw new IllegalArgumentException("标题和内容不能为空");
                }

                thread = threads.addThread(sender, title, content);
            } else {
                // POST /threads/{id} 回复指定帖子
                int threadId = Integer.parseInt(pathInfo.substring(1));
                thread = threads.getThread(threadId);

                String content = request.getParameter("content");
                int replyTo = request.getParameter("replyTo") != null ? Integer.parseInt(request.getParameter("replyTo")) : -1;
                if (content == null) {
                    throw new IllegalArgumentException("内容不能为空");
                }

                threads.addPostToThread(threadId, sender, content, replyTo);
            }

            response.sendRedirect(request.getContextPath() + "/threads/" + thread.getThreadId());
        } catch (IllegalArgumentException e) {
            request.setAttribute("message", e.getMessage());
            RequestDispatcher rd = servletContext.getRequestDispatcher("/error.jsp");
            rd.forward(request, response);
        }
    }
}
