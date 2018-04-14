import java.sql.*;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;

public class MaintainingServiceRecords {

	/**
	 * Create a service record
	 * @param stayId the customer stay's ID
	 * @param serviceCode the service's code
	 * @param staffId the staff's ID
	 * @param serviceDate the service's date
	 * @param serviceTime the service's time
	 * @return ServiceRecords object if successful, else null
	 */
	public static ServiceRecords createServiceRecord(int stayId, String serviceCode, int staffId, String serviceDate, String serviceTime) {
		String sqlStatement = "INSERT INTO service_records(stayId, serviceCode, staffId, serviceDate, serviceTime) VALUES (?, ?, ?, ?, ?);";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, stayId);
			statement.setString(2, serviceCode);
			statement.setInt(3, staffId);
			statement.setString(4, serviceDate);
			statement.setString(5, serviceTime);
			int rowsAffected = statement.executeUpdate();
			// A single row should have been inserted
			if (1==rowsAffected) {
				ServiceRecords serviceRecords = new ServiceRecords();
				serviceRecords.setStayId(stayId);
				serviceRecords.setServiceCode(serviceCode);
				serviceRecords.setStaffId(staffId);
				serviceRecords.setServiceDate(serviceDate);
				serviceRecords.setServiceTime(serviceTime);
				System.out.println("A new service record was inserted successfully!");
				return serviceRecords;
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
	 * Retrieve all service records from the database for a customer stay.
	 * @return An ArrayList containing ServiceRecords objects for each of the returned records if successful, else null
	 */
	public static ArrayList<ServiceRecords> retrieveServiceRecordsForStay(int stayId) {
		String sqlStatement = "SELECT stayId, serviceCode, staffId, serviceDate, serviceTime FROM service_records WHERE stayId=?;";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			ArrayList<ServiceRecords> serviceRecords = new ArrayList<ServiceRecords>();
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, stayId);
			results = statement.executeQuery();
			while (results.next()) {
				ServiceRecords serviceRecord = new ServiceRecords();
				serviceRecord.setStayId(results.getInt("stayId"));
				serviceRecord.setServiceCode(results.getString("serviceCode"));
				serviceRecord.setStaffId(results.getInt("staffId"));
				serviceRecord.setServiceDate(results.getString("serviceDate"));
				serviceRecord.setServiceTime(results.getString("serviceTime"));
				serviceRecords.add(serviceRecord);
			}
			//System.out.println("A list of existing service records for stayId = " + stayId + " was retrieved successfully!");
			return serviceRecords;
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
	 * Update a service record in the database with new field values
	 * @param serviceRecords the ServiceRecords object with new field values that needs to be updated in the database
	 * @return true if successful, else false
	 */
	public static boolean updateServiceRecord(ServiceRecords serviceRecords) {
		// Assume that we are only able to update service record when the stay is still active.
		String sqlStatement = "UPDATE service_records SET serviceCode=?, staffId=? WHERE stayId=? AND serviceDate=? AND serviceTime=? AND stayId IN (SELECT stayId FROM stays WHERE checkoutDate IS NULL AND checkoutTime IS NULL);";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setString(1, serviceRecords.getServiceCode());
			statement.setInt(2, serviceRecords.getStaffId());
			statement.setInt(3, serviceRecords.getStayId());
			statement.setString(4, serviceRecords.getServiceDate());
			statement.setString(5, serviceRecords.getServiceTime());
			int rowsAffected = statement.executeUpdate();
			// A single row should have been updated
			if (1==rowsAffected) {
				System.out.println("An existing service record was updated successfully!");
				return true;
			} else if (0==rowsAffected) {
				System.out.println("There is no corresponding existing service record or the customer stay is not active any more.");
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
	
	public static ServiceRecords createCheckinCheckoutRecord(int stayId, int staffId, boolean checkinFlag, Connection connection) {
		String sqlStatement = "INSERT INTO service_records(stayId, serviceCode, staffId, serviceDate, serviceTime) VALUES (?, ?, ?, ?, ?);";
		PreparedStatement statement = null;
		
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
		String serviceDate = dateFormat.format(now);
		String serviceTime = timeFormat.format(now);
		String serviceCode = "";
		
		try {
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, stayId);
			if (checkinFlag) {
				serviceCode = "CKIN";
			} else {
				serviceCode = "CKOT";		
			}
			statement.setString(2, serviceCode);		
			statement.setInt(3, staffId);
			statement.setString(4, serviceDate);
			statement.setString(5, serviceTime);
			int rowsAffected = statement.executeUpdate();
			
			// A single row should have been inserted
			if (1==rowsAffected) {
				ServiceRecords serviceRecords = new ServiceRecords();
				serviceRecords.setStayId(stayId);
				serviceRecords.setServiceCode(serviceCode);
				serviceRecords.setStaffId(staffId);
				serviceRecords.setServiceDate(serviceDate);
				serviceRecords.setServiceTime(serviceTime);
				System.out.println("A new service record was inserted successfully!");
				return serviceRecords;
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
		}
	}
}

