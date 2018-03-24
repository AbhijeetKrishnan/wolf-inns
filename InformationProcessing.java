import java.sql.*;
import java.util.ArrayList;


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
			} 
			
		} catch (SQLException ex) {
			// Log and return null
			return null;
		} finally {
			// Attempt to close all resources, ignore failures.
			try { statement.close(); } catch (Exception ex) {};
			try { connection.close(); } catch (Exception ex) {};
		}
		
		return null;
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
			} 
			
		} catch (SQLException ex) {
			// Log and return null
			return false;
		} finally {
			// Attempt to close all resources, ignore failures.
			try { statement.close(); } catch (Exception ex) {};
			try { connection.close(); } catch (Exception ex) {};
		}
		
		return false;
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
			results = statement.executeQuery(sqlStatement);
			
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
        String sqlStatement = "SELECT staffId FROM service_staff WHERE hotelId = ?";
        Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		
		try {
			statement.setString(1, Integer.toString(hotelId));
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
     * @return An integer representing the hotelId of the hotel that the staff is assigned to, or -1 
	 * if the staff is not assigned to any hotel
     */
    public static int retrieveServiceStaffAssignmentsByStaff(int staffId) {

        int hotelId = -1; // default value to return
        String sqlStatement = "SELECT hotelId FROM service_staff WHERE staffId = ?";
        Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		
		try {
			statement.setString(1, Integer.toString(staffId));
			results = statement.executeQuery();
			
			while (results.next()) {
				hotelId = results.getInt("hotelId")); // only one result set is expected
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

//----------------------------------------------------------------------------------------------------------------------------------
//******BEGIN CRUD HOTEL******
//----------------------------------------------------------------------------------------------------------------------------------
	public static Hotels createHotel(String name, String address, String city, String state, String phone, int managerId) {
		String sqlStatement = "INSERT INTO hotels(name, address, city, state, phone, managerId) VALUES (?, ?, ?, ?, ?, ?);";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet generatedKeys = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
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
				return hotel;
			}
		} catch (SQLException ex) {
			// Log and return null
			return null;
		} finally {
			// Attempt to close all resources, ignore failures
			try { generatedKeys.close(); } catch (Exception ex) {};
			try { statement.close(); } catch (Exception ex) {};
			try { connection.close(); } catch (Exception ex) {};
		}
		return null;
	}

	public static Hotels retrieveHotel(int hotelId) {
		Hotels hotel = new Hotels();
		String sqlStatement = "SELECT hotelId, name, address, city, state, phone, managerId FROM hotels WHERE hotelId=?;";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, hotelId);
			results = statement.executeQuery(sqlStatement);
			results.last();
			int rowsAffected = results.getRow();
			// A single row should have been retrieved
			if (1==rowsAffected) {
				results.first();
				hotel.setHotelId(hotelId);
				hotel.setName(results.getString("name"));
				hotel.setAddress(results.getString("address"));
				hotel.setCity(results.getString("city"));
				hotel.setState(results.getString("state"));
				hotel.setPhone(results.getString("phone"));
				hotel.setManagerId(results.getInt("managerId");
			} else {
				// Throw exception
				throw new SQLException("Multiple rows or no row returned when selecting hotel with hotelId = " + hotelId + ".");
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
		return hotel;
	}

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
//----------------------------------------------------------------------------------------------------------------------------------
//******END CRUD HOTEL******
//----------------------------------------------------------------------------------------------------------------------------------

//----------------------------------------------------------------------------------------------------------------------------------
//******BEGIN CRUD STAFF******
//----------------------------------------------------------------------------------------------------------------------------------
	public static Staff createStaff(String name, String titleCode, String deptCode, String address, String city, String state, String phone, String dob) {
		String sqlStatement = "INSERT INTO staff(name, titleCode, deptCode, address, city, state, phone, dob) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet generatedKeys = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
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
				return staff;
			}
		} catch (SQLException ex) {
			// Log and return null
			return null;
		} finally {
			// Attempt to close all resources, ignore failures
			try { generatedKeys.close(); } catch (Exception ex) {};
			try { statement.close(); } catch (Exception ex) {};
			try { connection.close(); } catch (Exception ex) {};
		}
		return null;
	}

	public static Staff retrieveStaff(int staffId) {
		Staff staff = new Staff();
		String sqlStatement = "SELECT staffId, name, titleCode, deptCode, address, city, state, phone, dob FROM staff WHERE staffId=?;";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, staffId);
			results = statement.executeQuery(sqlStatement);
			results.last();
			int rowsAffected = results.getRow();
			// A single row should have been retrieved
			if (1==rowsAffected) {
				results.first();
				staff.setStaffId(staffId);
				staff.setName(results.getString("name"));
				staff.setTitleCode(results.getString("titleCode"));
				staff.setDeptCode(results.getString("deptCode"));
				staff.setAddress(results.getString("address"));
				staff.setCity(results.getString("city"));
				staff.setState(results.getString("state"));
				staff.setPhone(results.getString("phone"));
				staff.setDob(results.getString("dob");
			} else {
				// Throw exception
				throw new SQLException("Multiple rows or no row returned when selecting staff with staffId = " + staffId + ".");
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
		return staff;
	}

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
//----------------------------------------------------------------------------------------------------------------------------------
//******END CRUD STAFF******
//----------------------------------------------------------------------------------------------------------------------------------

}
