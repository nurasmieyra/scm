<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta http-equiv="Content-Type"
content="text/html; charset=ISO-8859-1">
<title>Computer Lab Online Booking System - Registration</title>
<link rel="stylesheet" type="text/css" href="../css/pagelayout.css">
<script src="../javascript/menu_aufklappen.js" type="text/javascript"></script>
</head>
<body onload="treeMenu_init(document.getElementById('menu'), '')">
<div id="seitenbereich">
<div id="linksoben">
</div>
<div id="navi">
	<ul id="menu">
  		<li>
  			<a href="index.jsp">Login</a>
  		</li>
	</ul>
</div>
<div id="top" align="center"><h1>Registration</h1></div> 
<div id="containercenter">
	<div id="registrationwindow">
		<fieldset id="fieldsetData">
    		<legend>Registration - Please enter your data</legend>
    			<table align="center">
      				<tr>
        				<td><label>last name:</label></td><td><input type="text" id="lastname" name="Lastname"></td>
        				<td><label>first name:</label></td><td><input type="text" id="firstname" name="Firstname"></td>
        			</tr>
        			<tr>
        				<td><label>Email:</label></td><td><input type="text" id="username" name="Uorname"></td>
        				<td></td>
        			</tr>
        			<tr></tr>
        			<tr>	
        				<td><label>Username:</label></td><td><input type="text" id="username" name="Uorname"></td>
        				<td><label>Password:</label></td><td><input type="password" id="password" name="Password"></td>
        			</tr>
        			<tr>
        				<td><label>Retype Password:</label></td><td><input type="password" id="password" name="Password"></td>
      				</tr>
      				<tr>
        				<td colspan="3"><input value="Absenden" type="submit"></td>
        				<td style="color:red">Nicht implementiert!</td>
      				</tr>
    			</table>
  		</fieldset>
	</div>
</div>
</div>
</body>
</html>