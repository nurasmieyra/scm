</div>
<div id="navi">
 <% if (session.getAttribute("user") == null) { %> 
	<ul id="menu">
  		<li> 
  			<a href="registration.jsp">Registration</a>
  		</li>
	</ul>
<% } else { %>
<%@ page import="java.util.*" import="clobs.User" %>
<%
				User user = (User)session.getAttribute("user");
				int userstage =(int)user.getUserstage();
				if (userstage <= 3) {%>
<jsp:include page="/jsp/membermenu.jsp"  ></jsp:include>
		<%}
				if (userstage <= 2 ) {%>
<%--include institute menu --%>		
		<%}
				if (userstage <= 1 ) {%>
<jsp:include page="/jsp/providermenu.jsp"  ></jsp:include>		
		<%}
				if (userstage == 0 ) {%>
<%--include admin menu --%>				
		<%}
}%>	
</div>