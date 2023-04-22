package Chens_WEBD4201;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;

public class client {

	// static constants
	private static final String sqlGetOne = "SELECT * FROM clients WHERE clientID = ?;";
	private static final String sqlGetAll = "SELECT * FROM clients ORDER BY clientID;";
	private static final String sqlInsertOne = "INSERT INTO clients VALUES(?, ?, ?, ?, ?, ?, ?);";
	private static final String sqlUpdateOne = "UPDATE clients SET firstName=?, lastName=?, email=?, phone=?, clientSince=?,"
			+ "lastLogin=? WHERE clientID = ?";
	private static final String sqlDeleteOne = "DELETE FROM clients WHERE clientID=?;";
	
	private static Connection aConnection;
	private static Statement aStatement;
	private static client aClient;
	
	// Properties
	private int clientID;
	private String firstName;
	private String lastName;
	private String email;
	private long phone;
	private Date clientSince;
	private Date lastLogin;
	
	// Getters
	public int GetClientID() { return this.clientID; }
	public String GetFirstName() { return this.firstName; }
	public String GetLastName() { return this.lastName; }
	public String GetEmail() { return this.email; }
	public long GetPhone() { return this.phone; }
	public Date GetClientSince() { return this.clientSince; }
	public Date GetLastLogin() { return this.lastLogin; }
	
	// Setters
	public void SetClientID(int clientID) { this.clientID = clientID; }
	public void SetFirstName(String firstName) { this.firstName = firstName; }
	public void SetLastName(String lastName) { this.lastName = lastName; }
	public void SetEmail(String email) { this.email = email; }
	public void SetPhone(long phone) { this.phone = phone; }
	public void SetClientSince(Date clientSince) { this.clientSince = clientSince; }
	public void SetLastLogin(Date lastLogin) { this.lastLogin = lastLogin; }
	
	// Constructors
	public client() {};
	
	public client(int clientID, String firstName, String lastName, String email, long phone, Date clientSince, Date lastLofin) {
		SetClientID(clientID);
		SetFirstName(firstName);
		SetLastName(lastName);
		SetEmail(email);
		SetPhone(phone);
		SetClientSince(clientSince);
		SetLastLogin(lastLogin);
	}
	
	public client(int clientID) {
		try {
			client aClient = retrieve(clientID);
			SetClientID(aClient.clientID);
			SetFirstName(aClient.firstName);
			SetLastName(aClient.lastName);
			SetEmail(aClient.email);
			SetPhone(aClient.phone);
			SetClientSince(aClient.clientSince);
			SetLastLogin(aClient.lastLogin);
		} catch (NotFoundException nfe) {}
	}
	

	// establish the database connection
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

		// retrieve
		public static client retrieve(int clientID) throws NotFoundException {
			aClient = new client();
			
			try {
				//private static final String sqlGetOne = "SELECT * FROM clients WHERE clientID = ?;";
				PreparedStatement ps = aConnection.prepareStatement(sqlGetOne);
				ps.setInt(1, clientID);
				ResultSet rs = ps.executeQuery();
				
				boolean firstRecord = rs.next();
				if (firstRecord) {
					aClient.clientID = rs.getInt("clientID");
					aClient.firstName = rs.getString("firstName");
					aClient.lastName = rs.getString("lastName");
					aClient.email = rs.getString("email");
					aClient.phone = rs.getLong("phone");
					aClient.clientSince = rs.getDate("clientSince");
					aClient.lastLogin = rs.getDate("lastLogin");
				} else {
					throw (new NotFoundException("Client was not found!"));
				}
				rs.close();
			} 
			catch (SQLException sqle) { System.out.println(sqle); }
			
			return aClient;
		}
		
		// retrieve all
		public static Vector<client> retrieveAll() {
			Vector<client> clients = new Vector<client>();
			
			try {
				PreparedStatement ps = aConnection.prepareStatement(sqlGetAll);
				ResultSet rs = ps.executeQuery();
				boolean isMore = rs.next();
				while (isMore) {
					client c = new client();
					c.clientID = rs.getInt("clientID");
					c.firstName = rs.getString("firstName");
					c.lastName = rs.getString("lastName");
					c.email = rs.getString("email");
					c.phone = rs.getLong("phone");
					c.clientSince = rs.getDate("clientSince");
					c.lastLogin = rs.getDate("lastLogin");
					clients.addElement(c);
					isMore = rs.next();
				}
			}
			catch (SQLException sqle) {}
			
			return clients;
		}
		// Insert
		public static boolean insert(client client) throws DuplicateException {
			boolean retVal = false;
			
			try {
				retrieve(client.clientID);
				throw (new DuplicateException("Client already exists!"));
			} catch (NotFoundException nfe) { 
				try {
					//sqlInsertOne = "INSERT INTO clients VALUES(?, ?, ?, ?, ?, ?, ?);";
					PreparedStatement ps = aConnection.prepareStatement(sqlInsertOne);
					ps.setInt(1, client.clientID);
					ps.setString(2, client.firstName);
					ps.setString(3, client.lastName);
					ps.setString(4, client.email);
					ps.setLong(5,  client.phone);
					ps.setDate(6, new java.sql.Date(client.clientSince.getDate()));
					ps.setDate(7, new java.sql.Date(client.lastLogin.getDate()));
					
					retVal = ps.execute();
				} catch (SQLException sqle) {}
				
			}
			
			return retVal;
		}
		
		// Delete
		public static boolean delete(int clientID) {
			int retVal = 0;
		
			try {
				retrieve(clientID);
				PreparedStatement ps = aConnection.prepareStatement(sqlDeleteOne);
				ps.setInt(1, clientID);
				retVal = ps.executeUpdate();
			} 
			catch (NotFoundException nfe) {}
			catch (SQLException sqle) {}
			
			return retVal == 1;
		}
		
		// Update
		public static boolean update(client client) {
			int retVal = 0; 
			
			try {
				retrieve(client.clientID);
				PreparedStatement ps = aConnection.prepareStatement(sqlUpdateOne);
				ps.setString(1, client.firstName);
				ps.setString(2, client.lastName);
				ps.setString(3, client.email);
				ps.setLong(4, client.phone);
				ps.setDate(5, new java.sql.Date(client.clientSince.getDate()));
				ps.setDate(6, new java.sql.Date(client.lastLogin.getDate()));
				ps.setInt(7,  client.clientID);
				retVal = ps.executeUpdate();
			}
			catch (NotFoundException nfe) {}
			catch (SQLException nfe) {}
			
			return retVal == 1;
		}
		
		
		
		
}
