// MarketController.java
package controller;

import com.google.gson.Gson;
import dao.MarketProductDao;
import dao.impl.MarketProductDaoImpl;
import service.MarketProductService;
import service.impl.MarketProductServiceImpl;
import vo.MarketProduct;
import vo.Product;
import vo.Transaction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/market/*")
public class MarketController extends HttpServlet {
    private MarketProductService marketProductService;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        marketProductService = new MarketProductServiceImpl();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String[] paths = pathInfo.split("/");
            if (paths.length < 2) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            switch (paths[1]) {
                case "product":
                    Integer userIdGiven = Integer.parseInt(req.getParameter("userId"));
                    List<MarketProduct> products = marketProductService.getAllProduct(userIdGiven);
                    out.print(gson.toJson(products));
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
