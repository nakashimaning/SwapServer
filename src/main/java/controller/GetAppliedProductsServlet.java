package controller;

import com.google.gson.Gson;
import vo.User;
import vo.ProductListSection;
import dao.impl.ProductListSectionDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/applied-products")
public class GetAppliedProductsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Gson gson;

    public void init() {
        try {
            // Initialize Gson for JSON serialization/deserialization
            gson = new Gson();
        } catch (Exception e) {
            throw new RuntimeException("Initialization failed", e);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Check if the user is logged in via token-like authentication
        String userText = req.getHeader("user");
        User user = gson.fromJson(userText, User.class);

        if (user == null || user.getUser_id() == 0) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You must be logged in to access this resource.");
            return;
        }

        int currentUserId = user.getUser_id();

        try {
            ProductListSectionDaoImpl productDao = new ProductListSectionDaoImpl();
            List<ProductListSection> products = productDao.getProductsAppliedByUserId(currentUserId);

            // Convert the list to JSON and write to response
            String json = gson.toJson(products);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred.");
        }
    }
}