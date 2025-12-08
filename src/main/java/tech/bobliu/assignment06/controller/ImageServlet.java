package tech.bobliu.assignment06.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tech.bobliu.assignment06.service.ImageService;

import java.io.*;

@WebServlet(name = "imageServlet", value = "/image/*", loadOnStartup = 1)
public class ImageServlet extends HttpServlet {
    ImageService imageService = new ImageService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String hash = request.getPathInfo().replaceAll("^/(.+)$", "$1");
        String mime = imageService.getImageMimeByHash(hash);

        String appPath = request.getServletContext().getRealPath("");
        String filePath = appPath + File.separator + "uploads" + File.separator + hash;
        File imageFile = new File(filePath);

        OutputStream os = response.getOutputStream();
        if (hash.isEmpty() || mime == null || !mime.startsWith("image/") || !hash.matches("^[a-fA-F0-9]{32}$") || !imageFile.exists()) {
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
