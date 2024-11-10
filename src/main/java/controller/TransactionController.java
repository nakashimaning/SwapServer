// src/main/java/controller/TransactionController.java
package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import service.TransactionService;
import service.impl.TransactionServiceImpl;
import vo.Transaction;
import vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/transactions/*")
public class TransactionController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TransactionService service;
	private Gson gson;

	@Override
	public void init() {
		service = new TransactionServiceImpl();
		gson = new GsonBuilder()
				.setLenient()
				.serializeNulls()
				.create();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		
	    // Check if the user is logged in
		String userText = req.getHeader("user");
//		System.out.println(userText);
	    User user = gson.fromJson(userText, User.class); 
	    if (user == null) {
	        // User is not logged in; return 401 Unauthorized
	        resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not logged in.");
	        return;
	    }
	    
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
//				Integer userIdGiven = Integer.parseInt(req.getParameter("userId"));
		        Integer userIdGiven = user.getUser_id(); // Get userId from session

				List<Transaction> givenRatings = service.getGivenRatings(userIdGiven);
				out.print(gson.toJson(givenRatings));
				break;

			case "received":
//				Integer userIdReceived = Integer.parseInt(req.getParameter("userId"));
		        Integer userIdReceived = user.getUser_id(); // Get userId from session

				List<Transaction> receivedRatings = service.getReceivedRatings(userIdReceived);
				out.print(gson.toJson(receivedRatings));
				break;
				//http://localhost:8080/Swap/api/transactions/transaction?transaction_id=1
			case "transaction":
			    String transactionIdParam = req.getParameter("transaction_id");
			    if (transactionIdParam == null) {
			        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Transaction ID is required.");

			        return;
			    }
			    Integer transaction_id = Integer.parseInt(transactionIdParam);
			    Transaction transaction = service.getTransactionById(transaction_id);
			    if (transaction != null) {
//			        out.print(gson.toJson(transaction));
			     // Verify that the logged-in user is involved in this transaction
			        if ( service.isUserInvolved(user.getUser_id(),transaction_id)) {
			            out.print(gson.toJson(transaction));
			        } else {
			            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorized to view this transaction.");
			        }
			        
			    } else {
			        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			    }
			    break;
			  //http://localhost:8080/Swap/api/transactions/transaction/1

//			case "transaction":
//				if (paths.length < 3) {
//					resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
//					return;
//				}
//				Integer transactionId = Integer.parseInt(paths[2]);
//				Transaction transaction = service.getTransactionById(transactionId);
//				if (transaction != null) {
//					out.print(gson.toJson(transaction));
//				} else {
//					resp.sendError(HttpServletResponse.SC_NOT_FOUND);
//				}
//				break;

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
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
//http://localhost:8080/Swap/api/transactions/rating?transactionId=1&review=Great service!&stars=5&isProvider=true
	    // Check if the user is logged in
	    HttpSession session = req.getSession(false); // Use false to prevent creating a new session
	    User user = null;
	    if (session != null) {
	        user = (User) session.getAttribute("user");
	    }
	    if (user == null) {
	        // User is not logged in; return 401 Unauthorized
	        resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not logged in.");
	        return;
	    }
	    
		try {
			if (pathInfo == null || !pathInfo.startsWith("/rating")) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			Integer transactionId = Integer.parseInt(req.getParameter("transactionId"));
			String review = req.getParameter("review");
			Integer stars = Integer.parseInt(req.getParameter("stars"));
			boolean isProvider = Boolean.parseBoolean(req.getParameter("isProvider"));
			
		    // Retrieve the transaction and verify ownership
		    Transaction transaction = service.getTransactionById(transactionId);
		    if (transaction == null) {
		        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Transaction not found");
		        return;
		    }
		    if (!transaction.isUserInvolved(user.getUser_id())) {
		        resp.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorized to update this transaction.");
		        return;
		    }
		    
		    
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
