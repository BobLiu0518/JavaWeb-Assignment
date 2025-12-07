package tech.bobliu.assignment06.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;

@WebServlet(name = "imageServlet", value = "/image")
public class ImageServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String hash = request.getParameter("hash");
        String mime = request.getParameter("mime");

        String appPath = request.getServletContext().getRealPath("");
        String filePath = appPath + File.separator + "uploads" + File.separator + hash;
        File imageFile = new File(filePath);

        OutputStream os = response.getOutputStream();
        if (!mime.startsWith("image/") || !hash.matches("^[a-fA-F0-9]{32}$") || !imageFile.exists()) {
            response.setContentType("image/jpeg");
            try (InputStream is = getServletContext().getResourceAsStream("/WEB-INF/no-image.jpg")) {
                is.transferTo(os);
            }
        } else {
            response.setContentType(mime);
            try (InputStream is = new FileInputStream(imageFile)) {
                is.transferTo(os);
            }
        }
    }
}
