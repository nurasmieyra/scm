<%@ page import="java.util.*" import="clobs.User" %>
<%
User user = (User)session.getAttribute("user");
 if (user != null) {
%>
<table id="mysettings">
	<tr>
 		<td>Username:</td>
 		<td><b><%=user.getUsername() %></b></td>
 	</tr>
 	<tr>
 		<td>Firstname</td>
 		<td><b><%=user.getFirstname() %></b></td>
 	</tr>
 	<tr>
 		<td>Lastname:</td>
		 <td><b><%=user.getLastname() %></b></td>
 	</tr>
  	<tr>
  		<td>Email:</td>
  		<td><b><%=user.getEmail() %></b></td>
 	</tr>
 </table>
<% } %>