package wolf.inns;

import java.util.Scanner;

public class WolfInns {

  public static void main(String[] args) {

    try {

      boolean isExit = false;
      Scanner in = new Scanner(System.in);
      String choice;
      do {

        System.out.println(
            "|---------------------------------------------------------------------|");
        System.out.println(
            "| WOLF INNS HOTEL MANAGEMENT SYSTEM                                   |");
        System.out.println(
            "|---------------------------------------------------------------------|\n\n");

        System.out.println("1. Information Processing");
        System.out.println("2. Maintaining Service Records");
        System.out.println("3. Maintaining Billing Accounts");
        System.out.println("4. Reports");
        System.out.println("Q. Exit\n");

        System.out.print(">> ");
        choice = in.next();

        switch (choice) {
          case "1":
            MenuInformationProcessing.menuInformationProcessingMain();
            break;
          case "2":
            MenuMaintainServiceRecords.menuMaintainServiceRecordsMain();
            break;
          case "3":
            MenuMaintainBillingRecords.menuMaintainBillingRecordsMain();
            break;
          case "4":
            MenuReporting.menuReportingMain();
            break;
          case "Q":
            isExit = true;
            System.out.println("Exiting...");
            break;
          default:
            System.out.println("Incorrect option. Please try again");
            break;
        }
      } while (!isExit);
      in.close();
    } catch (RuntimeException ex) {
      System.err.println(ex.getMessage());
      ex.printStackTrace();
    }
  }
}
