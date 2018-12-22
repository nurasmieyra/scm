package clobs.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import clobs.BookingPeriode;
import clobs.User;
import clobs.util.StringToDate;

/**
 * Servlet implementation class SettingServlet
 */
public class MaintenanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private String targetsite = "/clobs/jsp/index.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MaintenanceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		 if (session == null) {
		 }
		 String action = request.getParameter("action");
		 User user = (User)session.getAttribute("user");
		 Vector<BookingPeriode> myBookingPeriode = null;
		 /**
		 if(session.getAttribute("myBookingPeriode") == null) { 
				myBookingPeriode = user.getMyBookingPeriode();
				session.setAttribute("myBookingPeriode", myBookingPeriode);
		}
		 **/
		 if(action.equalsIgnoreCase("editBP")) {
			 
			 int periodeID = Integer.parseInt(request.getParameter("bpIDToEdit"));
			 int stage = Integer.parseInt(request.getParameter("bpStageToEdit"));
			 String StartString = request.getParameter("bpStartYearToEdit") + "-" + request.getParameter("bpStartMonthToEdit") + "-" + request.getParameter("bpStartDayToEdit");
			 String EndString = request.getParameter("bpEndYearToEdit") + "-" + request.getParameter("bpEndMonthToEdit") + "-" + request.getParameter("bpEndDayToEdit");
			 java.sql.Date bpStart = new java.sql.Date((StringToDate.getDate(StartString, "yyyy-MM-dd")).getTime());
			 java.sql.Date bpEnd = new java.sql.Date((StringToDate.getDate(EndString, "yyyy-MM-dd")).getTime());
			 
			 BookingPeriode oldBP = new BookingPeriode();
			 oldBP.initPerID(periodeID, "periode");
			 
			 if(!(bpStart.before(new Date())) && bpStart.before(bpEnd)) {
				 if((bpStart.equals(oldBP.getStart()) && !(bpEnd.before(oldBP.getEnd())))  // Buchungsperiode kann verlängert werden, oder an Stage geändert werden
					 || (bpStart.after(oldBP.getEnd()))) {  // neue Buchungsperiode komplett nach der alten
					 
					 BookingPeriode newBP = new BookingPeriode();
					 newBP.setPeriodeID(periodeID);
					 newBP.setStart(bpStart);
					 newBP.setEnd(bpEnd);
					 newBP.setBookingStage(stage);
					 newBP.updateBookingPeriode();
					 response.sendRedirect(targetsite+"?target=mylocations");
				 } else  {
					 response.sendRedirect(targetsite+"?target=error&error=x");
				 }
			 } else {
				 response.sendRedirect(targetsite+"?target=error&error=x");
			 }
		 }
	}
}
