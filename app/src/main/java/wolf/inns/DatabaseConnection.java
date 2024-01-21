package wolf.inns;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DatabaseConnection {

  private static final String user = "root"; // TODO: remove this hardcoding
  private static final String dbName = "test";
  private static final String password = "";

  private static final String connectionURL = "jdbc:mariadb://localhost:3306/" + dbName;

  /**
   * This method will create and return a new Connection object that is attached to the database
   * WolfInns database.
   *
   * @throws RuntimeException On an inability to form a connection to the database.
   * @return Connection This will be connected to the WolfInns database upon return.
   */
  public static Connection getConnection() {
    try {
      return DriverManager.getConnection(connectionURL, user, password);
    } catch (SQLException ex) {
      // Throw a RuntimeException to go back to main to be caught so the program
      // exits. We cannot
      // continue.
      throw new RuntimeException("ERROR: Cannot connect to the database.\n" + ex.getMessage());
    }
  }

  /**
   * This method executes a select query and populates each row into a LinkedHashMap which is added
   * to ArrayList to be processed by the calling method.
   *
   * @param sqlStatement String A fully formed SQL SELECT query
   * @return ArrayList<LinkedHashMap<String, String>> A data structure to contain variably sized
   *     results of the SQL SELECT
   */
  public static ArrayList<LinkedHashMap<String, String>> resultsToHashMap(String sqlStatement) {
    ArrayList<LinkedHashMap<String, String>> queryResults =
        new ArrayList<LinkedHashMap<String, String>>();
    Connection connection = null;
    Statement statement = null;
    ResultSet results = null;
    ResultSetMetaData metaData = null;

    try {
      connection = DatabaseConnection.getConnection();
      statement = connection.createStatement();
      results = statement.executeQuery(sqlStatement);
      metaData = results.getMetaData();

      // Create a list of all columns to be used as the keys in the HashMap rows
      ArrayList<String> columns = new ArrayList<String>();
      for (int i = 1; i <= metaData.getColumnCount(); i++) {
        columns.add(metaData.getColumnLabel(i));
      }

      while (results.next()) {
        LinkedHashMap<String, String> row =
            new LinkedHashMap<String, String>(metaData.getColumnCount());
        for (String column : columns) {
          row.put(column, results.getString(column));
        }

        queryResults.add(row);
      }

      if (queryResults.size() == 0) {
        System.out.println("No records were found.");
        return null;
      } else {
        return queryResults;
      }

    } catch (SQLException ex) {
      // Log and return null
      ex.printStackTrace();
      return null;
    } finally {
      // Attempt to close all resources, ignore failures.
      try {
        results.close();
      } catch (Exception ex) {
      }
      ;
      try {
        statement.close();
      } catch (Exception ex) {
      }
      ;
      try {
        connection.close();
      } catch (Exception ex) {
      }
      ;
    }
  }
}
