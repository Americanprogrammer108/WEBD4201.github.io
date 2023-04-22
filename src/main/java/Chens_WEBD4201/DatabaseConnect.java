package Chens_WEBD4201;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class DatabaseConnect {
		// static connection variables
		static final String URL = "jdbc:postgresql://127.0.0.1:5432/webd4201_db";
		static final String user = "webd4201_admin";
		static final String password = "webd4201_password";
		static Connection aConnection;
		
		/**
		 * Initialize connection by connecting to the database.
		 * Terminate to end the connection.
		 * @return
		 */
		public static Connection initialize()
		{
			try
			{
				//load the JDBC driver for postgreSQL
				Class.forName("org.postgresql.Driver");
				//create the connection instance
				aConnection = DriverManager.getConnection(URL, user, password);
			}
			catch (ClassNotFoundException e)
			{
				System.out.println(e);
			}
			catch (SQLException sqle)
			{
				System.out.println(sqle);
			}
			return aConnection;
		}
		
		public static void terminate()
		{
			try
			{
				aConnection.close();
			}
			catch (SQLException sqle)
			{
				System.out.println(sqle);
			}
		}
}
