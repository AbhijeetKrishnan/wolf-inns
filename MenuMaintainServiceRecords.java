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
                System.out.println("3. Back\n");
                
                System.out.print(">> ");
                choice = in.nextInt();
                
                switch (choice) {
                    case 1:
                    menuServicesMain();
                    break;
                    case 2:
                    //menuServiceRecordsMain();
                    break;
                    case 3:
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
                System.out.println("4. Back\n");
                
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
}
