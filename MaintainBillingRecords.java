import java.sql.*;

public class MaintainBillingRecords {
	
	public static double calculateRoomCharge(int stayId) {
		double roomCharge = 0.0;
		String sqlStatement = "SELECT ((DATEDIFF(checkoutDate, checkinDate)+1)*rate) AS \"Room Charge\" FROM stays NATURAL JOIN rooms WHERE stayId = ?";
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
				
				roomCharge = results.getDouble("Room Charge");								
			} else {
				// Throw exception
				throw new SQLException("Multiple rows returned when selecting stay with stayId = " + stayId + ".");
			}
			
			return roomCharge;
				
		} catch (SQLException ex) {
			// Log and return null
			ex.printStackTrace();
			return roomCharge;
		} finally {
			// Attempt to close all resources, ignore failures.
			try { results.close(); } catch (Exception ex) {};
			try { statement.close(); } catch (Exception ex) {};
			try { connection.close(); } catch (Exception ex) {};
		}
	}
}
