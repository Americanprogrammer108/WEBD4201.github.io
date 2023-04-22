package Chens_WEBD4201;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Chens_WEBD4201.User;
import javax.swing.JOptionPane;


/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//JavasScript didn't work, so instead, I implemented a pop-up window to confirm if the user wants to delete the user or not.
		int response1 = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + request.getParameter("id") + " ?", "Delete User", JOptionPane.YES_NO_OPTION);
		if (response1 == JOptionPane.YES_OPTION)
		{
			//if yes, send them to the delete page
			response.sendRedirect("./Delete.jsp");
		}
		else if (response1 == JOptionPane.NO_OPTION)
		{
			//no? go back to the admin's dashboard page
			response.sendRedirect("./adminsdashboard.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
