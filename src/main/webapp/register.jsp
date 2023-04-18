<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	String pageTitle = "Registration Page";
	%>
	<jsp:include page="./inc/header.jsp">
		<jsp:param name="title" value="<%=pageTitle %>" />
	</jsp:include>
	<%
	
	String username = (String)session.getAttribute("username");
	String firstName = (String)session.getAttribute("firstName");
	String lastName = (String)session.getAttribute("lastName");
	String email= (String)session.getAttribute("email");
	String password = (String)session.getAttribute("password");
	
	if (session.getAttribute("user") == null) 
	{
	%>
	<p> </p>
	<div>
		<div id="contentContainer">
			<h2>Enter Your Information Below!</h2>
			<p></p>
			<form method="post" action="./Register">
				<label class="fixedlabel"> Username: </label> 
				<input type="text" name="username" value="<%=username %>" required/><br>
				
				<label class="fixedlabel"> First Name: </label> 
				<input type="text" name="firstName" value="<%=firstName %>" required/><br>
				
				<label class="fixedlabel"> Last Name: </label> 
				<input type="text" name="lastName" value="<%=lastName %>" required/><br>
				
				<label class="fixedlabel"> Email Address: </label> 
				<input type="text" name="email" value="<%=email%>" required/><br>
				
				<label class="fixedlabel"> Password: </label> 
				<input type="password" name="password" value="<%=password %>" required/><br>
				
				
				<label> &nbsp; </label> <button type="submit" name="submit">Submit</button><br>
				<label> &nbsp; </label> <button type="reset" name="clear">Clear</button><br>
				
			</form>
		</div>
	
		<jsp:include page="./inc/footer.jsp">
			<jsp:param name="footerTitle" value="<%=pageTitle %>" />
		</jsp:include>
		
	</div>
	<%}
	else
	{%>
		response.sendRedirect("dashboard.jsp");
	<%}
	%>
</body>
</html>