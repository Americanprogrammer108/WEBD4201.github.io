package Chens_WEBD4201;

import java.io.IOException;
import java.sql.Connection;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.startup.UserDatabase;


/**
 * Servlet implementation class Edit
 */
@WebServlet("/Edit")
public class Edit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Edit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		User editUser = null;
//		try {
//			editUser = new User();
//		} catch (InvalidUserDataException | InvalidNameException | InvalidIdException | InvalidPasswordException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		String stringid = request.getParameter("id");
//		long id = Long.parseLong(stringid);
//		  if(id>0) {
//		    User bean = null;
//		    try {
//				bean = editUser.retrieve(id);
//			} catch (NotFoundException | InvalidUserDataException | InvalidNameException | InvalidIdException
//					| InvalidPasswordException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		  }
//		  
//		  
//		  request.getRequestDispatcher("/edit.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession ses = request.getSession(true);
		
		String updatedID = request.getParameter("username");
		String updatedPassword = request.getParameter("password");
		String updatedFirstName = request.getParameter("firstname");
		String updatedLastName = request.getParameter("lastname");
		String updatedEmail = request.getParameter("email");
		String updatedEnabled = request.getParameter("enabled");
		String updatedType = request.getParameter("type");
		System.out.println(updatedID);
		System.out.println(updatedPassword);
		System.out.println(updatedFirstName);
		System.out.println(updatedLastName);
		System.out.println(updatedEmail);
		System.out.println(updatedEnabled);
		System.out.println(updatedType);
		try
		{
			
			Connection c = DatabaseConnect.initialize();
			User.initialize(c);
			long parseid = Long.parseLong(updatedID);
			char parsetype = updatedType.charAt(0);
			boolean parseenabled = Boolean.parseBoolean(updatedEnabled);
			System.out.println(updatedPassword);
			User updatedUser = new User(parseid, updatedPassword, updatedFirstName, updatedLastName, updatedEmail, new java.sql.Date(10), new java.sql.Date(10), parseenabled, parsetype);
			System.out.println(updatedUser.getid());
			boolean aUser = User.UPDATE(updatedUser);
			
			response.sendRedirect("./adminsdashboard.jsp");
			ses.setAttribute("userinfo", updatedUser.getid() + " is updated successfully");
		}
		catch(Exception e)
		{
			System.out.print(e);
		}

		
	}

}
