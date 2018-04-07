import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.sql.*;
import java.io.*;

public class TestJunit {
	@Test
	public void testStaffCRUD() {
		Staff expected = new Staff();
		expected.setName("Xiaohui");
		expected.setTitleCode("MNGR");
		expected.setDeptCode("MNGD");
		expected.setAddress("Sturdivant dr.");
		expected.setCity("Raleigh");
		expected.setState("NC");
		expected.setPhone("1234567890");
		expected.setDob("1988-01-01");

		// Create a staff
		Staff actual_c = InformationProcessing.createStaff("Xiaohui", "MNGR", "MNGD", "Sturdivant dr.", "Raleigh", "NC", "1234567890", "1988-01-01");
		expected.setStaffId(actual_c.getStaffId()); // I don't know how to get around this.
		assertEquals("Create a staff", expected, actual_c);

		// Retrieve a staff
		Staff actual_r = InformationProcessing.retrieveStaff(actual_c.getStaffId());
		assertEquals("Retrieve a staff", expected, actual_r);

		// Update a staff
		expected.setPhone("0000000000");
		assertTrue("Update a staff", InformationProcessing.updateStaff(expected));
		assertEquals("Update a staff", expected, InformationProcessing.retrieveStaff(actual_c.getStaffId()));

		// Delete a staff
		assertTrue("Delete a staff", InformationProcessing.deleteStaff(expected));
		assertNull("Delete a staff", InformationProcessing.retrieveStaff(actual_c.getStaffId()));
	}

	@Test
	public void testHotelsCRUD() {
		// Create manager for new hotel
		Staff mgn = InformationProcessing.createStaff("Xiaohui", "MNGR", "MNGD", "Sturdivant dr.", "Raleigh", "NC", "1234567890", "1988-01-01");

		Hotels expected = new Hotels();
		expected.setName("No.1 Hotel");
		expected.setAddress("No.1 st.");
		expected.setCity("Raleigh");
		expected.setState("NC");
		expected.setPhone("8888888888");
		expected.setManagerId(mgn.getStaffId());

		// Create a hotel
		Hotels actual_c = InformationProcessing.createHotel("No.1 Hotel", "No.1 st.", "Raleigh", "NC", "8888888888", mgn.getStaffId());
		expected.setHotelId(actual_c.getHotelId());
		assertEquals("Create a hotel", expected, actual_c);
		
		// Retrieve a hotel
		Hotels actual_r = InformationProcessing.retrieveHotel(actual_c.getHotelId());
		assertEquals("Retrieve a hotel", expected, actual_r);

		// Update a hotel
		expected.setName("World Best Hotel");
		assertTrue("Update a hotel", InformationProcessing.updateHotel(expected));
		assertEquals("Update a hotel", expected, InformationProcessing.retrieveHotel(actual_c.getHotelId()));

		// Delete a hotel
		assertTrue("Delete a hotel", InformationProcessing.deleteHotel(expected));
		assertNull("Delete a hotel", InformationProcessing.retrieveHotel(actual_c.getHotelId()));

		// Delete manager
		InformationProcessing.deleteStaff(mgn);
	}

	@Test
	public void testServiceStaffCRUD() {
		// Create service staff for a hotel
		Staff s = InformationProcessing.createStaff("Xiaohui", "FREP", "FRND", "Sturdivant dr.", "Raleigh", "NC", "1234567890", "1988-01-01");

		ServiceStaff expected = new ServiceStaff();
		expected.setStaffId(s.getStaffId());
		expected.setHotelId(4);

		// Create a service staff
		ServiceStaff actual_c = InformationProcessing.createServiceStaff(s.getStaffId(), 4);
		assertEquals("Create a service staff", expected, actual_c);

		// Retrieve a service staff
		ServiceStaff actual_r = InformationProcessing.retrieveServiceStaff(s.getStaffId());
		assertEquals("Retrieve a service staff", expected, actual_r);

		// Update a service staff
		expected.setHotelId(1);
		assertTrue("Update a service staff", InformationProcessing.updateServiceStaff(expected));
		assertEquals("Update a service staff", expected, InformationProcessing.retrieveServiceStaff(s.getStaffId()));

		// Delete a service staff
		assertTrue("Delete a service staff", InformationProcessing.deleteServiceStaff(expected));
		assertNull("Delete a service staff", InformationProcessing.retrieveServiceStaff(s.getStaffId()));

		// Delete service staff
		InformationProcessing.deleteStaff(s);
	}

	@Test
	public void testPaymentMethodsCRUD() {
		PaymentMethods expected = new PaymentMethods();
		expected.setPayMethodCode("SPEC");
		expected.setPayMethodDesc("Other");

		// Retrieve all payment methods before creating a new payment method
		ArrayList<PaymentMethods> old_tmp = MaintainBillingRecords.retrieveAllPaymentMethods();

		// Create a payment method
		PaymentMethods actual_c = MaintainBillingRecords.createPaymentMethod("SPEC", "Other");
		assertEquals("Create a payment method", expected, actual_c);

		// Retrieve all payment methods after creating a new payment method
		ArrayList<PaymentMethods> new_tmp = MaintainBillingRecords.retrieveAllPaymentMethods();
		assertEquals(old_tmp.size()+1, new_tmp.size());

		// Update a payment method
		expected.setPayMethodDesc("Special method");
		assertTrue("Update a payment method", MaintainBillingRecords.updatePaymentMethod(expected));

		// Delete a payment method
		assertTrue("Delete a payment method", MaintainBillingRecords.deletePaymentMethod(expected));
		ArrayList<PaymentMethods> old = MaintainBillingRecords.retrieveAllPaymentMethods();
		assertEquals(old_tmp.size(), old.size());
	}

	@Test
	public void testServiceRecordsCRUD() { //CRU only
		// Stay 5 is still active
		ServiceRecords expected = new ServiceRecords();
		expected.setStayId(5);
		expected.setServiceCode("RMSV");
		expected.setStaffId(3);
		expected.setServiceDate("2018-03-08");
		expected.setServiceTime("08:08:08");
		expected.setCharge(5.00);

		// Retrieve all service records for stay 5 before creating a new service record
		ArrayList<ServiceRecords> old_tmp = MaintainingServiceRecords.retrieveServiceRecordsForStay(5);

		// Create a service record
		ServiceRecords actual_c = MaintainingServiceRecords.createServiceRecord(5, "RMSV", 3, "2018-03-08", "08:08:08", 5.00);
		assertEquals("Create a service record", expected, actual_c);

		// Retrieve all service records for stay 5 after creating a new service record
		ArrayList<ServiceRecords> new_tmp = MaintainingServiceRecords.retrieveServiceRecordsForStay(5);
		assertEquals(old_tmp.size()+1, new_tmp.size());

		// Update a service record
		expected.setCharge(100.00);
		assertTrue("Update a service record", MaintainingServiceRecords.updateServiceRecord(expected));

		// Clean up
		String sqlStatement = "DELETE FROM service_records WHERE stayId=?;";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, 5);
			int rowsAffected = statement.executeUpdate();
			if (new_tmp.size()==rowsAffected) {
				System.out.println("Existing service records were deleted successfully!");
			} else if (0==rowsAffected) {
				System.out.println("There is no existing service record with stayId = 5.");
			} else {
				// Throw exception
				throw new SQLException("Error seems to have occured. Check the logs.");
			}
		} catch (SQLException ex) {
			// Log and return false
			ex.printStackTrace();
		} finally {
			// Attempt to close all resources, ignore failures.
			if (statement != null) { try { statement.close(); } catch (Exception ex) {}; }
			if (connection != null) { try { connection.close(); } catch (Exception ex) {}; }
		}
	}

	@Test
	public void testRetrieveAvailableRooms() {
		int econ = 1;
		int pres = 0;

		ArrayList<Rooms> e = HotelStayOperations.retrieveAvailableRooms(3, "ECON");
		ArrayList<Rooms> p = HotelStayOperations.retrieveAvailableRooms(3, "PRES");

		assertEquals("1 economy room in hotel 3 is available", econ, e.size());
		assertEquals("0 residential room in hotel 3 is available", pres, p.size());
	}

	@Test
	public void testRetrieveAvailableRoomServiceStaff() {
		int before = 0;
		int after = 1;

		ArrayList<ServiceStaff> b = HotelStayOperations.retrieveAvailableRoomServiceStaff(1);
		assertEquals("0 room service staff is available", before, b.size());

		Staff s = InformationProcessing.createStaff("Xiaohui", "RSST", "SRVD", "Sturdivant dr.", "Raleigh", "NC", "1234567890", "1988-01-01");
		ServiceStaff ss = InformationProcessing.createServiceStaff(s.getStaffId(), 1);
		ArrayList<ServiceStaff> a = HotelStayOperations.retrieveAvailableRoomServiceStaff(1);
		assertEquals("1 room service staff is available", after, a.size());

		InformationProcessing.deleteServiceStaff(ss);
		InformationProcessing.deleteStaff(s);
	}
  
  /* Abhijeet APIs start */
  
  // all my tests assume the existence of initial data entered as per Project Report #2
  
  @Test
  public void testBillingInfoCRUD() {
    
    //create a new record
    BillingInfo expected = new BillingInfo();
    expected.setResponsiblePartySSN("--Test---");
    expected.setAddress("Test");
    expected.setCity("Test");
    expected.setState("TT");
    expected.setPayMethodCode("CCWF");
    expected.setCardNumber("Test");
    
    /* Create */
    System.out.println("Testing createBillingInfo...");
    BillingInfo actual = MaintainBillingRecords.createBillingInfo("--Test---", "Test", "Test", "TT", "CCWF", "Test");
    expected.setBillingId(actual.getBillingId()); //necessary since billingId is autoincrement
    assertEquals("Create a BillingInfo record", expected, actual);
    System.out.println("Test for createBillingInfo passed!");
    
    /* Retrieve */
    System.out.println("Testing retrieveBillingInfo...");
    BillingInfo retrieved = MaintainBillingRecords.retrieveBillingInfo(actual.getBillingId());
    assertEquals("Retrieve a BillingInfo record", expected, retrieved);
    System.out.println("Test for retrieveBillingInfo passed!");
    
    /* Update */
    System.out.println("Testing updateBillingInfo...");
    actual.setAddress("New Test");
    assertTrue("Update a BillingInfo record", MaintainBillingRecords.updateBillingInfo(actual));
    System.out.println("Test for retrieveBillingInfo passed!");
    
    /* Delete */
    System.out.println("Testing deleteBillingInfo...");
    assertTrue("Delete a BillingInfo record", MaintainBillingRecords.deleteBillingInfo(actual));
    System.out.println("Test for deleteBillingInfo passed!");
  }
  
  @Test
  public void testCustomersCRUD() {
    //create a new record
    Customers expected = new Customers();
    expected.setCustomerId(50000);
    expected.setName("Test");
    expected.setDob("2018-01-01");
    expected.setPhone("Test");
    expected.setEmail("Test");
    
    /* Create */
    System.out.println("Testing createCustomer...");
    Customers actual = InformationProcessing.createCustomer(50000, "Test", "2018-01-01", "Test", "Test");
    assertEquals("Create a Customers record", expected, actual);
    System.out.println("Test for createCustomer passed!");
    
    /* Retrieve */
    System.out.println("Testing retrieveCustomer...");
    Customers retrieved = InformationProcessing.retrieveCustomer(actual.getCustomerId());
    assertEquals("Retrieve a Customers record", expected, retrieved);
    System.out.println("Test for retrieveCustomer passed!");
    
    /* Update */
    System.out.println("Testing updateCustomer...");
    actual.setPhone("New Test");
    assertTrue("Update a Customers record", InformationProcessing.updateCustomer(actual));
    System.out.println("Test for updateCustomer passed!");
    
    /* Delete */
    System.out.println("Testing deleteCustomer...");
    assertTrue("Delete a Customers record", InformationProcessing.deleteCustomer(actual));
    System.out.println("Test for deleteCustomer passed!");
  }
  
  @Test
  public void testRoomsCRUD() {
    //create a new record
    Rooms expected = new Rooms();
    expected.setHotelId(1);
    expected.setRoomNumber("Test");
    expected.setMaxAllowedOcc(4); // max possible occupancy allowed by DB
    expected.setRate(2.0);
    expected.setCategoryCode("ECON");
    
    /* Create */
    System.out.println("Testing createRoom...");
    Rooms actual = InformationProcessing.createRoom(1, "Test", 4, 2.0, "ECON");
    assertEquals("Create a Rooms record", expected, actual);
    System.out.println("Test for createRooms passed!");
    
    /* Retrieve */
    System.out.println("Testing retrieveRoom...");
    Rooms retrieved = InformationProcessing.retrieveRoom(actual.getHotelId(), actual.getRoomNumber());
    assertEquals("Retrieve a Rooms record", expected, retrieved);
    System.out.println("Test for retrieveRoom passed!");
    
    /* Update */
    System.out.println("Testing updateRoom...");
    actual.setMaxAllowedOcc(1); // min possible occupancy allowed by DB
    assertTrue("Update a Rooms record", InformationProcessing.updateRoom(actual));
    System.out.println("Test for updateRoom passed!");
    
    /* Delete */
    System.out.println("Testing deleteRoom...");
    assertTrue("Delete a Rooms record", InformationProcessing.deleteRoom(actual));
    System.out.println("Test for deleteRoom passed!");
  }
  
  @Test
  public void testRetrieveServiceStaffAssignmentsByHotel() {
    System.out.println("Testing retrieveServiceStaffAssignmentsByHotel...");
    ArrayList<Integer> expected = new ArrayList<Integer>();
    expected.add(1);
    expected.add(4);
    expected.add(5);
    expected.add(10);
    ArrayList<Integer> actual = InformationProcessing.retrieveServiceStaffAssignmentsByHotel(1);
    assertEquals("Retrieve Service Staff assignment by hotel", expected, actual);
    System.out.println("Test for retrieveServiceStaffAssignmentsByHotel passed!");
  }
  
  @Test
  public void testRetrieveServiceStaffAssignmentsbyStaff() {
    System.out.println("Testing retrieveServiceStaffAssignmentsbyStaff...");
    int expected = 1;
    int actual = InformationProcessing.retrieveServiceStaffAssignmentsByStaff(1);
    assertEquals("Retrieve Service Staff assignment by staff", expected, actual);
    
    // can't test default value case since all service staff have been assigned to hotels in default data
    // create that scenario in test code?
    System.out.println("Test for retrieveServiceStaffAssignmentsbyStaff passed!");
  }
  
  @Test
  public void testRetrieveAvailableCateringStaff() {
    System.out.println("Testing retrieveAvailableCateringStaff...");
    ArrayList<ServiceStaff> expected = new ArrayList<ServiceStaff>();
    ServiceStaff expectedStaff = new ServiceStaff();
    expectedStaff.setStaffId(1);
    expectedStaff.setHotelId(1);
    expected.add(expectedStaff);
    ArrayList<ServiceStaff> actual = HotelStayOperations.retrieveAvailableCateringStaff(1);
    assertEquals("Retrieve available catering staff", expected, actual);
    System.out.println("Test for retrieveAvailableCateringStaff passed!");
  }
  
  @Test
  public void testAssignDedicatedPresidentialSuiteStaff() {
    System.out.println("Testing assignDedicatedPresidentialSuiteStaff...");
    //create temp records for available service room service staff since there is none in default data
    //create room in hotel 1
    Rooms r = InformationProcessing.createRoom(1, "Test", 4, 2.0, "PRES");
    //create new staff to assign to hotel
    Staff s_rsst = InformationProcessing.createStaff("Test RSST", "RSST", "SRVD", "Test", "Test", "TT", "Test1", "2018-01-01");
    Staff s_cats = InformationProcessing.createStaff("Test CATS", "CATS", "SRVD", "Test", "Test", "TT", "Test2", "2018-01-01");
    //assign staff to hotel 1
		ServiceStaff ss_rsst = InformationProcessing.createServiceStaff(s_rsst.getStaffId(), 1);
    ServiceStaff ss_cats = InformationProcessing.createServiceStaff(s_cats.getStaffId(), 1);
    
    ArrayList<ServiceStaff> availCateringStaff = HotelStayOperations.retrieveAvailableCateringStaff(1);
    ArrayList<ServiceStaff> availRoomServiceStaff = HotelStayOperations.retrieveAvailableRoomServiceStaff(1);
    
    assertTrue("Assign dedicated staff to a Presidential Suite", HotelStayOperations.assignDedicatedPresidentialSuiteStaff(1, "Test", availCateringStaff.get(0).getStaffId(), availRoomServiceStaff.get(0).getStaffId()));
    
    //clean up
    assertTrue("Unassign dedicated staff from a Presidential Suite", HotelStayOperations.unassignDedicatedPresidentialSuiteStaff(1, "Test"));
		InformationProcessing.deleteServiceStaff(ss_cats);
    InformationProcessing.deleteServiceStaff(ss_rsst);
		InformationProcessing.deleteStaff(s_cats);
    InformationProcessing.deleteStaff(s_rsst);
    InformationProcessing.deleteRoom(r);
    System.out.println("Test for assignDedicatedPresidentialSuiteStaff passed!");
  }
  
  @Test
  public void testOccupancyByDateRange() {
  }
  
  @Test
  public void testOccupancyByCity() {
    //ideally I should be checking the values in the default data and verifying that this query returns the correct output
    /*
    System.out.println("Testing occupancyByCity...");
    //Output of query on default data
    String expected =   "+--------------+-------+-----------------+---------------------+\n"
                      + "| city         | state | total_occupancy | percentage_occupied |\n"
                      + "+--------------+-------+-----------------+---------------------+\n"
                      + "| Los Angeles  | CA    |               1 |             50.0000 |\n"
                      + "| Fayetteville | NC    |               0 |              0.0000 |\n"
                      + "| Raleigh      | NC    |               0 |              0.0000 |\n"
                      + "| New York     | NY    |               0 |              0.0000 |\n"
                      + "+--------------+-------+-----------------+---------------------+\n";

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent)); // set up streams
    Reporting.occupancyByCity();
    System.setOut(System.out); // restore streams
    System.out.println(outContent.toString());
    assertEquals("Report occupancy by city", expected, outContent.toString());
    System.out.println("Test for occupancyByCity passed!");
    */
  }
  
  /* Abhijeet APIs end */
}
