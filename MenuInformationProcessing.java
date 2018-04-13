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
				Hotels hotel = Hotels.select();
				
				if (null == hotel) { 
					System.out.println("No record was selected.");
				} else {
					// Create the service staff record using the required values from the contributing objects
					ServiceStaff serviceStaff = InformationProcessing.createServiceStaff(staff.getStaffId(), hotel.getHotelId());
					
					if (null == serviceStaff) {
						System.out.println("Something unexpected happened while attempting to create the record.");
					} else {
						System.out.println("Record created successfully.");
					}
				}
			}
		} else {
			System.out.println("There are no service staff members who are not assigned to a hotel.");
		}
	}
	
	public static void menuOptionUpdateServiceStaff() {
		System.out.println("|---------------------------------------------------------------------|");
		System.out.println("| UPDATE SERVICE STAFF RECORD                                         |");
		System.out.println("|---------------------------------------------------------------------|\n\n");
		
		
		// Select a service staff record, null if none selected
		ServiceStaff serviceStaff = ServiceStaff.select();
		
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
				Hotels hotel = Hotels.select();
				
				if (null == hotel) { 
					System.out.println("No record was selected.");
				} else {
					serviceStaff.setHotelId(hotel.getHotelId());
				}
			}
			
			if (InformationProcessing.updateServiceStaff(serviceStaff)) {
				System.out.println("Record updated successfully.");
			} else {
				System.out.println("Something unexpected happened while attempting to update the record.");
			}
		}
	}
	
	public static void menuOptionDeleteServiceStaff() {
		System.out.println("|---------------------------------------------------------------------|");
		System.out.println("| DELETE SERVICE STAFF RECORD                                         |");
		System.out.println("|---------------------------------------------------------------------|\n\n");
		
		
		// Select a service staff record, null if none selected
		ServiceStaff serviceStaff = ServiceStaff.select();
		
		if (null == serviceStaff) {
			System.out.println("No record was selected.");
		} else {
			Scanner scanner = new Scanner(System.in);
			
			// For every update eligible field, ask the user if they want to update it, if yes then accept new input and set value in object
			// Only hotelId is eligible to be updated for service staff
			
			// Update hotelId field
			System.out.println("Attempting to delete service staff assignment record with staff Id: " + serviceStaff.getStaffId());
			System.out.println("Are you sure you want to delete this record? (Y/N)");
			
			String response = "";
			do {
				System.out.print("===> ");
			    while (!scanner.hasNext()) {
			    }
			    response = scanner.next();
			} while (!response.equals("Y") && !response.equals("N"));
			
			if (response.equals("Y")) {
				if (InformationProcessing.deleteServiceStaff(serviceStaff)) {
					System.out.println("Record deleted successfully.");
				} else {
					System.out.println("Something unexpected happened while attempting to delete the record.");
				}
			} else {
				System.out.println("Deletion was cancelled.");
			}			
		}
	}
	
	
	public static void menuOptionCreateRoomCategoriesServices() {
		System.out.println("|---------------------------------------------------------------------|");
		System.out.println("| ASSIGN SERVICE TO ROOM CATEGORY                                     |");
		System.out.println("|---------------------------------------------------------------------|\n\n");
		
		// Get list of room categories
		ArrayList<RoomCategories> roomCategoriesList = InformationProcessing.retrieveAllRoomCategories();
		
		if (null != roomCategoriesList && roomCategoriesList.size() > 0) {
			
			ArrayList<DatabaseObject> databaseObjectList = (ArrayList<DatabaseObject>) ((ArrayList<?>) roomCategoriesList);
			
			// Have the user select a room category record to work with
			RoomCategories roomCategory = (RoomCategories)MenuUtilities.paginatedRecordSelection(databaseObjectList);
			
			if (null == roomCategory) {
				System.out.println("No record was selected.");
			} else {
				System.out.println("Choose a service to assign to room category " + roomCategory.getCategoryCode() + ".");
				
				// Have the user select a services record to work with
				Services service = Services.select();
				
				if (null == service) { 
					System.out.println("No record was selected.");
				} else {
					// Add the service assignment
					if (InformationProcessing.assignServiceToRoomCategory(roomCategory.getCategoryCode(), service.getServiceCode())) {
						System.out.println("Record created successfully.");
					} else {
						System.out.println("Something unexpected happened while attempting to create the record.");
					}
				}
			}
		} else {
			System.out.println("There are no service staff members who are not assigned to a hotel.");
		}
	}
	
	
	public static void menuOptionDeleteRoomCateogoriesServices() {
		System.out.println("|---------------------------------------------------------------------|");
		System.out.println("| REMOVE SERVICE FROM ROOM CATEGORY                                   |");
		System.out.println("|---------------------------------------------------------------------|\n\n");
		
		
		// Select a room categories services record, null if none selected
		RoomCategoriesServices roomCategoriesServices = RoomCategoriesServices.select();
		
		if (null == roomCategoriesServices) {
			System.out.println("No record was selected.");
		} else {
			Scanner scanner = new Scanner(System.in);
			
			// For every update eligible field, ask the user if they want to update it, if yes then accept new input and set value in object
			// Only hotelId is eligible to be updated for service staff
			
			// Update hotelId field
			System.out.println("Attempting to delete room category service assignment record with room category " + roomCategoriesServices.getCategoryCode() + " and service code " + roomCategoriesServices.getServiceCode());
			System.out.println("Are you sure you want to delete this record? (Y/N)");
			
			String response = "";
			do {
				System.out.print("===> ");
			    while (!scanner.hasNext()) {
			    }
			    response = scanner.next();
			} while (!response.equals("Y") && !response.equals("N"));
			
			if (response.equals("Y")) {
				if (InformationProcessing.deleteServiceStaff(serviceStaff)) {
					System.out.println("Record deleted successfully.");
				} else {
					System.out.println("Something unexpected happened while attempting to delete the record.");
				}
			} else {
				System.out.println("Deletion was cancelled.");
			}			
		}
	}
}
