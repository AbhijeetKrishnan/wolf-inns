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
	
	/**
	 * Creates a new billing_info record
	 * @param String responsiblePartySSN
	 * @param String address
	 * @param String city
	 * @param String state
	 * @param String payMethodCode
	 * @param String cardNumber
	 * @return BillingInfo object which contains the newly inserted fields
	 */
	public static BillingInfo createBillingInfo(String responsiblePartySSN, String address, String city, String state, String payMethodCode, String cardNumber) {
		
		String sqlStatement = "INSERT INTO billing_info (responsiblePartySSN, address, city, state, payMethodCode, cardNumber) VALUES (?, ?, ?, ?, ?, ?)";
		Connection connection = null;
		PreparedStatement statement = null;
		BillingInfo billingInfo = null;
		ResultSet generatedKeys = null;
		
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			
			statement.setString(1, responsiblePartySSN);
			statement.setString(2, address);
			statement.setString(3, city);
			statement.setString(4, state);
			statement.setString(5, payMethodCode);
			statement.setString(6, cardNumber);
			
			int rowsAffected = statement.executeUpdate();
			
			if (rowsAffected == 1) {
				billingInfo = new BilingInfo();
				generatedKeys = statement.getGeneratedKeys();
				generatedKeys.next();
				billingInfo.setBillingId(generatedKeys.getInt(1));
				billingInfo.setResponsiblePartySSN(responsiblePartySSN);
				billingInfo.setAddress(address);
				billingInfo.setCity(city);
				billingInfo.setState(state);
				billingInfo.setPayMethodCode(payMethodCode);
				billingInfo.setCardNumber(cardNumber);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try { generatedKeys.close(); } catch (Exception ex) {};
			try { statement.close(); } catch (Exception ex) {};
			try { connection.close(); } catch (Exception ex) {};
		}
		
		return billingInfo;
	}
	
	/**
	 * Retrieve a record of billing_info table from the database
	 * @param int billingId: the billingId of the record which is to be retrieved from the table
	 * @return BillingInfo object which is null in case billingId does not exist in the table
	 */
	public static boolean retrieveBillingInfo(int billingId) {
		
		String sqlStatement = "SELECT * FROM billing_info WHERE billingId = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		BillingInfo billingInfo = null;
		
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, billingId);
			results = statement.executeQuery();
			
			while (results.next()) { // expect only 0/1 row
				billingInfo = new BillingInfo();
				billingInfo.setResponsiblePartySSN(results.getString("responsiblePartySSN"));
				billingInfo.setAddress(results.getString("address"));
				billingInfo.setCity(results.getString("city"));
				billingInfo.setState(results.getString("state"));
				billingInfo.setPayMethodCode(results.getString("payMethodCode"));
				billingInfo.setCardNumber(results.getString("cardNumber"));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try { results.close(); } catch (Exception ex) {};
			try { statement.close(); } catch (Exception ex) {};
			try { connection.close(); } catch (Exception ex) {};
		}
		
		return billingInfo;
	}
	
	/**
	 * Update a record of billing_info table in the database
	 * @param BillingInfo billingInfo: the object which is to be updated in the table
	 * @return boolean which is true on success, false on failure
	 */
	public static boolean updateBillingInfo(BillingInfo billingInfo) {
		
		String sqlStatement = "UPDATE billing_info SET responsiblePartySSN = ?, address = ?, city = ?, state = ?, payMethodCode = ?, cardNumber = ? WHERE billingId = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		boolean returnValue = false;
		
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			
			statement.setString(1, responsiblePartySSN);
			statement.setString(2, address);
			statement.setString(3, city);
			statement.setString(4, state);
			statement.setString(5, payMethodCode);
			statement.setString(6, cardNumber);
			statement.setInt(7, billingInfo.getBillingId());
			
			int rowsAffected = statement.executeUpdate();
			
			if (rowsAffected == 1) {
				returnValue = true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try { statement.close(); } catch (Exception ex) {};
			try { connection.close(); } catch (Exception ex) {};
		}
		
		return returnValue;
	}
	
	
}
