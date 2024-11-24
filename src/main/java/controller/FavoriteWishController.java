package controller;

import com.google.gson.Gson;
import service.FavoriteWishService;
import service.impl.FavoriteWishServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/wish/favorite/*")
public class FavoriteWishController extends HttpServlet {
    private FavoriteWishService favoriteWishService;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        try {
            favoriteWishService = new FavoriteWishServiceImpl();
            gson = new Gson();
        } catch (Exception e) {
            throw new ServletException("Error initializing FavoriteWishController", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                String userIdParam = req.getParameter("userId");
                String wishItemIdParam = req.getParameter("wishItemId");

                if (userIdParam == null || userIdParam.isEmpty() || wishItemIdParam == null || wishItemIdParam.isEmpty()) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required parameters");
                    return;
                }

                Integer userId = Integer.parseInt(userIdParam);
                Integer wishItemId = Integer.parseInt(wishItemIdParam);

                boolean isFavorited = favoriteWishService.isWishItemFavorited(userId, wishItemId);
                out.print(gson.toJson(new FavoriteResponse(isFavorited, "Success")));
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid endpoint");
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                String userIdParam = req.getParameter("userId");
                String wishItemIdParam = req.getParameter("wishItemId");

                if (userIdParam == null || userIdParam.isEmpty() || wishItemIdParam == null || wishItemIdParam.isEmpty()) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required parameters");
                    return;
                }

                Integer userId = Integer.parseInt(userIdParam);
                Integer wishItemId = Integer.parseInt(wishItemIdParam);

                // 檢查是否已經收藏
                if (favoriteWishService.isWishItemFavorited(userId, wishItemId)) {
                    out.print(gson.toJson(new FavoriteResponse(false, "Item already in favorites")));
                    return;
                }

                // 新增收藏
                boolean isAdded = favoriteWishService.toggleFavorite(userId, wishItemId);
                out.print(gson.toJson(new FavoriteResponse(isAdded, "Successfully added to favorites")));
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid endpoint");
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                String userIdParam = req.getParameter("userId");
                String wishItemIdParam = req.getParameter("wishItemId");

                if (userIdParam == null || userIdParam.isEmpty() || wishItemIdParam == null || wishItemIdParam.isEmpty()) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required parameters");
                    return;
                }

                Integer userId = Integer.parseInt(userIdParam);
                Integer wishItemId = Integer.parseInt(wishItemIdParam);

                // 檢查是否已經收藏
                if (!favoriteWishService.isWishItemFavorited(userId, wishItemId)) {
                    out.print(gson.toJson(new FavoriteResponse(false, "Item not in favorites")));
                    return;
                }

                // 移除收藏
                boolean isRemoved = !favoriteWishService.toggleFavorite(userId, wishItemId);
                out.print(gson.toJson(new FavoriteResponse(isRemoved, "Successfully removed from favorites")));
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid endpoint");
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // Response class for favorite operations
    private static class FavoriteResponse {
        private final boolean success;
        private final String message;

        public FavoriteResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    }
}