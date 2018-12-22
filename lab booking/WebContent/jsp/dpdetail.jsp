<%@ page import="java.util.*" import="clobs.*" import="clobs.util.*"%>
<%
		
	    User user = (User)session.getAttribute("user");
		if(user.getUserstage() == 1) {
			Vector<BookingPeriode> myBookingPeriode = null;
			if(session.getAttribute("myBookingPeriode") == null) { 
				myBookingPeriode = user.getMyBookingPeriode();
			} else {
				myBookingPeriode = (Vector<BookingPeriode>)session.getAttribute("myBookingPeriode");
			}
		
%>
<% } %>