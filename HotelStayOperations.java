import java.sql.*;

public class HotelStayOperations {

	/**
	 * This method retrieve the staffId of the catering staff member who is dedicated to a particular presidential suite.
	 * @param hotelId The ID of the hotel the presidential suite is associated with.
	 * @param roomNumber The room number of the presidential suite.
	 * @return -1 if no dedicated staff was found, else the staffId of the dedicated staff member.
	 */
	public static int retrieveDedicatedCateringStaffForSuite(int hotelId, String roomNumber) {
		
		int cateringStaffId = -1;
		String sqlStatement = "SELECT cateringStaffId FROM occupied_presidential_suite WHERE hotelId = ? AND roomNumber = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;

		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1,  hotelId);
			statement.setString(2,  roomNumber);
			results = statement.executeQuery(sqlStatement);
			
			results.last();
			int rowsAffected = results.getRow();
			
			// A single row should have been selected
			if (1==rowsAffected) {
				results.first();
				
				cateringStaffId = results.getInt("cateringStaffId");
			} else {
				// Throw exception
				throw new SQLException("Multiple rows returned when selecting staff for presidential suite with with hotelId = " + hotelId + " and roomNumber = " + roomNumber + ".");
			}
			
			return cateringStaffId;
				
		} catch (SQLException ex) {
			// Log and return -1
			ex.printStackTrace();
			
			return cateringStaffId;
		} finally {
			// Attempt to close all resources, ignore failures.
			try { results.close(); } catch (Exception ex) {};
			try { statement.close(); } catch (Exception ex) {};
			try { connection.close(); } catch (Exception ex) {};
		}
	}
	
	
	/**
	 * This method retrieve the staffId of the catering staff member who is dedicated to a particular presidential suite.
	 * @param hotelId The ID of the hotel the presidential suite is associated with.
	 * @param roomNumber The room number of the presidential suite.
	 * @return -1 if no dedicated staff was found, else the staffId of the dedicated staff member.
	 */
	public static int retrieveDedicateRoomServiceStaffForSuite(int hotelId, String roomNumber) {
		
		int roomServiceStaffId = -1;
		String sqlStatement = "SELECT roomServiceStaffId FROM occupied_presidential_suite WHERE hotelId = ? AND roomNumber = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;

		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1,  hotelId);
			statement.setString(2,  roomNumber);
			results = statement.executeQuery(sqlStatement);
			
			results.last();
			int rowsAffected = results.getRow();
			
			// A single row should have been selected
			if (1==rowsAffected) {
				results.first();
				
				roomServiceStaffId = results.getInt("cateringStaffId");
			} else {
				// Throw exception
				throw new SQLException("Multiple rows returned when selecting staff for presidential suite with with hotelId = " + hotelId + " and roomNumber = " + roomNumber + ".");
			}
			
			return roomServiceStaffId;
				
		} catch (SQLException ex) {
			// Log and return -1
			ex.printStackTrace();
			
			return roomServiceStaffId;
		} finally {
			// Attempt to close all resources, ignore failures.
			try { results.close(); } catch (Exception ex) {};
			try { statement.close(); } catch (Exception ex) {};
			try { connection.close(); } catch (Exception ex) {};
		}
	}
	
	/**
	 * This method will remove the record associated the dedicated staff to a presidential suite.
	 * @param hotelId The ID of the hotel the presidential suite is associated with.
	 * @param roomNumber The room number of the presidential suite.
	 * @return A value representing a success (true) or failure (false) of the delete.
	 */
    public static boolean unassignDedicatedPresidentialSuiteStaff(int hotelId, String roomNumber) {
		
		String sqlStatement = "DELETE FROM occupied_presidential_suite WHERE hotelId = ? AND roomNumber = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1,  hotelId);
			statement.setString(2,  roomNumber);
			int rowsAffected = statement.executeUpdate();
			
			// A single row should have been deleted
			if (1==rowsAffected) {
				return true;
			} else {
				throw new SQLException("More than one row was affected by the delete of the occupied presidential suite with hotelId = " + hotelId + " and room number" + roomNumber + ".");
			}
		} catch (SQLException ex) {
			// Log and return false
			ex.printStackTrace();
			
			return false;
		} finally {
			// Attempt to close all resources, ignore failures.
			try { statement.close(); } catch (Exception ex) {};
			try { connection.close(); } catch (Exception ex) {};
		}
	}
}
