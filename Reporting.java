import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;
import java.sql.*;

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
		
		String sqlStatement = 
              "SELECT total_occupancy, (total_occupancy/total_rooms) AS percentage_occupied\n"
            + "FROM\n"
            + "(\n"
			+   "SELECT (COUNT(roomNumber)*(DATEDIFF('" + endDate + "', '" + beginDate + "')+1)) AS total_rooms FROM rooms\n"
            + ") AS avail,\n"
			+ "(\n"
            +   "SELECT SUM(occupied_room_days) AS total_occupancy FROM\n"
			+   "(\n"
            +       "SELECT SUM(DATEDIFF(checkoutDate, checkinDate)+1) AS occupied_room_days FROM stays\n"
            +       "WHERE '" + beginDate + "'=checkinDate AND '" + endDate + "'=checkoutDate\n"
			+       "UNION\n"
			+       "SELECT SUM(DATEDIFF('" + endDate + "', '" + beginDate + "')+1) AS occupied_room_days FROM stays\n"
            +       "WHERE checkinDate<='" + beginDate + "' AND '" + endDate + "' < checkoutDate\n"
			+       "UNION\n"
			+       "SELECT SUM(DATEDIFF('" + endDate + "', checkinDate)+1) AS occupied_room_days FROM stays\n"
            +       "WHERE '" + beginDate + "'<checkinDate AND checkinDate<='" + endDate + "' AND '" + endDate + "'<=checkoutDate\n"
			+       "UNION\n"
			+       "SELECT SUM(DATEDIFF(checkoutDate, '" + beginDate + "')+1) AS occupied_room_days FROM stays\n"
            +       "WHERE checkinDate<'" + beginDate + "' AND '" + beginDate + "'<=checkoutDate AND checkoutDate<='" + endDate + "'\n"
			+       "UNION\n"
			+       "SELECT SUM(DATEDIFF(checkoutDate, checkinDate)+1) AS occupied_room_days FROM stays\n"
            +       "WHERE '" + beginDate + "'<=checkinDate AND checkoutDate<'" + endDate + "'\n"
			+       "UNION\n"
			+       "SELECT SUM(DATEDIFF('" + endDate + "', '" + beginDate + "')+1) AS occupied_room_days FROM stays\n"
            +       "WHERE checkoutDate IS NULL AND checkinDate='" + beginDate + "' AND '" + endDate + "'=CURDATE()\n"
			+       "UNION\n"
			+       "SELECT SUM(DATEDIFF('" + endDate + "', '" + beginDate + "')+1) AS occupied_room_days FROM stays\n"
            +       "WHERE checkoutDate IS NULL AND checkinDate<='" + beginDate + "' AND '" + endDate + "'<CURDATE()\n"
			+       "UNION\n"
			+       "SELECT SUM(DATEDIFF('" + endDate + "', checkinDate)+1) AS occupied_room_days FROM stays\n"
            +       "WHERE checkoutDate IS NULL AND '" + beginDate + "'<checkinDate AND checkinDate<='" + endDate + "' AND '" + endDate + "'<=CURDATE()\n"
			+       "UNION\n"
			+       "SELECT SUM(DATEDIFF(CURDATE(), '" + beginDate + "')+1) AS occupied_room_days FROM stays\n"
            +       "WHERE checkoutDate IS NULL AND checkinDate<'" + beginDate + "' AND '" + beginDate + "'<=CURDATE() AND CURDATE()<='" + endDate + "'\n"
			+       "UNION\n"
			+       "SELECT SUM(DATEDIFF(CURDATE(), checkinDate)+1) AS occupied_room_days FROM stays\n"
            +       "WHERE checkoutDate IS NULL AND '" + beginDate + "'<=checkinDate AND checkinDate<=CURDATE() AND CURDATE()<'" + endDate + "'\n"
            +   ") AS occupancy_union\n"
			+ ") AS occ";
            		
		ArrayList<LinkedHashMap<String,String>> queryResults = DatabaseConnection.resultsToHashMap(sqlStatement);
		easyReport(queryResults);
	}
	
	/**
	 * Report occupancy by city
	 */
	public static void occupancyByCity() {
		
		String sqlStatement = "SELECT avail.city, avail.state, COALESCE(occupied_rooms, 0) AS total_occupancy, (COALESCE(occupied_rooms, 0)/COALESCE(total_rooms, 0)*100) AS percentage_occupied "
			+ "FROM (SELECT  city, state, COUNT(stayId) AS occupied_rooms FROM hotels NATURAL JOIN stays WHERE checkoutDate IS NULL GROUP BY city, state) AS occ "
			+ "RIGHT JOIN (SELECT city, state, COUNT(roomNumber) AS total_rooms FROM hotels NATURAL JOIN rooms GROUP BY city, state) AS avail "
			+ "ON occ.city=avail.city AND occ.state=avail.state ORDER BY avail.state, avail.city";
		
		ArrayList<LinkedHashMap<String,String>> queryResults = DatabaseConnection.resultsToHashMap(sqlStatement);
		
		easyReport(queryResults);
	}
	
	    /**
     /**
     * Return staffId, name, titleDesc, deptDesc, DOB, phone, address, city,
     * state grouped by their title
     *
     * @param tc title code
     */
    public static void reportStaffInformationByTitle() {
        String sqlStatement = "SELECT staffId, name, titleDesc, deptDesc, address, city, state, phone, DOB " +
" FROM (staff NATURAL JOIN job_titles) NATURAL JOIN departments ORDER BY titleCode, staffId;";
       // ArrayList<LinkedHashMap<String, String>> queryResults = DatabaseConnection.resultsToHashMap(sqlStatement);
        //easyReport(queryResults);
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results = null;
        try {
            // ArrayList<Departments> depts = new ArrayList<>(); in case you want to return it!
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sqlStatement);

            results = statement.executeQuery();
            ResultSetMetaData rsMetaData = results.getMetaData();
            int numberOfColumns = rsMetaData.getColumnCount();
            StringBuilder sb = new StringBuilder();
            System.out.println("A list of Staff ordered by their role:\n ");
            for (int i = 1; i <= numberOfColumns; i++) {
                //int width=rsMetaData.getPrecision(i);
                int width =rsMetaData.getColumnDisplaySize(i);
                //sb.append(String.format("| %-25s", rsMetaData.getColumnLabel(i)));
                sb.append(String.format("| %-"+width+"s", rsMetaData.getColumnLabel(i)));
            }
            String str = "-";
       
            String divider = new String(new char[sb.length()+1]).replace("\0", str);
            System.out.println(divider);
            System.out.println(sb + "|");
            System.out.println(divider);
            while (results.next()) {
                //System.out.print("|  ");
                for (int i = 1; i <= numberOfColumns; i++) {
                    
                    int width =rsMetaData.getColumnDisplaySize(i);
                    String columnValue = results.getString(i);
                    //System.out.printf("|%-15s ", columnValue);
                    System.out.printf("|%-"+width+"s ", columnValue);
                }
                System.out.println("|");
                

                /*Departments dept = new Departments();
                dept.setDeptCode(results.getString("deptCode"));
                dept.setDeptDesc(results.getString("deptDesc"));
                depts.add(dept);*/
            }
            System.out.println(divider);

            //return depts;
        } catch (SQLException ex) {
            // Log and return null
            ex.printStackTrace();
            //return null;
        } finally {
            // Attempt to close all resources, ignore failures.
            if (results != null) {
                try {
                    results.close();
                } catch (Exception ex) {
                };
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                }
                ;
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ex) {
                }
            }
        }
    }

    /**
     * Return list of all staff IDs of staff which served a customer on a
     * particular stay
     *
     * @param stayId
     */
    public static void reportStayStaff(int stayId) {
        String stID = Integer.toString(stayId);
        System.out.println("list of all staff IDs of staff which served a customer on Stay with ID =" + stayId + "\n\n");
        String sqlStatement = "SELECT staffId, name, titleDesc, deptDesc, address, city, state, phone, DOB "
                + " FROM staff INNER JOIN job_titles ON staff.titleCode=job_titles.titleCode"
                + " NATURAL JOIN departments NATURAL JOIN service_records WHERE service_records.stayId = \""
                + stID + "\";";
        System.out.println(sqlStatement);
        System.out.print("report Stay Staff =" + stayId + "\n\n");
        ArrayList<LinkedHashMap<String, String>> queryResults = DatabaseConnection.resultsToHashMap(sqlStatement);
        easyReport(queryResults);

    }

    /**
     * Return total revenue earned in the period between startDate and endDate
     * for a given hotel
     *
     * @param hotelId
     * @param startDate
     * @param endDate
     */
    public static void reportRevenue(int hotelId, String startDate, String endDate) {
        System.out.print("total revenue earned in the period between startDate and endDate" + "\n\n");
        String sqlStatement = "SELECT SUM(totalCharges) AS rev FROM stays NATURAL JOIN billing_info "
                + "WHERE (checkoutDate BETWEEN \""
                + startDate
                + "\" AND \""
                + endDate
                + "\") AND hotelId=\"" + hotelId + "\";";
        System.out.println(sqlStatement);
        ArrayList<LinkedHashMap<String, String>> queryResults = DatabaseConnection.resultsToHashMap(sqlStatement);
        easyReport(queryResults);

    }
	
	
}
