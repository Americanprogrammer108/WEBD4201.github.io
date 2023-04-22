<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import = "Chens_WEBD4201.*" %>
    <%@ page import = "java.util.Vector" %>
    <%@ page import = "java.sql.*" %>
    <%@ page import="java.io.PrintStream" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

</head>

<body>
	<jsp:include page="./inc/header.jsp">
		<jsp:param name="title" value="Admin's Dashboard" />
	</jsp:include>
	
<%

String userinfo = (String)session.getAttribute("userinfo");
Connection c = DatabaseConnect.initialize();
User.initialize(c);
Vector<User> users = User.retrieveAll();

%>
<p> <%=userinfo %> </p>
<style>
table {
  font-family: arial, sans-serif;
  border-collapse: collapse;
  width: 100%;
};

td, th {
  border: 1px solid #dddddd;
  text-align: left;
  padding: 8px;
};

tr:nth-child(even) {
  background-color: #dddddd;
};
</style>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<table border="1" cellspacing="0" cellpadding="3" >
  <tbody>
  <thread>
	  <tr>
	    <th>ID</th>
	    <th>Password</th>
	    <th>First Name</th>
	    <th>Last Name</th>
	    <th>Email Address</th>
	    <th>Last Access</th>
	    <th>Enrolled on</th>
	    <th>Enabled</th>
	    <th>Type</th>
	    <th></th>
	    <th></th>
	  </tr>
  </thread>
  </tbody>
  
<%
	for (int i = 0; i < users.size(); i++) 
	{
					User aUser = users.get(i);
%>
	<tr>
		<td><%=aUser.getid() %></td>
		<td><%=aUser.getpassword() %></td>
		<td><%=aUser.getFirstName() %></td>
		<td><%=aUser.getLastName() %></td>
		<td><%=aUser.getEmailAddress() %></td>
		<td><%=aUser.getLastAccess() %></td>
		<td><%=aUser.getEnrollDate() %></td>
		<td><%=aUser.getEnabled() %></td>
		<td><%=aUser.getType() %></td>
		<td><a href="./edit.jsp?id=<%=aUser.getid() %>">Edit</a></td>
		
	<!-- <td><a href="./DeleteServlet?id=<%=aUser.getid() %>">Delete</a></td> -->
	<!-- This is the delete function that I tried to get out. I got this from a video. -->	
	
	<td> <a href="./Delete.jsp?id=<%=aUser.getid()%>" > Delete </a> </td>
		 <!-- <td> <button onclick="callAlert();">Delete </button></td> -->
		<!-- 
		<script type="text/javascript" >
			function callAlert()
			{
				alert("Are you sure you want to delete this user?"
				);
			}
		
		</script> -->
	</tr>
<% } %>

</table>
<% %>
		<jsp:include page="./inc/footer.jsp">
			<jsp:param name="footerTitle" value="Admin's dashboard" />
		</jsp:include>
</body>
</html>