import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Scanner;
import com.bethecoder.ascii_table.ASCIITable;


public class MenuUtilities {
	
	public static final int PAGINATION_THRESHOLD = 20;
	
	public static int easyList(List<DatabaseObject> objectList, int currentRecordId) {
		
		ArrayList<String> objectFieldNames = objectList.get(0).getFieldNamesList();
		
		objectFieldNames.add(0, "Record Id"); // Add record Id heading
		String[] headers = objectFieldNames.toArray(new String[objectFieldNames.size()]);
		
		// Create rows of records in a dual-dimensional array
		String[][] records = new String[objectList.size()][headers.length];
		
		for (int row=0; row < objectList.size(); row++) {
			records[row][0] = Integer.toString(currentRecordId++); // Add record Id to row
			ArrayList<String> values = objectList.get(row).toStringArrayList();
			
			for (int col=1; col <= values.size(); col++) {
				String value = values.get(col-1);
				
				if (null == value) {
					records[row][col] = "";
				} else {
					records[row][col] = value;	
				}
			}
		}
		
		printTable(headers, records);
		
		return currentRecordId;
	}
	
	/**
	 * This method will print query results in an ASCII-based tabular format using a ASCII table library.
	 * @param headers A String[] array of column headings
	 * @param data A dual-dimensional String array of multiple rows and columns of tabular data.
	 */
	public static void printTable(String[] headers, String[][] data) {
		ASCIITable.getInstance().printTable(headers, data);
	}
	
	public static DatabaseObject paginatedRecordSelection(ArrayList<DatabaseObject> recordList) {
		
		DatabaseObject selectedRecordObject = null;
		
		if (null != recordList && recordList.size() > 0) {
		
	        Scanner scanner = new Scanner(System.in);
			
			int currentRecordId = 1;
			int currentPageNumber = 1;
			int recordListSize = recordList.size();
			
			//Determine number of pages at current threshold
			int numberOfPages = recordListSize/PAGINATION_THRESHOLD;
			if (recordListSize%PAGINATION_THRESHOLD != 0) {numberOfPages += 1;} // Add one more pages if the record list isn't divided evenly
			
			boolean loop = true;
			while (loop) {
				
				System.out.println("Page: [" + currentPageNumber + " of " + numberOfPages + "]");
				if (currentPageNumber < numberOfPages) {
					System.out.println("Enter the record Id of the record you want to work with, press enter to go to next page, or enter 'X' to exit.");
					currentRecordId = easyList(recordList.subList((currentRecordId-1), (currentRecordId-1+PAGINATION_THRESHOLD)), currentRecordId);
					System.out.print("===> ");
					
					String input = scanner.nextLine();
					
					if (input.equals("X")) {
						loop = false;
						
					} else {
						try { // See if it was a valid record selected
							int selectedRecordId = Integer.parseInt(input);
							
							if (selectedRecordId >= 1 && selectedRecordId <= recordListSize) {
								selectedRecordObject = recordList.get(selectedRecordId-1);
								loop = false;
							} else {
								System.out.println(selectedRecordId + " is not a valid record Id.");
							}
							
						} catch (Exception ex) {
							// It is not an integer so move on to the next thing
						}
						
						if (loop) {
							currentPageNumber++;
						}
					}
					
				} else {
					System.out.println("Enter the record Id of the record you want to work with, press enter to restart from page 1, or enter 'X' to exit.");
					currentRecordId = easyList(recordList.subList((currentRecordId-1), recordList.size()), currentRecordId);
					System.out.print("===> ");
					
					String input = scanner.nextLine();
					
					if (input.equals("X")) {
						loop = false;
						
					} else {
						try { // See if it was a valid record selected
							int selectedRecordId = Integer.parseInt(input);
							
							if (selectedRecordId >= 1 && selectedRecordId <= recordListSize) {
								selectedRecordObject = recordList.get(selectedRecordId-1);
								loop = false;
							} else {
								System.out.println(selectedRecordId + " is not a valid record Id.");
							}
							
						} catch (Exception ex) {
							// It is not an integer so move on to the next thing
						}
						
						if (loop) {
							// We haven't exited or chosen a record so start back from page 1 and record Id 1
							currentPageNumber = 1;
							currentRecordId = 1;
						}
					}
				}
				
				System.out.println("\n"); // Add blanks lines between pages
			}
		} else {
			System.out.println("There are no records available.");
		}
		
		return selectedRecordObject;
	}

}
