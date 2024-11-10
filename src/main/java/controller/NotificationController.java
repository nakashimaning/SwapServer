package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import service.NotificationService;
import service.impl.NotificationServiceImpl;
import vo.Notification;

@WebServlet("/notification")
public class NotificationController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private NotificationService notificationService;
	private final static String CONTENT_TYPE = "application/json; charset=UTF-8";

	@Override
	public void init() {
		try {
			notificationService = new NotificationServiceImpl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        response.setContentType(CONTENT_TYPE);
//
        Gson gson = new Gson();
//        BufferedReader br = request.getReader();
//        StringBuilder jsonIn = new StringBuilder();
//        String line;
//
//        while ((line = br.readLine()) != null) {
//            jsonIn.append(line);
//        }
//
//        JsonObject js = gson.fromJson(jsonIn.toString(), JsonObject.class);
//        int userId = js.get("user_id").getAsInt();
//        int type = js.get("type").getAsInt();
        
       int userId = Integer.parseInt(request.getParameter("user_id"));
       int type = Integer.parseInt(request.getParameter("type"));

        System.out.println(userId);
        System.out.println(type);

        
        List<Notification> notifications = new ArrayList<Notification>();
		try {
			notifications = notificationService.searchByUser(userId, type);
			System.out.println(notifications);
			response.getWriter().write(gson.toJson(notifications));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		}
       
	}
}
