<div id="containercenter">
 <% if(session.getAttribute("user") == null) { %> 
	<div id="loginwindow">
	<form name="loginForm" method="post" action="/clobs/LoginServlet">
		<fieldset id="fieldsetData">
    		<legend>Login</legend>
    			<table align="center">
      				<tr>
        				<td><label for="username">Username:</label></td>
        				<td><input type="text" id="username" name="username"></td>
      				</tr>
      				<tr>
        				<td><label for="password">Password:</label></td>
        				<td><input type="password" id="password" name="password"></td>
      				</tr>
      				<%if(session.getAttribute("falselogin") == null) { 
      					
      				} else {%>
      				<tr><td colspan ="2" style="color:red;">Password or Username is wrong. Try it again.</td></tr>
      				<%} %>
      				<tr>
        				<td colspan="2" align="right">
        				    <input type="Submit" name="Submit" value="Login">
        				</td>
      				</tr>
    			</table>
  		</fieldset>
  		<input type="hidden" name="action" value="LOGIN">
  	</form>
  		<div align="right"><a href="registration.jsp">register</a></div>
	</div>
<% } else  {
				if(request.getParameter("target").equalsIgnoreCase("mysettings")) {	
%>
					<jsp:include page="/jsp/mysettings.jsp"/>
<% 			} else if (request.getParameter("target").equalsIgnoreCase("search")) {	
%>
					<jsp:include page="/jsp/search.jsp"/>
<% 			} else if (request.getParameter("target").equalsIgnoreCase("mybookings")) {	
%>
					<jsp:include page="/jsp/mybookings.jsp"/>		
<% 			} else if (request.getParameter("target").equalsIgnoreCase("newbooking")) {	
%>
					<jsp:include page="/jsp/newbooking.jsp"/>			
<% 			} else if (request.getParameter("target").equalsIgnoreCase("quicksearch")) {	
%>
					<jsp:include page="/jsp/quicksearchresult.jsp"/>			
<% 			} else if (request.getParameter("target").equalsIgnoreCase("mylocations")) {	
%>
					<jsp:include page="/jsp/mylocations.jsp"/>				
<% 			} else if (request.getParameter("target").equalsIgnoreCase("publishing")) {	
%>
					<jsp:include page="/jsp/publishing.jsp"/>	
<%			} else if (request.getParameter("target").equalsIgnoreCase("error")) {	
%>			
					<jsp:include page="/jsp/error.jsp"/>		
<%			} else if (request.getParameter("target").equalsIgnoreCase("dpdetail")) {	
%>			
					<jsp:include page="/jsp/dpdetail.jsp"/>			
<%			} else if (request.getParameter("target").equalsIgnoreCase("labmap")) {	
%>			
					<jsp:include page="/jsp/labmap.jsp"/>							
<% 			}else {	
%>				
						<%@ page import="java.util.*" import="clobs.User" %>
						<%
						User user = (User)session.getAttribute("user");
						 if (user != null) {
						%>
						<% if(user.getUserstage() == 3) {%>
											<jsp:include page="/jsp/mybookings.jsp"/>
						<% } else {%>
											<jsp:include page="/jsp/mylocations.jsp"/>
						<%}
						 }
				}
}
%>
</div>