package wolf.inns;

import com.bethecoder.ascii_table.ASCIITable;
import com.bethecoder.ascii_table.ASCIITableHeader;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

public class Reporting {

  /**
   * This method gathers data from the database on current hotel occupancy for each hotel in the
   * database and then uses other methods to print the results in an ASCII-based tabular format.
   */
  public static void occupancyByHotel() {
    String sqlStatement =
        "SELECT name AS Name, COALESCE(occupied_rooms, 0) AS \"Total Occupancy\", (COALESCE(occupied_rooms/total_rooms, 0)*100) AS \"Percentage Occupied\" "
            + "FROM (SELECT hotelId, COUNT(*) AS occupied_rooms FROM stays WHERE checkoutDate IS NULL GROUP BY hotelId) AS occ "
            + "RIGHT JOIN (SELECT rooms.hotelId, name, COUNT(roomNumber) AS total_rooms FROM rooms RIGHT JOIN hotels ON rooms.hotelId=hotels.hotelId GROUP BY hotelId) AS avail ON occ.hotelId=avail.hotelId;";

    ArrayList<LinkedHashMap<String, String>> queryResults =
        DatabaseConnection.resultsToHashMap(sqlStatement);

    if (null != queryResults) {
      easyReport(queryResults);
    }
  }

  /** Report occupancy by room type */
  public static void occupancyByRoomType() {
    String sqlStatement =
        "SELECT categoryDesc AS \"Room Type\", COALESCE(occupied_rooms, 0) AS \"Total Occupancy\", (COALESCE(occupied_rooms/total_rooms, 0)*100) AS \"Percentage Occupied\" "
            + "FROM (SELECT  categoryCode, COUNT(stayId) AS occupied_rooms FROM rooms NATURAL JOIN stays WHERE checkoutDate IS NULL GROUP BY categoryCode) AS occ "
            + "RIGHT JOIN (SELECT rooms.categoryCode, COUNT(roomNumber) AS total_rooms, room_categories.categoryDesc FROM rooms RIGHT JOIN room_categories "
            + "ON rooms.categoryCode=room_categories.categoryCode GROUP BY categoryCode) AS avail ON occ.categoryCode=avail.categoryCode;";

    ArrayList<LinkedHashMap<String, String>> queryResults =
        DatabaseConnection.resultsToHashMap(sqlStatement);

    if (null != queryResults) {
      easyReport(queryResults);
    }
  }

  /**
   * This method will convert any ArrayList of query results in LinkedHashMaps into arrays
   * containing the column names and rows of data to be printed by the printTable() method.
   *
   * @param reportData ArrayList<LinkedHashMap<String, String>> Contains the results of the query
   *     with columns as keys in the LinkedHashMap
   */
  public static void easyReport(ArrayList<LinkedHashMap<String, String>> reportData) {

    // Create an array of the columns in the order they were selected
    Set<String> columns = reportData.get(0).keySet();
    String[] headers = columns.toArray(new String[columns.size()]);

    // Create rows of data in a dual-dimensional array
    String[][] data = new String[reportData.size()][columns.size()];

    for (int row = 0; row < reportData.size(); row++) {
      for (int col = 0; col < columns.size(); col++) {
        if (null == reportData.get(row).get(headers[col])) {
          data[row][col] = "";
        } else {
          data[row][col] = reportData.get(row).get(headers[col]);
        }
      }
    }

    printTable(headers, data);
  }

  /**
   * This method will print query results in an ASCII-based tabular format using a ASCII table
   * library.
   *
   * @param headers A String[] array of column headings
   * @param data A dual-dimensional String array of multiple rows and columns of tabular data.
   */
  public static void printTable(String[] headers, String[][] data) {
    ASCIITable.getInstance().printTable(headers, data);
  }

  public static void printTable(ASCIITableHeader[] headerObjects, String[][] data) {
    ASCIITable.getInstance().printTable(headerObjects, data);
  }

  /**
   * Report occupancy by date range
   *
   * @param String startDate: starting date for range
   * @param String endDate: ending date for range
   */
  public static void occupancyByDateRange(String beginDate, String endDate) {

    String sqlStatement =
        "SELECT total_occupancy AS \"Total Occupancy\", (COALESCE(total_occupancy/total_rooms)*100) AS \"Percentage Occupied\"\n"
            + "FROM\n"
            + "(\n"
            + "SELECT (COUNT(roomNumber)*(DATEDIFF('"
            + endDate
            + "', '"
            + beginDate
            + "')+1)) AS total_rooms FROM rooms\n"
            + ") AS avail,\n"
            + "(\n"
            + "SELECT SUM(occupied_room_days) AS total_occupancy FROM\n"
            + "(\n"
            + "SELECT SUM(DATEDIFF(checkoutDate, checkinDate)+1) AS occupied_room_days FROM stays\n"
            + "WHERE '"
            + beginDate
            + "'=checkinDate AND '"
            + endDate
            + "'=checkoutDate\n"
            + "UNION\n"
            + "SELECT SUM(DATEDIFF('"
            + endDate
            + "', '"
            + beginDate
            + "')+1) AS occupied_room_days FROM stays\n"
            + "WHERE checkinDate<='"
            + beginDate
            + "' AND '"
            + endDate
            + "' < checkoutDate\n"
            + "UNION\n"
            + "SELECT SUM(DATEDIFF('"
            + endDate
            + "', checkinDate)+1) AS occupied_room_days FROM stays\n"
            + "WHERE '"
            + beginDate
            + "'<checkinDate AND checkinDate<='"
            + endDate
            + "' AND '"
            + endDate
            + "'<=checkoutDate\n"
            + "UNION\n"
            + "SELECT SUM(DATEDIFF(checkoutDate, '"
            + beginDate
            + "')+1) AS occupied_room_days FROM stays\n"
            + "WHERE checkinDate<'"
            + beginDate
            + "' AND '"
            + beginDate
            + "'<=checkoutDate AND checkoutDate<='"
            + endDate
            + "'\n"
            + "UNION\n"
            + "SELECT SUM(DATEDIFF(checkoutDate, checkinDate)+1) AS occupied_room_days FROM stays\n"
            + "WHERE '"
            + beginDate
            + "'<=checkinDate AND checkoutDate<'"
            + endDate
            + "'\n"
            + "UNION\n"
            + "SELECT SUM(DATEDIFF('"
            + endDate
            + "', '"
            + beginDate
            + "')+1) AS occupied_room_days FROM stays\n"
            + "WHERE checkoutDate IS NULL AND checkinDate='"
            + beginDate
            + "' AND '"
            + endDate
            + "'=CURDATE()\n"
            + "UNION\n"
            + "SELECT SUM(DATEDIFF('"
            + endDate
            + "', '"
            + beginDate
            + "')+1) AS occupied_room_days FROM stays\n"
            + "WHERE checkoutDate IS NULL AND checkinDate<='"
            + beginDate
            + "' AND '"
            + endDate
            + "'<CURDATE()\n"
            + "UNION\n"
            + "SELECT SUM(DATEDIFF('"
            + endDate
            + "', checkinDate)+1) AS occupied_room_days FROM stays\n"
            + "WHERE checkoutDate IS NULL AND '"
            + beginDate
            + "'<checkinDate AND checkinDate<='"
            + endDate
            + "' AND '"
            + endDate
            + "'<=CURDATE()\n"
            + "UNION\n"
            + "SELECT SUM(DATEDIFF(CURDATE(), '"
            + beginDate
            + "')+1) AS occupied_room_days FROM stays\n"
            + "WHERE checkoutDate IS NULL AND checkinDate<'"
            + beginDate
            + "' AND '"
            + beginDate
            + "'<=CURDATE() AND CURDATE()<='"
            + endDate
            + "'\n"
            + "UNION\n"
            + "SELECT SUM(DATEDIFF(CURDATE(), checkinDate)+1) AS occupied_room_days FROM stays\n"
            + "WHERE checkoutDate IS NULL AND '"
            + beginDate
            + "'<=checkinDate AND checkinDate<=CURDATE() AND CURDATE()<'"
            + endDate
            + "'\n"
            + ") AS occupancy_union\n"
            + ") AS occ";

    ArrayList<LinkedHashMap<String, String>> queryResults =
        DatabaseConnection.resultsToHashMap(sqlStatement);

    if (null != queryResults) {
      easyReport(queryResults);
    }
  }

  /** Report occupancy by city */
  public static void occupancyByCity() {

    String sqlStatement =
        "SELECT avail.city AS City, avail.state AS State, COALESCE(occupied_rooms, 0) AS \"Total Occupancy\", (COALESCE(occupied_rooms/total_rooms, 0)*100) AS \"Percentage Occupied\" "
            + "FROM (SELECT  city, state, COUNT(stayId) AS occupied_rooms FROM hotels NATURAL JOIN stays WHERE checkoutDate IS NULL GROUP BY city, state) AS occ "
            + "RIGHT JOIN (SELECT city, state, COUNT(roomNumber) AS total_rooms FROM hotels NATURAL JOIN rooms GROUP BY city, state) AS avail "
            + "ON occ.city=avail.city AND occ.state=avail.state ORDER BY avail.state, avail.city";

    ArrayList<LinkedHashMap<String, String>> queryResults =
        DatabaseConnection.resultsToHashMap(sqlStatement);

    if (null != queryResults) {
      easyReport(queryResults);
    }
  }

  /** Report staff information by title */
  public static void reportStaffInformationByTitle() {

    String sqlStatement =
        "SELECT staffId AS \"Staff Id\", name AS Name, titleDesc AS \"Job Title\", deptDesc AS Department, address AS Address, city AS City, state AS State, phone AS Phone, DOB "
            + " FROM (staff NATURAL JOIN job_titles) NATURAL JOIN departments ORDER BY titleCode, staffId;";

    ArrayList<LinkedHashMap<String, String>> queryResults =
        DatabaseConnection.resultsToHashMap(sqlStatement);

    if (null != queryResults) {
      easyReport(queryResults);
    }
  }

  /**
   * Return list of all staff IDs of staff which served a customer on a particular stay
   *
   * @param stayId
   */
  public static void reportStayStaff(int stayId) {
    String stID = Integer.toString(stayId);

    String sqlStatement =
        "SELECT staffId AS \"Staff Id\", name AS Name, titleDesc AS \"Job Title\", deptDesc AS Department, address AS Address, city AS City, state AS State, phone AS Phone, DOB "
            + " FROM staff INNER JOIN job_titles ON staff.titleCode=job_titles.titleCode"
            + " NATURAL JOIN departments NATURAL JOIN service_records WHERE service_records.stayId = \""
            + stID
            + "\";";

    ArrayList<LinkedHashMap<String, String>> queryResults =
        DatabaseConnection.resultsToHashMap(sqlStatement);

    if (null != queryResults) {
      easyReport(queryResults);
    }
  }

  /**
   * Return total revenue earned in the period between startDate and endDate for a given hotel
   *
   * @param hotelId2
   * @param startDate
   * @param endDate
   */
  public static void reportRevenue(int hotelId, String startDate, String endDate) {
    System.out.print(
        "Total revenue earned in the period between " + startDate + " and " + endDate + "\n");
    String sqlStatement =
        "SELECT SUM(totalCharges) AS \"Revenue $\" FROM stays NATURAL JOIN billing_info "
            + "WHERE (checkoutDate BETWEEN \""
            + startDate
            + "\" AND \""
            + endDate
            + "\") AND hotelId=\""
            + hotelId
            + "\";";

    ArrayList<LinkedHashMap<String, String>> queryResults =
        DatabaseConnection.resultsToHashMap(sqlStatement);

    if (null != queryResults) {
      easyReport(queryResults);
    }
  }

  /**
   * Print itemized receipt for a stay
   *
   * @param headers
   * @param receiptData
   */
  public static void printItemizedReceipt(
      String[] headers, ArrayList<ArrayList<String>> receiptData) {

    System.out.println("Itemized Receipt For Your Stay");

    // Create rows of data in a dual-dimensional array
    String[][] data = new String[receiptData.size()][headers.length];

    for (int row = 0; row < receiptData.size(); row++) {
      for (int col = 0; col < headers.length; col++) {
        if (null == receiptData.get(row).get(col)) {
          data[row][col] = "";
        } else {
          data[row][col] = receiptData.get(row).get(col);
        }
      }
    }

    printTable(headers, data);

    System.out.println("Thank you for staying at Wolf Inns.");
  }
}
