package controller;

import service.UserService;
import service.impl.UserServiceImpl;
import vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private UserService userService;

    public void init() {
        try {
            userService = new UserServiceImpl();
        } catch (Exception e) {
            throw new RuntimeException("Initialization failed", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle login
//        String identifier = request.getParameter("identifier"); // username or email
    	String email = request.getParameter("email");
    	String username = request.getParameter("username");
    	String identifier = (email != null) ? email : username;
    	String password = request.getParameter("password");

        try {
            User user = userService.login(identifier, password);


            // Store user in session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            response.getWriter().write("Login successful.");
        } catch (Exception e) {
            response.getWriter().write("Login failed: " + e.getMessage());
        }
    }

}