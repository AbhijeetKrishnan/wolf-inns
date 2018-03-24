import java.sql.*;
import java.util.ArrayList;

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

	public static PaymentMethods createPaymentMethod(String payMethodCode, String payMethodDesc) {
		
		String sqlStatement = "INSERT INTO payment_methods(payMethodCode, payMethodDesc) VALUES(?, ?);";
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setString(1, payMethodCode);
			statement.setString(2, payMethodDesc);
			int rowsAffected = statement.executeUpdate();
			
			// A single row should have been inserted
			if (1==rowsAffected) {
				PaymentMethods paymentMethod = new PaymentMethods();
				paymentMethod.setPayMethodCode(payMethodCode);
				paymentMethod.setPayMethodDesc(payMethodDesc);
				return paymentMethod;
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

	public static ArrayList<PaymentMethods> retrieveAllPaymentMethods() {
		
		ArrayList<PaymentMethods> paymentMethods = new ArrayList<PaymentMethods>();
		String sqlStatement = "SELECT payMethodCode, payMethodDesc FROM payment_methods;";
		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;

		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			results = statement.executeQuery(sqlStatement);
			
			while (results.next()) {
				PaymentMethods paymentMethod = new PaymentMethods();

				paymentMethod.setPayMethodCode(results.getString("payMethodCode"));
				paymentMethod.setPayMethodDesc(results.getString("payMethodDesc"));

				paymentMethods.add(paymentMethod);
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

		return paymentMethods;
	}

	public static boolean updatePaymentMethod(PaymentMethods paymentMethod) {
		
		String sqlStatement = "UPDATE payment_methods SET payMethodDesc=? WHERE payMethodCode=?;";
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setString(1, paymentMethod.getPayMethodDesc());
			statement.setString(2, paymentMethod.getPayMethodCode());
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

	public static boolean deletePaymentMethod(PaymentMethods paymentMethod) {
		
		String sqlStatement = "DELETE FROM payment_methods WHERE payMethodCode=?;";
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setString(1, paymentMethod.getPayMethodCode());
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
