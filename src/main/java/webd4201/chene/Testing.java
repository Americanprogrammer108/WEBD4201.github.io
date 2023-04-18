package webd4201.chene;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class Testing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			createuser();
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPasswordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidUserDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void createuser() throws InvalidIdException, InvalidNameException, InvalidPasswordException, InvalidUserDataException
	{
		User user1 = new User(120393891, "4g3BUJ#8n1", "Tyler", "West", "t123@gmail.com", new Date(), new Date(), true, 's');
		try {
			Connection c = DatabaseConnect.initialize();
			try {
				StudentDA.initialize(c);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			StudentDA.retrieve(120393891);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print("User is retrieved or not");
	}

}
