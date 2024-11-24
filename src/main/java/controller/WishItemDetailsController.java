package controller;

import service.WishItemdetailService;
import service.impl.WishitemdetailServiceImpl;
import vo.WishItemDetail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.google.gson.Gson;

import java.io.IOException;

@WebServlet("/api/wishItemDetails")
public class WishItemDetailsController extends HttpServlet {

    private WishItemdetailService wishItemService;
    private Gson gson;

    public void init() {
        try {
            wishItemService = new WishitemdetailServiceImpl();
            gson = new Gson();
        } catch (Exception e) {
            throw new RuntimeException("Initialization failed", e);
        }
    }
    

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("favorite".equalsIgnoreCase(action)) {
            handleFavorite(request, response);
        } else if ("register".equalsIgnoreCase(action)) {
            handleRegister(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid action parameter");
        }
    }

    private void handleFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userIdParam = request.getParameter("userId");
        String wishItemIdParam = request.getParameter("wishItemId");

        if (userIdParam == null || wishItemIdParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing userId or wishItemId parameter");
            return;
        }

        Integer userId, wishItemId;
        try {
            userId = Integer.parseInt(userIdParam);
            wishItemId = Integer.parseInt(wishItemIdParam);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid userId or wishItemId parameter");
            return;
        }

        try {
            boolean isFavorited = wishItemService.isWishItemFavoritedByUser(userId, wishItemId);

            if (isFavorited) {
                response.getWriter().write("Already favorited");
            } else {
                wishItemService.addFavorite(userId, wishItemId);
                response.getWriter().write("Favorite added successfully");
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error updating favorite status: " + e.getMessage());
        }
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userIdParam = request.getParameter("userId");
        String wishItemIdParam = request.getParameter("wishItemId");

        if (userIdParam == null || wishItemIdParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing userId or wishItemId parameter");
            return;
        }

        Integer userId, wishItemId;
        try {
            userId = Integer.parseInt(userIdParam);
            wishItemId = Integer.parseInt(wishItemIdParam);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid userId or wishItemId parameter");
            return;
        }

        try {
            boolean isRegistered = wishItemService.isUserRegisteredForWishItem(userId, wishItemId);

            if (isRegistered) {
                response.getWriter().write("Already registered");
            } else {
                wishItemService.addRegistration(userId, wishItemId);
                response.getWriter().write("Registration added successfully");
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error updating registration status: " + e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("wishItemId");
        if (idParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing wishitem id parameter");
            return;
        }

        Integer wishItemId;
        try {
            wishItemId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid wishitem id parameter");
            return;
        }

        try {
            WishItemDetail wishItem = wishItemService.getWishItemDetails(wishItemId);

            String jsonResponse = gson.toJson(wishItem);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error fetching wish item details: " + e.getMessage());
        }
    }
}
