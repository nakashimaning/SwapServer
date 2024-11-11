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

        System.out.println("Path Info: " + pathInfo);

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path or endpoint");
                return;
            }

            String[] paths = pathInfo.split("/");
            System.out.println("Paths length: " + paths.length);

            if (paths.length < 2) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Insufficient path segments");
                return;
            }

            if ("product".equals(paths[1])) {
                // 獲取市集商品，排除指定 userId
                if (paths.length == 2) {
                    String userIdParam = req.getParameter("userId");
                    if (userIdParam == null || userIdParam.isEmpty()) {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or empty userId parameter");
                        return;
                    }

                    Integer userId = Integer.parseInt(userIdParam);
                    List<MarketProduct> products = marketProductService.getAllProducts(userId); // 修改為 getAllProducts
                    out.print(gson.toJson(products));
                }
                // 查詢特定用戶的產品
                else if (paths.length > 2 && "userProducts".equals(paths[2])) {
                    String userIdParam = req.getParameter("userId");
                    if (userIdParam == null || userIdParam.isEmpty()) {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or empty userId parameter");
                        return;
                    }

                    Integer userId = Integer.parseInt(userIdParam);
                    List<MarketProduct> products = marketProductService.getUserProducts(userId);
                    out.print(gson.toJson(products));
                }
                // 查詢申請狀態
                else if (paths.length > 2 && "applicationStatus".equals(paths[2])) {
                    String productIdParam = req.getParameter("productId");
                    String userIdParam = req.getParameter("userId");
                    if (productIdParam == null || productIdParam.isEmpty() || userIdParam == null || userIdParam.isEmpty()) {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or empty parameters");
                        return;
                    }

                    Integer productId = Integer.parseInt(productIdParam);
                    Integer userId = Integer.parseInt(userIdParam);
                    boolean hasApplied = marketProductService.hasAppliedForExchange(userId, productId);
                    out.print("{\"hasApplied\":" + hasApplied + "}");
                }
                // 查詢產品詳細信息
                else if (paths.length > 2 && "detail".equals(paths[2])) {
                    String productIdParam = req.getParameter("productId");
                    if (productIdParam == null || productIdParam.isEmpty()) {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or empty productId parameter");
                        return;
                    }

                    Integer productId = Integer.parseInt(productIdParam);
                    Integer currentUserId = req.getParameter("currentUserId") != null ? Integer.parseInt(req.getParameter("currentUserId")) : null;

                    MarketProduct product = marketProductService.getProductById(productId, currentUserId);
                    out.print(gson.toJson(product));
                } else {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product sub-path");
                }
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid endpoint");
            }
        } catch (NumberFormatException e) {
            System.out.println("Number format exception: " + e.getMessage());
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format");
        } catch (Exception e) {
            System.out.println("General exception: " + e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        System.out.println("doPost: "+pathInfo);

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
            System.out.println("applyForExchange");

            try {
                Integer userId = Integer.parseInt(req.getParameter("userId"));
                Integer toBeTradedProductId = Integer.parseInt(req.getParameter("productId"));
                Integer applyingProductId = Integer.parseInt(req.getParameter("applyingProductId"));

                System.out.println("userId:"+userId);
                System.out.println("toBeTradedProductId:"+toBeTradedProductId);
                System.out.println("applyingProductId:"+applyingProductId);

                
                boolean hasApplied = marketProductService.hasAppliedForExchange(userId, toBeTradedProductId);
                if (hasApplied) {
                    resp.getWriter().write("{\"success\": false, \"message\": \"已經提出申請，無法再次申請。\"}");
                    return;
                }

                boolean isApplied = marketProductService.applyForExchange(userId, toBeTradedProductId, applyingProductId);
                resp.getWriter().write("{\"success\":" + isApplied + "}");
            } catch (Exception e) {
            	System.out.println("faild because: "+e.getMessage());
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
