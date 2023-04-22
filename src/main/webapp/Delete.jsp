<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ page import = "Chens_WEBD4201.*" %>
    <%@ page import = "java.util.Vector" %>
    <%@ page import = "java.sql.*" %>
    <%@ page import="java.io.PrintStream" %>
    <%@ page import = "javax.swing.JOptionPane" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
Connection c = DatabaseConnect.initialize();
User.initialize(c);
//The UserDA CRUD methods didn't work properly, so I had to merge them to the User class

long id = Long.parseLong(request.getParameter("id"));
%>

<%
//First delete the user, but we need to confirm if the admin wants to delete the user.
int response1 = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + request.getParameter("id") + " ?", "Delete User", JOptionPane.YES_NO_OPTION);
if (response1 == JOptionPane.YES_OPTION)
{
	HttpSession ses = request.getSession(true);
	User deleteUser = User.retrieve(id);
	//then delete
	//The code is working to delete both the user/student or user/faculty as it references the foreign key
	if (deleteUser.getType() == 's')
	{
		StudentDA.initialize(c);
		StudentDA.delete(id);
	}
	else if(deleteUser.getType() == 'f')
	{
		FacultyDA.initialize(c);
		FacultyDA.delete(id);
	}

	boolean isdeleted = User.delete(id);
	ses.setAttribute("userinfo", deleteUser.getid() + " is deleted successfully");
	response.sendRedirect("./adminsdashboard.jsp");
}
else if (response1 == JOptionPane.NO_OPTION)
{
	response.sendRedirect("./adminsdashboard.jsp");
}



//after deletion, redirect back to the adminsdashboard.jsp page
%>
<p> <%= request.getParameter("id") %> has been deleted. </p>


<%




%>

</body>
</html>