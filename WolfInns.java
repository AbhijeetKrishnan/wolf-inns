import java.util.ArrayList;


public class WolfInns {

	public static void main(String[] args) {
		try {
			
			// This is sample code which will be completely replaced with actual code to run the application eventually.
			// For now, you can use this to test your method until proper tests are written.
			
			System.out.println("Welcome to Wolf Inns.\n");
			
			System.out.println("We're going to create a new room category for Michael.");
			// Create a new room category
			RoomCategories roomCategory = InformationProcessing.createRoomCategory("MIKE",	"Michael's personal room");
			if (null==roomCategory) {
				System.out.println("Something seems to have happened. Check the logs.");
			} else {
				System.out.println("A new room category has been created with a categoryCode of " + 
				roomCategory.getCategoryCode() + " and a description of " + roomCategory.getCategoryDesc() + ".");
			}
					
			printRoomCategories();	
			
			
			System.out.println("Michael doesn't need his own room category. He's not special. Let's delete his room type.");
			// Delete a room category
			if(InformationProcessing.deleteRoomCategory(roomCategory)) {
				System.out.println("Michael's room category has been deleted successfully.");
			} else {
				System.out.println("Michael's room category has not been deleted. Maybe somebody already checked into it?");
			}			
			
			printRoomCategories();			
			
		} catch (RuntimeException ex) {
			System.err.println(ex.getMessage());
		}

	}
	
	public static void printRoomCategories() {
		// Show all room categories
		ArrayList<RoomCategories> roomCategories = InformationProcessing.retrieveAllRoomCategories();
		
		System.out.println("\nBelow is a listing of all room categories in the database.");
		for (RoomCategories rc : roomCategories) {
			System.out.println(rc.getCategoryCode() + " || " + rc.getCategoryDesc());
		}
		System.out.println();
	}

}
