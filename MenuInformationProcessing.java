import java.util.ArrayList;
import java.util.Scanner;


public class MenuInformationProcessing {
	
	// Accepts nothing and returns nothing. Fully encapsulated dialog for service staff entry!
	public static void menuOptionCreateServiceStaff() {
		System.out.println("|---------------------------------------------------------------------|");
		System.out.println("| CREATE SERVICE STAFF RECORD                                         |");
		System.out.println("|---------------------------------------------------------------------|\n\n");
		
		// Get list of service staff who are yet to be assigned to a hotel
		ArrayList<Staff> serviceStaffList = InformationProcessing.retrieveAllStaffNotAssignedToHotel();
		
		if (null != serviceStaffList && serviceStaffList.size() > 0) {
			
			ArrayList<DatabaseObject> databaseObjectList = (ArrayList<DatabaseObject>) ((ArrayList<?>) serviceStaffList);
			
			// Have the user select a staff record to work with
			Staff staff = (Staff)MenuUtilities.paginatedRecordSelection(databaseObjectList);
			
			if (null == staff) {
				System.out.println("No record was selected.");
			} else {
				System.out.println("Choose a hotel to assign " + staff.getName() + " to.");
				
				// Have the user select a hotel record to work with
				Hotels hotel = Hotels.selectHotel();
				
				if (null == hotel) { 
					System.out.println("No record was selected.");
				} else {
					// Create the service staff record using the required values from the contributing objects
					InformationProcessing.createServiceStaff(staff.getStaffId(), hotel.getHotelId());
				}
			}
		} else {
			System.out.println("There are no service staff members who are not assigned to a hotel.");
		}
	}
	
	public static void updateServiceStaff() {
		System.out.println("|---------------------------------------------------------------------|");
		System.out.println("| UPDATE SERVICE STAFF RECORD                                         |");
		System.out.println("|---------------------------------------------------------------------|\n\n");
		
		
		// Select a service staff record, null if none selected
		ServiceStaff serviceStaff = ServiceStaff.selectServiceStaff();
		
		if (null == serviceStaff) {
			System.out.println("No record was selected.");
		} else {
			Scanner scanner = new Scanner(System.in);
			
			// For every update eligible field, ask the user if they want to update it, if yes then accept new input and set value in object
			// Only hotelId is eligible to be updated for service staff
			
			// Update hotelId field
			System.out.println("Current Hotel Id: " + serviceStaff.getHotelId());
			System.out.println("Do you want to update this field? (Y/N)");
			
			String response = "";
			do {
				System.out.print("===> ");
			    while (!scanner.hasNext()) {
			    }
			    response = scanner.next();
			} while (!response.equals("Y") && !response.equals("N"));
			
			if (response.equals("Y")) {
				Hotels hotel = Hotels.selectHotel();
				
				if (null == hotel) { 
					System.out.println("No record was selected.");
				} else {
					serviceStaff.setHotelId(hotel.getHotelId());
				}
			}
			// End update hotelId
			
			// Update the next field and then the next until all update eligible fields have been presented to the user
			
			// Now send the object with updated values to the update method
			InformationProcessing.updateServiceStaff(serviceStaff);
		}
	}
	
	public static void deleteServiceStaff() {
		
	}

}
