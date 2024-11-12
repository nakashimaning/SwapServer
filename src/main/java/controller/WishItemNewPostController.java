package controller;

import service.WishItemNewPostService;
import service.impl.WishItemNewPostServiceImpl;
import dao.impl.WishItemNewPostDaoImpl;
import vo.WishItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

@WebServlet("/api/wishitem/add")
public class WishItemNewPostController extends HttpServlet {
    private WishItemNewPostService wishItemNewPostService;
    private Gson gson;

    public WishItemNewPostController() {
    }

    @Override
    public void init() throws ServletException {
        super.init();
        this.wishItemNewPostService = new WishItemNewPostServiceImpl(new WishItemNewPostDaoImpl());
        this.gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (BufferedReader reader = request.getReader()) {
            WishItem wishItem = gson.fromJson(reader, WishItem.class);
            
            if (wishItem == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\":\"Wish item data is missing\"}");
                return;
            }

            wishItemNewPostService.addWishItem(wishItem);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"message\":\"Wish item added successfully\"}");
            
        } catch (JsonSyntaxException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\":\"Invalid JSON format\"}");
            e.printStackTrace();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\":\"Failed to add wish item\"}");
            e.printStackTrace();
        }
    }
}
