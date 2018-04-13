import java.util.ArrayList;
import java.util.Scanner;


public class MenuInformationProcessing {
    
    public static void menuInformationProcessingMain() {
        try {	
        
            boolean isExit = false;
            Scanner in = new Scanner(System.in);
            int choice;
            do {
                
                System.out.println("|---------------------------------------------------------------------|");
                System.out.println("| INFORMATION PROCESSING                                              |");
                System.out.println("|---------------------------------------------------------------------|\n\n");
                
                System.out.println("1. Hotels");
                System.out.println("2. Rooms");
                System.out.println("3. Staff");
                System.out.println("4. Service Staff");
                System.out.println("5. Customers");
                System.out.println("6. Stay");
                System.out.println("7. Search Available Rooms");
                System.out.println("8. Other");
                System.out.println("9. Back\n");
                
                System.out.print(">> ");
                choice = in.nextInt();
                
                switch (choice) {
                    case 1:
                    menuHotelsMain();
                    break;
                    case 2:
                    menuRoomsMain();
                    break;
                    case 3:
                    //menuStaffMain();
                    break;
                    case 4:
                    //menuServiceStaffMain();
                    break;
                    case 5:
                    menuCustomersMain();
                    break;
                    case 6:
                    //menuStayMain();
                    break;
                    case 7:
                    //menuSearchAvailableRoomsMain();
                    break;
                    case 8:
                    menuOtherMain();
                    break;
                    case 9:
                    isExit = true;
                    break;
                    default:
                    System.out.println("Incorrect option. Please try again");
                    break;
                }
            } while (!isExit);    
		} catch (RuntimeException ex) {
			System.err.println(ex.getMessage());
			ex.printStackTrace();
		}
    }
    
    public static void menuHotelsMain() {
        try {	
        
            boolean isExit = false;
            Scanner in = new Scanner(System.in);
            int choice;
            do {
                
                System.out.println("|---------------------------------------------------------------------|");
                System.out.println("| HOTELS                                                              |");
                System.out.println("|---------------------------------------------------------------------|\n\n");
                
                System.out.println("1. Create a hotel");
                System.out.println("2. Update a hotel");
                System.out.println("3. Delete a hotel");
                System.out.println("4. Back\n");
                
                System.out.print(">> ");
                choice = in.nextInt();
                
                switch (choice) {
                    case 1:
                    menuOptionCreateHotel();
                    break;
                    case 2:
                    menuOptionUpdateHotel();
                    break;
                    case 3:
                    menuOptionDeleteHotel();
                    break;
                    case 4:
                    isExit = true;
                    break;
                    default:
                    System.out.println("Incorrect option. Please try again");
                    break;
                }
            } while (!isExit);    
		} catch (RuntimeException ex) {
			System.err.println(ex.getMessage());
			ex.printStackTrace();
		}
    }

	public static void menuOptionCreateHotel() {
		System.out.println("|---------------------------------------------------------------------|");
		System.out.println("| CREATE HOTEL RECORD                                                 |");
		System.out.println("|---------------------------------------------------------------------|\n\n");
		Scanner scanner = new Scanner(System.in);
		String[] field = {"name", "address", "city", "state", "phone", "managerId"};
		String[] response = new String[6];
		for (int i = 0; i < 6; i++) {
			System.out.println("Hotel " + field[i]);
			System.out.println("===> ");
			while (!scanner.hasNextLine()) {
				System.out.println("===> ");
			}
			response[i] = scanner.nextLine();
		}
		Hotels h = InformationProcessing.createHotel(response[0], response[1], response[2], response[3], response[4], Integer.parseInt(response[5]));
		if (null == h) {
			System.out.println("Something unexpected happened while attempting to create the record.");
		}
		else {
			System.out.println("Record created successfully.");
		}
	}

	public static void menuOptionUpdateHotel() {
		System.out.println("|---------------------------------------------------------------------|");
		System.out.println("| UPDATE HOTEL RECORD                                                 |");
		System.out.println("|---------------------------------------------------------------------|\n\n");
		Hotels h = Hotels.select();
		if (null == h) {
			System.out.println("No record was selected.");
		} else {
			Scanner scanner = new Scanner(System.in);
			String[] field = {"name", "address", "city", "state", "phone", "managerId"};
			String[] current = {h.getName(), h.getAddress(), h.getCity(), h.getState(), h.getPhone(), Integer.toString(h.getManagerId())};
			String[] update = {h.getName(), h.getAddress(), h.getCity(), h.getState(), h.getPhone(), Integer.toString(h.getManagerId())};
			System.out.println("Current hotel id: " + h.getHotelId());
			for (int i = 0; i < 6; i++) {
				System.out.println("Current hotel " + field[i] + ": " + current[i]);
				System.out.println("Do you want to update this field? (Y/N)");
				String response = "";
				do {
					System.out.print("===> ");
					while (!scanner.hasNextLine()) {
						System.out.println("===> ");
					}
					response = scanner.nextLine();
				} while (!response.equals("Y") && !response.equals("N"));
				if (response.equals("Y")) {
					System.out.print("===> ");
					while (!scanner.hasNextLine()) {
						System.out.println("===> ");
					}
					update[i] = scanner.nextLine();
				}
			}
			h.setName(update[0]);
			h.setAddress(update[1]);
			h.setCity(update[2]);
			h.setState(update[3]);
			h.setPhone(update[4]);
			h.setManagerId(Integer.parseInt(update[5]));
			if (InformationProcessing.updateHotel(h)) {
				System.out.println("Record updated successfully.");
			} else {
				System.out.println("Something unexpected happened while attempting to update the record.");
			}
		}
	}

	public static void menuOptionDeleteHotel() {
		System.out.println("|---------------------------------------------------------------------|");
		System.out.println("| DELETE HOTEL RECORD                                                 |");
		System.out.println("|---------------------------------------------------------------------|\n\n");
		Hotels h = Hotels.select();
		if (null == h) {
			System.out.println("No record was selected.");
		} else {
			Scanner scanner = new Scanner(System.in);			
			System.out.println("Attempting to delete hotel record with hotel id: " + h.getHotelId());
			System.out.println("Are you sure you want to delete this record? (Y/N)");			
			String response = "";
			do {
				System.out.print("===> ");
				while (!scanner.hasNextLine()) {
					System.out.print("===> ");
				}
				response = scanner.nextLine();
			} while (!response.equals("Y") && !response.equals("N"));
			if (response.equals("Y")) {
				if (InformationProcessing.deleteHotel(h)) {
					System.out.println("Record deleted successfully.");
				} else {
					System.out.println("Something unexpected happened while attempting to delete the record.");
				}
			} else {
				System.out.println("Deletion was cancelled.");
			}
		}
	}
    
    public static void menuRoomsMain() {
        try {	
        
            boolean isExit = false;
            Scanner in = new Scanner(System.in);
            int choice;
            do {
                
                System.out.println("|---------------------------------------------------------------------|");
                System.out.println("| ROOMS                                                               |");
                System.out.println("|---------------------------------------------------------------------|\n\n");
                
                System.out.println("1. Create a room");
                System.out.println("2. Update a room");
                System.out.println("3. Delete a room");
                System.out.println("4. Back\n");
                
                System.out.print(">> ");
                choice = in.nextInt();
                
                switch (choice) {
                    case 1:
                    menuOptionCreateRoom();
                    break;
                    case 2:
                    menuOptionUpdateRoom();
                    break;
                    case 3:
                    menuOptionDeleteRoom();
                    break;
                    case 4:
                    isExit = true;
                    break;
                    default:
                    System.out.println("Incorrect option. Please try again");
                    break;
                }
            } while (!isExit);    
		} catch (RuntimeException ex) {
			System.err.println(ex.getMessage());
			ex.printStackTrace();
		}
    }
    
    public static void menuOptionCreateRoom() {
        //Abhijeet
    }
    
    public static void menuOptionUpdateRoom() {
        //Abhijeet
    }
    
    public static void menuOptionDeleteRoom() {
        //Abhijeet
    }

    public static void menuCustomersMain() {
        try {	
        
            boolean isExit = false;
            Scanner in = new Scanner(System.in);
            int choice;
            do {
                
                System.out.println("|---------------------------------------------------------------------|");
                System.out.println("| CUSTOMER                                                            |");
                System.out.println("|---------------------------------------------------------------------|\n\n");
                
                System.out.println("1. Create a customer");
                System.out.println("2. Update a customer");
                System.out.println("3. Delete a customer");
                System.out.println("4. Back\n");
                
                System.out.print(">> ");
                choice = in.nextInt();
                
                switch (choice) {
                    case 1:
                    menuOptionCreateCustomer();
                    break;
                    case 2:
                    menuOptionUpdateCustomer();
                    break;
                    case 3:
                    menuOptionDeleteCustomer();
                    break;
                    case 4:
                    isExit = true;
                    break;
                    default:
                    System.out.println("Incorrect option. Please try again");
                    break;
                }
            } while (!isExit);    
		} catch (RuntimeException ex) {
			System.err.println(ex.getMessage());
			ex.printStackTrace();
		}
    }

    public static void menuOptionCreateCustomer() {
        //Xiaohui
    }
    
    public static void menuOptionUpdateCustomer() {
        //Xiaohui
    }
    
    public static void menuOptionDeleteCustomer() {
        //Xiaohui
    }

    public static void menuOtherMain() {
        try {	
        
            boolean isExit = false;
            Scanner in = new Scanner(System.in);
            int choice;
            do {
                
                System.out.println("|---------------------------------------------------------------------|");
                System.out.println("| OTHER                                                               |");
                System.out.println("|---------------------------------------------------------------------|\n\n");
                
                System.out.println("1. Job Titles");
                System.out.println("2. Departments");
                System.out.println("3. Room Categories");
                System.out.println("4. Back\n");
                
                System.out.print(">> ");
                choice = in.nextInt();
                
                switch (choice) {
                    case 1:
                    //menuJobTitlesMain();
                    break;
                    case 2:
                    //menuDepartmentsMain();
                    break;
                    case 3:
                    //menuRoomCategoriesMain();
                    break;
                    case 4:
                    isExit = true;
                    break;
                    default:
                    System.out.println("Incorrect option. Please try again");
                    break;
                }
            } while (!isExit);    
		} catch (RuntimeException ex) {
			System.err.println(ex.getMessage());
			ex.printStackTrace();
		}
    }
    
    public static void menuJobTitlesMain() {
        try {	
        
            boolean isExit = false;
            Scanner in = new Scanner(System.in);
            int choice;
            do {
                
                System.out.println("|---------------------------------------------------------------------|");
                System.out.println("| JOB TITLES                                                          |");
                System.out.println("|---------------------------------------------------------------------|\n\n");
                
                System.out.println("1. Create a job title");
                System.out.println("2. Update a job title");
                System.out.println("3. Delete a job title");
                System.out.println("4. Back\n");
                
                System.out.print(">> ");
                choice = in.nextInt();
                
                switch (choice) {
                    case 1:
                    //menuOptionCreateJobTitle();
                    break;
                    case 2:
                    //menuOptionUpdateJobTitle();
                    break;
                    case 3:
                    //menuOptionDeleteJobTitle();
                    break;
                    case 4:
                    isExit = true;
                    break;
                    default:
                    System.out.println("Incorrect option. Please try again");
                    break;
                }
            } while (!isExit);    
		} catch (RuntimeException ex) {
			System.err.println(ex.getMessage());
			ex.printStackTrace();
		}
    }
    
    public static void menuOptionCreateJobTitle() {
        //Abhijeet
    }
    
    public static void menuOptionUpdateJobTitle() {
        //Abhijeet
    }
    
    public static void menuOptionDeleteJobTitle() {
        //Abhijeet
    }
	
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
				while (!scanner.hasNextLine()) {
					System.out.print("===> ");
				}
				response = scanner.nextLine();
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
				while (!scanner.hasNextLine()) {
					System.out.print("===> ");
				}
				response = scanner.nextLine();
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
				while (!scanner.hasNextLine()) {
					System.out.print("===> ");
				}
				response = scanner.nextLine();
			} while (!response.equals("Y") && !response.equals("N"));
			
			if (response.equals("Y")) {
				if (InformationProcessing.deleteRoomCategoriesServices(roomCategoriesServices)) {
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
