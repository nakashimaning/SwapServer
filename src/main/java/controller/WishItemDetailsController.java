package controller;

import service.FavoriteWishService;
import service.RegisterService;
import service.WishItemdetailService;
import service.impl.FavoriteWishServiceImpl;
import service.impl.RegisterServiceImpl;
import service.impl.WishitemdetailServiceImpl;
import vo.WishItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.gson.Gson;

import java.io.IOException;

@WebServlet("/wishitemDetails")
public class WishItemDetailsController extends HttpServlet {

    private WishItemdetailService wishItemService;
    private FavoriteWishService favoriteWishService;
    private RegisterService registerService;
    private Gson gson;

    public void init() {
        try {
            wishItemService = new WishitemdetailServiceImpl();
            favoriteWishService = new FavoriteWishServiceImpl();
            registerService = new RegisterServiceImpl();
            gson = new Gson();
        } catch (Exception e) {
            throw new RuntimeException("Initialization failed", e);
        }
    }

    
    

    /**
     * Handle favorite/unfavorite a wish item.
     * Endpoint: POST /wishitemDetails/favorite
     * Parameters: userId, wishItemId
     */
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
            boolean isFavorited = favoriteWishService.toggleFavorite(userId, wishItemId);

            // Send response indicating the new favorite status
            String jsonResponse = gson.toJson(isFavorited);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);

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
            boolean isRegistered = false;
			try {
				isRegistered = registerService.toggleRegister(userId, wishItemId);
			} catch (Exception e) {
				e.printStackTrace();
			}

            // Send response indicating the new registration status
            String jsonResponse = gson.toJson(isRegistered);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error updating registration status: " + e.getMessage());
        }
    }
    
    
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//    	回傳如下JSON:
//    	{
//    		  "wishItemId": 1,
//    		  "wishUserId": 2,
//    		  "wishCategoryId": 3,
//    		  "wishCreatedDate": "2023-10-31T10:20:30Z",
//    		  "wishDeleteDate": null,
//    		  "wishItemTitle": "Sample Wish Item",
//    		  "wishStatus": 0,
//    		  "wishDescription": "This is a sample wish item description.",
//    		  "wishImageList": [
//    		    "http://example.com/image1.jpg",
//    		    "http://example.com/image2.jpg"
//    		  ],
//    		  "registerList": [
//    		    {
//    		      "user_id": 3,
//    		      "username": "registrant1",
//    		      "email": "registrant1@example.com",
//    		      "password": "hashed_password",
//    		      "profile_pic": "http://example.com/registrant1_pic.jpg",
//    		      "asseek_totalstarcount": 10,
//    		      "asprovider_totalstarcount": 8,
//    		      "asseek_totalreviewcount": 5,
//    		      "asprovider_totalreviewcount": 3,
//    		      "FCM_token": "registrant1_fcm_token"
//    		    },
//    		    {
//    		      "user_id": 4,
//    		      "username": "registrant2",
//    		      "email": "registrant2@example.com",
//    		      "password": "hashed_password",
//    		      "profile_pic": "http://example.com/registrant2_pic.jpg",
//    		      "asseek_totalstarcount": 12,
//    		      "asprovider_totalstarcount": 15,
//    		      "asseek_totalreviewcount": 7,
//    		      "asprovider_totalreviewcount": 9,
//    		      "FCM_token": "registrant2_fcm_token"
//    		    }
//    		    // ... other registrants
//    		  ],
//    		  "publisher": {
//    		    "user_id": 2,
//    		    "username": "publisher_username",
//    		    "email": "publisher@example.com",
//    		    "password": "hashed_password",
//    		    "profile_pic": "http://example.com/profile_pic.jpg",
//    		    "asseek_totalstarcount": 20,
//    		    "asprovider_totalstarcount": 18,
//    		    "asseek_totalreviewcount": 15,
//    		    "asprovider_totalreviewcount": 13,
//    		    "FCM_token": "publisher_fcm_token"
//    		  }
//    		}
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
            WishItem wishItem = wishItemService.getWishItemDetails(wishItemId);

            // Convert wishItem to JSON and send response
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