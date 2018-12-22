<%@ page import="java.util.*" import="clobs.*" import="clobs.util.*"%>
<html>
	<head>
		<title>Error-Page</title>
	</head>
	<body>
	<div id="mysettings">
			<%! ExceptionStatement error_out; %>
			<%! String error_in; %>
			<%! String error_display; %>
	
			<%
			if(request.getParameter("error") != null) {
				error_in = request.getParameter("error");
				error_out = new ExceptionStatement();
				error_display = error_out.getErrorStatement(error_in);
			}
			else{
				error_display = "not further specified.....";
			}
			%>
			<p>
			
			<h3>
				Sorry, an error occured...
			</h3>
				<p> <%= error_display %>
			</div>

	</body>
</html>