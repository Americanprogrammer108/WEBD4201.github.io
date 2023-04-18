package webd4201.chene;

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
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			//perform data validation
			if(username == "" || firstName == "" || lastName == "" || email == "" || password == "" || !email.contains("@"))
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
				
				if (firstName == "")
				{
					System.out.println("Enter a first name!");
				}
				else
				{
					try
					{
						//if the first name is numeric,
						id = Long.parseLong(firstName);
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
					id = Long.parseLong(username);
					User newUser = new User(id, password, firstName, lastName, email, new Date(), new Date(), true, 's');
					Client newClient = new Client(username, firstName, lastName, email, true);
					ClientRoles newClientRole = new ClientRoles(username, ClientRoles.DEF_ISUSER, ClientRoles.DEF_ISADMIN, ClientRoles.DEF_ISDEV);
					// debugging output only
					System.out.println("Username: " + newClient.getUserName());
					System.out.println("First Name: " + newClient.getFirstName());
					System.out.println("Last Name: " + newClient.getLastName());
					System.out.println("E-Mail: " + newClient.getEmail());
					System.out.println("IsActive: " + newClient.getIsActive());
					System.out.println("Username 2: " + newClientRole.getUserName());
					System.out.println("IsUser: " + newClientRole.getIsUser());
					System.out.println("IsAdmin: " + newClientRole.getIsAdmin());
					System.out.println("IsDev: " + newClientRole.getIsDev());
					System.out.println("Password: " + password);
					
					System.out.println("Starting Transaction!");
					System.out.println(DBL.CreateClientAndRoles(newClient, newClientRole, newUser));
					if (DBL.CreateClientAndRoles(newClient, newClientRole, newUser)) {
						System.out.println("Transaction Returned TRUE!");
						session.setAttribute("username", newClient.getUserName());
						session.setAttribute("firstName", newClient.getFirstName());
						session.setAttribute("lastName", newClient.getLastName());
						session.setAttribute("E-Mail", newClient.getEmail());
						session.setAttribute("isActive", newClient.getIsActive());
						session.setAttribute("isUser", newClientRole.getIsUser());
						session.setAttribute("isAdmin", newClientRole.getIsAdmin());
						session.setAttribute("isDev", newClientRole.getIsDev());
						
						session.setAttribute("msg", "Registration is going well so far!");
						System.out.println("SUCCESS");
						response.sendRedirect("./dashboard.jsp");
					} else {
						System.out.println("Transaction Returned FALSE!");
						session.setAttribute("msg", "Error: Registration Failed!");
						System.out.println("FAILURE");
						response.sendRedirect("./register.jsp");
					}
				}
				catch (Exception e) 
				{
					System.out.println("An error occurred: " + e);
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
