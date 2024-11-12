package controller;

import com.google.gson.Gson;
import service.WishListService;
import service.impl.WishListServiceImpl;
import vo.WishItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/wishlist/*")
public class WishListController extends HttpServlet {
    private WishListService wishListService;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        wishListService = new WishListServiceImpl();
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

            switch (paths[1]) {
                case "items":
                    Integer userIdGiven = Integer.parseInt(req.getParameter("userId"));
                    List<WishItem> wishItems = wishListService.getAllWishItems(userIdGiven);
                    out.print(gson.toJson(wishItems));
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
