// src/main/java/controller/TransactionController.java
package controller;

import com.google.gson.Gson;
import service.TransactionService;
import service.impl.TransactionServiceImpl;
import vo.Transaction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/transactions/*")
public class TransactionController extends HttpServlet {
    private TransactionService service;
    private Gson gson;

    @Override
    public void init() {
        service = new TransactionServiceImpl();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
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
                case "given":
                    Integer userIdGiven = Integer.parseInt(req.getParameter("userId"));
                    List<Transaction> givenRatings = service.getGivenRatings(userIdGiven);
                    out.print(gson.toJson(givenRatings));
                    break;

                case "received":
                    Integer userIdReceived = Integer.parseInt(req.getParameter("userId"));
                    List<Transaction> receivedRatings = service.getReceivedRatings(userIdReceived);
                    out.print(gson.toJson(receivedRatings));
                    break;

                case "transaction":
                    if (paths.length < 3) {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }
                    Integer transactionId = Integer.parseInt(paths[2]);
                    Transaction transaction = service.getTransactionById(transactionId);
                    if (transaction != null) {
                        out.print(gson.toJson(transaction));
                    } else {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    }
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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            if (pathInfo == null || !pathInfo.startsWith("/rating")) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Integer transactionId = Integer.parseInt(req.getParameter("transactionId"));
            String review = req.getParameter("review");
            Integer stars = Integer.parseInt(req.getParameter("stars"));
            boolean isProvider = Boolean.parseBoolean(req.getParameter("isProvider"));

            boolean success = service.updateRating(transactionId, review, stars, isProvider);
            if (success) {
                out.print(gson.toJson(new Response("Rating updated successfully")));
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Transaction not found");
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private static class Response {
        private String message;

        public Response(String message) {
            this.message = message;
        }
    }
}
