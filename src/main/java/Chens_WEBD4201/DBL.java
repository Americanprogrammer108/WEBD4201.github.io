package Chens_WEBD4201;

import java.sql.*;

public class DBL {

	private static Connection aConnection;
	
	// TRANSACTION
	public static boolean CreateUserandStudent(User newUser, Student newStudent) throws DuplicateException, SQLException, InvalidIdException, InvalidNameException, InvalidPasswordException, InvalidUserDataException {
		boolean retVal = false;
		
		try {
			aConnection = DatabaseConnect.initialize();
			StudentDA.initialize(aConnection);
//			ClientRoles.initialize(aConnection);
//			Client.initialize(aConnection);
			
			aConnection.setAutoCommit(false);  // turns auto commit off to allow a transaction!
			
			if (StudentDA.create(newStudent)) {
				System.out.println("Student Create Yes!");
				if (StudentDA.create(newStudent)) {
					System.out.println("Student Role Create Yes!");
					retVal = true;
//					aConnection.commit();

				}
				else { 
					System.out.println("Student Role Create No!");
					retVal = false;
//					aConnection.rollback();
				}
			}
			else 
			{
				// client Roles could not be created, roll back the transaction
				System.out.println("Student Create No!");
				retVal = false;
//				aConnection.rollback();
			}
			StudentDA.terminate();
			
		}
		catch (SQLException sqle) {
			System.out.println("Error: Something went wrong!");
//			aConnection.rollback();
		}
		catch (DuplicateException de) {
			System.out.println("Error: That student username is already used!");
//			aConnection.rollback();
		}

		aConnection.setAutoCommit(true);
		return retVal;
	}
}
