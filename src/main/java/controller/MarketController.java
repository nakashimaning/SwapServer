package controller;

import com.google.gson.Gson;
import service.MarketProductService;
import service.impl.MarketProductServiceImpl;
import vo.MarketProduct;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/market/*")
public class MarketController extends HttpServlet {
    private MarketProductService marketProductService;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        marketProductService = new MarketProductServiceImpl();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String[] paths = pathInfo.split("/");
            if (paths.length < 2) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            if ("product".equals(paths[1])) {
                // 新增: 獲取指定會員的所有發布商品
                if (paths.length > 2 && "userProducts".equals(paths[2])) {
                    Integer userId = Integer.parseInt(req.getParameter("userId"));
                    List<MarketProduct> products = marketProductService.getUserProducts(userId);
                    out.print(gson.toJson(products));
                } else if (paths.length > 2 && "detail".equals(paths[2])) {
                    Integer productId = Integer.parseInt(req.getParameter("productId"));
                    Integer userId = req.getParameter("userId") != null ? Integer.parseInt(req.getParameter("userId")) : null;

                    MarketProduct product = marketProductService.getProductById(productId, userId);
                    out.print(gson.toJson(product));
                } else if (paths.length == 2) { 
                    Integer userIdGiven = Integer.parseInt(req.getParameter("userId"));
                    List<MarketProduct> products = marketProductService.getAllProduct(userIdGiven);
                    out.print(gson.toJson(products));
                } else if (paths.length > 2 && "applicationStatus".equals(paths[2])) {
                    Integer productId = Integer.parseInt(req.getParameter("productId"));
                    Integer userId = Integer.parseInt(req.getParameter("userId"));

                    boolean hasApplied = marketProductService.hasAppliedForExchange(userId, productId);
                    out.print("{\"hasApplied\":" + hasApplied + "}");
                } else {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
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

        if (pathInfo != null && pathInfo.equals("/product/favorite")) {
            try {
                Integer userId = Integer.parseInt(req.getParameter("userId"));
                Integer productId = Integer.parseInt(req.getParameter("productId"));

                boolean isAdded = marketProductService.addFavorite(userId, productId);
                resp.getWriter().write("{\"success\":" + isAdded + "}");
            } catch (Exception e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request parameters");
            }
        } else if (pathInfo != null && pathInfo.equals("/product/applyForExchange")) {
            try {
                Integer userId = Integer.parseInt(req.getParameter("userId"));
                Integer toBeTradedProductId = Integer.parseInt(req.getParameter("productId"));
                Integer applyingProductId = Integer.parseInt(req.getParameter("applyingProductId"));

                boolean hasApplied = marketProductService.hasAppliedForExchange(userId, toBeTradedProductId);
                if (hasApplied) {
                    resp.getWriter().write("{\"success\": false, \"message\": \"已經提出申請，無法再次申請。\"}");
                    return;
                }

                boolean isApplied = marketProductService.applyForExchange(userId, toBeTradedProductId, applyingProductId);
                resp.getWriter().write("{\"success\":" + isApplied + "}");
            } catch (Exception e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request parameters");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid endpoint");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (pathInfo != null && pathInfo.equals("/product/favorite")) {
            try {
                Integer userId = Integer.parseInt(req.getParameter("userId"));
                Integer productId = Integer.parseInt(req.getParameter("productId"));

                boolean isRemoved = marketProductService.removeFavorite(userId, productId);
                resp.getWriter().write("{\"success\":" + isRemoved + "}");
            } catch (Exception e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request parameters");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid endpoint");
        }
    }
}
