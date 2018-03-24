import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

import com.bethecoder.ascii_table.ASCIITable;


public class Reporting {

	/**
	 * This method gathers data from the database on current hotel occupancy for each hotel in the 
	 * database and then uses other methods to print the results in an ASCII-based tabular format.
	 */
	public static void occupancyByHotel() {
		String sqlStatement = "SELECT name AS Name, COALESCE(occupied_rooms, 0) AS \"Total Occupancy\", (COALESCE(occupied_rooms, 0)/COALESCE(total_rooms, 0)*100) AS \"Percentage Occupied\" " + 
            "FROM (SELECT hotelId, COUNT(*) AS occupied_rooms FROM stays WHERE checkoutDate IS NULL GROUP BY hotelId) AS occ " +
            "RIGHT JOIN (SELECT rooms.hotelId, name, COUNT(roomNumber) AS total_rooms FROM rooms RIGHT JOIN hotels ON rooms.hotelId=hotels.hotelId GROUP BY hotelId) AS avail ON occ.hotelId=avail.hotelId;";
		
		ArrayList<LinkedHashMap<String,String>> queryResults = DatabaseConnection.resultsToHashMap(sqlStatement);
		
		easyReport(queryResults);
	}
	
	public static void occupancyByRoomType() {
		String sqlStatement = "SELECT categoryDesc AS \"Room Category\", COALESCE(occupied_rooms, 0) AS \"Total Occupancy\", (COALESCE(occupied_rooms, 0)/COALESCE(total_rooms, 0)*100) AS \"Percentage Occupied\" " +
            "FROM (SELECT  categoryCode, COUNT(stayId) AS occupied_rooms FROM rooms NATURAL JOIN stays WHERE checkoutDate IS NULL GROUP BY categoryCode) AS occ " +
            "RIGHT JOIN (SELECT rooms.categoryCode, COUNT(roomNumber) AS total_rooms, room_categories.categoryDesc FROM rooms RIGHT JOIN room_categories " +
            "ON rooms.categoryCode=room_categories.categoryCode GROUP BY categoryCode) AS avail ON occ.categoryCode=avail.categoryCode;";
		
		ArrayList<LinkedHashMap<String,String>> queryResults = DatabaseConnection.resultsToHashMap(sqlStatement);
		
		easyReport(queryResults);
	}
	
	/**
	 * This method will convert any ArrayList of query results in LinkedHashMaps into arrays 
	 * containing the column names and rows of data to be printed by the printTable() method.
	 * @param reportData ArrayList<LinkedHashMap<String, String>> Contains the results of the query with columns as keys in the LinkedHashMap
	 */
	public static void easyReport(ArrayList<LinkedHashMap<String, String>> reportData) {
		
		// Create an array of the columns in the order they were selected
		Set<String> columns = reportData.get(0).keySet();
		String[] headers = columns.toArray(new String[columns.size()]);
		
		// Create rows of data in a dual-dimensional array
		String[][] data = new String[reportData.size()][columns.size()];
		
		for (int row=0; row < reportData.size(); row++) {
			for (int col=0; col < columns.size(); col++) {
				data[row][col] = reportData.get(row).get(headers[col]);
			}
		}
		
		printTable(headers, data);
	}
	
	/**
	 * This method will print query results in an ASCII-based tabular format using a ASCII table library.
	 * @param headers A String[] array of column headings
	 * @param data A dual-dimensional String array of multiple rows and columns of tabular data.
	 */
	public static void printTable(String[] headers, String[][] data) {
		ASCIITable.getInstance().printTable(headers, data);
	}
	
	/**
	 * Report occupancy by date range
	 * @param String startDate: starting date for range
	 * @param String endDate: ending date for range
	 */
	public static void occupancyByDateRange(String beginDate, String endDate) {
		
		String sqlStatement = "SELECT total_occupancy, (total_occupancy/total_rooms) AS percentage_occupied FROM"
			+ "(SELECT (COUNT(roomNumber)*(DATEDIFF(" + endDate + ", " + beginDate + ")+1)) AS total_rooms FROM rooms) AS avail, "
			+ "(SELECT SUM(occupied_room_days) AS total_occupancy FROM"
			+ "(SELECT SUM(DATEDIFF(checkoutDate, checkinDate)+1) AS occupied_room_days FROM stays WHERE " + beginDate + "=checkinDate AND " + endDate + "=checkoutDate"
			+ "UNION"
			+ "SELECT SUM(DATEDIFF(" + endDate + ", " + beginDate + ")+1) AS occupied_room_days FROM stays WHERE checkinDate<=" + beginDate + " AND " + endDate + " < checkoutDate"
			+ "UNION"
			+ "SELECT SUM(DATEDIFF(" + endDate + ", checkinDate)+1) AS occupied_room_days FROM stays WHERE " + beginDate + "<checkinDate AND checkinDate<=" + endDate + " AND " + endDate + "<=checkoutDate"
			+ "UNION"
			+ "SELECT SUM(DATEDIFF(checkoutDate, " + beginDate + ")+1) AS occupied_room_days FROM stays WHERE checkinDate<" + beginDate + " AND " + beginDate + "<=checkoutDate AND checkoutDate<=" + endDate + ""
			+ "UNION"
			+ "SELECT SUM(DATEDIFF(checkoutDate, checkinDate)+1) AS occupied_room_days FROM stays WHERE " + beginDate + "<=checkinDate AND checkoutDate<" + endDate + ""
			+ "UNION"
			+ "SELECT SUM(DATEDIFF(" + endDate + ", " + beginDate + ")+1) AS occupied_room_days FROM stays WHERE checkoutDate IS NULL AND checkinDate=" + beginDate + " AND " + endDate + "=CURDATE()"
			+ "UNION"
			+ "SELECT SUM(DATEDIFF(" + endDate + ", " + beginDate + ")+1) AS occupied_room_days FROM stays WHERE checkoutDate IS NULL AND checkinDate<=" + beginDate + " AND " + endDate + "<CURDATE()"
			+ "UNION"
			+ "SELECT SUM(DATEDIFF(" + endDate + ", checkinDate)+1) AS occupied_room_days FROM stays WHERE checkoutDate IS NULL AND " + beginDate + "<checkinDate AND " + endDate + "<=CURDATE()"
			+ "UNION"
			+ "SELECT SUM(DATEDIFF(CURDATE(), " + beginDate + ")+1) AS occupied_room_days FROM stays WHERE checkoutDate IS NULL AND checkinDate<" + beginDate + " AND CURDATE()<=" + endDate + ""
			+ "UNION"
			+ "SELECT SUM(DATEDIFF(CURDATE(), checkinDate)+1) AS occupied_room_days FROM stays WHERE checkoutDate IS NULL AND " + beginDate + "<=checkinDate AND CURDATE()<" + endDate + ") AS occupancy_union"
			+ ") AS occ";
		
		ArrayList<LinkedHashMap<String,String>> queryResults = DatabaseConnection.resultsToHashMap(sqlStatement);
		
		easyReport(queryResults);
	}
	
	/**
	 * Report occupancy by city
	 */
	public static void occupancyByCity() {
		
		String sqlStatement = "SELECT avail.city, avail.state, COALESCE(occupied_rooms, 0) AS total_occupancy, (COALESCE(occupied_rooms, 0)/COALESCE(total_rooms, 0)*100) AS percentage_occupied"
			+ "FROM (SELECT  city, state, COUNT(stayId) AS occupied_rooms FROM hotels NATURAL JOIN stays WHERE checkoutDate IS NULL GROUP BY city, state) AS occ"
			+ "RIGHT JOIN (SELECT city, state, COUNT(roomNumber) AS total_rooms FROM hotels NATURAL JOIN rooms GROUP BY city, state) AS avail"
			+ "ON occ.city=avail.city AND occ.state=avail.state ORDER BY avail.state, avail.city";
		
		ArrayList<LinkedHashMap<String,String>> queryResults = DatabaseConnection.resultsToHashMap(sqlStatement);
		
		easyReport(queryResults);
	}
}
