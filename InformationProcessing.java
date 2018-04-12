import java.sql.*;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;  

public class InformationProcessing {

	/**
	 * Create a new room category in the database.
	 * @param categoryCode Four character code used as the primary key for this record
	 * @param categoryDesc A description of this room category.
	 * @return RoomCategories An object representing the newly created room category. Will return null if no room category is created.
	 */
	public static RoomCategories createRoomCategory(String categoryCode, String categoryDesc) {
		
		String sqlStatement = "INSERT INTO room_categories(categoryCode, categoryDesc) VALUES(?, ?)";
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setString(1,  categoryCode);
			statement.setString(2, categoryDesc);
			int rowsAffected = statement.executeUpdate();
			
			// A single row should have been inserted
			if (1==rowsAffected) {
				RoomCategories roomCategory = new RoomCategories();
				roomCategory.setCategoryCode(categoryCode);
				roomCategory.setCategoryDesc(categoryDesc);
				return roomCategory;
			}  else {
				throw new SQLException("More than one row was affected while inserting into room_categories with categoryCode = " + categoryCode + ".");
			} 
			
		} catch (SQLException ex) {
			// Log and return null
			return null;
		} finally {
			// Attempt to close all resources, ignore failures.
			try { statement.close(); } catch (Exception ex) {};
			try { connection.close(); } catch (Exception ex) {};
		}
	}
	
	/**
	 * Retrieve all room category records from the database.
	 * @return An ArrayList containing RoomCategories objects for each of the returned records.
	 */
	public static ArrayList<RoomCategories> retrieveAllRoomCategories() {
		
		ArrayList<RoomCategories> roomCategories = new ArrayList<RoomCategories>();
		String sqlStatement = "SELECT categoryCode, categoryDesc FROM room_categories";
		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;

		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			results = statement.executeQuery(sqlStatement);
			
			while (results.next()) {
				RoomCategories roomCategory = new RoomCategories();

				roomCategory.setCategoryCode(results.getString("categoryCode"));
				roomCategory.setCategoryDesc(results.getString("categoryDesc"));

				roomCategories.add(roomCategory);
			}
				
		} catch (SQLException ex) {
			// Log and return null
			ex.printStackTrace();
		} finally {
			// Attempt to close all resources, ignore failures.
			try { results.close(); } catch (Exception ex) {};
			try { statement.close(); } catch (Exception ex) {};
			try { connection.close(); } catch (Exception ex) {};
		}

		return roomCategories;
	}
	
	/**
	 * Update a single room category in the database.
	 * @param roomCategory A RoomCategories object representing the existing record to be updated.
	 * @return boolean A value representing a success (true) or failure (false) of the update.
	 */
	public static boolean updateRoomCategory(RoomCategories roomCategory) {
		
		String sqlStatement = "UPDATE room_categories SET categoryDesc=? WHERE categoryCode=?";
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setString(1,  roomCategory.getCategoryDesc());
			statement.setString(2, roomCategory.getCategoryCode());
			int rowsAffected = statement.executeUpdate();
			
			// A single row should have been updated
			if (1==rowsAffected) {
				return true;
			} else {
				throw new SQLException("More than one row was updated while updating room_categories with categoryCode = " + roomCategory.getCategoryCode() + ".");
			} 
			
		} catch (SQLException ex) {
			// Log and return false
			return false;
		} finally {
			// Attempt to close all resources, ignore failures.
			try { statement.close(); } catch (Exception ex) {};
			try { connection.close(); } catch (Exception ex) {};
		}
	}	
	
	/**
	 * Delete a single room category from the database.
	 * @param roomCategory A RoomCategories object representing the existing record to be deleted.
	 * @return A value representing a success (true) or failure (false) of the delete.
	 */
	public static boolean deleteRoomCategory(RoomCategories roomCategory) {
		
		String sqlStatement = "DELETE FROM room_categories WHERE categoryCode=?";
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setString(1,  roomCategory.getCategoryCode());
			int rowsAffected = statement.executeUpdate();
			
			// A single row should have been deleted
			if (1==rowsAffected) {
				return true;
			} else {
				throw new SQLException("More than one row was deleted while updating room_categories with categoryCode = " + roomCategory.getCategoryCode() + ".");
			} 
			
		} catch (SQLException ex) {
			// Log and return false
			return false;
		} finally {
			// Attempt to close all resources, ignore failures.
			try { statement.close(); } catch (Exception ex) {};
			try { connection.close(); } catch (Exception ex) {};
		}
	}
	
	/**
	 * This method will associate an existing service with an existing room category.
	 * @param categoryCode Four character code used to uniquely identify the room category
	 * @param serviceCode Four character code used to uniquely identify the service
	 * @return A value representing a success (true) or failure (false) of the insert.
	 */
	public static boolean assignServiceToRoomCategory(String categoryCode, String serviceCode) {
		
		String sqlStatement = "INSERT INTO room_categories_services(categoryCode, serviceCode) VALUES(?, ?)";
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);

			statement.setString(1,  categoryCode);
			statement.setString(2, serviceCode);
			int rowsAffected = statement.executeUpdate();
			
			// A single row should have been inserted
			if (1==rowsAffected) {
				return true;
			} else {
				throw new SQLException("More than one row was updated while updating room_categories_services with categoryCode = " + categoryCode + ".");
			}
			
		} catch (SQLException ex) {
			// Log and return null
			return false;
		} finally {
			// Attempt to close all resources, ignore failures.
			try { statement.close(); } catch (Exception ex) {};
			try { connection.close(); } catch (Exception ex) {};
		}
	}	
	
	public static Stays createStay(int hotelId, String roomNumber, int customerId, int numOfGuests, int billingId, Connection connection) {
		String sqlStatement = "INSERT INTO stays (hotelId, roomNumber, customerId, numOfGuests, checkinDate, checkinTime, checkoutDate, checkoutTime, billingId) VALUES(?, ?, ?, ?, ?, ?, NULL, NULL, ?);";
		PreparedStatement statement = null;
		ResultSet results = null;
		
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
		String checkinDate = dateFormat.format(now);
		String checkinTime = timeFormat.format(now);
		//System.out.println("Updating date to: " + dateFormat.format(now));
		//System.out.println("Updating time to: " + timeFormat.format(now));
		
		try {
			statement = connection.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setInt(1, hotelId);
			statement.setString(2, roomNumber);
			statement.setInt(3, customerId);
			statement.setInt(4, numOfGuests);
			statement.setString(5, checkinDate);
			statement.setString(6, checkinTime);
			statement.setInt(7, billingId);			
			int rowsAffected = statement.executeUpdate();
			
			
			
			// A single row should have been inserted
			if (1==rowsAffected) {
				Stays stay = new Stays();
				results = statement.getGeneratedKeys();
				results.next();
				stay.setStayId(results.getInt(1));
				stay.setHotelId(hotelId);
				stay.setRoomNumber(roomNumber);
				stay.setCustomerId(customerId);
				stay.setNumOfGuests(numOfGuests);
				stay.setCheckinDate(checkinDate);
				stay.setCheckinTime(checkinTime);
				stay.setCheckoutDate(null);
				stay.setCheckoutTime(null);
				stay.setBillingId(billingId);
				
				return stay;
			} else {
				// Throw exception
				throw new SQLException("Error seems to have occured. Check the logs.");
			}
		} catch (SQLException ex) {
			// Log and return null
			ex.printStackTrace();
			return null;
		} finally {
			// Attempt to close all resources, ignore failures
			if (results != null) { try { results.close(); } catch (Exception ex) {}; }
			if (statement != null) { try { statement.close(); } catch (Exception ex) {}; }
		}
	}
	
	public static Stays retrieveStay(int stayId) {
		Stays stay = new Stays();
		String sqlStatement = "SELECT hotelId, roomNumber, customerId, numOfGuests, checkinDate, checkinTime, checkoutDate, checkoutTime, billingId FROM stays WHERE stayId = ?;";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;

		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1,  stayId);
			results = statement.executeQuery();
			
			results.last();
			int rowsAffected = results.getRow();
			
			// A single row should have been inserted
			if (1==rowsAffected) {
				results.first();
				
				stay.setStayId(stayId);
				stay.setHotelId(results.getInt("hotelId"));
				stay.setRoomNumber(results.getString("roomNumber"));
				stay.setCustomerId(results.getInt("customerId"));
				stay.setNumOfGuests(results.getInt("numOfGuests"));
				stay.setCheckinDate(results.getString("checkinTime"));
				stay.setCheckoutDate(results.getString("checkoutDate"));
				stay.setCheckoutTime(results.getString("checkoutTime"));
				stay.setBillingId(results.getInt("billingId"));
			} else {
				// Throw exception
				throw new SQLException("Multiple rows returned when selecting stay with stayId = " + stayId + ".");
			}
				
		} catch (SQLException ex) {
			// Log and return null
			ex.printStackTrace();
		} finally {
			// Attempt to close all resources, ignore failures.
			try { results.close(); } catch (Exception ex) {};
			try { statement.close(); } catch (Exception ex) {};
			try { connection.close(); } catch (Exception ex) {};
		}

		return stay;
	}
	
	
	/**
	 * This method will delete a stay.
	 * @param stay An object representing the existing stay
	 * @return A value representing a success (true) or failure (false) of the delete.
	 */
	public static boolean deleteStay(Stays stay) {
		
		String sqlStatement = "DELETE FROM stays WHERE stayId=?";
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1,  stay.getStayId());
			int rowsAffected = statement.executeUpdate();
			
			// A single row should have been deleted
			if (1==rowsAffected) {
				return true;
			} 
			
		} catch (SQLException ex) {
			// Log and return false
			return false;
		} finally {
			// Attempt to close all resources, ignore failures.
			try { statement.close(); } catch (Exception ex) {};
			try { connection.close(); } catch (Exception ex) {};
		}
		
		return false;
	}
	
	/**
	 * Return list of staffIds assigned to a particular hotel.
	 * @param hotelId A hotel ID representing a particular hotel.
	 * @return An ArrayList containing staff IDs working at that hotel
	 */
	public static ArrayList<Integer> retrieveServiceStaffAssignmentsByHotel(int hotelId) {
		ArrayList<Integer> staffIds = new ArrayList<Integer>();
		String sqlStatement = "SELECT staffId FROM service_staff WHERE hotelId = ?;";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		
		try {
      connection = DatabaseConnection.getConnection();
      statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, hotelId);
			results = statement.executeQuery();
			while (results.next()) {
				staffIds.add(results.getInt("staffId"));
			}
		} catch (SQLException ex) {
			// Log and return null
			ex.printStackTrace();
		} finally {
			// Attempt to close all resources, ignore failures.
			try { results.close(); } catch (Exception ex) {};
			try { statement.close(); } catch (Exception ex) {};
			try { connection.close(); } catch (Exception ex) {};
		}
    
		return staffIds;
	}
	
	/**
	 * Return hotelId of hotel that staff has been assigned to.
	 * @param staffId A staff ID representing a particular staff member.
	 * @return An integer representing the hotelId of the hotel that the staff is assigned to, or -1 if the staff is not assigned to any hotel
	 */
	public static int retrieveServiceStaffAssignmentsByStaff(int staffId) {

		int hotelId = -1; // default value to return
		String sqlStatement = "SELECT hotelId FROM service_staff WHERE staffId = ?;";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		
		try {
      connection = DatabaseConnection.getConnection();
      statement = connection.prepareStatement(sqlStatement);
			statement.setString(1, Integer.toString(staffId));
			results = statement.executeQuery();
			
			while (results.next()) {
				hotelId = results.getInt("hotelId"); // only one result set is expected
			}
		} catch (SQLException ex) {
			// Log and return null
			ex.printStackTrace();
		} finally {
			// Attempt to close all resources, ignore failures.
			try { results.close(); } catch (Exception ex) {};
			try { statement.close(); } catch (Exception ex) {};
			try { connection.close(); } catch (Exception ex) {};
		}

		return hotelId;
	}
	
	/**
	 * Create a hotel
	 * @param name the hotel's name
	 * @param address the hotel's address
	 * @param city the hotel's city
	 * @param state the hotel's state
	 * @param phone the hotel's phone number
	 * @param managerId the hotel's manager's staff ID
	 * @return Hotels object if successful, else null
	 */
	public static Hotels createHotel(String name, String address, String city, String state, String phone, int managerId) {
		String sqlStatement = "INSERT INTO hotels(name, address, city, state, phone, managerId) VALUES (?, ?, ?, ?, ?, ?);";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet generatedKeys = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setString(1, name);
			statement.setString(2, address);
			statement.setString(3, city);
			statement.setString(4, state);
			statement.setString(5, phone);
			statement.setInt(6, managerId);
			int rowsAffected = statement.executeUpdate();
			// A single row should have been inserted
			if (1==rowsAffected) {
				Hotels hotel = new Hotels();
				generatedKeys = statement.getGeneratedKeys();
				generatedKeys.next();
				hotel.setHotelId(generatedKeys.getInt(1));
				hotel.setName(name);
				hotel.setAddress(address);
				hotel.setCity(city);
				hotel.setState(state);
				hotel.setPhone(phone);
				hotel.setManagerId(managerId);
				System.out.println("A new hotel was inserted successfully!");
				return hotel;
			} else {
				// Throw exception
				throw new SQLException("Error seems to have occured. Check the logs.");
			}
		} catch (SQLException ex) {
			// Log and return null
			ex.printStackTrace();
			return null;
		} finally {
			// Attempt to close all resources, ignore failures
			if (generatedKeys != null) { try { generatedKeys.close(); } catch (Exception ex) {}; }
			if (statement != null) { try { statement.close(); } catch (Exception ex) {}; }
			if (connection != null) { try { connection.close(); } catch (Exception ex) {}; }
		}
	}

	/**
	 * Retrieve a hotel by hotel ID
	 * @param hotelId the hotel's ID
	 * @return Hotels object if given hotel exists, else null
	 */
	public static Hotels retrieveHotel(int hotelId) {
		String sqlStatement = "SELECT hotelId, name, address, city, state, phone, managerId FROM hotels WHERE hotelId=?;";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, hotelId);
			results = statement.executeQuery();
			results.last();
			int rowsAffected = results.getRow();
			// A single row should have been retrieved
			if (1==rowsAffected) {
				results.first();
				Hotels hotel = new Hotels();
				hotel.setHotelId(hotelId);
				hotel.setName(results.getString("name"));
				hotel.setAddress(results.getString("address"));
				hotel.setCity(results.getString("city"));
				hotel.setState(results.getString("state"));
				hotel.setPhone(results.getString("phone"));
				hotel.setManagerId(results.getInt("managerId"));
				System.out.println("An existing hotel was retrieved successfully!");
				return hotel;
			} else if (0==rowsAffected) {
				System.out.println("There is no existing hotel with hotelId = " + hotelId + ".");
				return null;
			} else {
				// Throw exception
				throw new SQLException("Multiple rows returned when selecting hotel with hotelId = " + hotelId + ".");
			}
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
	 * Update a hotel record in the database with new field values
	 * @param hotel the Hotels object with new field values that needs to be updated in the database
	 * @return true if successful, else false
	 */
	public static boolean updateHotel(Hotels hotel) {
		String sqlStatement = "UPDATE hotels SET name=?, address=?, city=?, state=?, phone=?, managerId=? WHERE hotelId=?;";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setString(1, hotel.getName());
			statement.setString(2, hotel.getAddress());
			statement.setString(3, hotel.getCity());
			statement.setString(4, hotel.getState());
			statement.setString(5, hotel.getPhone());
			statement.setInt(6, hotel.getManagerId());
			statement.setInt(7, hotel.getHotelId());
			int rowsAffected = statement.executeUpdate();
			// A single row should have been updated
			if (1==rowsAffected) {
				System.out.println("An existing hotel was updated successfully!");
				return true;
			} else if (0==rowsAffected) {
				System.out.println("There is no existing hotel with hotelId = " + hotel.getHotelId() + ".");
				return false;
			} else {
				// Throw exception
				throw new SQLException("Error seems to have occured. Check the logs.");
			}
		} catch (SQLException ex) {
			// Log and return false
			ex.printStackTrace();
			return false;
		} finally {
			// Attempt to close all resources, ignore failures.
			if (statement != null) { try { statement.close(); } catch (Exception ex) {}; }
			if (connection != null) { try { connection.close(); } catch (Exception ex) {}; }
		}
	}

	/**
	 * Delete a hotel record from the database
	 * @param hotel the Hotels object that needs to be deleted from the database
	 * @return true if successful, else false
	 */
	public static boolean deleteHotel(Hotels hotel) {
		String sqlStatement = "DELETE FROM hotels WHERE hotelId=?;";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, hotel.getHotelId());
			int rowsAffected = statement.executeUpdate();
			// A single row should have been deleted
			if (1==rowsAffected) {
				System.out.println("An existing hotel was deleted successfully!");
				return true;
			} else if (0==rowsAffected) {
				System.out.println("There is no existing hotel with hotelId = " + hotel.getHotelId() + ".");
				return false;
			} else {
				// Throw exception
				throw new SQLException("Error seems to have occured. Check the logs.");
			}
		} catch (SQLException ex) {
			// Log and return false
			ex.printStackTrace();
			return false;
		} finally {
			// Attempt to close all resources, ignore failures.
			if (statement != null) { try { statement.close(); } catch (Exception ex) {}; }
			if (connection != null) { try { connection.close(); } catch (Exception ex) {}; }
		}
	}

	/**
	 * Create a staff member
	 * @param name the staff's name
	 * @param titleCode the staff's title code
	 * @param deptCode the staff's department code
	 * @param address the staff's address
	 * @param city the staff's city
	 * @param state the staff's state
	 * @param phone the staff's phone number
	 * @param dob the staff's date of birth
	 * @return Staff object if successful, else null
	 */
	public static Staff createStaff(String name, String titleCode, String deptCode, String address, String city, String state, String phone, String dob) {
		String sqlStatement = "INSERT INTO staff(name, titleCode, deptCode, address, city, state, phone, dob) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet generatedKeys = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setString(1, name);
			statement.setString(2, titleCode);
			statement.setString(3, deptCode);
			statement.setString(4, address);
			statement.setString(5, city);
			statement.setString(6, state);
			statement.setString(7, phone);
			statement.setString(8, dob);
			int rowsAffected = statement.executeUpdate();
			// A single row should have been inserted
			if (1==rowsAffected) {
				Staff staff = new Staff();
				generatedKeys = statement.getGeneratedKeys();
				generatedKeys.next();
				staff.setStaffId(generatedKeys.getInt(1));
				staff.setName(name);
				staff.setTitleCode(titleCode);
				staff.setDeptCode(deptCode);
				staff.setAddress(address);
				staff.setCity(city);
				staff.setState(state);
				staff.setPhone(phone);
				staff.setDob(dob);
				System.out.println("A new staff member was inserted successfully!");
				return staff;
			} else {
				// Throw exception
				throw new SQLException("Error seems to have occured. Check the logs.");
			}
		} catch (SQLException ex) {
			// Log and return null
			ex.printStackTrace();
			return null;
		} finally {
			// Attempt to close all resources, ignore failures
			if (generatedKeys != null) { try { generatedKeys.close(); } catch (Exception ex) {}; }
			if (statement != null) { try { statement.close(); } catch (Exception ex) {}; }
			if (connection != null) { try { connection.close(); } catch (Exception ex) {}; }
		}
	}

	/**
	 * Retrieve a staff member by staff ID
	 * @param staffId the staff's ID
	 * @return Staff object if given staff member exists, else null
	 */
	public static Staff retrieveStaff(int staffId) {
		String sqlStatement = "SELECT staffId, name, titleCode, deptCode, address, city, state, phone, dob FROM staff WHERE staffId=?;";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, staffId);
			results = statement.executeQuery();
			results.last();
			int rowsAffected = results.getRow();
			// A single row should have been retrieved
			if (1==rowsAffected) {
				results.first();
				Staff staff = new Staff();
				staff.setStaffId(staffId);
				staff.setName(results.getString("name"));
				staff.setTitleCode(results.getString("titleCode"));
				staff.setDeptCode(results.getString("deptCode"));
				staff.setAddress(results.getString("address"));
				staff.setCity(results.getString("city"));
				staff.setState(results.getString("state"));
				staff.setPhone(results.getString("phone"));
				staff.setDob(results.getString("DOB"));
				System.out.println("An existing staff member was retrieved successfully!");
				return staff;
			} else if (0==rowsAffected) {
				System.out.println("There is no existing staff member with staffId = " + staffId + ".");
				return null;
			} else {
				// Throw exception
				throw new SQLException("Multiple rows returned when selecting staff with staffId = " + staffId + ".");
			}
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
	 * Update a staff member record in the database with new field values
	 * @param staff the Staff object with new field values that needs to be updated in the database
	 * @return true if successful, else false
	 */
	public static boolean updateStaff(Staff staff) {
		String sqlStatement = "UPDATE staff SET name=?, titleCode=?, deptCode=?, address=?, city=?, state=?, phone=?, dob=? WHERE staffId=?;";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setString(1, staff.getName());
			statement.setString(2, staff.getTitleCode());
			statement.setString(3, staff.getDeptCode());
			statement.setString(4, staff.getAddress());
			statement.setString(5, staff.getCity());
			statement.setString(6, staff.getState());
			statement.setString(7, staff.getPhone());
			statement.setString(8, staff.getDob());
			statement.setInt(9, staff.getStaffId());
			int rowsAffected = statement.executeUpdate();
			// A single row should have been updated
			if (1==rowsAffected) {
				System.out.println("An existing staff member was updated successfully!");
				return true;
			} else if (0==rowsAffected) {
				System.out.println("There is no existing staff member with staffId = " + staff.getStaffId() + ".");
				return false;
			} else {
				// Throw exception
				throw new SQLException("Error seems to have occured. Check the logs.");
			}

		} catch (SQLException ex) {
			// Log and return false
			ex.printStackTrace();
			return false;
		} finally {
			// Attempt to close all resources, ignore failures.
			if (statement != null) { try { statement.close(); } catch (Exception ex) {}; }
			if (connection != null) { try { connection.close(); } catch (Exception ex) {}; }
		}
	}

	/**
	 * Delete a staff record from the database
	 * @param staff the Staff object that needs to be deleted from the database
	 * @return true if successful, else false
	 */
	public static boolean deleteStaff(Staff staff) {
		String sqlStatement = "DELETE FROM staff WHERE staffId=?;";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, staff.getStaffId());
			int rowsAffected = statement.executeUpdate();
			// A single row should have been deleted
			if (1==rowsAffected) {
				System.out.println("An existing staff member was deleted successfully!");
				return true;
			} else if (0==rowsAffected) {
				System.out.println("There is no existing staff member with staffId = " + staff.getStaffId() + ".");
				return false;
			} else {
				// Throw exception
				throw new SQLException("Error seems to have occured. Check the logs.");
			}
		} catch (SQLException ex) {
			// Log and return false
			ex.printStackTrace();
			return false;
		} finally {
			// Attempt to close all resources, ignore failures.
			if (statement != null) { try { statement.close(); } catch (Exception ex) {}; }
			if (connection != null) { try { connection.close(); } catch (Exception ex) {}; }
		}
	}

	/**
	 * Create a customer record with given data
   * @param customerId represents the customer's ID in the database
	 * @param name represents the customer name
	 * @param DOB represents the customer's date of birth
	 * @param phone represents the customer's phone number
	 * @param email represents the customer's email ID
	 * @return Customers object if successful, else null
	 */
	public static Customers createCustomer(int customerId, String name, String DOB, String phone, String email) {
		String sqlStatement = "INSERT INTO customers(customerId, name, DOB, phone, email) VALUES (?, ?, ?, ?, ?);";
		Connection connection = null;
		PreparedStatement statement = null;
		Customers customer = null; // default value
		ResultSet generatedKeys = null;
		
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);
      statement.setInt(1, customerId);
			statement.setString(2, name);
			statement.setString(3, DOB);
			statement.setString(4, phone);
			statement.setString(5, email);
			int rowsAffected = statement.executeUpdate();
			
			// A single row should have been inserted
			if (rowsAffected == 1) {
				customer = new Customers();
				customer.setCustomerId(customerId);
				customer.setName(name);
				customer.setDob(DOB);
				customer.setPhone(phone);
				customer.setEmail(email);
			}
		} catch (SQLException ex) {
			// Log
			ex.printStackTrace();
		} finally {
			// Attempt to close all resources, ignore failures
			try { statement.close(); } catch (Exception ex) {};
			try { connection.close(); } catch (Exception ex) {};
		}
		return customer;
	}

	/**
	 * Retrieve a customer by customerId
	 * @param customerId represents the customer ID
	 * @return Customers object if given customer exists else null
	 */
	public static Customers retrieveCustomer(int customerId) {
		Customers customer = null;
		String sqlStatement = "SELECT * FROM customers WHERE customerId = ?;";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, customerId);
			results = statement.executeQuery();

			while (results.next()) { //expecting only 0 or 1 row
				customer = new Customers();
				customer.setCustomerId(customerId);
				customer.setName(results.getString("name"));
				customer.setDob(results.getString("DOB"));
				customer.setPhone(results.getString("phone"));
				customer.setEmail(results.getString("email"));
			}
		} catch (SQLException ex) {
			// Log and return null
			ex.printStackTrace();
		} finally {
			// Attempt to close all resources, ignore failures.
			try { results.close(); } catch (Exception ex) {};
			try { statement.close(); } catch (Exception ex) {};
			try { connection.close(); } catch (Exception ex) {};
		}
		
		return customer;
	}
	
	/**
	 * Update a customer record in the database with new field values
	 * @param customer: the Customers object with new field values that needs to be updated in the database
	 * @return true on success or false on failure
	 */
	public static boolean updateCustomer(Customers customer) {
		String sqlStatement = "UPDATE customers SET name = ?, DOB = ?, phone = ?, email = ? WHERE customerId = ?;";
		Connection connection = null;
		PreparedStatement statement = null;
		boolean returnValue = false;
		
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setString(1, customer.getName());
			statement.setString(2, customer.getDob());
			statement.setString(3, customer.getPhone());
			statement.setString(4, customer.getEmail());
			statement.setInt(5, customer.getCustomerId());
			int rowsAffected = statement.executeUpdate();
			
			// A single row should have been updated
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
	 * Delete a customer record from the database
	 * @param customer: the Customers object that needs to be deleted from the database
	 * @return true on success or false on failure
	 */
	public static boolean deleteCustomer(Customers customer) {
		String sqlStatement = "DELETE FROM customers WHERE customerId = ?;";
		Connection connection = null;
		PreparedStatement statement = null;
		boolean returnValue = false;
		
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, customer.getCustomerId());
			int rowsAffected = statement.executeUpdate();
			// A single row should have been deleted
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
	 * Create a Rooms record with given data
	 * @param hotelId represents the hotel in which the room is located
	 * @param roomNumber
	 * @param categoryCode represents the room's category
	 * @param maxAllowedOcc represents the maximum number of guests that can stay in the room
	 * @param rate the nightly rate of the room
	 * @return Rooms object if successful, else null
	 */
	public static Rooms createRoom(int hotelId, String roomNumber, int maxAllowedOcc, double rate, String categoryCode, String available) {
		String sqlStatement = "INSERT INTO rooms(hotelId, roomNumber, maxAllowedOcc, rate, categoryCode, available) VALUES (?, ?, ?, ?, ?, ?);";
		Connection connection = null;
		PreparedStatement statement = null;
		Rooms room = null; // default value
		
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, hotelId);
			statement.setString(2, roomNumber);
			statement.setInt(3, maxAllowedOcc);
			statement.setDouble(4, rate);
			statement.setString(5, categoryCode);
			statement.setString(6, available);
			int rowsAffected = statement.executeUpdate();
			
			// A single row should have been inserted
			if (rowsAffected == 1) {
				room = new Rooms();
				
				// could possible retrieve the newly inserted record from the database and update values from there
				room.setHotelId(hotelId);
				room.setRoomNumber(roomNumber);
				room.setMaxAllowedOcc(maxAllowedOcc);
				room.setRate(rate);
				room.setCategoryCode(categoryCode);
				room.setAvailable(available);
			}
		} catch (SQLException ex) {
			// Log
			ex.printStackTrace();
		} finally {
			// Attempt to close all resources, ignore failures
			try { statement.close(); } catch (Exception ex) {};
			try { connection.close(); } catch (Exception ex) {};
		}
		return room;
	}

	/**
	 * Retrieve a room by roomNumber and hotelId
	 * @param hotelId represents the hotel in which the room is located
	 * @param roomNumber
	 * @return Rooms object if given room exists else null
	 */
	public static Rooms retrieveRoom(int hotelId, String roomNumber) {
		Rooms room = null;
		String sqlStatement = "SELECT * FROM rooms WHERE hotelId = ? AND roomNumber = ?;";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, hotelId);
			statement.setString(2, roomNumber);
			results = statement.executeQuery();

			while (results.next()) { //expecting only 0 or 1 row
				room = new Rooms();
				room.setHotelId(results.getInt("hotelId"));
				room.setRoomNumber(results.getString("roomNumber"));
				room.setMaxAllowedOcc(results.getInt("maxAllowedOcc"));
				room.setRate(results.getDouble("rate"));
				room.setCategoryCode(results.getString("categoryCode"));
				room.setAvailable(results.getString("available"));
			}
		} catch (SQLException ex) {
			// Log
			ex.printStackTrace();
		} finally {
			// Attempt to close all resources, ignore failures.
			try { results.close(); } catch (Exception ex) {};
			try { statement.close(); } catch (Exception ex) {};
			try { connection.close(); } catch (Exception ex) {};
		}
		
		return room;
	}
	
	/**
	 * Update a room record in the database with new field values
	 * @param room: the Rooms object with new field values that needs to be updated in the database
	 * @return true on success or false on failure
	 */
	public static boolean updateRoom(Rooms room) {
		String sqlStatement = "UPDATE rooms SET maxAllowedOcc = ?, rate = ?, categoryCode = ?, available = ? WHERE hotelId = ? AND roomNumber = ?;";
		Connection connection = null;
		PreparedStatement statement = null;
		boolean returnValue = false;
		
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, room.getMaxAllowedOcc());
			statement.setDouble(2, room.getRate());
			statement.setString(3, room.getCategoryCode());
			statement.setString(4, room.getAvailable());
			statement.setInt(5, room.getHotelId());
			statement.setString(6, room.getRoomNumber());
			int rowsAffected = statement.executeUpdate();
			
			// A single row should have been updated
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
	 * Delete a room record from the database
	 * @param room: the Rooms object that needs to be deleted from the database
	 * @return true on success or false on failure
	 */
	public static boolean deleteRoom(Rooms room) {
		String sqlStatement = "DELETE FROM rooms WHERE hotelId = ? AND roomNumber = ?;";
		Connection connection = null;
		PreparedStatement statement = null;
		boolean returnValue = false;
		
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, room.getHotelId());
			statement.setString(2, room.getRoomNumber());
			int rowsAffected = statement.executeUpdate();
			// A single row should have been deleted
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
	 * Create a service staff record
	 * @param staffId the staff's ID
	 * @param hotelId the hotel's ID
	 * @return ServiceStaff object if successful, else null
	 */
	public static ServiceStaff createServiceStaff(int staffId, int hotelId) {
		String sqlStatement = "INSERT INTO service_staff(staffId, hotelId) VALUES (?, ?);";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, staffId);
			statement.setInt(2, hotelId);
			int rowsAffected = statement.executeUpdate();
			// A single row should have been inserted
			if (1==rowsAffected) {
				ServiceStaff serviceStaff = new ServiceStaff();
				serviceStaff.setStaffId(staffId);
				serviceStaff.setHotelId(hotelId);
				System.out.println("A new service staff record was inserted successfully!");
				return serviceStaff;
			} else {
				// Throw exception
				throw new SQLException("Error seems to have occured. Check the logs.");
			}
		} catch (SQLException ex) {
			// Log and return null
			ex.printStackTrace();
			return null;
		} finally {
			// Attempt to close all resources, ignore failures
			if (statement != null) { try { statement.close(); } catch (Exception ex) {}; }
			if (connection != null) { try { connection.close(); } catch (Exception ex) {}; }
		}
	}

	/**
	 * Retrieve a service staff by staff ID
	 * @param staffId the staff's ID
	 * @return ServiceStaff object if given service staff exists, else null
	 */
	public static ServiceStaff retrieveServiceStaff(int staffId) {
		String sqlStatement = "SELECT staffId, hotelId FROM service_staff WHERE staffId=?;";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, staffId);
			results = statement.executeQuery();
			results.last();
			int rowsAffected = results.getRow();
			// A single row should have been retrieved
			if (1==rowsAffected) {
				results.first();
				ServiceStaff serviceStaff = new ServiceStaff();
				serviceStaff.setStaffId(staffId);
				serviceStaff.setHotelId(results.getInt("hotelId"));
				System.out.println("An existing service staff record was retrieved successfully!");
				return serviceStaff;
			} else if (0==rowsAffected) {
				System.out.println("There is no existing service staff record with staffId = " + staffId + ".");
				return null;
			} else {
				// Throw exception
				throw new SQLException("Multiple rows returned when selecting service staff record with staffId = " + staffId + ".");
			}
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
	 * Update a service staff record in the database with new field values
	 * @param serviceStaff the ServiceStaff object with new field values that needs to be updated in the database
	 * @return true if successful, else false
	 */
	public static boolean updateServiceStaff(ServiceStaff serviceStaff) {
		String sqlStatement = "UPDATE service_staff SET hotelId=? WHERE staffId=?;";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, serviceStaff.getHotelId());
			statement.setInt(2, serviceStaff.getStaffId());
			int rowsAffected = statement.executeUpdate();
			// A single row should have been updated
			if (1==rowsAffected) {
				System.out.println("An existing service staff record was updated successfully!");
				return true;
			} else if (0==rowsAffected) {
				System.out.println("There is no existing service staff record with staffId = " + serviceStaff.getStaffId() + ".");
				return false;
			} else {
				// Throw exception
				throw new SQLException("Error seems to have occured. Check the logs.");
			}
		} catch (SQLException ex) {
			// Log and return false
			ex.printStackTrace();
			return false;
		} finally {
			// Attempt to close all resources, ignore failures.
			if (statement != null) { try { statement.close(); } catch (Exception ex) {}; }
			if (connection != null) { try { connection.close(); } catch (Exception ex) {}; }
		}
	}

	/**
	 * Delete a service staff record from the database
	 * @param serviceStaff the ServiceStaff object that needs to be deleted from the database
	 * @return true if successful, else false
	 */
	public static boolean deleteServiceStaff(ServiceStaff serviceStaff) {
		String sqlStatement = "DELETE FROM service_staff WHERE staffId=?;";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, serviceStaff.getStaffId());
			int rowsAffected = statement.executeUpdate();
			// A single row should have been deleted
			if (1==rowsAffected) {
				System.out.println("An existing service staff record was deleted successfully!");
				return true;
			} else if (0==rowsAffected) {
				System.out.println("There is no existing service staff record with staffId = " + serviceStaff.getStaffId() + ".");
				return false;
			} else {
				// Throw exception
				throw new SQLException("Error seems to have occured. Check the logs.");
			}
		} catch (SQLException ex) {
			// Log and return false
			ex.printStackTrace();
			return false;
		} finally {
			// Attempt to close all resources, ignore failures.
			if (statement != null) { try { statement.close(); } catch (Exception ex) {}; }
			if (connection != null) { try { connection.close(); } catch (Exception ex) {}; }
		}
	}

}
