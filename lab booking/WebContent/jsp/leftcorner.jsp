<div id="linksoben">
 <% if (session.getAttribute("user") == null) { %> 

<% } else { %>
<%@ page import="java.util.*" import="clobs.User" import="java.text.*" %>
<%
User user = (User) session.getAttribute("user");
%>
<div id="leftcornercontent">
<table>
	<tr>
		<td align="center">You are logged in as:</td>
		<td align="center"><b><%=user.getUsername()%></b></td>
	</tr>
<% 			
			Calendar cal = new GregorianCalendar( TimeZone.getTimeZone("ECT") );
		    DateFormat formater = DateFormat.getDateInstance(DateFormat.FULL);
%>
	<tr>
		<td colspan="2" align="center">
			<%= formater.format(cal.getTime()) %>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<form name="loginForm" method="post" action="/clobs/LoginServlet">
			<input type="submit" value="Logout"></input>
  			<input type="hidden" name="action" value="LOGOUT"></input>
  			</form>
  		</td>
  	</tr>
</table>
</div>	
</div>
<% }  %>