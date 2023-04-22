package Chens_WEBD4201;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			HttpSession session = request.getSession(true);
			
			long id;
			
			// obtain values from the form
			String username = request.getParameter("username");
			String firstname = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String code = request.getParameter("code");
			String description = request.getParameter("description");
			String year = request.getParameter("year");
			
			//perform data validation
			if(username == "" || firstname == "" || lastName == "" || email == "" || password == "" || !email.contains("@"))
			{

				if(username == "")
				{
					System.out.print("Please type a username!");
					response.sendRedirect("./register.jsp");
				}
				else
				{
					// Try to create the student
					try
					{
						id = Long.parseLong(username);
					}
					catch(Exception e)
					{
						System.out.print(e);
						response.sendRedirect("./register.jsp");
					}
				}
				
				if (firstname == "")
				{
					System.out.println("Enter a first name!");
				}
				else
				{
					try
					{
						//if the first name is numeric,
						id = Long.parseLong(firstname);
						response.sendRedirect("./register.jsp");
					}
					catch(Exception e)
					{
						
					}
				}
				
				if(lastName == "")
				{
					System.out.println("Enter a last name!");
				}
				else
				{
					try
					{
						//if the first name is numeric,
						id = Long.parseLong(lastName);
						response.sendRedirect("./register.jsp");
					}
					catch(Exception e)
					{
						
					}
				}
				
				if (email == "")
				{
					System.out.println("Enter an email!");
					response.sendRedirect("./register.jsp");
				}
				else if (!email.contains("@"))
				{
					System.out.println("Please include the @ symbol!");
					response.sendRedirect("./register.jsp");
				}
				else
				{
					
				}
				
				if (password == "")
				{
					System.out.print("Enter a password!");
					response.sendRedirect("./register.jsp");
				}
				
			}
			else
			{
				try
				{
					username = request.getParameter("username");
					firstname = request.getParameter("firstName");
					lastName = request.getParameter("lastName");
					email = request.getParameter("email");
					password = request.getParameter("password");
					code = request.getParameter("code");
					description = request.getParameter("description");
					year = request.getParameter("year");
					
					long getid = Long.parseLong(username);
					int myyear = Integer.parseInt(year);
					// debugging output only
					System.out.println("Username: " + getid);
					System.out.println("First Name: " + firstname);
					System.out.println("Last Name: " + lastName);
					System.out.println("E-Mail: " + email);
					System.out.println("Password: " + password);
					System.out.println("Code: " + code);
					System.out.println("Descrption: " + description);
					System.out.println("Year: " + myyear);
					User newUser = new User(getid, password, firstname, lastName, email, new Date(), new Date(), true, 's');
					Student newStudent = new Student(getid, password, firstname, lastName, email, new Date(), new Date(), 's', true, code, description, myyear);
					
					
//					System.out.println("Starting Transaction!");
					if (DBL.CreateUserandStudent(newUser, newStudent)) {
						System.out.println("Transaction Returned TRUE!");
//						session.setAttribute("username", newUser.getid());
//						session.setAttribute("firstName", newUser.getFirstName());
//						session.setAttribute("lastName", newUser.getLastName());
//						session.setAttribute("emailaddress", newUser.getEmailAddress());
//						session.setAttribute("password", newUser.getpassword());
//						session.setAttribute("programcode", newStudent.getProgramCode());
//						session.setAttribute("description", newStudent.getProgramDescription());
//						session.setAttribute("year", newStudent.getYear());
						System.out.print("Registration successful");
						response.sendRedirect("./dashboard.jsp");
					} else {
						System.out.println("Transaction Returned FALSE!");
						session.setAttribute("errors", "Error: Registration Failed!");
						System.out.println("FAILURE");
						response.sendRedirect("./register.jsp");
					}
					
					StudentDA.create(newStudent);

				}
				catch (Exception e) 
				{
					System.out.println("An error occurred: " + e);
					response.sendRedirect("./register.jsp");
					session.setAttribute("errors", "Something went wrong");
				}
			}

			//ClientRoles newClientRole = new ClientRoles(username, ClientRoles.DEF_ISUSER, ClientRoles.DEF_ISADMIN, ClientRoles.DEF_ISDEV);	
	}
		catch(Exception e)
		{
			
		}
		
	}
	
	public void HashPassword(String password)
	{
		
	}
		// TODO Auto-generated method stub
		
		
		

}
