package controller;

import service.WishItemdetailService;
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
    private Gson gson;

    public void init() {
        try {
            wishItemService = new WishitemdetailServiceImpl();
            gson = new Gson();
        } catch (Exception e) {
            throw new RuntimeException("Initialization failed", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String idParam = request.getParameter("id");
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