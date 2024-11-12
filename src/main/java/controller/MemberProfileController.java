package controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import service.UserService;
import service.impl.UserServiceImpl;
import vo.User;

@WebServlet("/memberprofile")
public class MemberProfileController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private final static String CONTENT_TYPE = "application/json; charset=UTF-8";
	
	private UserService userService;
	
	public void init() {
        try {
            userService = new UserServiceImpl();
        } catch (Exception e) {
            throw new RuntimeException("Initialization failed", e);
        }
    }
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 獲取使用者資料 (這裡可以從資料庫獲取)
        String email = request.getParameter("email");
        try {
			User user = userService.getUserprofile(email);
			
			HttpSession session = request.getSession();
            session.setAttribute("user", user);
            
		} catch (Exception e) {
			response.getWriter().write("GetEmail failed: " + e.getMessage());
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 獲取使用者名稱和頭像圖片
		String email = request.getParameter("email");
        String username = request.getParameter("username");
        String profile_pic = request.getParameter("profile_pic");

        try {
            // 儲存頭像圖片
        	User user = userService.updateUserProfile(email, username, profile_pic);

            // 將用戶資訊儲存到會話中
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // 返回註冊成功的響應
            response.getWriter().write("Registration successful!");
        } catch (Exception e) {
            // 處理註冊失敗的情況
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Registration failed: " + e.getMessage());
        }
    }

}

