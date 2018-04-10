import java.sql.*;
import java.util.ArrayList;

public class MaintainBillingRecords {
	public static final double CCWF_DISCOUNT = .05;
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
			results = statement.executeQuery();
			
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

	/**
	 * Create a payment method
	 * @param payMethodCode the payment method's code
	 * @param payMethodDesc the payment method's description
	 * @return PaymentMethods object if successful, else null
	 */
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
				System.out.println("A new payment method was inserted successfully!");
				return paymentMethod;
			} else {
				// Throw exception
				throw new SQLException("Error seems to have occured. Check the logs.");
			}	
		} catch (SQLException ex) {
			// Log and return null
			ex.printStackTrace();
			return null;
		} finally {
			// Attempt to close all resources, ignore failures.
			if (statement != null) { try { statement.close(); } catch (Exception ex) {}; }
			if (connection != null) { try { connection.close(); } catch (Exception ex) {}; }
		}
	}

	/**
	 * Retrieve all payment method records from the database.
	 * @return An ArrayList containing PaymentMethods objects for each of the returned records if successful, else null
	 */
	public static ArrayList<PaymentMethods> retrieveAllPaymentMethods() {
		String sqlStatement = "SELECT payMethodCode, payMethodDesc FROM payment_methods;";
		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		try {
			ArrayList<PaymentMethods> paymentMethods = new ArrayList<PaymentMethods>();
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			results = statement.executeQuery(sqlStatement);
			while (results.next()) {
				PaymentMethods paymentMethod = new PaymentMethods();
				paymentMethod.setPayMethodCode(results.getString("payMethodCode"));
				paymentMethod.setPayMethodDesc(results.getString("payMethodDesc"));
				paymentMethods.add(paymentMethod);
			}
			System.out.println("A list of existing payment methods was retrieved successfully!");
			return paymentMethods;
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
	 * Update a payment method record in the database with new field values
	 * @param paymentMethod the PaymentMethods object with new field values that needs to be updated in the database
	 * @return true if successful, else false
	 */
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
				System.out.println("An existing payment method was updated successfully!");
				return true;
			} else if (0==rowsAffected) {
				System.out.println("There is no existing payment method with payMethodCode = " + paymentMethod.getPayMethodCode() + ".");
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
	 * Delete a payment method record from the database
	 * @param paymentMethod the PaymentMethods object that needs to be deleted from the database
	 * @return true if successful, else false
	 */
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
				System.out.println("An existing payment method was deleted successfully!");
				return true;
			} else if (0==rowsAffected) {
				System.out.println("There is no existing payment method with payMethodCode = " + paymentMethod.getPayMethodCode() + ".");
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
			statement = connection.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, responsiblePartySSN);
			statement.setString(2, address);
			statement.setString(3, city);
			statement.setString(4, state);
			statement.setString(5, payMethodCode);
			statement.setString(6, cardNumber);
			
			int rowsAffected = statement.executeUpdate();
			
			if (rowsAffected == 1) {
				billingInfo = new BillingInfo();
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
	public static BillingInfo retrieveBillingInfo(int billingId) {
		
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
        billingInfo.setBillingId(billingId);
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
			
			statement.setString(1, billingInfo.getResponsiblePartySSN());
			statement.setString(2, billingInfo.getAddress());
			statement.setString(3, billingInfo.getCity());
			statement.setString(4, billingInfo.getState());
			statement.setString(5, billingInfo.getPayMethodCode());
			statement.setString(6, billingInfo.getCardNumber());
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
  
  /**
   * Delete a billing_info record
   * @param billingInfo: a BillingInfo object representing the record to be deleted
   * @return true on success, false on error
   */
  public static boolean deleteBillingInfo(BillingInfo billingInfo) {
    String sqlStatement = "DELETE FROM billing_info WHERE billingId=?;";
		Connection connection = null;
		PreparedStatement statement = null;
    boolean returnValue = false;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, billingInfo.getBillingId());
			int rowsAffected = statement.executeUpdate();
			// A single row should have been deleted
			if (rowsAffected == 1) {
				returnValue = true;
			} else {
				// Throw exception
				throw new SQLException("Unexpected behaviour in deleteBillingInfo()");
			}	
		} catch (SQLException ex) {
			// Log and return false
			ex.printStackTrace();
		} finally {
			// Attempt to close all resources, ignore failures.
			if (statement != null) { try { statement.close(); } catch (Exception ex) {}; }
			if (connection != null) { try { connection.close(); } catch (Exception ex) {}; }
		}
    
    return returnValue;
  }
    /**
     * the sum of service record charges and room charge, if paid with hotel CC 
     * gives discount
     * @param stayId
     */
    public static void generateBillingTotal(int stayId) throws SQLException {
        double totalCharge = -1;

        Connection con = null;
        PreparedStatement prst1 = null;
        PreparedStatement prst2 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        String sqlString1 = "SELECT sum(cg) as totCharge\n"
                + "FROM (\n"
                + "(SELECT (DATEDIFF(checkoutDate, checkinDate) + 1) * rate  cg \n"
                + "FROM stays NATURAL JOIN rooms GROUP BY stayId HAVING stayId = ?)\n"
                + "\n"
                + "UNION ALL\n"
                + "\n"
                + "(SELECT sum(charge) cg\n"
                + "FROM stays NATURAL JOIN service_records NATURAL JOIN services\n"
                + "GROUP BY stayId HAVING stayId =? )\n"
                + ") a;";
        
        String SqlString2 = String.format("SELECT payMethodCode FROM stays\n"
                + "NATURAL JOIN billing_info\n"
                + "WHERE stayId = ? ;");

        try {
            con = DatabaseConnection.getConnection();
            con.setAutoCommit(false);
            prst1 = con.prepareStatement(sqlString1);
            prst1.setInt(1, stayId);
            prst1.setInt(2, stayId);
            rs1 = prst1.executeQuery();
            prst2 = con.prepareStatement(SqlString2);
            prst2.setInt(1, stayId);
            rs2 = prst2.executeQuery();
            con.commit();
            if (rs1.next()) {
                totalCharge = rs1.getInt("totCharge");
            }
            if (rs2.next()) {
                if ("CCWF".equals(rs2.getString("payMethodCode"))){
                    System.out.println("You are paying discounted Charge.");
                    totalCharge *= (1-CCWF_DISCOUNT);
                }
            }

            System.out.printf("total Charge for stay %d is %.2f%n", stayId, totalCharge);
            System.out.printf("You saved $%.2f.%n",  CCWF_DISCOUNT*totalCharge);
            
        } catch (SQLException e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    con.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
        } finally {
            if (prst1 != null) {
                prst1.close();
            }
            if (prst2 != null) {
                prst2.close();
            }
            con.setAutoCommit(true);
        }

    }
}
