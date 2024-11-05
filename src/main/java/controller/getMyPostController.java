package controller;

import java.io.IOException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import dao.impl.ProductDaoImpl;
import dao.impl.WishItemDaoImpl;
import service.impl.ProductServiceImpl;
import service.impl.WishItemServiceImpl;
import vo.MyPost;
import vo.Product;
import vo.WishItem;

@WebServlet("/myPost/getMyPost")
public class GetMyPostController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private final static String CONTENT_TYPE = "application/json; charset=UTF-8";

	private ProductServiceImpl productService;
	private WishItemServiceImpl wishItemService;
	
	@Override
	public void init() throws ServletException {
		try {
			productService = new ProductServiceImpl();
			wishItemService = new WishItemServiceImpl();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType(CONTENT_TYPE);
		
	    try {
	    	Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();
            JsonObject reqBody = gson.fromJson(req.getReader(), JsonObject.class);
            
            if (!reqBody.has("userId")) {
                JsonObject respBody = new JsonObject();
                respBody.addProperty("error", "未提供 userId");
                resp.getWriter().write(respBody.toString());
                return;
            }
            
            int userId = reqBody.get("userId").getAsInt();
            
            List<Product> myProduct = productService.getMyProducts(userId);
            List<WishItem> myWishItem = wishItemService.getMyWishItems(userId);
            MyPost myPost = new MyPost(myProduct, myWishItem);
            
            String jsonResponse = gson.toJson(myPost);
            resp.getWriter().write(jsonResponse);
            
        } catch (Exception e) {
        	e.printStackTrace(); 
            JsonObject respBody = new JsonObject();
            respBody.addProperty("error", "處理請求時發生錯誤: " + e.getMessage());
            resp.getWriter().write(respBody.toString());
        }
	}
}
