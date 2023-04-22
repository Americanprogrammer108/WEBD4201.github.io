<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    %>
    <%@ page import = "Chens_WEBD4201.*" %>
<%
User aUser = (User)session.getAttribute("user");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>${ param.title }</title>
<link rel="stylesheet" href="./css/theme.css"/>
</head>
<body>

	<div id="container">
		
		<header>
			<p class="header">
				
				<% if (session.getAttribute("user") == null) 
				{%>
					<a href="./login.jsp">Login</a> |
					<a href="./register.jsp">Register</a>
			  <%} 
				else
				{%>
					Welcome <%=session.getAttribute("user")%> |
					<a href="./profile.jsp">Profile</a> 
					<a href="./LogoutServlet">Logout</a>
		      <%}
			  %>


			</p>
			<img width="100%" src="./images/TechProfBanner.png" alt="TechProf Banner" />
			<h1><%= request.getParameter("title") %></h1>
		</header>