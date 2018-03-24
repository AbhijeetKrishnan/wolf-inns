import java.sql.*;

public class MaintainingServiceRecords {

	public static ServiceRecords createServiceRecord(int stayId, String serviceCode, int staffId, String serviceDate, String serviceTime, double charge) {
		String sqlStatement = "INSERT INTO service_records(stayId, serviceCode, staffId, serviceDate, serviceTime, charge) VALUES (?, ?, ?, ?, ?, ?);";
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
			statement.setDouble(6, charge);
			int rowsAffected = statement.executeUpdate();
			// A single row should have been inserted
			if (1==rowsAffected) {
				ServiceRecords serviceRecords = new ServiceRecords();
				serviceRecords.setStayId(stayId);
				serviceRecords.setServiceCode(serviceCode);
				serviceRecords.setStaffId(staffId);
				serviceRecords.setServiceDate(serviceDate);
				serviceRecords.setServiceTime(serviceTime);
				serviceRecords.setCharge(charge);
				return serviceRecords;
			}
		} catch (SQLException ex) {
			// Log and return null
			return null;
		} finally {
			// Attempt to close all resources, ignore failures
			try { statement.close(); } catch (Exception ex) {};
			try { connection.close(); } catch (Exception ex) {};
		}
		return null;
	}

	public static ArrayList<ServiceRecords> retrieveServiceRecordsForStay(int stayId) {
		ArrayList<ServiceRecords> serviceRecords = new ArrayList<ServiceRecords>();
		String sqlStatement = "SELECT stayId, serviceCode, staffId, serviceDate, serviceTime, charge FROM service_records WHERE stayId=?;";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, stayId);
			results = statement.executeQuery(sqlStatement);
			while (results.next()) {
				ServiceRecords serviceRecord = new ServiceRecords();
				serviceRecord.setStayId(results.getInt("stayId"));
				serviceRecord.setServiceCode(results.getString("serviceCode"));
				serviceRecord.setStaffId(results.getInt("staffId"));
				serviceRecord.setServiceDate(results.getString("serviceDate"));
				serviceRecord.setServiceTime(results.getString("serviceTime"));
				serviceRecord.setCharge(results.getDouble("charge"));
				serviceRecords.add(serviceRecord);
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
		return serviceRecords;
	}

	public static boolean updateServiceRecord(ServiceRecords serviceRecords) {
		String sqlStatement = "UPDATE service_records SET serviceCode=?, staffId=?, charge=? WHERE stayId=? AND serviceDate=? AND serviceTime=?;";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setString(1, serviceRecords.getServiceCode());
			statement.setInt(2, serviceRecords.getStaffId());
			statement.setDouble(3, serviceRecords.getCharge());
			statement.setInt(4, serviceRecords.getStayId());
			statement.setString(5, serviceRecords.getServiceDate());
			statement.setString(6, serviceRecords.getServiceTime());
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

}

