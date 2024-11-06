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

import service.WishItemService;
import service.impl.WishItemServiceImpl;
import vo.WishItem;

@WebServlet("/myPost/deleteWishItem")
public class DeleteWishItemController extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private final static String CONTENT_TYPE = "application/json; charset=UTF-8";
    
    private WishItemService wishItemService;
    
    @Override
    public void init() throws ServletException {
        try {
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
            WishItem wishItem = gson.fromJson(req.getReader(), WishItem.class);
            
            System.out.println("intput:" + wishItem.toString());
            String errMsg = wishItemService.deleteWishItem(wishItem);
            
            JsonObject resBody = new JsonObject();
            resBody.addProperty("result", errMsg == null);
            resBody.addProperty("errMsg", errMsg);
            
            resp.getWriter().write(resBody.toString());
            System.out.println("output: " + resBody.toString());
            
        } catch (Exception e) {
            e.printStackTrace();
            JsonObject respBody = new JsonObject();
            respBody.addProperty("error", "處理請求時發生錯誤: " + e.getMessage());
            resp.getWriter().write(respBody.toString());
        }
    }
}

