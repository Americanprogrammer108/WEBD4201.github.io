<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	String pageTitle = "Profile Page";
	
	%>
	<jsp:include page="./inc/header.jsp">
		<jsp:param name="title" value="<%=pageTitle %>" />
	</jsp:include>
	<%
	
	String errMessage = (String)session.getAttribute("msg");
	String username = (String)session.getAttribute("username");
	String firstName = (String)session.getAttribute("firstName");
	String lastName = (String)session.getAttribute("lastName");
	String email= (String)session.getAttribute("email");
	String age = (String)session.getAttribute("age");
	
	%>
	<div>
		<p><%=session.getAttribute("user") %></p>
	</div>
<body>


</body>
</html>