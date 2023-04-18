package webd4201.chene;

import java.sql.*;

public class DBL {

	private static Connection aConnection;
	
	// TRANSACTION
	public static boolean CreateClientAndRoles(Client newClient, ClientRoles clientRole, User newUser) throws DuplicateException, SQLException {
		boolean retVal = false;
		
		try {
			aConnection = DatabaseConnect.initialize();
			StudentDA.initialize(aConnection);
			ClientRoles.initialize(aConnection);
			Client.initialize(aConnection);
			
			aConnection.setAutoCommit(false);  // turns auto commit off to allow a transaction!
			
			if (ClientRoles.Create(clientRole)) {
				System.out.println("Student Create Yes!");
				if (ClientRoles.Create(clientRole)) {
					System.out.println("Student Role Create Yes!");
					retVal = true;
					aConnection.commit();

				}
				else { 
					System.out.println("Student Role Create No!");
					retVal = false;
					aConnection.rollback();
				}
			}
			else 
			{
				// client Roles could not be created, roll back the transaction
				System.out.println("Student Create No!");
				retVal = false;
				aConnection.rollback();
			}
			
		}
		catch (SQLException sqle) {
			System.out.println("Error: Something went wrong!");
			aConnection.rollback();
		}
		catch (DuplicateException de) {
			System.out.println("Error: That student username is already used!");
			aConnection.rollback();
		}

		aConnection.setAutoCommit(true);
		return retVal;
	}
}
