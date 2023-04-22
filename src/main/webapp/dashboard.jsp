<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "Chens_WEBD4201.*" %>
<%
	User aUser = (User)session.getAttribute("user");
	if (aUser == null) { response.sendRedirect("./login.jsp"); }
	else {
%>
	<jsp:include page="./inc/header.jsp">
		<jsp:param name="title" value="User Dashboard" />
	</jsp:include>
		
		<div id="contentContainer">
			<h2>Welcome <%= aUser.getFirstName() + ' ' + aUser.getLastName() %></h2>

		</div>
	
		<jsp:include page="./inc/footer.jsp">
			<jsp:param name="footerTitle" value="User Dashboard" />
		</jsp:include>
		<% } %>
	</div>

</body>
</html>