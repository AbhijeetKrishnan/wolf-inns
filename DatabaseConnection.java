import java.sql.*;

public class DatabaseConnection {

	private static final String user = "xzheng6";
	private static final String password = "200016878";
	private static final String connectionURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/" + user;	
	
	/**
	 * This method will create and return a new Connection object that is attached to 
	 * the database WolfInns database.
	 * @throws RuntimeException On an inability to form a connection to the database.
	 * @return Connection This will be connected to the WolfInns database upon return.
	 */
	public static Connection getConnection()  {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			return DriverManager.getConnection(connectionURL, user, password);
		} catch (SQLException | ClassNotFoundException ex) {
			// Throw a RuntimeException to go back to main to be caught so the program exits. We cannot continue.
			throw new RuntimeException("ERROR: Cannot connection to the database.\n" + ex.getMessage());
		}
	}
}
