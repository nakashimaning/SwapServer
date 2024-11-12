package controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import service.NotificationService;
import service.impl.NotificationServiceImpl;

@WebServlet("/notification/is_read")
public class NotificationIsReadController extends HttpServlet{


	private static final long serialVersionUID = 1L;
    private final static String CONTENT_TYPE = "application/json; charset=UTF-8";
	private NotificationService notificationService;

	@Override
	public void init() {
		try {
			notificationService = new NotificationServiceImpl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        response.setContentType(CONTENT_TYPE);
        
        System.out.println("doPost");

        Gson gson = new Gson();
       

        // 獲取要更新的項目 ID 和新的 boolean 值
        int notificationId = Integer.parseInt(request.getParameter("notification_id"));
        boolean is_read = Boolean.parseBoolean(request.getParameter("is_read"));
        
        System.out.println(notificationId);
        System.out.println(is_read);


        // 更新數據庫中的 boolean 值（根據具體需求編寫）
        int isUpdated = notificationService.updateNotificationIsRead(notificationId, is_read);

        if (isUpdated>0) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"success\": true, \"message\": \"Boolean value updated successfully.\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\": false, \"message\": \"Failed to update boolean value.\"}");
        }
    }

}

