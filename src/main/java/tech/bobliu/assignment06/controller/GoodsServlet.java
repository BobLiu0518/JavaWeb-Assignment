package tech.bobliu.assignment06.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import tech.bobliu.assignment06.model.Goods;
import tech.bobliu.assignment06.model.User;
import tech.bobliu.assignment06.service.GoodsService;
import tech.bobliu.assignment06.service.ImageService;
import tech.bobliu.assignment06.service.UserService;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HexFormat;

@WebServlet(name = "goodsListServlet", value = "/goods/*", loadOnStartup = 1)
@MultipartConfig(
        maxFileSize = 1024 * 1024 * 8,
        maxRequestSize = 1024 * 1024 * 12
)
public class GoodsServlet extends HttpServlet {
    UserService userService = new UserService();
    GoodsService goodsService = new GoodsService();
    ImageService imageService = new ImageService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        int id = 0;
        try {
            id = Integer.parseInt(pathInfo.replaceAll("^.*?/(\\d+).*$", "$1"));
        } catch (NumberFormatException ignored) {
        }
        String action = request.getParameter("action") != null ? request.getParameter("action") : "view";

        if (id == 0 && !action.equals("publish")) {
            // GET /goods/ 列出所有商品
            String keyword = request.getParameter("keyword") != null ? request.getParameter("keyword") : "";
            int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

            ArrayList<Goods> goodsList = goodsService.queryGoodsList(keyword, page);
            int goodsCount = goodsService.getGoodsCount(keyword);
            int pageCount = (int) Math.ceil((double) goodsCount / 10);
            request.setAttribute("goodsList", goodsList);
            request.setAttribute("goodsCount", goodsCount);
            request.setAttribute("pageCount", pageCount);
            request.setAttribute("keyword", keyword);
            request.setAttribute("page", page);

            request.getServletContext().getRequestDispatcher("/goodsList.jsp").forward(request, response);
        } else {
            // GET /goods/{id} 查看指定商品
            // GET /goods/{id}?action=edit 编辑指定商品
            // GET /goods/?action=publish 发布新商品
            Goods goods = id == 0 ? null : goodsService.getGoodsById(id);
            User publisher = goods == null || goods.getPublisherId() == 0 ? null : userService.getUserById(goods.getPublisherId());
            request.setAttribute("goods", goods);
            request.setAttribute("publisher", publisher);

            if (action.equals("view")) {
                request.getServletContext().getRequestDispatcher("/goodsView.jsp").forward(request, response);
            } else if (action.equals("edit") || action.equals("publish")) {
                request.getServletContext().getRequestDispatcher("/goodsEdit.jsp").forward(request, response);
            } else if (action.equals("delete")) {
                User user = (User) request.getSession().getAttribute("user");
                if (user == null || goods == null || goods.getPublisherId() != user.getId()) {
                    request.setAttribute("message", "无权限删除该商品");
                    request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
                    return;
                }
                goodsService.deleteGoodsById(id);
                request.setAttribute("message", "删除成功");
                request.getServletContext().getRequestDispatcher("/success.jsp").forward(request, response);
            } else if (action.equals("sold")) {
                User user = (User) request.getSession().getAttribute("user");
                if (user == null || goods == null || goods.getPublisherId() != user.getId()) {
                    request.setAttribute("message", "无权限操作该商品");
                    request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
                    return;
                }
                goods.setSold(true);
                goodsService.modifyGoods(goods);
                request.setAttribute("message", "商品已标记为已售出");
                request.getServletContext().getRequestDispatcher("/success.jsp").forward(request, response);
            }
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("message", "无权限操作该商品");
            request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        int id = request.getParameter("id") != null ? Integer.parseInt(request.getParameter("id")) : 0;
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        BigDecimal price = new BigDecimal(request.getParameter("price"));

        if (name == null || name.isEmpty() || description == null || description.isEmpty() || price.compareTo(BigDecimal.ZERO) < 0) {
            request.setAttribute("message", "各项内容不能为空");
            request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        String imageHash = null;
        String imageMime = null;

        Part filePart = request.getPart("image");
        if (filePart != null && filePart.getSize() > 0) {
            imageMime = filePart.getContentType();
            if (imageMime != null && !imageMime.startsWith("image/")) {
                request.setAttribute("message", "上传的文件必须是图片");
                request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            try {
                imageHash = HexFormat.of().formatHex(MessageDigest.getInstance("MD5").digest(filePart.getInputStream().readAllBytes()));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

            String filePath = imageService.getImageFullPath(imageHash);
            filePart.write(filePath);
        }
        if (imageHash != null && imageMime != null) {
            imageService.saveImage(imageHash, imageMime);
        }

        // POST /goods/ 保存商品信息（新增或编辑）
        Goods goods;
        if (id == 0) {
            goods = goodsService.addGoods(name, description, price, user.getId(), imageHash);
        } else {
            goods = goodsService.getGoodsById(id);
            if (goods == null || goods.getPublisherId() != user.getId()) {
                request.setAttribute("message", "无权限操作该商品");
                request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            goods.setName(name);
            goods.setDescription(description);
            goods.setPrice(price);
            if (imageHash != null && imageMime != null) {
                goods.setImageHash(imageHash);
            }

            goods = goodsService.modifyGoods(goods);
        }

        response.sendRedirect(request.getContextPath() + "/goods/" + goods.getId());
    }
}