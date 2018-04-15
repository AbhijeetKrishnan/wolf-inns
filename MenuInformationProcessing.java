import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class MenuInformationProcessing {
    
    public static void menuInformationProcessingMain() {
        try {	
        
            boolean isExit = false;
            Scanner in = new Scanner(System.in);
            int choice;
            do {
                
                System.out.println("+---------------------------------------------------------------------+");
                System.out.println("| INFORMATION PROCESSING                                              |");
                System.out.println("+---------------------------------------------------------------------+\n\n");
                
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
                    menuStaffMain();
                    break;
                    case 4:
                    menuServiceStaffMain();
                    break;
                    case 5:
                    menuCustomersMain();
                    break;
                    case 6:
                    menuStaysMain();
                    break;
                    case 7:
                    menuOptionSearchAvailableRooms();
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
                
                System.out.println("+---------------------------------------------------------------------+");
                System.out.println("| HOTELS                                                              |");
                System.out.println("+---------------------------------------------------------------------+\n\n");
                
                System.out.println("1. Create a hotel");
                System.out.println("2. Update a hotel");
                System.out.println("3. Delete a hotel");
                System.out.println("9. Back\n");
                
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

	public static void menuOptionCreateHotel() {
		System.out.println("+---------------------------------------------------------------------+");
		System.out.println("| CREATE HOTEL RECORD                                                 |");
		System.out.println("+---------------------------------------------------------------------+\n\n");
		Scanner scanner = new Scanner(System.in);
		String[] field = {"Name", "Address", "City", "State", "Phone", "Manager Id"};
		String[] response = new String[6];
		for (int i = 0; i < 6; i++) {
			System.out.println("Hotel " + field[i]);
			System.out.print("===> ");
			while (!scanner.hasNext()) {
				System.out.print("===> ");
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
		System.out.println("+---------------------------------------------------------------------+");
		System.out.println("| UPDATE HOTEL RECORD                                                 |");
		System.out.println("+---------------------------------------------------------------------+\n\n");
		Hotels h = Hotels.select();
		if (null == h) {
			System.out.println("No record was selected.");
		} else {
			Scanner scanner = new Scanner(System.in);
			String[] field = {"Name", "Address", "City", "State", "Phone", "Manager Id"};
			String[] current = {h.getName(), h.getAddress(), h.getCity(), h.getState(), h.getPhone(), Integer.toString(h.getManagerId())};
			String[] update = {h.getName(), h.getAddress(), h.getCity(), h.getState(), h.getPhone(), Integer.toString(h.getManagerId())};
			System.out.println("Current hotel id: " + h.getHotelId());
			for (int i = 0; i < 6; i++) {
				System.out.println("Current hotel " + field[i] + ": " + current[i]);
				System.out.println("Do you want to update this field? (Y/N)");
				String response = "";
				do {
					System.out.print("===> ");
					while (!scanner.hasNext()) {
						System.out.print("===> ");
					}
					response = scanner.nextLine();
				} while (!response.equals("Y") && !response.equals("N"));
				if (response.equals("Y")) {
					System.out.print("===> ");
					while (!scanner.hasNext()) {
						System.out.print("===> ");
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
		System.out.println("+---------------------------------------------------------------------+");
		System.out.println("| DELETE HOTEL RECORD                                                 |");
		System.out.println("+---------------------------------------------------------------------+\n\n");
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
				while (!scanner.hasNext()) {
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
                
                System.out.println("+---------------------------------------------------------------------+");
                System.out.println("| ROOMS                                                               |");
                System.out.println("+---------------------------------------------------------------------+\n\n");
                
                System.out.println("1. Create a room");
                System.out.println("2. Update a room");
                System.out.println("3. Delete a room");
                System.out.println("9. Back\n");
                
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
    
    public static void menuOptionCreateRoom() {
        try {
            Scanner in = new Scanner(System.in);
            System.out.print("Hotel ID: ");
            int hotelId = Integer.parseInt(in.nextLine());
            System.out.print("Room Number: ");
            String roomNumber = in.nextLine();
            System.out.print("Max. Allowed Occupancy: ");
            int maxAllowedOcc = Integer.parseInt(in.nextLine());
            System.out.print("Rate: ");
            double rate = Double.parseDouble(in.nextLine());
            System.out.print("Category Code: ");
            String categoryCode = in.nextLine();
            System.out.print("Available: ");
            String available = in.nextLine();
            Rooms r = InformationProcessing.createRoom(hotelId, roomNumber, maxAllowedOcc, rate, categoryCode, available);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        
        System.out.println("Room created successfully");
    }
    
    public static void menuOptionUpdateRoom() {
        try {
            Scanner in = new Scanner(System.in);
            System.out.print("Hotel ID: ");
            int hotelId = Integer.parseInt(in.nextLine());
            System.out.print("Room Number: ");
            String roomNumber = in.nextLine();
            System.out.print("Max. Allowed Occupancy: ");
            int maxAllowedOcc = Integer.parseInt(in.nextLine());
            System.out.print("Rate: ");
            double rate = Double.parseDouble(in.nextLine());
            System.out.print("Category Code: ");
            String categoryCode = in.nextLine();
            System.out.print("Available: ");
            String available = in.nextLine();
            
            Rooms r = new Rooms();
            r.setHotelId(hotelId);
            r.setRoomNumber(roomNumber);
            r.setMaxAllowedOcc(maxAllowedOcc);
            r.setRate(rate);
            r.setCategoryCode(categoryCode);
            r.setAvailable(available);
            
            boolean result = InformationProcessing.updateRoom(r);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        
        System.out.println("Room updated successfully");
    }
    
    public static void menuOptionDeleteRoom() {
        try {
            Scanner in = new Scanner(System.in);
            System.out.print("Hotel ID: ");
            int hotelId = Integer.parseInt(in.nextLine());
            System.out.print("Room Number: ");
            String roomNumber = in.nextLine();
            
            Rooms r = InformationProcessing.retrieveRoom(hotelId, roomNumber);
            
            boolean result = InformationProcessing.deleteRoom(r);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        
        System.out.println("Room deleted successfully");
    }
	
	
	private static void menuStaffMain() {

        boolean isExit = false;
        Scanner in = new Scanner(System.in);
        int choice;
        do {

            System.out.println("+---------------------------------------------------------------------+");
            System.out.println("| STAFF                                                               |");
            System.out.println("+---------------------------------------------------------------------+\n\n");

            System.out.println("1. Create a staff");
            System.out.println("2. Update a staff");
            System.out.println("3. Delete a staff");
            System.out.println("9. Back\n");

            System.out.print(">> ");
            choice = in.nextInt();

            switch (choice) {
                case 1:
                    menuOptionCreateStaff();
                    break;
                case 2:
                    menuOptionUpdateStaff();
                    break;
                case 3:
                    menuOptionDeleteStaff();
                    break;
                case 9:
                    isExit = true;
                    break;
                default:
                    System.out.println("Incorrect option. Please try again");
                    break;
            }
        } while (!isExit);
    }

    private static void menuOptionCreateStaff() {
        System.out.println("+---------------------------------------------------------------------+");
        System.out.println("| CREATE STAFF RECORD                                                 |");
        System.out.println("+---------------------------------------------------------------------+\n\n");
        Scanner scanner = new Scanner(System.in);
        String[] field = {"Name", "Title", "Department", "Address", "City", "State", "Phone", "DOB"};
        String[] response = new String[field.length];
        for (int i = 0; i < field.length; i++) {
            System.out.println("Staff " + field[i]);
            System.out.print("===> ");
            while (!scanner.hasNext()) {
                System.out.print("===> ");
            }
            response[i] = scanner.nextLine();
        }
        Staff h = InformationProcessing.createStaff(response[0], response[1], response[2], response[3], response[4], response[5], response[6], response[7]);
        if (null == h) {
            System.out.println("Something unexpected happened while attempting to create the record.");
        } else {
            System.out.println("Record created successfully.");
        }
    }

    private static void menuOptionUpdateStaff() {
        System.out.println("+---------------------------------------------------------------------+");
        System.out.println("| UPDATE STAFF RECORD                                                 |");
        System.out.println("+---------------------------------------------------------------------+\n\n");
        Staff c = Staff.select();
        if (null == c) {
            System.out.println("No record was selected.");
        } else {
            Scanner scanner = new Scanner(System.in);
            String[] field = {"Name", "Title", "Department", "Address", "City", "State", "Phone", "DOB"};
            String[] current = {c.getName(), c.getTitleCode(), c.getDeptCode(), c.getAddress(),
                c.getCity(), c.getState(), c.getPhone(), c.getDob()};
            String[] update = {c.getName(), c.getTitleCode(), c.getDeptCode(), c.getAddress(),
                c.getCity(), c.getState(), c.getPhone(), c.getDob()};
            for (int i = 0; i < field.length; i++) {
                System.out.println("Current staff " + field[i] + ": " + current[i]);
                System.out.println("Do you want to update this field? (Y/N)");
                String response = "";
                do {
                    System.out.print("===> ");
                    while (!scanner.hasNext()) {
                        System.out.print("===> ");
                    }
                    response = scanner.nextLine();
                } while (!response.equals("Y") && !response.equals("N"));
                if (response.equals("Y")) {
                    System.out.print("===> ");
                    while (!scanner.hasNext()) {
                        System.out.print("===> ");
                    }
                    update[i] = scanner.nextLine();
                }
            }

            c.setName(update[0]);
            c.setTitleCode(update[1]);
            c.setDeptCode(update[2]);
            c.setAddress(update[3]);

            c.setCity(update[4]);
            c.setState(update[5]);
            c.setPhone(update[6]);
            c.setDob(update[7]);

            if (InformationProcessing.updateStaff(c)) {
                System.out.println("Record updated successfully.");
            } else {
                System.out.println("Something unexpected happened while attempting to update the record.");
            }
        }

    }

    private static void menuOptionDeleteStaff() {
        System.out.println("+---------------------------------------------------------------------+");
        System.out.println("| DELETE STAFF RECORD                                                 |");
        System.out.println("+---------------------------------------------------------------------+\n\n");
        Staff c = Staff.select();
        if (null == c) {
            System.out.println("No record was selected.");
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Attempting to delete customer record with Staff id: " + c.getStaffId());
            System.out.println("Are you sure you want to delete this record? (Y/N)");
            String response = "";
            do {
                System.out.print("===> ");
                while (!scanner.hasNext()) {
                    System.out.print("===> ");
                }
                response = scanner.nextLine();
            } while (!response.equals("Y") && !response.equals("N"));
            if (response.equals("Y")) {
                if (InformationProcessing.deleteStaff(c)) {
                    System.out.println("Record deleted successfully.");
                } else {
                    System.out.println("Something unexpected happened while attempting to delete the record.");
                }
            } else {
                System.out.println("Deletion was cancelled.");
            }
        }
    }

    public static void menuCustomersMain() {
        try {	
        
            boolean isExit = false;
            Scanner in = new Scanner(System.in);
            int choice;
            do {
                
                System.out.println("+---------------------------------------------------------------------+");
                System.out.println("| CUSTOMER                                                            |");
                System.out.println("+---------------------------------------------------------------------+\n\n");
                
                System.out.println("1. Create a customer");
                System.out.println("2. Update a customer");
                System.out.println("3. Delete a customer");
                System.out.println("9. Back\n");
                
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

	public static void menuOptionCreateCustomer() {
		System.out.println("+---------------------------------------------------------------------+");
		System.out.println("| CREATE CUSTOMER RECORD                                              |");
		System.out.println("+---------------------------------------------------------------------+\n\n");
		Scanner scanner = new Scanner(System.in);
		String[] field = {"Name", "DOB", "Phone", "E-Mail"};
		String[] response = new String[4];
		for (int i = 0; i < 4; i++) {
			System.out.println("Customer " + field[i]);
			System.out.print("===> ");
			while (!scanner.hasNext()) {
				System.out.print("===> ");
			}
			response[i] = scanner.nextLine();
		}
		Customers c = InformationProcessing.createCustomer(response[0], response[1], response[2], response[3]);
		if (null == c) {
			System.out.println("Something unexpected happened while attempting to create the record.");
		}
		else {
			System.out.println("Record created successfully.");
		}
	}
    
	public static void menuOptionUpdateCustomer() {
		System.out.println("+---------------------------------------------------------------------+");
		System.out.println("| UPDATE CUSTOMER RECORD                                              |");
		System.out.println("+---------------------------------------------------------------------+\n\n");
		Customers c = Customers.select();
		if (null == c) {
			System.out.println("No record was selected.");
		} else {
			Scanner scanner = new Scanner(System.in);
			String[] field = {"Name", "DOB", "Phone", "E-Mail"};
			String[] current = {c.getName(), c.getDob(), c.getPhone(), c.getEmail()};
			String[] update = {c.getName(), c.getDob(), c.getPhone(), c.getEmail()};
			System.out.println("Current customer id: " + c.getCustomerId());
			for (int i = 0; i < 4; i++) {
				System.out.println("Current customer " + field[i] + ": " + current[i]);
				System.out.println("Do you want to update this field? (Y/N)");
				String response = "";
				do {
					System.out.print("===> ");
					while (!scanner.hasNext()) {
						System.out.print("===> ");
					}
					response = scanner.nextLine();
				} while (!response.equals("Y") && !response.equals("N"));
				if (response.equals("Y")) {
					System.out.print("===> ");
					while (!scanner.hasNext()) {
						System.out.print("===> ");
					}
					update[i] = scanner.nextLine();
				}
			}
			c.setName(update[0]);
			c.setDob(update[1]);
			c.setPhone(update[2]);
			c.setEmail(update[3]);
			if (InformationProcessing.updateCustomer(c)) {
				System.out.println("Record updated successfully.");
			} else {
				System.out.println("Something unexpected happened while attempting to update the record.");
			}
		}
	}    

	public static void menuOptionDeleteCustomer() {
		System.out.println("+---------------------------------------------------------------------+");
		System.out.println("| DELETE CUSTOMER RECORD                                              |");
		System.out.println("+---------------------------------------------------------------------+\n\n");
		Customers c = Customers.select();
		if (null == c) {
			System.out.println("No record was selected.");
		} else {
			Scanner scanner = new Scanner(System.in);			
			System.out.println("Attempting to delete customer record with customer id: " + c.getCustomerId());
			System.out.println("Are you sure you want to delete this record? (Y/N)");			
			String response = "";
			do {
				System.out.print("===> ");
				while (!scanner.hasNext()) {
					System.out.print("===> ");
				}
				response = scanner.nextLine();
			} while (!response.equals("Y") && !response.equals("N"));
			if (response.equals("Y")) {
				if (InformationProcessing.deleteCustomer(c)) {
					System.out.println("Record deleted successfully.");
				} else {
					System.out.println("Something unexpected happened while attempting to delete the record.");
				}
			} else {
				System.out.println("Deletion was cancelled.");
			}
		}
	}

    public static void menuOtherMain() {
        try {	
        
            boolean isExit = false;
            Scanner in = new Scanner(System.in);
            int choice;
            do {
                
                System.out.println("+---------------------------------------------------------------------+");
                System.out.println("| OTHER                                                               |");
                System.out.println("+---------------------------------------------------------------------+\n\n");
                
                System.out.println("1. Job Titles");
                System.out.println("2. Departments");
                System.out.println("3. Room Categories");
                System.out.println("9. Back\n");
                
                System.out.print(">> ");
                choice = in.nextInt();
                
                switch (choice) {
                    case 1:
                    menuJobTitlesMain();
                    break;
                    case 2:
                    menuDepartmentsMain();
                    break;
                    case 3:
                    menuRoomCategoriesMain();
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
    
    public static void menuJobTitlesMain() {
        try {	
        
            boolean isExit = false;
            Scanner in = new Scanner(System.in);
            int choice;
            do {
                
                System.out.println("+---------------------------------------------------------------------+");
                System.out.println("| JOB TITLES                                                          |");
                System.out.println("+---------------------------------------------------------------------+\n\n");
                
                System.out.println("1. Create a job title");
                System.out.println("2. Update a job title");
                System.out.println("3. Delete a job title");
                System.out.println("9. Back\n");
                
                System.out.print(">> ");
                choice = in.nextInt();
                
                switch (choice) {
                    case 1:
                    menuOptionCreateJobTitle();
                    break;
                    case 2:
                    menuOptionUpdateJobTitle();
                    break;
                    case 3:
                    menuOptionDeleteJobTitle();
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
    
    public static void menuOptionCreateJobTitle() {
        boolean result = false;
        try {
            Scanner in = new Scanner(System.in);
            System.out.print("Title code: ");
            String tc = in.nextLine();
            System.out.print("Title description: ");
            String td = in.nextLine();
            
            result = InformationProcessing.createJobTitle(tc, td);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        
        if (result) {
            System.out.println("Job title created successfully");
        } else {
            System.out.println("There was an error");
        }
    }
    
    public static void menuOptionUpdateJobTitle() {
        boolean result = false;
        try {
            Scanner in = new Scanner(System.in);
            System.out.print("Old Title code: ");
            String otc = in.nextLine();
            System.out.print("New Title code: ");
            String ntc = in.nextLine();
            System.out.print("New Title description: ");
            String td = in.nextLine();
            
            result = InformationProcessing.updateJobTitle(otc, ntc, td);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        
        if (result) {
            System.out.println("Job title updated successfully");
        } else {
            System.out.println("There was an error");
        }
    }
    
    public static void menuOptionDeleteJobTitle() {
        boolean result = false;
        try {
            Scanner in = new Scanner(System.in);
            System.out.print("Title code: ");
            String tc = in.nextLine();
            
            result = InformationProcessing.deleteJobTitle(tc);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        
        if (result) {
            System.out.println("Job title deleted successfully");
        } else {
            System.out.println("There was an error");
        }
    }

    public static void menuRoomCategoriesMain() {
        try {	
        
            boolean isExit = false;
            Scanner in = new Scanner(System.in);
            int choice;
            do {
                
                System.out.println("+---------------------------------------------------------------------+");
                System.out.println("| ROOM CATEGORIES                                                     |");
                System.out.println("+---------------------------------------------------------------------+\n\n");
                
                System.out.println("1. Create a room category");
                System.out.println("2. Update a room category");
                System.out.println("3. Delete a room category");
                System.out.println("9. Back\n");
                
                System.out.print(">> ");
                choice = in.nextInt();
                
                switch (choice) {
                    case 1:
                    menuOptionCreateRoomCategory();
                    break;
                    case 2:
                    menuOptionUpdateRoomCategory();
                    break;
                    case 3:
                    menuOptionDeleteRoomCategory();
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
    
	public static void menuOptionCreateRoomCategory() {
		System.out.println("+---------------------------------------------------------------------+");
		System.out.println("| CREATE ROOM CATEGORY RECORD                                         |");
		System.out.println("+---------------------------------------------------------------------+\n\n");
		Scanner scanner = new Scanner(System.in);
		String[] field = {"Category Code", "Category Description"};
		String[] response = new String[2];
		for (int i = 0; i < 2; i++) {
			System.out.println("Room " + field[i]);
			System.out.print("===> ");
			while (!scanner.hasNext()) {
				System.out.print("===> ");
			}
			response[i] = scanner.nextLine();
		}
		RoomCategories r = InformationProcessing.createRoomCategory(response[0], response[1]);
		if (null == r) {
			System.out.println("Something unexpected happened while attempting to create the record.");
		}
		else {
			System.out.println("Record created successfully.");
		}
	}

	public static void menuOptionUpdateRoomCategory() {
		System.out.println("+---------------------------------------------------------------------+");
		System.out.println("| UPDATE ROOM CATEGORY RECORD                                         |");
		System.out.println("+---------------------------------------------------------------------+\n\n");
		RoomCategories r = RoomCategories.select();
		if (null == r) {
			System.out.println("No record was selected.");
		} else {
			Scanner scanner = new Scanner(System.in);
			String[] field = {"Category Description"};
			String[] current = {r.getCategoryDesc()};
			String[] update = {r.getCategoryDesc()};
			System.out.println("Current room category code: " + r.getCategoryCode());
			for (int i = 0; i < 1; i++) {
				System.out.println("Current room " + field[i] + ": " + current[i]);
				System.out.println("Do you want to update this field? (Y/N)");
				String response = "";
				do {
					System.out.print("===> ");
					while (!scanner.hasNext()) {
						System.out.print("===> ");
					}
					response = scanner.nextLine();
				} while (!response.equals("Y") && !response.equals("N"));
				if (response.equals("Y")) {
					System.out.print("===> ");
					while (!scanner.hasNext()) {
						System.out.print("===> ");
					}
					update[i] = scanner.nextLine();
				}
			}
			r.setCategoryDesc(update[0]);
			if (InformationProcessing.updateRoomCategory(r)) {
				System.out.println("Record updated successfully.");
			} else {
				System.out.println("Something unexpected happened while attempting to update the record.");
			}
		}
	}

	public static void menuOptionDeleteRoomCategory() {
		System.out.println("+---------------------------------------------------------------------+");
		System.out.println("| DELETE ROOM CATEGORY RECORD                                         |");
		System.out.println("+---------------------------------------------------------------------+\n\n");
		RoomCategories r = RoomCategories.select();
		if (null == r) {
			System.out.println("No record was selected.");
		} else {
			Scanner scanner = new Scanner(System.in);			
			System.out.println("Attempting to delete room category record with room category code: " + r.getCategoryCode());
			System.out.println("Are you sure you want to delete this record? (Y/N)");			
			String response = "";
			do {
				System.out.print("===> ");
				while (!scanner.hasNext()) {
					System.out.print("===> ");
				}
				response = scanner.nextLine();
			} while (!response.equals("Y") && !response.equals("N"));
			if (response.equals("Y")) {
				if (InformationProcessing.deleteRoomCategory(r)) {
					System.out.println("Record deleted successfully.");
				} else {
					System.out.println("Something unexpected happened while attempting to delete the record.");
				}
			} else {
				System.out.println("Deletion was cancelled.");
			}
		}
	}

    public static void menuServiceStaffMain() {
        try {	
        
            boolean isExit = false;
            Scanner in = new Scanner(System.in);
            int choice;
            do {
                
                System.out.println("+---------------------------------------------------------------------+");
                System.out.println("| SERVICE STAFF                                                       |");
                System.out.println("+---------------------------------------------------------------------+\n\n");
                
                System.out.println("1. Create a service staff member");
                System.out.println("2. Update a service staff member");
                System.out.println("3. Delete a service staff member");
                System.out.println("9. Back\n");
                
                System.out.print(">> ");
                choice = in.nextInt();
                
                switch (choice) {
                    case 1:
                    menuOptionCreateServiceStaff();
                    break;
                    case 2:
                    menuOptionUpdateServiceStaff();
                    break;
                    case 3:
                    menuOptionDeleteServiceStaff();
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

	// Accepts nothing and returns nothing. Fully encapsulated dialog for service staff entry!
	public static void menuOptionCreateServiceStaff() {
		System.out.println("+---------------------------------------------------------------------+");
		System.out.println("| CREATE SERVICE STAFF RECORD                                         |");
		System.out.println("+---------------------------------------------------------------------+\n\n");
		
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
		System.out.println("+---------------------------------------------------------------------+");
		System.out.println("| UPDATE SERVICE STAFF RECORD                                         |");
		System.out.println("+---------------------------------------------------------------------+\n\n");
		
		
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
		System.out.println("+---------------------------------------------------------------------+");
		System.out.println("| DELETE SERVICE STAFF RECORD                                         |");
		System.out.println("+---------------------------------------------------------------------+\n\n");
		
		
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
		System.out.println("+---------------------------------------------------------------------+");
		System.out.println("| ASSIGN SERVICE TO ROOM CATEGORY                                     |");
		System.out.println("+---------------------------------------------------------------------+\n\n");
		
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
		System.out.println("+---------------------------------------------------------------------+");
		System.out.println("| REMOVE SERVICE FROM ROOM CATEGORY                                   |");
		System.out.println("+---------------------------------------------------------------------+\n\n");
		
		
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
	
	
	public static void menuStaysMain() {
        try {	
        
            boolean isExit = false;
            Scanner in = new Scanner(System.in);
            int choice;
            do {
                
                System.out.println("+---------------------------------------------------------------------+");
                System.out.println("| STAYS                                                               |");
                System.out.println("+---------------------------------------------------------------------+\n\n");
                
                System.out.println("1. Checkin");
                System.out.println("2. Checkout");
                System.out.println("3. Reprint Itemized Receipt");
                System.out.println("9. Back\n");
                
                System.out.print(">> ");
                choice = in.nextInt();
                
                switch (choice) {
                     case 1:
                        menuOptionCheckin();
                        break;
                    case 2:
                        menuOptionCheckout();
                        break;
                    case 3:
                        menuOptionReprintItemizedReceipt();
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
	
	
	
	
	public static void menuOptionReprintItemizedReceipt() {
		System.out.println("+---------------------------------------------------------------------+");
		System.out.println("| REPRINT ITEMIZED RECEIPT FOR STAY                                   |");
		System.out.println("+---------------------------------------------------------------------+\n\n");
		
		Stays stay = Stays.select();
		
		if (null == stay) {
			System.out.println("No record was selected.");
		} else {
			HotelStayOperations.generateItemizedReceipt(stay);
		}
	}
	
	private static void menuDepartmentsMain(){
        try {
            boolean isExit = false;
            Scanner in = new Scanner(System.in);
            int choice;
            do {

                System.out.println("|-------------------------------------------------------------------------|");
                System.out.println("| Department                                                              |");
                System.out.println("|-------------------------------------------------------------------------|\n\n");

                System.out.println("1. Create a department");
                System.out.println("2. Update a department");
                System.out.println("3. Delete a department");
                System.out.println("9. Back\n");

                System.out.print(">> ");
                choice = in.nextInt();

                switch (choice) {
                    case 1:
                        menuOptionCreateDepartment();
                        break;
                    case 2:
                        menuOptionUpdateDepartment();
                        break;
                    case 3:
                        menuOptionDeleteDepartment();
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

    private static void menuOptionCreateDepartment() {
        boolean result = false;
        try {
            Scanner in = new Scanner(System.in);
            System.out.print("Department code: ");
            String dc = in.nextLine();
            System.out.print("Department description: ");
            String dd = in.nextLine();

            result = InformationProcessing.createDepartment(dc, dd);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }

        if (result) {
            System.out.println("Department created successfully");
        } else {
            System.out.println("There was an error");
        }
    }

    private static void menuOptionUpdateDepartment() {
        boolean result = false;
        try {
            Scanner in = new Scanner(System.in);
            System.out.print("Old department code: ");
            String otc = in.nextLine();
            System.out.print("New department code: ");
            String ntc = in.nextLine();
            System.out.print("New department description: ");
            String td = in.nextLine();

            result = InformationProcessing.updateDepartment(otc, ntc, td);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        if (result) {
            System.out.println("Department updated successfully");
        } else {
            System.out.println("There was an error");
        }
    }

    private static void menuOptionDeleteDepartment() {
        boolean result = false;
        try {
            Scanner in = new Scanner(System.in);
            System.out.print("department code: ");
            String tc = in.nextLine();
            
            result = InformationProcessing.deleteDepartment(tc);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        
        if (result) {
            System.out.println("Department deleted successfully");
        } else {
            System.out.println("There was an error");
        }
    }
    
    
    public static void menuOptionCheckin() {
    	System.out.println("+---------------------------------------------------------------------+");
	System.out.println("| CHECKIN                                                             |");
	System.out.println("+---------------------------------------------------------------------+\n\n");
		
        Scanner scanner = new Scanner(System.in);
        
        Customers customer = Customers.select();
        
        Hotels hotel = Hotels.select();
        
        RoomCategories roomCategory = RoomCategories.select();  
                
        ArrayList<Rooms> roomsList = HotelStayOperations.retrieveAvailableRooms(hotel.getHotelId(), roomCategory.getCategoryCode());
		ArrayList<DatabaseObject> databaseObjectList = (ArrayList<DatabaseObject>) ((ArrayList<?>) roomsList);		
		Rooms room = (Rooms)MenuUtilities.paginatedRecordSelection(databaseObjectList);
		
		
		System.out.println("Enter number of guests. (" + room.getMaxAllowedOcc() + " max)");
		
		String response = "";
		int occValue = -1;
		do {
			System.out.print("===> ");
			while (!scanner.hasNextLine()) {
				System.out.print("===> ");
			}
			response = scanner.nextLine();
			
			occValue = Integer.parseInt(response);
		} while (occValue < 0 || occValue > room.getMaxAllowedOcc());		
		int numOfGuests = occValue;
		
		
		ServiceStaff servicingStaff = ServiceStaff.select();
		
		
		System.out.println("Enter the SSN of the person responsible for payment (9 characters max).");
		
		response = "";
		do {
			System.out.print("===> ");
			response = scanner.nextLine();
		} while (response.length() < 1 || response.length() > 9);
		
		String responsiblePartySSN = response;
		
        System.out.println("Enter the street address of the person responsible for payment (75 characters max).");
		
		response = "";
		do {
			System.out.print("===> ");
			response = scanner.nextLine();
		} while (response.length() < 1 || response.length() > 75);
		
		String address = response;
		
        System.out.println("Enter the city of the person responsible for payment (50 characters max).");
		
		response = "";
		do {
			System.out.print("===> ");
			response = scanner.nextLine();
		} while (response.length() < 1 || response.length() > 50);
		
		String city = response;
		
        System.out.println("Enter the state of the person responsible for payment (2 character state).");
		
		response = "";
		do {
			System.out.print("===> ");
			response = scanner.nextLine();
		} while (response.length() != 2);
		
		String state = response;
		
				
		PaymentMethods paymentMethod = PaymentMethods.select();
		String cardNumber = "";
		if (paymentMethod.getPayMethodCode().equals("CCWF") || paymentMethod.getPayMethodCode().equals("CCON")) {
			
			System.out.println("Enter the card number for payment (25 characters max).");
			
			response = "";
			do {
				System.out.print("===> ");
				response = scanner.nextLine();
			} while (response.length() < 1 || response.length() > 25);
			cardNumber = response;
		}		
		
		boolean checkinResult = HotelStayOperations.checkinStay(room.getHotelId(), room.getRoomNumber(), customer.getCustomerId(), numOfGuests, servicingStaff.getStaffId(), responsiblePartySSN, address, city, state, paymentMethod.getPayMethodCode(), cardNumber);
		
		if (!checkinResult) {
			System.out.println("Something unexpected happened while attempting to checkin.");
		} else {
			System.out.println("Customer successfully checked in.");
		}	
    }
    
    public static void menuOptionCheckout() {
    	System.out.println("+---------------------------------------------------------------------+");
	System.out.println("| CHECKOUT                                                            |");
	System.out.println("+---------------------------------------------------------------------+\n\n");
	
	ArrayList<Stays> staysList = InformationProcessing.retrieveAllCheckedInStays();
	ArrayList<DatabaseObject> databaseObjectList = (ArrayList<DatabaseObject>) ((ArrayList<?>) staysList);		
	Stays stay = (Stays)MenuUtilities.paginatedRecordSelection(databaseObjectList);
	
	ServiceStaff servicingStaff = ServiceStaff.select();
	
	boolean checkoutResult = HotelStayOperations.checkoutStay(stay, servicingStaff.getStaffId());
	
	if (!checkoutResult) {
		System.out.println("Something unexpected happened while attempting to checkout.");
	} else {
		System.out.println("Customer successfully checked in.");
	}
    }
    
    
    public static void menuOptionSearchAvailableRooms() {
    	System.out.println("+---------------------------------------------------------------------+");
	System.out.println("| SEARCH FOR AVAILABLE ROOMS                                          |");
	System.out.println("+---------------------------------------------------------------------+\n\n");
		
	System.out.println("Select the hotel to search.");
	Hotels hotel = Hotels.select();
	
	System.out.println("Select the category of room requested.");
	RoomCategories roomCategory = RoomCategories.select();
	
	ArrayList<Rooms> availableRooms = HotelStayOperations.retrieveAvailableRooms(hotel.getHotelId(), roomCategory.getCategoryCode());
	
	if (null == availableRooms || availableRooms.size() == 0) {
		System.out.println("No rooms are available.");
	} else {
		ArrayList<DatabaseObject> databaseObjectList = (ArrayList<DatabaseObject>) ((ArrayList<?>) availableRooms);
		MenuUtilities.paginatedRecordSelection(databaseObjectList);
	}
    }
}
