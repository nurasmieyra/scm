<%@ page  import="java.text.*" import="java.util.*" import="clobs.*" import="clobs.util.*"%>
<% 
	    User user = (User)session.getAttribute("user");
		Calendar cal = Calendar.getInstance();
		if(user.getUserstage() == 1) {
			Vector<BookingPeriode> myBookingPeriode = null;
			myBookingPeriode = user.getMyBookingPeriode();
%>
<table>
	<tr>
		<td>
			<div id="mysettings">
			<table border="0">
				<tr>
					<th>Building</th>
					<th>Room Nr.</th>
					<th>From</th>
					<th>To</th>
					<th>Stage</th>
					<th></th>
					<th></th>
				</tr>
				<% Iterator<BookingPeriode> itr = myBookingPeriode.iterator();
						while(itr.hasNext()) {
							BookingPeriode bp = itr.next();
				%>
				<tr>
					<td><%=bp.getLocation().getBuilding() %></td>
					<td><%=bp.getLocation().getRoomnumber() %></td>
					<td><%=bp.getStart() %></td>
					<td><%=bp.getEnd() %>	</td>
					<td><%=bp.getBookingStage() %></td>
				</tr>
				<% } %>
			</table>
			</div>
		</td>
		<td>
			<div>
				<form action="/clobs/MaintenanceServlet">
					<table border="1">
						<tr>
							<td>Lab</td>
							<td>
								<select name="bpIDToEdit">
										<% Iterator<BookingPeriode> itr1 = myBookingPeriode.iterator();
												while(itr1.hasNext()) {
													BookingPeriode bp = itr1.next();
										%>
									<option value="<%=bp.getPeriodeID() %>"><%=bp.getLocation().getBuilding() + "  " + bp.getLocation().getRoomnumber() %>
										<% } %>
								</select>
							</td>	
						</tr>
						<tr>
							<td>Start</td>
							<td>
								<select name="bpStartYearToEdit">
										<% for(int i = 0; i < 3; i++) {
													int year = cal.get(Calendar.YEAR) + i; %>
										<option value="<%=year %>"><%=year %></option>
										<% } %>
								</select>
								<select name="bpStartMonthToEdit">
										<% for(int i = 0; i < cal.getMaximum(Calendar.MONTH) + 1; i++) {
													int month = i + 1; %>
										<option value="<%=(String.valueOf(month).trim().length() == 1)?"0"+month:month %>" ><%=month %></option>
										<% } %>
								</select>
								<select name="bpStartDayToEdit">
										<% for(int i = 0; i < cal.getMaximum(Calendar.DAY_OF_MONTH); i++) {
													int day = i + 1; %>
										<option value="<%=(String.valueOf(day).trim().length() == 1)?"0"+day:day %>" ><%=day %></option>
										<% } %>
								</select>
							</td>
						</tr>
						<tr>
							<td>End</td>
							<td>
								<select name="bpEndYearToEdit">
										<% for(int i = 0; i < 3; i++) {
													int year = cal.get(Calendar.YEAR) + i; %>
										<option value="<%=year %>"><%=year %></option>
										<% } %>
								</select>
								<select name="bpEndMonthToEdit">
										<% for(int i = 0; i < cal.getMaximum(Calendar.MONTH) + 1; i++) {
													int month = i + 1; %>
										<option value="<%=(String.valueOf(month).trim().length() == 1)?"0"+month:month %>" ><%=month %></option>
										<% } %>
								</select>
								<select name="bpEndDayToEdit">
										<% for(int i = 0; i < cal.getMaximum(Calendar.DAY_OF_MONTH); i++) {
													int day = i + 1; %>
										<option value="<%=(String.valueOf(day).trim().length() == 1)?"0"+day:day %>" ><%=day %></option>
										<% } %>
								</select>
							</td>
						</tr>		
						<tr>
							<td>Stage</td>
							<td>
								<select name="bpStageToEdit">
									<%for(int i = 1; i < 4; i++) {
									%>
										<option value=<%=i %>><%=i %></option>
									<%
											}
									%>
								</select>
							</td>
						</tr>			
						<tr>
							<td>
								<input type="submit"  value="Submit">
								<input type="hidden" name="action" value="editBP">
							</td>
						</tr>
					</table>
				</form>
			</div>
		</td>
	</tr>
</table>
<% } %>