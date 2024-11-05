package controller;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import service.ProductService;
import service.WishItemService;
import service.impl.ProductServiceImpl;
import vo.Product;

@WebServlet("/myPost/updateProduct")
public class UpdateProductController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private final static String CONTENT_TYPE = "application/json; charset=UTF-8";
	
	private ProductService productService;
	
	@Override
	public void init() throws ServletException {
		try {
			productService = new ProductServiceImpl();
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
	        Product product = gson.fromJson(req.getReader(), Product.class);
	        
	        String errMsg = productService.updateProduct(product);
	        
	        JsonObject resBody = new JsonObject();
	        resBody.addProperty("result", errMsg == null);
	        resBody.addProperty("errMsg", errMsg);
	        
	        resp.getWriter().write(resBody.toString());
	        
	    } catch (Exception e) {
	        e.printStackTrace(); 
	        JsonObject respBody = new JsonObject();
	        respBody.addProperty("error", "處理請求時發生錯誤: " + e.getMessage());
	        resp.getWriter().write(respBody.toString());
	    }
	}

}
