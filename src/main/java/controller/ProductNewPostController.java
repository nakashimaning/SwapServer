package controller;

import service.ProductNewPostService;
import service.impl.ProductNewPostServiceImpl;
import dao.impl.ProductNewPostDaoImpl;
import vo.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

@WebServlet("/api/product/new")
public class ProductNewPostController extends HttpServlet {
    private ProductNewPostService productNewPostService;
    private Gson gson;

    public ProductNewPostController() {
    }

    @Override
    public void init() throws ServletException {
        super.init();
        this.productNewPostService = new ProductNewPostServiceImpl(new ProductNewPostDaoImpl());
        this.gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (BufferedReader reader = request.getReader()) {
            Product product = gson.fromJson(reader, Product.class);
            
            if (product == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\":\"Product data is missing\"}");
                return;
            }
            
            // 檢查 userId 和 categoryId 是否為 null
            if (product.getUserId() == null || product.getCategoryId() == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\":\"User ID and Category ID are required\"}");
                return;
            }

            System.out.println("Received product: " + product);

            // 使用 service 層新增 product
            productNewPostService.addProduct(product);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"message\":\"Product added successfully\"}");
            
        } catch (JsonSyntaxException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\":\"Invalid JSON format\"}");
            e.printStackTrace();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\":\"Failed to add product\"}");
            e.printStackTrace();
        }
    }
}