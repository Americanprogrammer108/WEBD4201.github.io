<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
String pageTitle = "User Authentication";
String errMsg = (String)session.getAttribute("errors");
String username = (String)session.getAttribute("username");

if(username == null)
{
	username = "";
}
if (errMsg == null) { errMsg = ""; }

%>
	<jsp:include page="./inc/header.jsp">
		<jsp:param name="title" value="<%=pageTitle %>" />
	</jsp:include>
	<div>
		<div id="contentContainer">
			<p style="color: red;"><%= errMsg %></p>
			<h2>Please Login Below</h2>
			<p>The current date and time is: <%= new java.util.Date() %></p>
			
			<form method="post" action="./Login">
                 <label class="fixedlabel">Username</label>
                 <input name="username" type="text" value="<%=username %>"><br/>
                 <label class="fixedlabel">Password</label>
                 <input name="password" type="password"><br/>
                 <label class="fixedlabel"></label>
                 <button type="submit">Login</button>
             </form>
             
		</div>
	
		<jsp:include page="./inc/footer.jsp">
			<jsp:param name="footerTitle" value="<%=pageTitle %>" />
		</jsp:include>
		
	</div>

</body>
</html>