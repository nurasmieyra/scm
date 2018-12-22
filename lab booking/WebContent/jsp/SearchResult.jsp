<%@ page import="java.util.*" import="clobs.*" import="clobs.util.*" import="java.sql.Array" import="java.lang.reflect.Constructor"%>
 <% 
 if (session.getAttribute("searchResult") != null) {
	Vector<Item> itemVector = null;
	Vector<Location> locationVector = null;
	if(session.getAttribute("searchResultType").toString().equalsIgnoreCase("item")) {
		itemVector =  (Vector<Item>)session.getAttribute("searchResult");
	} else if (session.getAttribute("searchResultType").toString().equalsIgnoreCase("Location")) {
		locationVector =  (Vector<Location>)session.getAttribute("searchResult");
	}
	String searchStartDate = session.getAttribute("searchStartDate").toString();
	String searchEndDate = session.getAttribute("searchEndDate").toString();
	String searchStartTime = session.getAttribute("searchStartTime").toString();
	String searchEndTime = session.getAttribute("searchEndTime").toString();
	String startDateTimeToBook = searchStartDate + " " + searchStartTime;
	String endDateTimeToBook = searchEndDate + " " + searchEndTime;
	Vector<String> weeklyDates = WeeklyDates.getDates(searchStartDate, searchEndDate);
	int repeatTimes = weeklyDates.size();
%>
<div>
	<table border="0">
		<tr>
	<td>
<div>
<form name="SearchResultForm" method="post" action="/clobs/BookingServlet">
	<table id="">
		<thead>
			<tr><th></th>
			<% for(String dt: weeklyDates) { %>	
					<th><%=dt %></th>
			<%	} 	%>
			</tr>
		</thead>
		<tbody>
		<% if(itemVector != null && locationVector == null) { 
			
				 	for(Item anItem: itemVector) {
						if (anItem != null) {
							int counter = 0;
		%>			
				<tr><td><%=anItem.getItemID() %></td>
					<%	for(String dt: weeklyDates) {
								String date = dt.substring(0,10);
								String startDatetime = date + " " + searchStartTime;
								String endDatetime = date + " " + searchEndTime;
								Boolean availability = anItem.getAvailability(startDatetime, endDatetime);
								if(availability)  counter++;
					%>
					<td><%=availability?"yes":"no" %></td>
					<% } %>
					<td align="left">
					<%if(counter == repeatTimes) { 
					%>
						<input type="checkbox" name="toBook"  value="<%="item;" + anItem.getItemID() + ";" + startDateTimeToBook + ";" + endDateTimeToBook%>" >
					<% } %>
					</td>
				<% }  %>
				</tr>
			<%
				} 
			} else if(locationVector != null && itemVector == null) {
					for(Location anLocation: locationVector) {
						if (anLocation != null) { 
							int counter = 0;
			%>			
				<tr><td><%=anLocation.getRoomnumber() %></td>
					<%	for(String dt: weeklyDates) {
								String date = dt.substring(0,10);
								String startDatetime = date + " " + searchStartTime;
								String endDatetime = date + " " + searchEndTime;
								Boolean availability = anLocation.getAvailability(startDatetime, endDatetime);
								if(availability)  counter++;
					%>
					<td><%=availability?"yes":"no" %></td>
					<% } %>
					<td align="left">
					<%if(counter == repeatTimes) { 
					%>
						<input type="checkbox" name="toBook"  value="<%="location;" + anLocation.getLocationID() + ";" + startDateTimeToBook + ";" + endDateTimeToBook%>" >
					<% } %>
					</td>
				<% }  %>
				</tr>
		<%
				} 
			}
		%>
		</tbody>	
	</table>
<input type="submit" value="Submit">
<input type="hidden" name="action"  value="doBooking">
</div>
</form>	
			</td>
						<td>
<div>	
<%
	if(session.getAttribute("roomItemConfiguration") != null) {
		Configuration itemConfiguration = (Configuration)session.getAttribute("roomItemConfiguration");
		Vector<String> propertyCategory = itemConfiguration.getPropertyCategory();
		Vector<String> propertyName = itemConfiguration.getPropertyName();
		Vector<String> propertyValue = itemConfiguration.getPropertyValue();
%>
		Configurations:
		<table id="configuration">
			<%
			int rowNumber = propertyCategory.size();
			String lastCategory = null;
			String lastName = null;
			String lastValue = null;
			for(int i = 0; i < rowNumber; i++) {
				String tmpCategory = lastCategory;
				String tmpName = lastName;
				String tmpValue = lastValue;
			%>
		<tr><td><%=((propertyCategory.get(i) == null) ||  propertyCategory.get(i).equalsIgnoreCase(tmpCategory))?"":propertyCategory.get(i) + ": "%></td><td><%=propertyName.get(i)%></td><td><%=propertyValue.get(i)%></td></tr>
		<%
				lastCategory = propertyCategory.get(i);
				lastName = propertyName.get(i);
				lastValue = propertyValue.get(i);
			}
		%>
		</table>
	<% 
	}
	%>
</div>
	</td>
			
		</tr>
	</table>
</div>
<% 
} 
%>
