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
}
