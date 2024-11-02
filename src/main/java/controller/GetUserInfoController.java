package controller;

import vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/userInfo")
public class GetUserInfoController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve user info from session
        HttpSession session = request.getSession(false); // Do not create a new session
        if (session == null) {
            response.getWriter().write("User not logged in.");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.getWriter().write("User not logged in.");
            return;
        }

        // Return user info (you might want to convert user object to JSON)
        // For simplicity, we just return username and email
        response.getWriter().write("User Info:\n");
        response.getWriter().write("Username: " + user.getUsername() + "\n");
        response.getWriter().write("Email: " + user.getEmail());
    }

}