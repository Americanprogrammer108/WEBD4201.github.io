<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ page import = "java.util.Vector" %>
    <%@ page import = "java.sql.*" %>
    <%@ page import="java.io.PrintStream" %>
        <%@ page import = "Chens_WEBD4201.*" %>
    <%@ page import = "java.util.Vector" %>
    <%@ page import = "java.sql.*" %>
    <%@ page import="java.io.PrintStream" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<% String pageTitle = "Edit User";%>
<title>Edit User</title>
</head>
<%
Connection c = DatabaseConnect.initialize();
User.initialize(c);
long id = Long.parseLong(request.getParameter("id"));
User edituser = User.retrieve(id);
%>
<body>
	<jsp:include page="./inc/header.jsp">
		<jsp:param name="title" value="<%=pageTitle %>" />
	</jsp:include>
	

	<div>
		<div id="contentContainer">
			<%
	
			%>
			<form method="post" action="./Edit">
                 <label class="fixedlabel"></label>
                 <input name="username" type="text" value="<%=request.getParameter("id") %>" hidden><br/>
				 <label class="fixedlabel"></label>
                 <input name="password" type="text" value="<%=edituser.getpassword() %>" hidden><br/>
                 <label class="fixedlabel">First Name</label>
                 <input name="firstname" type="text" value="<%=edituser.getFirstName()%>" ><br/>
                 <label class="fixedlabel">Last Name</label>
                 <input name="lastname" type="text" value="<%=edituser.getLastName()%>" ><br/>
                 <label class="fixedlabel">Email Address</label>
                 <input name="email" type="email" value="<%=edituser.getEmailAddress()%>" ><br/>
				 <label class="fixedlabel"></label>
                 <input name="email" type="text" value="<%=edituser.getEnrollDate()%>" hidden><br/>
				 <label class="fixedlabel"></label>
                 <input name="email" type="text" value="<%=edituser.getLastAccess()%>" hidden><br/>
                 <label class="fixedlabel">Enabled</label>  
                 <% //DEPENDING ON THE ENABLED, SHOW IF TRUE OR FALSE IS CHECKED
                 	if (edituser.getEnabled() == true)
                 	{%>
                 		<input name="enabled" type="radio" value='true' checked><label>true</label>
                 		<input name="enabled" type="radio" value='false'><label>false</label> <br>
                 <%	}
                 	else
                 	{%>
                 		<input name="enabled" type="radio" value='true' checked><label>true</label>
                 		<input name="enabled" type="radio" value='false' checked><label>false</label> <br>
                 <%	}
                 %>               
                 
                 
			     <label class="fixedlabel"></label>
			     <!-- we will need to determine the type of user; however, it remains hidden to retain its value -->
			     <% if (edituser.getType() == 's')
			    	 {%>
			    	 	<input name="type" type="radio" value='s' checked hidden><label></label>
			      <% }
			      else
			      {%>
			      		<input name="type" type="radio" value='f' checked hidden><label></label> <br>
			      <%} %> 
                 
                 <!-- Update the user -->
                 <button type="submit">Submit</button>
             </form>
             
		</div>
	
		<jsp:include page="./inc/footer.jsp">
			<jsp:param name="footerTitle" value="<%=pageTitle %>" />
		</jsp:include>
		
	</div>

</body>
</html>