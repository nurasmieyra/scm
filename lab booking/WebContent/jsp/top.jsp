<%@ page import="java.text.*" import="java.util.*" import="clobs.util.*" %>
<div id="top" align="center">
 <% if (session.getAttribute("user") == null) { 
	 		Calendar cal=Calendar.getInstance();
 %> 
<h1>Welcome to the  online lab booking system</h1>
<% } else { %>
<%@ page import="java.util.*" import="clobs.*" import="clobs.util.*"%>
<div id="searchtable">
<form  name="SearchForm" method="post" action="/clobs/SearchServlet">
	<table border="0">
		<% 
			Calendar cal = Calendar.getInstance();
			String selectedSearchType = (session.getAttribute("selectedSearchType") == null)?null:session.getAttribute("selectedSearchType").toString();
			int selectedStartMonth = cal.get(Calendar.MONTH) + 1;
			int selectedStartYear = cal.get(Calendar.YEAR);
			int selectedStartDay = cal.get(Calendar.DAY_OF_MONTH);
			int selectedEndMonth = cal.get(Calendar.MONTH) + 1;
			int selectedEndYear = cal.get(Calendar.YEAR);
			int selectedEndDay = cal.get(Calendar.DAY_OF_MONTH);
			if(session.getAttribute("searchStartDate") != null) {
				String selectedStartDate = session.getAttribute("searchStartDate").toString();
				selectedStartYear = Integer.parseInt(selectedStartDate.substring(0, 4));
				selectedStartMonth =  Integer.parseInt(selectedStartDate.substring(5, 7));
				selectedStartDay =  Integer.parseInt(selectedStartDate.substring(8, 10));
				System.out.println(selectedStartYear + "  "+selectedStartMonth + "  "+selectedStartDay);
			} 
			if(session.getAttribute("searchEndDate") != null) {
				String selectedEndDate = session.getAttribute("searchEndDate").toString();
				selectedEndYear =  Integer.parseInt(selectedEndDate.substring(0, 4));
				selectedEndMonth =  Integer.parseInt(selectedEndDate.substring(5, 7));
				selectedEndDay =  Integer.parseInt(selectedEndDate.substring(8, 10));
				System.out.println(selectedEndYear + "  "+selectedEndMonth + "  "+selectedEndDay);
			}
		%>
		<tr>
			<td>
				Search for available:
			</td>
			<td>
				<select name="searchType" onChange="document.SearchForm.submit();">
					<option value="pcsearch"  <%=(selectedSearchType == null || selectedSearchType.equalsIgnoreCase("pcsearch"))?"selected=\"selected\"":"" %>>PC</option>
					<option value="labsearch" <%=(selectedSearchType != null && selectedSearchType.equalsIgnoreCase("labsearch"))?"selected=\"selected\"":"" %>>Lab</option>
				</select>
			</td>
			<td>
				Time period:
			</td>
			<td>
				<input type="checkbox" name="recurType" value="weekly" <%=(session.getAttribute("recurType") != null && session.getAttribute("recurType").toString().equalsIgnoreCase("weekly"))?"checked=\"checked\"":""%>>Weekly
			</td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<!-- <td>Start on <input type="text" name="searchStartDate" value="2010-02-26"><br></td> -->
			<td>
				Start on
			</td> 
			<td>
					<select name="searchStartYear">
							<% for(int i = 0; i < 3; i++) {
										int year = cal.get(Calendar.YEAR) + i; %>
							<option value="<%=year %>" <%=(selectedStartYear == year)?"\"selected = selected\"":"" %>><%=year %></option>
							<% } %>
					</select>
					<select name="searchStartMonth">
							<% for(int i = 0; i < cal.getMaximum(Calendar.MONTH) + 1; i++) {
										int month = i + 1; %>
							<option value="<%=(String.valueOf(month).trim().length() == 1)?"0"+month:month %>" <%=(selectedStartMonth == month)?"\"selected = selected\"":"" %>><%=month %></option>
							<% } %>
					</select>
					<select name="searchStartDay">
							<% for(int i = 0; i < cal.getMaximum(Calendar.DAY_OF_MONTH); i++) {
										int day = i + 1; %>
							<option value="<%=(String.valueOf(day).trim().length() == 1)?"0"+day:day %>" <%=(selectedStartDay == day)?"\"selected = selected\"":"" %>><%=day %></option>
							<% } %>
					</select>
			</td>
			<td>
				End on
			</td>
			<td>
				<select name="searchEndYear">
					<% for(int i = 0; i < 3; i++) {
						int year = cal.get(Calendar.YEAR) + i; %>
					<option value="<%=year %>" <%=(selectedEndYear == year)?"\"selected = selected\"":"" %>><%=year %></option>
							<% } %>
				</select>
				<select name="searchEndMonth">
					<% for(int i = 0; i < cal.getMaximum(Calendar.MONTH) + 1; i++) {
						int month = i + 1; %>
					<option value="<%=(String.valueOf(month).trim().length() == 1)?"0"+month:month %>" <%=(selectedEndMonth == month)?"\"selected = selected\"":"" %>><%=month %></option>
							<% } %>
				</select>
				<select name="searchEndDay">
					<% for(int i = 0; i < cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
						int day = i + 1; %>
					<option value="<%=(String.valueOf(day).trim().length() == 1)?"0"+day:day %>" <%=(selectedEndDay == day)?"\"selected = selected\"":"" %>><%=day %></option>
					<% } %>
				</select>
			</td>
			<td><label for="starttime">From:</label></td>
       		<td>
      			<select name="searchStartTime">
      				<%
      					for(String time: Consts.startTimesForDD) {
      				%>
      				 <option <%=(session.getAttribute("searchStartTime") != null && session.getAttribute("searchStartTime").toString().equalsIgnoreCase(time))?"selected=\"selected\"":""%>><%=time%></option>
      				 <%
      				 	}
      				 %>
				</select>
			</td>
			<td><label for="endtime">To:</label></td>
        	<td>	
        		<select name="searchEndTime" onChange="document.SearchForm.submit();">
      				<%
      					for(String time: Consts.endTimesForDD) {
      				%>
      				 <option <%=(session.getAttribute("searchEndTime") != null && session.getAttribute("searchEndTime").toString().equalsIgnoreCase(time))?"selected=\"selected\"":"" %>><%=time %></option>
      				 <% } %>
				</select>
			</td>
		</tr>
		<tr>
       		<td><label for="location">Building:</label></td>
       		<td>
       			<select name="searchBuildingDD" onChange="document.SearchForm.submit();">
       				<% 	String buildingSelected = (session.getAttribute("searchBuildingDD")==null || session.getAttribute("searchBuildingDD").toString().equalsIgnoreCase("default"))?"default":session.getAttribute("searchBuildingDD").toString(); %>
       				<option value="default" <%=(buildingSelected.equalsIgnoreCase("default"))?"selected=\"selected\"":"" %>>-- Building --</option>
	  				<%
	  					Vector<String> buildingList = BuildingList.getList();  					 	
	  						  					Iterator<String> bItr = buildingList.iterator();
	  						  					while(bItr.hasNext()) {
	  						  					  	String building = bItr.next().toString();
	  				%>
	   				<option value="<%=building%>" "<%=(building.equals(buildingSelected))?"selected=\"selected\"":"" %>"><%=building%></option>
	   				 <%}%>     				
				</select>

       		</td>
       		<% if(selectedSearchType == null || selectedSearchType.equalsIgnoreCase("pcsearch")) { %>
       		<td><label for="room">Lab:</label></td>
       		<td>
       			<select name="searchRoomDD" onChange="document.SearchForm.submit();">
       				<% String roomSelected = (session.getAttribute("searchRoomDD")==null || session.getAttribute("searchRoomDD").toString().equalsIgnoreCase("default"))?"default":session.getAttribute("searchRoomDD").toString(); %>
       				<option value="default" <%=(roomSelected.equalsIgnoreCase("default"))?"selected=\"selected\"":"" %>>-- Room --</option>
	  				<%
	  						Vector<Location> roomList = new Vector<Location>();
	  						if (!(session.getAttribute("roomsFromBuilding")==null)) {
		  						roomList = (Vector<Location>) session.getAttribute("roomsFromBuilding");
								Iterator<Location> rItr = roomList.iterator();
		   						while(rItr.hasNext()) {
		   							Location room = rItr.next();
		   							String roomNr = room.getRoomnumber(); 
		   							String roomID = Integer.toString(room.getLocationID());
		   			%>
		   				<option value="<%=roomID%>" "<%=(roomID.equalsIgnoreCase(roomSelected))?"selected=\"selected\"":"" %>"><%=roomNr%></option>
		   				 <%}     				
	  						}%>
				</select>
			</td>
			<%} else {
			%>
			<td></td><td></td>
			<% } %>
        </tr>
		<tr>
			<td><input type="submit" value="Search"></td>			
		</tr>
	</table>
		<input type="hidden" name="action" value="doSearch">
</form>
</div>
<% }  %>
</div> 
