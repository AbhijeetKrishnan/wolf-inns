import java.sql.*;
import java.util.ArrayList;

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
			results = statement.executeQuery();
			
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
			results = statement.executeQuery();
			
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
	
	/**
	 * Assigns dedicated staff to Presidential Suite
	 * @param hotelId: the hotel ID where the Presidential Suite is located
	 * @param roomNumber: the room number of the Presidential Suite to which staff is to be assigned
	 * @param cateringStaffId: ID of available dedicated catering staff
	 * @param roomServiceStaffId: ID of available dedicated room service staff
	 * @return true on success, or false on failure
	 */
	public static boolean assignDedicatedPresidentialSuiteStaff(int hotelId, String roomNumber, int cateringStaffId, int roomServiceStaffId) {
		
		String sqlStatement = "INSERT INTO occupied_presidential_suite (hotelId, roomNumber, cateringStaffId, roomServiceStaffId) VALUES (?, ?, ?, ?)";
		Connection connection = null;
		PreparedStatement statement = null;
		boolean returnValue = false;
		
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, hotelId);
			statement.setString(2, roomNumber);
			statement.setInt(3, cateringStaffId);
			statement.setInt(4, roomServiceStaffId);
			
			int rowsAffected = statement.executeUpdate();
			
			// A single row should have been inserted
			if (rowsAffected == 1) {
				returnValue = true;
			}
		} catch (SQLException ex) {
			// Log
			ex.printStackTrace();
		} finally {
			// Attempt to close all resources, ignore failures.
			try { statement.close(); } catch (Exception ex) {};
			try { connection.close(); } catch (Exception ex) {};
		}
		
		return returnValue;
	}

	/**
	 * Retrieve all available rooms in a hotel from the database.
	 * @param hotelId the hotel's ID
	 * @param categoryCode the hotel room's category code
	 * @return An ArrayList containing Rooms objects for each of the returned records if successful, else null
	 */
	public static ArrayList<Rooms> retrieveAvailableRooms(int hotelId, String categoryCode) {
		String sqlStatement = "SELECT hotelId, roomNumber, maxAllowedOcc, rate, categoryCode FROM rooms WHERE hotelId=? AND categoryCode=? AND roomNumber NOT IN (SELECT roomNumber FROM stays WHERE hotelId=? AND checkoutDate IS NULL AND checkoutTime IS NULL);";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			ArrayList<Rooms> rooms = new ArrayList<Rooms>();
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, hotelId);
			statement.setString(2, categoryCode);
			statement.setInt(3, hotelId);
			results = statement.executeQuery();
			while (results.next()) {
				Rooms room = new Rooms();
				room.setHotelId(results.getInt("hotelId"));
				room.setRoomNumber(results.getString("roomNumber"));
				room.setMaxAllowedOcc(results.getInt("maxAllowedOcc"));
				room.setRate(results.getDouble("rate"));
				room.setCategoryCode(results.getString("categoryCode"));
				rooms.add(room);
			}
			System.out.println("A list of available rooms in hotelId = " + hotelId + " was retrieved successfully!");
			return rooms;
		} catch (SQLException ex) {
			// Log and return null
			ex.printStackTrace();
			return null;
		} finally {
			// Attempt to close all resources, ignore failures.
			if (results != null) { try { results.close(); } catch (Exception ex) {}; }
			if (statement != null) { try { statement.close(); } catch (Exception ex) {}; }
			if (connection != null) { try { connection.close(); } catch (Exception ex) {}; }
		}
	}

	public static ArrayList<ServiceStaff> retrieveAvailableRoomServiceStaff(int hotelId) {
		String sqlStatement = "SELECT staffId, hotelId FROM service_staff WHERE hotelId=? AND staffId IN (SELECT staffId FROM staff WHERE titleCode=?) AND staffId NOT IN (SELECT roomServiceStaffId FROM occupied_presidential_suite WHERE hotelId=?)";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			ArrayList<ServiceStaff> roomServiceStaff = new ArrayList<ServiceStaff>();
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, hotelId);
			statement.setString(2, "RSST");
			statement.setInt(3, hotelId);
			results = statement.executeQuery();
			while (results.next()) {
				ServiceStaff rss = new ServiceStaff();
				rss.setHotelId(results.getInt("hotelId"));
				rss.setStaffId(results.getInt("staffId"));
				roomServiceStaff.add(rss);
			}
			System.out.println("A list of available room service staff in hotelId = " + hotelId + " was retrieved successfully!");
			return roomServiceStaff;
		} catch (SQLException ex) {
			// Log and return null
			ex.printStackTrace();
			return null;
		} finally {
			// Attempt to close all resources, ignore failures.
			if (results != null) { try { results.close(); } catch (Exception ex) {}; }
			if (statement != null) { try { statement.close(); } catch (Exception ex) {}; }
			if (connection != null) { try { connection.close(); } catch (Exception ex) {}; }
		}
	}
}
