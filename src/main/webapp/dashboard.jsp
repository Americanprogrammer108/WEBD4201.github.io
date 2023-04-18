<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "webd4201.chene.*" %>
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
			<p>The current date and time is: <%= new java.util.Date() %></p>
			<p>You should only see this if you are logged in!</p>
		</div>
	
		<jsp:include page="./inc/footer.jsp">
			<jsp:param name="footerTitle" value="User Dashboard" />
		</jsp:include>
		<% } %>
	</div>

</body>
</html>