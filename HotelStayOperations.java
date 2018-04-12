import java.sql.*;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;  

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
    public static boolean unassignDedicatedPresidentialSuiteStaff(int hotelId, String roomNumber, Connection connection) {
		
		String sqlStatement = "DELETE FROM occupied_presidential_suite WHERE hotelId = ? AND roomNumber = ?";
		PreparedStatement statement = null;
		
		try {
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
	public static boolean assignDedicatedPresidentialSuiteStaff(int hotelId, String roomNumber, int cateringStaffId, int roomServiceStaffId, Connection connection) {
		
		String sqlStatement = "INSERT INTO occupied_presidential_suite (hotelId, roomNumber, cateringStaffId, roomServiceStaffId) VALUES (?, ?, ?, ?)";
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, hotelId);
			statement.setString(2, roomNumber);
			statement.setInt(3, cateringStaffId);
			statement.setInt(4, roomServiceStaffId);
			
			int rowsAffected = statement.executeUpdate();
			
			// A single row should have been inserted
			if (rowsAffected == 1) {
				return true;
			} else {
				throw new SQLException("Error seems to have occured. Check the logs.");
			}
		} catch (SQLException ex) {
			// Log
			ex.printStackTrace();
			return false;
		} finally {
			// Attempt to close all resources, ignore failures.
			try { statement.close(); } catch (Exception ex) {};
		}
	}

	/**
	 * Retrieve all available rooms in a hotel from the database.
	 * @param hotelId the hotel's ID
	 * @param categoryCode the hotel room's category code
	 * @return An ArrayList containing Rooms objects for each of the returned records if successful, else null
	 */
	public static ArrayList<Rooms> retrieveAvailableRooms(int hotelId, String categoryCode) {
		String sqlStatement = "SELECT hotelId, roomNumber, maxAllowedOcc, rate, categoryCode, available FROM rooms WHERE hotelId=? AND categoryCode=? AND available=? AND roomNumber NOT IN (SELECT roomNumber FROM stays WHERE hotelId=? AND ((checkoutDate IS NULL AND checkoutTime IS NULL) OR (checkoutDate IS NOT NULL AND checkoutDate <> CURDATE())));";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			ArrayList<Rooms> rooms = new ArrayList<Rooms>();
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, hotelId);
			statement.setString(2, categoryCode);
			statement.setString(3, "Y");
			statement.setInt(4, hotelId);
			results = statement.executeQuery();
			while (results.next()) {
				Rooms room = new Rooms();
				room.setHotelId(results.getInt("hotelId"));
				room.setRoomNumber(results.getString("roomNumber"));
				room.setMaxAllowedOcc(results.getInt("maxAllowedOcc"));
				room.setRate(results.getDouble("rate"));
				room.setCategoryCode(results.getString("categoryCode"));
				room.setAvailable(results.getString("available"));
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
  
  /**
   * Retrieve list of catering staff in a hotel that can be assigned to a Presidential Suite in that hotel
   * @param hotelId: the hotel in which we check the catering staff availability
   * @return list of catering staff not assigned to a Presidential Suite in that hotel
   */
  public static ArrayList<ServiceStaff> retrieveAvailableCateringStaff(int hotelId) {
		String sqlStatement = "SELECT staffId, hotelId FROM service_staff WHERE hotelId=? AND " //why do we need to retrieve hotelId here?
                          + "staffId IN (SELECT staffId FROM staff WHERE titleCode=?) AND "
                          + "staffId NOT IN (SELECT cateringStaffId FROM occupied_presidential_suite WHERE hotelId=?)";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
        ArrayList<ServiceStaff> cateringStaffList = null;
    
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, hotelId);
			statement.setString(2, "CATS");
			statement.setInt(3, hotelId);
			results = statement.executeQuery();
			
            cateringStaffList = new ArrayList<ServiceStaff>();
			while (results.next()) {
				ServiceStaff cats = new ServiceStaff();
				cats.setHotelId(results.getInt("hotelId"));
				cats.setStaffId(results.getInt("staffId"));
				cateringStaffList.add(cats);
			}
			
			return cateringStaffList;
			
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
  
  
    public static boolean updateStayCheckoutDateTime(int stayId, Connection connection) {
		
		String sqlStatement = "UPDATE stays SET checkoutDate=?, checkoutTime=? WHERE stayId=?";
		PreparedStatement statement = null;
		  
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
		String checkoutDate = dateFormat.format(now);
		String checkoutTime = timeFormat.format(now);
		
		//System.out.println("Updating date to: " + dateFormat.format(now));
		//System.out.println("Updating time to: " + timeFormat.format(now));
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setString(1, checkoutDate);
			statement.setString(2, checkoutTime);
			statement.setInt(3, stayId);
			int rowsAffected = statement.executeUpdate();
			
			// A single row should have been updated
			if (1==rowsAffected) {
				return true;
			} else {
				throw new SQLException("More than one row was updated while updating stays with stayId = " + stayId + ".");
			}
			
		} catch (SQLException ex) {
			// Log and return false
			return false;
		} finally {
			// Attempt to close all resources, ignore failures.
			try { statement.close(); } catch (Exception ex) {};
		}
	}
    
    public static boolean checkinStay(int hotelId, String roomNumber, int customerId, int numOfGuests, int billingId, int servicingStaffId) {
		
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			
			connection = DatabaseConnection.getConnection();
			connection.setAutoCommit(false);			
			Savepoint save1 = connection.setSavepoint();
			
			// Create the stays record
			Stays stay = InformationProcessing.createStay(hotelId, roomNumber, customerId, numOfGuests, billingId, connection);
			if (null == stay) {throw new SQLException("Error seems to have occured. Check the logs.");}
			
			// Retrieve room information to determine if it is a presidential suite
			Rooms room = InformationProcessing.retrieveRoom(hotelId, roomNumber);
			if (null == room) {throw new SQLException("Error seems to have occured. Check the logs.");}
			
			// Check for presidential suite
			if (room.getCategoryCode() == "PRES") {
				// Retrieve available catering staff
				ArrayList<ServiceStaff> cateringStaff = retrieveAvailableCateringStaff(hotelId);
				if (null == cateringStaff && cateringStaff.size() > 0) {throw new SQLException("Error seems to have occured. Check the logs.");}
				
				// Retrieve available room service staff
				ArrayList<ServiceStaff> roomServiceStaff = retrieveAvailableRoomServiceStaff(hotelId);
				if (null == roomServiceStaff && roomServiceStaff.size() > 0) {throw new SQLException("Error seems to have occured. Check the logs.");}
				
				int cateringStaffId = cateringStaff.get(0).getStaffId();
				int roomServiceStaffId = roomServiceStaff.get(0).getStaffId();
				
				// Assign dedicated staff to presidential suite
				if (!assignDedicatedPresidentialSuiteStaff(hotelId, roomNumber, cateringStaffId, roomServiceStaffId, connection)) {
					throw new SQLException("Error seems to have occured. Check the logs.");
				}
			}
			
			// Add a checkin service record
			ServiceRecords serviceRecord = MaintainingServiceRecords.createCheckinCheckoutRecord(stay.getStayId(), servicingStaffId, true, connection);	
			if (null == serviceRecord) {throw new SQLException("Error seems to have occured. Check the logs.");}
			
			connection.commit();
			
			return true;
			
		} catch (SQLException ex) {
			try { connection.rollback(); } catch (Exception e) {};
			return false;
		} finally {
			// Attempt to close all resources, ignore failures.
			try { connection.setAutoCommit(true); } catch (Exception ex) {};
			try { connection.close(); } catch (Exception ex) {};
		}
	}
    
    public static boolean checkoutStay(Stays stay, int servicingStaffId) {
		
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {			
			connection = DatabaseConnection.getConnection();
			connection.setAutoCommit(false);			
			Savepoint save1 = connection.setSavepoint();			
			
			if(!HotelStayOperations.updateStayCheckoutDateTime(stay.getStayId(), connection)) {
				throw new SQLException("Error seems to have occured. Check the logs.");
			}
			
			// Retrieve room information to determine if it is a presidential suite
			Rooms room = InformationProcessing.retrieveRoom(stay.getHotelId(), stay.getRoomNumber());			
			if (null == room) {throw new SQLException("Error seems to have occured. Check the logs.");}
			
			// Check for presidential suite
			if (room.getCategoryCode() == "PRES") {
				// Unassign dedicated staff for presidential suite
				if (!unassignDedicatedPresidentialSuiteStaff(stay.getHotelId(), stay.getRoomNumber(), connection)) {
					throw new SQLException("Error seems to have occured. Check the logs.");
				}
			}
			
			double totalCharges = MaintainBillingRecords.generateBillingTotal(stay.getStayId());
			if (totalCharges < 0) {throw new SQLException("Error seems to have occured. Check the logs.");}
			
			if (!MaintainBillingRecords.updateBillingInfoTotalCharges(stay.getBillingId(), totalCharges, connection)) {
				throw new SQLException("Error seems to have occured. Check the logs.");
			}
			
			// Add checkout service record
			ServiceRecords serviceRecord = MaintainingServiceRecords.createCheckinCheckoutRecord(stay.getStayId(), servicingStaffId, false, connection);	
			if (null == serviceRecord) {throw new SQLException("Error seems to have occured. Check the logs.");}
			
			connection.commit();			
			
			return true;
			
		} catch (SQLException ex) {
			try { connection.rollback(); } catch (Exception e) {};
			return false;
		} finally {
			// Attempt to close all resources, ignore failures.
			try { connection.setAutoCommit(true); } catch (Exception ex) {};
			try { connection.close(); } catch (Exception ex) {};
		}
	}
}
