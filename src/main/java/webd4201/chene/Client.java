package webd4201.chene;

import java.sql.*;

public class Client {
	
	// Default Values
	public static final Boolean DEF_ISACTIVE = true;
	
	// SQL Statements
	private static Connection aConnection;
	private static Statement aStatement;
	
	private static final String sqlGetOne = "SELECT * FROM clients WHERE username = ?;";
	private static final String sqlInsertClient = "INSERT INTO clients VALUES (?,?,?,?,?);";
	private static final String sqlDeleteClient = "DELETE FROM clients WHERE username = ?;";
	private static final String sqlUpdateClient = "UPDATE clients SET firstName = ?, lastName = ?, email = ?, isActive = ? WHERE username = ?;";
	
	
	// properties
	private String Username;
	private String FirstName;
	private String LastName;
	private String Email;
	private Boolean IsActive;

	// setters
	public void setUsername(String username) { Username = username; }
	public void setFirstName(String firstName) { FirstName = firstName; }
	public void setLastName(String lastName) { LastName = lastName; }
	public void setEmail(String email) { Email = email; }
	public void setIsActive(Boolean isActive) { IsActive = isActive; }
	
	// getters 
	public String getUserName() { return Username; }
	public String getFirstName() { return FirstName; }
	public String getLastName() { return LastName; }
	public String getEmail() { return Email; }
	public Boolean getIsActive() { return IsActive; }
	
	// constructors
	public Client (String username, String firstName, String lastName, String email, Boolean isActive) {
		try {
			setUsername(username);
			setFirstName(firstName);
			setLastName(lastName);
			setEmail(email);
			setIsActive(isActive);
		}
		catch (Exception sqle) {
			System.out.println("Error: " + sqle);
		}
	}
	
	public Client (String username, String firstName, String lastName) {
		this(username, firstName, lastName, null, DEF_ISACTIVE);
	}
	
	// Database methods
	public static void initialize(Connection c)
	{
            try {
                aConnection=c;
                aStatement=aConnection.createStatement();
                ClientRoles.initialize(c);
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
                ClientRoles.terminate();
            }
            catch (SQLException e)
            { System.out.println(e);	}
	}
	
	
	// CRUD Methods
	// getOne (SELECT one)
	public static Client GetOne(String username) throws NotFoundException {
		Client retVal = null;
		
		try {
			PreparedStatement psGetOne = aConnection.prepareStatement(sqlGetOne);
			psGetOne.setString(1, username);
			ResultSet rs = psGetOne.executeQuery();
			
			boolean gotIt = rs.next();
			if (gotIt) {
				Client aClient = new Client(rs.getString("username"), rs.getString("firstName"), rs.getString("lastName"),
						rs.getString("email"),rs.getBoolean("isActive"));
				retVal = aClient;
			} else {
				throw (new NotFoundException("Client was not found!"));
			}
			rs.close();
		}
		catch (NotFoundException nfe) {
			throw (new NotFoundException("Client was not found!"));
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
	public static boolean Create(Client client) throws DuplicateException {
		Boolean retVal = false;
		
		try {
			GetOne(client.getUserName());
			throw (new DuplicateException("Client already exists!"));
		}
		catch (NotFoundException nfe) {
			try {
				System.out.println("Client does not exist, so good to create a new one!");
				
				PreparedStatement psInsertClient = aConnection.prepareStatement(sqlInsertClient);
				psInsertClient.setString(1, client.getUserName());
				psInsertClient.setString(2, client.getFirstName());
				psInsertClient.setString(3, client.getLastName());
				psInsertClient.setString(4, client.getEmail());
				psInsertClient.setBoolean(5, client.getIsActive());
				
				retVal = (psInsertClient.executeUpdate() == 1);
				
				System.out.println("Client Insert Result: " + retVal);
			}
			catch (SQLException sqle) {
				System.out.println("SQL Error: " + sqle);
			}
		}
		
		return retVal;
	}

	// DELETE
	public static int Delete(String username) {
		int records = 0;
		
		try {
			PreparedStatement psDeleteClient = aConnection.prepareStatement(sqlDeleteClient);
			psDeleteClient.setString(1, username);
			records = psDeleteClient.executeUpdate();
		}
		catch (SQLException sqle) {
			System.out.println("Error: " + sqle);
		}
		
		return records;
	}
	
	// UPDATE
	public static int Update(Client client) throws NotFoundException {
		int records = 0;
		
		try {
			GetOne(client.getUserName());
			PreparedStatement psUpdateClient = aConnection.prepareStatement(sqlUpdateClient);
			psUpdateClient.setString(1, client.getFirstName());
			psUpdateClient.setString(2, client.getLastName());
			psUpdateClient.setString(3, client.getEmail());
			psUpdateClient.setBoolean(4, client.getIsActive());
			psUpdateClient.setString(5, client.getUserName());
			
			records = psUpdateClient.executeUpdate();
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
