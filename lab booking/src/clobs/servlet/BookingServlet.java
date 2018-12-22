package clobs.servlet;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import clobs.Booking;
import clobs.Item;
import clobs.Location;
import clobs.User;
import clobs.util.AvailabilityCheck;
import clobs.util.MaxBookingNr;
import clobs.util.WeeklyDates;

/**
 * Servlet implementation class Booking
 * Stellt Interaktion zwischen JSP und hauptsaechlich der Klasse Booking.java dar.
 */
public class BookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private String targetsite = "/clobs/jsp/index.jsp";
	 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected  synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		 if (session == null) {
		 }
		  //System.out.println("in booking " + session.getAttribute("recurType"));
		 String action = request.getParameter("action");
		 String[] toBook = request.getParameterValues("toBook");
		 User user = (User)session.getAttribute("user");
		 if (action.equalsIgnoreCase("doBooking")) {
			 if(toBook != null) {
				 
				 synchronized (toBook) {
					
				 int maxNr = MaxBookingNr.getMaxNr();
				
				 for(int i = 0; i < toBook.length; i++) {
					 System.out.println(toBook[i]);
					 String[] bookingDetailSplit = toBook[i].split(";", 4);
					 String bookingType = bookingDetailSplit[0];
					 int bookingItemID = Integer.parseInt(bookingDetailSplit[1]);
					 String bookingStartDateTime = bookingDetailSplit[2];
					 String bookingEndDateTime = bookingDetailSplit[3];
					 Vector<String> weeklyDates = WeeklyDates.getDates(bookingStartDateTime, bookingEndDateTime);
					 //for(String df: weeklyDates) System.out.println("+++ " + df);

					 if(bookingType.equalsIgnoreCase("item")){ //PC Buchung
						 if(session.getAttribute("recurType") == null) { //Einzeln Buchung
							 Booking booking = new Booking(user.getUserID(),  bookingItemID, bookingStartDateTime, bookingEndDateTime, 1, 1 );
							 if(AvailabilityCheck.getAvailability(bookingItemID, bookingStartDateTime, bookingEndDateTime, "item")) {
								 booking.createBooking(maxNr);
							 }
							 //boolean done = booking.createBooking(maxNr);
							 //System.out.println(done);
						 }	 else {
							 	for(String df: weeklyDates) {System.out.println(df);
							 		Booking booking = new Booking(user.getUserID(),  bookingItemID, df + " " + bookingStartDateTime.substring(11), df + " " + bookingEndDateTime.substring(11) , 2, 1 );
									 if(AvailabilityCheck.getAvailability(bookingItemID, df + " " + bookingStartDateTime.substring(11), df + " " + bookingEndDateTime.substring(11) , "item")) {
									 	booking.createBooking(maxNr);
									 }
							 		//boolean done = booking.createBooking(maxNr);
									//System.out.println(done);
							 	}
						 }
					 } else if(bookingType.equalsIgnoreCase("location")) { //Lab Buchung
							 Location location = new Location();
							 location.setLocationID(bookingItemID);
							 Vector<Item> items = location.getItemList();
							 if(session.getAttribute("recurType") == null) { //Einzeln Buchung
								 for(Item item: items) {
									 Booking booking = new Booking(user.getUserID(),  item.getItemID(), bookingStartDateTime, bookingEndDateTime, 1, 2 );
									 if(AvailabilityCheck.getAvailability(bookingItemID, bookingStartDateTime, bookingEndDateTime, "item")) {
										 booking.createBooking(maxNr);
									 }
									 //boolean done = booking.createBooking(maxNr);
									 //System.out.println(done);
								 }
							 } else {
									 for(Item item: items) {
										 for(String df: weeklyDates) {
										 		Booking booking = new Booking(user.getUserID(),  item.getItemID(), df + " " + bookingStartDateTime.substring(11), df + " " + bookingEndDateTime.substring(11) , 2, 2 );
												 if(AvailabilityCheck.getAvailability(bookingItemID, bookingStartDateTime, bookingEndDateTime, "item")) {
													 booking.createBooking(maxNr);
												 }
										 }
									 }
							 }
					 }
				 }
				}
			 }
			 response.sendRedirect(targetsite+"?target=mybookings");	
		 }
	}	 
}
