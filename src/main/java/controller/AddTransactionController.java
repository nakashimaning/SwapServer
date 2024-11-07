package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import service.TransactionService;
import service.impl.TransactionServiceImpl;
import vo.Transaction;

@WebServlet("/myPost/addTransaction")
public class AddTransactionController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final static String CONTENT_TYPE = "application/json; charset=UTF-8";
    private TransactionService transactionService;

    @Override
    public void init() throws ServletException {
		transactionService = new TransactionServiceImpl();
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType(CONTENT_TYPE);
        
        try {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();
            JsonObject jsonRequest = gson.fromJson(req.getReader(), JsonObject.class);
            
            Transaction transaction = new Transaction();
            transaction.setProvider_product_id(jsonRequest.get("providerProductId").getAsInt());
            transaction.setSeeker_product_id(jsonRequest.get("seekerProductId").getAsInt());
            
            String errMsg = transactionService.addTransaction(transaction);
            
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

