package controller;

import com.google.gson.Gson;
import service.WishItemRegisterService;
import service.impl.WishItemRegisterServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/wish/register/*")
public class WishItemRegisterController extends HttpServlet {
    private WishItemRegisterService wishItemRegisterService;
    private Gson gson;

    // Servlet 初始化
    @Override
    public void init() throws ServletException {
        try {
            wishItemRegisterService = new WishItemRegisterServiceImpl();
            gson = new Gson();
        } catch (Exception e) {
            throw new ServletException("初始化 WishItemRegisterController 時發生錯誤", e);
        }
    }

    // 處理 GET 請求 - 檢查登記狀態
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // 獲取請求參數
                String registrantUserIdParam = req.getParameter("registrantUserId");
                String wishitemIdParam = req.getParameter("wishitemId");

                // 檢查參數是否完整
                if (registrantUserIdParam == null || registrantUserIdParam.isEmpty() || 
                    wishitemIdParam == null || wishitemIdParam.isEmpty()) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "缺少必要參數");
                    return;
                }

                // 轉換參數型別
                Integer registrantUserId = Integer.parseInt(registrantUserIdParam);
                Integer wishitemId = Integer.parseInt(wishitemIdParam);

                // 檢查登記狀態
                boolean isRegistered = wishItemRegisterService.isAlreadyRegistered(registrantUserId, wishitemId);
                out.print(gson.toJson(new RegisterResponse(isRegistered, 
                    isRegistered ? "已經登記" : "尚未登記")));
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "無效的端點");
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "數字格式錯誤");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // 處理 POST 請求 - 新增登記
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // 獲取請求參數
                String registrantUserIdParam = req.getParameter("registrantUserId");
                String wishitemIdParam = req.getParameter("wishitemId");

                // 檢查參數是否完整
                if (registrantUserIdParam == null || registrantUserIdParam.isEmpty() || 
                    wishitemIdParam == null || wishitemIdParam.isEmpty()) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "缺少必要參數");
                    return;
                }

                // 轉換參數型別
                Integer registrantUserId = Integer.parseInt(registrantUserIdParam);
                Integer wishitemId = Integer.parseInt(wishitemIdParam);

                // 檢查是否已登記
                if (wishItemRegisterService.isAlreadyRegistered(registrantUserId, wishitemId)) {
                    out.print(gson.toJson(new RegisterResponse(false, "已經登記過了")));
                    return;
                }

                // 執行登記
                boolean isRegistered = wishItemRegisterService.toggleRegister(registrantUserId, wishitemId);
                out.print(gson.toJson(new RegisterResponse(isRegistered, "登記成功")));
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "無效的端點");
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "數字格式錯誤");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // 處理 DELETE 請求 - 取消登記
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // 獲取請求參數
                String registrantUserIdParam = req.getParameter("registrantUserId");
                String wishitemIdParam = req.getParameter("wishitemId");

                // 檢查參數是否完整
                if (registrantUserIdParam == null || registrantUserIdParam.isEmpty() || 
                    wishitemIdParam == null || wishitemIdParam.isEmpty()) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "缺少必要參數");
                    return;
                }

                // 轉換參數型別
                Integer registrantUserId = Integer.parseInt(registrantUserIdParam);
                Integer wishitemId = Integer.parseInt(wishitemIdParam);

                // 檢查是否已登記
                if (!wishItemRegisterService.isAlreadyRegistered(registrantUserId, wishitemId)) {
                    out.print(gson.toJson(new RegisterResponse(false, "尚未登記")));
                    return;
                }

                // 執行取消登記
                boolean isRemoved = !wishItemRegisterService.toggleRegister(registrantUserId, wishitemId);
                out.print(gson.toJson(new RegisterResponse(isRemoved, "取消登記成功")));
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "無效的端點");
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "數字格式錯誤");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // 響應類別 - 用於封裝 API 響應
    private static class RegisterResponse {
        private final boolean success; // 操作是否成功
        private final String message;  // 響應訊息

        public RegisterResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    }
}