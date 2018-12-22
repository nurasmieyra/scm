<%@ page import="java.util.*" import="clobs.*" import="clobs.util.*"%>
<table id="searchresults">
<tr><th>Building</th><th>Lab</th><th>Open Time</th><th>Close Time</th></tr>
<%
	Vector<Location> map = LabMap.getMap();
	String lastBuilding = null;
	Iterator<Location> itr = map.iterator();
	while(itr.hasNext()){
		Location location = itr.next();
%>
	<tr>
		<td><%=(location.getBuilding().equalsIgnoreCase(lastBuilding))?"":location.getBuilding() %></td>
		<td><%=location.getRoomnumber() %></td>
		<td><%=location.getOpentime() %></td>
		<td><%=location.getClosetime() %></td>
	</tr>		
<%
	   lastBuilding = location.getBuilding();
	}
%>
</table>