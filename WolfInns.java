import java.util.ArrayList;


public class WolfInns {

	public static void main(String[] args) {

        try {		
			
			System.out.println("Menu options examples.");
			
			System.out.println("Test service staff create...");
			MenuInformationProcessing.menuOptionCreateServiceStaff();
			
			System.out.println("Test service staff update...");
			MenuInformationProcessing.updateServiceStaff();
			
			
		} catch (RuntimeException ex) {
			System.err.println(ex.getMessage());
			ex.printStackTrace();
		}
	}	
}
