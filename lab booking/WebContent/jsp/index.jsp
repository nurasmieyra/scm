<jsp:include page="/jsp/header.jsp"  ></jsp:include>
<%String target = request.getParameter("target"); 
%>
<jsp:include page="/jsp/leftcorner.jsp"  ></jsp:include>
<jsp:include page="/jsp/navi.jsp"  ></jsp:include>
<jsp:include page="/jsp/top.jsp"  ></jsp:include>
<jsp:include page="/jsp/center.jsp"  >
        <jsp:param name="target" value="<%=target%>" />
</jsp:include>
<jsp:include page="/jsp/footer.jsp"  ></jsp:include>