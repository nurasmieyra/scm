<%@ page import="java.util.*" import="clobs.*" import="clobs.util.*"%>
<% 
	    User user = (User)session.getAttribute("user");
		Vector<Booking> myBookings = null;
		if(session.getAttribute("mySettings") == null) { 
			myBookings = user.getMyBookings();
		} else {
			myBookings = (Vector<Booking>)session.getAttribute("myBookings");
		}
%>

<div id="mylocations">
<form>
<table border="0">
	<tr>
		<th width="10%">Booking ID</th>
		<th width="10%">Item</th>
		<th width="10%">Recurrence</th>
		<th width="10%">Start Date</th>
		<th width="10%">End Date</th>
		<th width="10%">Fome</th>
		<th width="10%">To</th>
		<th width="2%"></th>
	</tr>
	<% 
			Iterator<Booking> itr = myBookings.iterator();
	 		int tmpBookingID = 0;
	 		int tmpID = 0;
			while(itr.hasNext()){ 
				
				Booking booking = itr.next();
				int bookingID = booking.getBookingID();
				int id = booking.getItemID();
				int recurrenceType = booking.getRecurrencetypeID();
				String startDate = booking.getStart().substring(0,10);
				String endDate = booking.getEnd().substring(0,10);
				String startTime = booking.getStart().substring(11,16);
				String endTime = booking.getEnd().substring(11,16);
				String Category = (booking.getCategoryID() == 1)?"PC":(booking.getCategoryID() == 2)?"Lab":"";
				
				Boolean sameBooking = (tmpBookingID == bookingID);
				
				if(Category.equalsIgnoreCase("lab")) {
					
					Item item = new Item(id);
					id = item.getLocationID();
				}
				
				Boolean sameID = (tmpID == id);
				
				if(recurrenceType == 2) {
					startDate = booking.getAbsoluteBookingStart().substring(0,10);
					endDate = booking.getAbsoluteBookingEnd().substring(0,10);
				}
				if(!sameID) {
%>
	<tr>
		<td><a href="bookingdetail.jsp?bookingid=<%=bookingID %>"/><%=sameBooking?"":bookingID %></a></td>
		<td><%=Category + " " + id %></td>
		<td><%=(recurrenceType==1)?"Single":(recurrenceType==2)?"Weekly":"" %></td>
		<td><%=startDate %></td>
		<td><%=endDate %></td>
		<td><%=startTime %></td>
		<td><%=endTime %></td>
		<!-- 
		<td align="left"><input type="image" src="../images/book_delete.png" title="delete booking" ></td>
		 -->
	</tr>
		<% 
				}
				tmpBookingID = bookingID;
				tmpID = id;
			}
		%>
</table>
</form>
</div>