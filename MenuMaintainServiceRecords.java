import java.util.ArrayList;
import java.util.Scanner;


public class MenuMaintainServiceRecords {
    
    public static void menuMaintainServiceRecordsMain() {
        try {	
        
            boolean isExit = false;
            Scanner in = new Scanner(System.in);
            int choice;
            do {
                
                System.out.println("|---------------------------------------------------------------------|");
                System.out.println("| MAINTAINING SERVICE RECORDS                                         |");
                System.out.println("|---------------------------------------------------------------------|\n\n");
                
                System.out.println("1. Services");
                System.out.println("2. Service Records");
                System.out.println("9. Back\n");
                
                System.out.print(">> ");
                choice = in.nextInt();
                
                switch (choice) {
                    case 1:
                    menuServicesMain();
                    break;
                    case 2:
                    menuServiceRecordsMain();
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

    public static void menuServicesMain() {
        try {	
        
            boolean isExit = false;
            Scanner in = new Scanner(System.in);
            int choice;
            do {
                
                System.out.println("|---------------------------------------------------------------------|");
                System.out.println("| SERVICES                                                            |");
                System.out.println("|---------------------------------------------------------------------|\n\n");
                
                System.out.println("1. Create a service");
                System.out.println("2. Update a service");
                System.out.println("3. Delete a service");
                System.out.println("9. Back\n");
                
                System.out.print(">> ");
                choice = in.nextInt();
                
                switch (choice) {
                    case 1:
                    menuOptionCreateService();
                    break;
                    case 2:
                    menuOptionUpdateService();
                    break;
                    case 3:
                    menuOptionDeleteService();
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
    
    public static void menuOptionCreateService() {
        Services result = null;
        try {
            Scanner in = new Scanner(System.in);
            System.out.print("Service code: ");
            String sc = in.nextLine();
            System.out.print("Service description: ");
            String sd = in.nextLine();
            System.out.print("Charge: ");
            double charge = Double.parseDouble(in.nextLine());
            
            result = InformationProcessing.createService(sc, sd, charge);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        
        if (result != null) {
            System.out.println("Service created successfully");
        } else {
            System.out.println("There was an error");
        }
    }
    
    public static void menuOptionUpdateService() {
        boolean result = false;
        try {
            Scanner in = new Scanner(System.in);
            System.out.print("Old Service code: ");
            String osc = in.nextLine();
            System.out.print("New Service code: ");
            String nsc = in.nextLine();
            System.out.print("New Service description: ");
            String sd = in.nextLine();
            System.out.print("New Charge: ");
            double charge = Double.parseDouble(in.nextLine());
            
            result = InformationProcessing.updateService(osc, nsc, sd, charge);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        
        if (result) {
            System.out.println("Service updated successfully");
        } else {
            System.out.println("There was an error");
        }
    }
    
    public static void menuOptionDeleteService() {
        boolean result = false;
        try {
            Scanner in = new Scanner(System.in);
            System.out.print("Service code: ");
            String sc = in.nextLine();
            
            result = InformationProcessing.deleteService(sc);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        
        if (result) {
            System.out.println("Service deleted successfully");
        } else {
            System.out.println("There was an error");
        }
    }
private static void menuServiceRecordsMain() {
        try {

            boolean isExit = false;
            Scanner in = new Scanner(System.in);
            int choice;
            do {

                System.out.println("|---------------------------------------------------------------------|");
                System.out.println("| SERVICE RECORDS                                                     |");
                System.out.println("|---------------------------------------------------------------------|\n\n");

                System.out.println("1. Create a service record");
                System.out.println("2. Update a service record");
                System.out.println("3. List services used during a stay");
                System.out.println("9. Back\n");

                System.out.print(">> ");
                choice = in.nextInt();

                switch (choice) {
                    case 1:
                        menuOptionCreateServiceRecord();
                        break;
                    case 2:
                        menuOptionUpdateServiceRecord();
                        break;
                    case 3:
                        menuOptionListServicesDuringAStay();
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

    private static void menuOptionCreateServiceRecord() {
        System.out.println("|---------------------------------------------------------------------|");
        System.out.println("| CREATE SERVICE RECORD                                               |");
        System.out.println("|---------------------------------------------------------------------|\n\n");
        Scanner scanner = new Scanner(System.in);
        //int stayId, String serviceCode, int staffId, String serviceDate, String serviceTime
        String[] field = {"stayId", "serviceCode", "staffId", "serviceDate", "serviceTime"};
        String[] response = new String[field.length];
        for (int i = 0; i < field.length; i++) {
            System.out.println("Hotel " + field[i]);
            System.out.println("===> ");
            while (!scanner.hasNextLine()) {
                System.out.println("===> ");
            }
            response[i] = scanner.nextLine();
        }
        ServiceRecords h = MaintainingServiceRecords.createServiceRecord(Integer.parseInt(response[0]),
                response[1], Integer.parseInt(response[2]), response[3], response[4]);
        if (null == h) {
            System.out.println("Something unexpected happened while attempting to create the record.");
        } else {
            System.out.println("Record created successfully.");
        }
    }

    private static void menuOptionUpdateServiceRecord() {
        System.out.println("|---------------------------------------------------------------------|");
        System.out.println("| UPDATE ServiceRecords RECORD                                        |");
        System.out.println("|---------------------------------------------------------------------|\n\n");
        ServiceRecords h = ServiceRecords.select();
        if (null == h) {
            System.out.println("No record was selected.");
        } else {
            Scanner scanner = new Scanner(System.in);

            String[] field = {"stayId", "serviceCode", "staffId", "serviceDate", "serviceTime"};
            String[] current = {Integer.toString(h.getStayId()),
                h.getServiceCode(), Integer.toString(h.getStaffId()), h.getServiceDate(),
                h.getServiceTime()};
            String[] update = {Integer.toString(h.getStayId()),
                h.getServiceCode(), Integer.toString(h.getStaffId()), h.getServiceDate(),
                h.getServiceTime()};
            System.out.println("Current hotel id: " + h.getStayId());
            for (int i = 0; i < field.length; i++) {
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
            h.setStayId(Integer.parseInt(update[0]));
            h.setServiceCode(update[1]);
            h.setStaffId(Integer.parseInt(update[2]));
            h.setServiceDate(update[3]);
            h.setServiceTime(update[4]);

            if (MaintainingServiceRecords.updateServiceRecord(h)) {
                System.out.println("Record updated successfully.");
            } else {
                System.out.println("Something unexpected happened while attempting to update the record.");
            }
        }
    }

    private static void menuOptionListServicesDuringAStay() {

        System.out.println("|---------------------------------------------------------------------|");
        System.out.println("| LIST ServiceRecords DURING A STAY                                 |");
        System.out.println("|---------------------------------------------------------------------|\n\n");
        //String s = "|stayId   " + "| serviceCode " + "| staffId " + "|serviceDate " + "| serviceTime";
        String s = String.format("|%-12s|%-12s|%-12s|%-12s|%-12s|","stayId", "serviceCode" , "staffId" ,"serviceDate" , "serviceTime");
        String div = new String(new char[s.length()]).replace("\0", "-");
        int stayId;
        System.out.println("Enter 1 after ===> if you want to continue "); // this is for the bug!
        ServiceRecords h = ServiceRecords.select();
        System.out.println("Enter the stayId? ");
        
        ArrayList<ServiceRecords> result = new ArrayList<ServiceRecords>();

        Scanner scanner = new Scanner(System.in);
        stayId = scanner.nextInt();

        result = MaintainingServiceRecords.retrieveServiceRecordsForStay(stayId);
        if (result == null) {
            System.out.println("No record was selected.");
        } else {
            System.out.println(div);
            System.out.println(s);
            System.out.println(div);
            
            for (ServiceRecords sr : result) {
                System.out.println(sr);
            }
            System.out.println(div);
            System.out.println("Records printed successfully.\n\n");
        }
    }	
	
	
}
