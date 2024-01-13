package wolf.inns;

import java.util.Scanner;

public class MenuReporting {

  public static void menuReportingMain() {
    try {

      boolean isExit = false;
      Scanner in = new Scanner(System.in);
      int choice;
      do {

        System.out.println(
            "+---------------------------------------------------------------------+");
        System.out.println(
            "| REPORTING                                                           |");
        System.out.println(
            "+---------------------------------------------------------------------+\n\n");
        System.out.println("1. Occupancy grouped by hotel");
        System.out.println("2. Occupancy grouped by room type");
        System.out.println("3. Occupancy given date range");
        System.out.println("4. Occupancy grouped by city");
        System.out.println("5. Staff information ordered by role");
        System.out.println("6. Staff information given stay");
        System.out.println("7. Revenue for given hotel during given date range");
        System.out.println("9. Back\n");

        System.out.print(">> ");
        choice = in.nextInt();

        switch (choice) {
          case 1:
            menuOccupancyByHotelMain();
            break;
          case 2:
            menuOccupancyByRoomTypeMain();
            break;
          case 3:
            menuOccupancyByDateRangeMain();
            break;
          case 4:
            menuOccupancyByCityMain();
            break;
          case 5:
            menuReportStaffInformationByTitleMain();
            break;
          case 6:
            menuReportStayStaffMain();
            break;
          case 7:
            menuReportRevenueMain();
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

  public static void menuOccupancyByHotelMain() {
    System.out.println("+---------------------------------------------------------------------+");
    System.out.println("| OCCUPANCY GROUPED BY HOTEL                                          |");
    System.out.println("+---------------------------------------------------------------------+\n");
    Reporting.occupancyByHotel();
  }

  public static void menuOccupancyByRoomTypeMain() {
    System.out.println("+---------------------------------------------------------------------+");
    System.out.println("| OCCUPANCY GROUPED BY ROOM TYPE                                      |");
    System.out.println("+---------------------------------------------------------------------+\n");
    Reporting.occupancyByRoomType();
  }

  public static void menuOccupancyByDateRangeMain() {
    System.out.println("+---------------------------------------------------------------------+");
    System.out.println("| OCCUPANCY GIVEN DATE RANGE                                          |");
    System.out.println("+---------------------------------------------------------------------+\n");
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter date range for occupancy report.");
    System.out.println("Begin date (format: YYYY-MM-DD)");
    System.out.print("===> ");
    while (!scanner.hasNext()) {
      System.out.print("===> ");
    }
    String begin = scanner.nextLine();
    System.out.println("End date (format: YYYY-MM-DD)");
    System.out.print("===> ");
    while (!scanner.hasNext()) {
      System.out.print("===> ");
    }
    String end = scanner.nextLine();
    Reporting.occupancyByDateRange(begin, end);
  }

  public static void menuOccupancyByCityMain() {
    System.out.println("+---------------------------------------------------------------------+");
    System.out.println("| OCCUPANCY GROUPED BY CITY                                           |");
    System.out.println("+---------------------------------------------------------------------+\n");
    Reporting.occupancyByCity();
  }

  public static void menuReportStaffInformationByTitleMain() {
    System.out.println("+---------------------------------------------------------------------+");
    System.out.println("| STAFF INFORMATION ORDERED BY ROLE                                   |");
    System.out.println("+---------------------------------------------------------------------+\n");
    Reporting.reportStaffInformationByTitle();
  }

  public static void menuReportStayStaffMain() {
    System.out.println("+---------------------------------------------------------------------+");
    System.out.println("| STAFF INFORMATION GIVEN STAY                                        |");
    System.out.println("+---------------------------------------------------------------------+\n");
    Stays stay = Stays.select();
    if (null == stay) {
      System.out.println("No record was selected.");
    } else {
      Reporting.reportStayStaff(stay.getStayId());
    }
  }

  public static void menuReportRevenueMain() {
    System.out.println("+---------------------------------------------------------------------+");
    System.out.println("| REVENUE FOR GIVEN HOTEL DURING GIVEN DATE RANGE                     |");
    System.out.println("+---------------------------------------------------------------------+\n");
    Hotels hotel = Hotels.select();
    if (null == hotel) {
      System.out.println("No record was selected.");
    } else {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Enter date range for revenue report.");
      System.out.println("Begin date (format: YYYY-MM-DD)");
      System.out.print("===> ");
      while (!scanner.hasNext()) {
        System.out.print("===> ");
      }
      String begin = scanner.nextLine();
      System.out.println("End date (format: YYYY-MM-DD)");
      System.out.print("===> ");
      while (!scanner.hasNext()) {
        System.out.print("===> ");
      }
      String end = scanner.nextLine();
      Reporting.reportRevenue(hotel.getHotelId(), begin, end);
    }
  }
}
