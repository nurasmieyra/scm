package clobs.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import clobs.User;
/**
 * 
 * Bietet private Methode getUser an @see {@link #getUser(HttpServletRequest)}
 * Stellt Interaktion zwischen JSP und hauptsaechlich der Klasse User.java da.
 */
public class LoginServlet extends HttpServlet {

	 /**
	 * @see HttpServlet#HttpServlet()
	 */
	private static final long serialVersionUID = 1L;
	private String targetsite = "/clobs/jsp/index.jsp";
	 private String errorsite = "/clobs/jsp/error.jsp";
	 private String loginsite = "/clobs/jsp/index.jsp";
	 
	 public void init(ServletConfig config)
	  throws ServletException {
	
	  super.init(config);
	 }
	 
	/**
	* @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	*/
	 
	 public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	  	 // If it is a get request forward to doPost()
		 doPost(request, response);
	 }
	 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	 
	 public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 HttpSession session = request.getSession(false);
		 if (session == null) {
			 response.sendRedirect(targetsite+"?target=error&error=conn_lost");
		 }
  		 
		  String action = request.getParameter("action");
		    
		  synchronized (action) {
			
		  if (action.equals("LOGIN")) {
			  User user = getUser(request);
			 
			  if (user.getUserID() < 1) {
				  session.setAttribute("falselogin", "yes" );
				  response.sendRedirect(loginsite);
			  } else {
				  session.setAttribute("falselogin", "no" );
				  if ( session.getAttribute("user") == null) {
					  session.setAttribute("user", user);
				  } 
	
				  response.sendRedirect(targetsite);
				  /*
			      ServletContext sc = getServletContext();
			      RequestDispatcher rd = sc.getRequestDispatcher(targetsite);
			      rd.forward(request, response);
			      */
			  }
		  }
	  
		  if (action.equals("LOGOUT")) {
			  
			  session.invalidate();
			  response.sendRedirect(loginsite);
			  
		  }
		}
	 }
	 
	/**
	 * Logt einen User ein. 
	 * @param request HttpServletRequest: Request aus der JSP
	 * @return User
	 */
	 
	  private User getUser(HttpServletRequest request) {
		  String username = request.getParameter("username");
		  String password = request.getParameter("password");
		  
		  User user = new User();
		  user.loginUser(username, password);
		  return user;		  
	  }
	  

	 
}