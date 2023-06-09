package Chens_WEBD4201;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession ses = request.getSession(true);
		String login = request.getParameter("username");
		String pw = request.getParameter("password");
		
		try
		{
			Connection c = DatabaseConnect.initialize();
//			StudentDA.initialize(c);
			User.initialize(c);
			long studentlogin = Long.parseLong(login);
//			Student defUser = Student.authenticate(studentlogin, pw);//reached here
			User defUser = User.authenticate(studentlogin, pw);
			System.out.print(defUser + "\n");
			if (!(defUser == null)) {
				System.out.println("Authentication successful!");
				ses.setAttribute("user", defUser);
				ses.setAttribute("errors", "");
				
				if(defUser.getType() == 'a')
				{
					response.sendRedirect("./adminsdashboard.jsp");
				}
				else
				{
					response.sendRedirect("./dashboard.jsp");
				}

			} else {
				System.out.println("Authentication FAILED!");
				ses.setAttribute("errors", "Authentication failed, try again!");
				response.sendRedirect("./login.jsp");
			}
		}
		catch(Exception e)
		{
			System.out.println("Something went wrong.");
			System.out.println(e);
			response.sendRedirect("./login.jsp");
			ses.setAttribute("errors", "This user is not found");
		}
		
		

		
		
		
	}

}
