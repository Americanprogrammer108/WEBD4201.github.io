package webd4201.chene;

import java.sql.*;

public class ClientRoles {
	
	// DEFAULT VALUES
	public static final Boolean DEF_ISUSER = false;
	public static final Boolean DEF_ISADMIN = false;
	public static final Boolean DEF_ISDEV = false;
	
	// DATABASE VALUES
	private static Connection aConnection;
	private static Statement aStatement;
	
	private static final String sqlGetOne = "SELECT * FROM ClientRoles WHERE username = ?;";
	private static final String sqlInsertClientRole = "INSERT INTO ClientRoles VALUES (?,?,?,?);";
	private static final String sqlDeleteClientRole = "DELETE FROM ClientRoles WHERE username = ?;";
	private static final String sqlUpdateClientRole = "UPDATE ClientRoles SET isUser = ?, isAdmin= ?, isDev = ?, WHERE username = ?;";
	
	// properties
	private String Username;
	private Boolean IsUser;
	private Boolean IsAdmin;
	private Boolean IsDev;
	
	// setters
	public void setUsername(String username) { Username = username; }
	public void setIsUser(Boolean isUser) { IsUser = isUser; }
	public void setIsAdmin(Boolean isAdmin) { IsAdmin = isAdmin; }
	public void setIsDev(Boolean isDev) { IsDev = isDev; }
	
	// getters 
	public String getUserName() { return Username; }
	public Boolean getIsUser() { return IsUser; }
	public Boolean getIsAdmin() { return IsAdmin; }
	public Boolean getIsDev() { return IsDev; }
	
	// Constructors
	ClientRoles(){}
	
	ClientRoles(String username) {
		this(username, DEF_ISUSER, DEF_ISADMIN, DEF_ISDEV);
	}
	
	ClientRoles(String username, Boolean isUser, Boolean isAdmin, Boolean isDev) {
		setUsername(username);
		setIsUser(isUser);
		setIsAdmin(isAdmin);
		setIsDev(isDev);
	}
	
	// Database methods
		public static void initialize(Connection c)
		{
	            try {
	                aConnection=c;
	                aStatement=aConnection.createStatement();
	            }
	            catch (SQLException e)
	            { System.out.println(e); }
		}

		// close the database connection
		public static void terminate()
		{
	            try
	            { 	// close the statement
	                aStatement.close();
	            }
	            catch (SQLException e)
	            { System.out.println(e);	}
		}
		
	// CRUD Methods
	
	// SELECTONE
	public static ClientRoles GetOne(String username) throws NotFoundException {
		
		ClientRoles retVal = null;
		
		try {
			PreparedStatement psGetOne = aConnection.prepareStatement(sqlGetOne);
			psGetOne.setString(1, username);
			ResultSet rs = psGetOne.executeQuery();
			
			Boolean gotIt = rs.next();
			if (gotIt) {
				ClientRoles aClientRole = new ClientRoles(rs.getString("username"), rs.getBoolean("isUser"), rs.getBoolean("isAdmin"), rs.getBoolean("isDev"));
				retVal = aClientRole;
			} else {
				throw (new NotFoundException("ClientRole was not found!"));
			}
			rs.close();
		}
		catch (NotFoundException nfe) {
			throw (new NotFoundException("ClientRole was not found!"));
		}
		catch (SQLException sqle) {
			System.out.println("SQL Error: " + sqle);
		}
		catch (Exception e) {
			System.out.println("General Error: " + e);
		}

		return retVal;
	}
	
	// INSERT
	public static boolean Create(ClientRoles clientRole) throws DuplicateException {
		Boolean retVal = false;
		try {
			GetOne(clientRole.getUserName());
			throw (new DuplicateException("ClientRole already exists!")); 
		}
		catch (NotFoundException nfe) {
			try {
				System.out.println("Client Role does not exist - good to create one!");
				PreparedStatement psInsertClientRole = aConnection.prepareStatement(sqlInsertClientRole);
				psInsertClientRole.setString(1, clientRole.getUserName());
				psInsertClientRole.setBoolean(2, clientRole.getIsUser());
				psInsertClientRole.setBoolean(3, clientRole.getIsAdmin());
				psInsertClientRole.setBoolean(4, clientRole.getIsDev());
				
				retVal = (psInsertClientRole.executeUpdate() == 1);
				System.out.println("Client Role result: " + retVal);
			}
			catch (SQLException sqle) {
				System.out.println("Error: " + sqle);
			}
		}
		return retVal;
	}
	
	// DELETE
	public static int Delete(String username) {
		int records = 0;
		
		try {
			PreparedStatement psDeleteClientRole = aConnection.prepareStatement(sqlDeleteClientRole);
			psDeleteClientRole.setString(1, username);
			records = psDeleteClientRole.executeUpdate();
		}
		catch (SQLException sqle) {
			System.out.println("Error: " + sqle);
		}
		
		return records;
	}
	
	// UPDATE
	public static int Update(ClientRoles clientRole) throws NotFoundException {
		int records = 0;
		
		try {
			GetOne(clientRole.getUserName());
			PreparedStatement psUpdateClientRole = aConnection.prepareStatement(sqlUpdateClientRole);
			psUpdateClientRole.setString(1, clientRole.getUserName());
			psUpdateClientRole.setBoolean(2, clientRole.getIsUser());
			psUpdateClientRole.setBoolean(3, clientRole.getIsAdmin());
			psUpdateClientRole.setBoolean(4, clientRole.getIsDev());
			
			records = psUpdateClientRole.executeUpdate();
		}
		catch (NotFoundException nfe) {
			throw new NotFoundException(nfe.getMessage());
		}
		catch (SQLException sqle) {
			System.out.println("Error: " + sqle);
		}
		
		return records;
	}
}
