package controller;

import service.UserService;
import service.impl.UserServiceImpl;
import vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/register")
public class RegisterController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserService userService;

	public void init() {
		try {
			userService = new UserServiceImpl();
		} catch (Exception e) {
			throw new RuntimeException("Initialization failed", e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Handle registration
//    	private final static String CONTENT_TYPE = "application/json; charset=UTF-8";

		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String profilePic = request.getParameter("profile_pic");
//        String fcmToken = request.getParameter("FCM_token");

		// Initialize counts to 0
		String fcmToken = "review_only";
		Integer asseekTotalStarCount = 0;
		Integer asproviderTotalStarCount = 0;
		Integer asseekTotalReviewCount = 0;
		Integer asproviderTotalReviewCount = 0;

		// Validate required fields
		if (username == null || email.trim().isEmpty() || email == null || email.trim().isEmpty() || password == null
				|| password.trim().isEmpty()) {
			response.getWriter().write("Missing required fields: username, email, or password.");
			return;
		}

		// Create a new User object
		User user = new User(null, username, email, password, profilePic, asseekTotalStarCount,
				asproviderTotalStarCount, asseekTotalReviewCount, asproviderTotalReviewCount, fcmToken);

		try {
			Integer status = userService.register(user);
			if (status == 0) {
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write("{\"message\": \"Registration successful.\"}");
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().write("{\"error\": \"Registration failed. User already exists.\"}");
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("{\"error\": \"Registration failed: " + e.getMessage() + "\"}");
		}
		response.getWriter().write("Registration successful.");
	}

}
