//package filter;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import vo.User;
//
//@WebFilter("/transactions/*")
//public class AuthenticationFilter implements Filter {
//    private static final String[] excludedUrls = {"/login", "/register", "/api/market"}; // Add URLs that don't require auth
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse resp = (HttpServletResponse) response;
//        String path = req.getRequestURI().substring(req.getContextPath().length());
//
//        // Skip authentication for excluded URLs
//        for (String excludedUrl : excludedUrls) {
//            if (path.startsWith(excludedUrl)) {
//                chain.doFilter(request, response);
//                return;
//            }
//        }
//
//        HttpSession session = req.getSession(false);
//        User user = null;
//        if (session != null) {
//            user = (User) session.getAttribute("user");
//        }
//        if (user == null) {
//            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not logged in.");
//        } else {
//            chain.doFilter(request, response);
//        }
//    }
//
////	@Override
////	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
////			throws IOException, ServletException {
////		// TODO Auto-generated method stub
////		
////	}
//
//}