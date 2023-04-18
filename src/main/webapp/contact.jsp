<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

	<jsp:include page="./inc/header.jsp">
		<jsp:param name="title" value="Company Contact Page" />
	</jsp:include>
		
		<div id="contentContainer">
			<h2>Contact Second Header</h2>
			<p>The current date and time is: <%= new java.util.Date() %></p>
			<p>Don't Call Us, We will Call You!</p>
		</div>
	
		<jsp:include page="./inc/footer.jsp">
			<jsp:param name="footerTitle" value="Company Contact Page" />
		</jsp:include>
		
	</div>

</body>
</html>