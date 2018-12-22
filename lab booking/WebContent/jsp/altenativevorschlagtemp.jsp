<div>
	<form  name="SearchResultForm" method="post" action="/clobs/BookingServlet">
		<table id="alternativ">
			<thead>
				<tr>
					<th>pc</th>
					<th>available</th>
				</tr>
			</thead>
			<tbody>
			<% 
			for(Item anItem: itemVector) {
					if (anItem != null) {
						Vector<Vector<String>> periods = new Vector<Vector<String>>();
						periods = anItem.getBookablePeriodsOnDate(session.getAttribute("searchStartDate").toString(), session.getAttribute("searchEndDate").toString());
			%>
				<tr>
					<td><%=anItem.getItemID() %></td>
			 		<td>
			 			<table>
						<% for(Vector<String> period: periods) {
						%>
							<tr><td><%=period.get(0).toString() + "  -  " + period.get(1).toString()%></td><td align="left"><input type="image" src="../images/book_add.png" title="create booking" ></td></tr>
						<%
								}
						%>
						</table>
					</td>
				</tr>
			 <%} %>
		 	</tbody>
		<%
		} 
		%>
		</table>
	</form>
</div>