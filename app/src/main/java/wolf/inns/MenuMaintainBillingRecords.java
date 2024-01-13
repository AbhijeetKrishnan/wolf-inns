package wolf.inns;

import java.util.Scanner;

public class MenuMaintainBillingRecords {

  public static void menuMaintainBillingRecordsMain() {
    try {

      boolean isExit = false;
      Scanner in = new Scanner(System.in);
      int choice;
      do {

        System.out.println(
            "+---------------------------------------------------------------------+");
        System.out.println(
            "| MAINTAINING BILLING ACCOUNTS                                        |");
        System.out.println(
            "+---------------------------------------------------------------------+\n\n");

        System.out.println("1. Payment Methods");
        System.out.println("2. Billing Info");
        System.out.println("9. Back\n");

        System.out.print(">> ");
        choice = in.nextInt();

        switch (choice) {
          case 1:
            menuPaymentMethodsMain();
            break;
          case 2:
            menuBillingInfoMain();
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

  public static void menuPaymentMethodsMain() {
    try {

      boolean isExit = false;
      Scanner in = new Scanner(System.in);
      int choice;
      do {

        System.out.println(
            "+---------------------------------------------------------------------+");
        System.out.println(
            "| PAYMENT METHODS                                                     |");
        System.out.println(
            "+---------------------------------------------------------------------+\n\n");

        System.out.println("1. Create a payment method");
        System.out.println("2. Update a payment method");
        System.out.println("3. Delete a payment method");
        System.out.println("9. Back\n");

        System.out.print(">> ");
        choice = in.nextInt();

        switch (choice) {
          case 1:
            menuOptionCreatePaymentMethod();
            break;
          case 2:
            menuOptionUpdatePaymentMethod();
            break;
          case 3:
            menuOptionDeletePaymentMethod();
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

  public static void menuBillingInfoMain() {
    try {

      boolean isExit = false;
      Scanner in = new Scanner(System.in);
      int choice;
      do {

        System.out.println(
            "+---------------------------------------------------------------------+");
        System.out.println(
            "| BILLING INFO                                                        |");
        System.out.println(
            "+---------------------------------------------------------------------+\n\n");

        System.out.println("1. Update a billing info");
        System.out.println("9. Back\n");

        System.out.print(">> ");
        choice = in.nextInt();

        switch (choice) {
          case 1:
            menuOptionUpdateBillingInfo();
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

  public static void menuOptionUpdateBillingInfo() {
    System.out.println("+---------------------------------------------------------------------+");
    System.out.println("| UPDATE BILLING INFO RECORD                                          |");
    System.out.println(
        "+---------------------------------------------------------------------+\n\n");
    BillingInfo b = BillingInfo.select();
    if (null == b) {
      System.out.println("No record was selected.");
    } else {
      Scanner scanner = new Scanner(System.in);
      String[] field = {
        "Responsible Party SSN",
        "Address",
        "City",
        "State",
        "Payment Method Code",
        "Card Number",
        "Total Charges"
      };
      String[] current = {
        b.getResponsiblePartySSN(),
        b.getAddress(),
        b.getCity(),
        b.getState(),
        b.getPayMethodCode(),
        b.getCardNumber(),
        Double.toString(b.getTotalCharges())
      };
      String[] update = {
        b.getResponsiblePartySSN(),
        b.getAddress(),
        b.getCity(),
        b.getState(),
        b.getPayMethodCode(),
        b.getCardNumber(),
        Double.toString(b.getTotalCharges())
      };
      System.out.println("Current billing info id: " + b.getBillingId());
      for (int i = 0; i < 7; i++) {
        System.out.println("Current billing info " + field[i] + ": " + current[i]);
        System.out.println("Do you want to update this field? (Y/N)");
        String response = "";
        do {
          System.out.print("===> ");
          while (!scanner.hasNext()) {
            System.out.print("===> ");
          }
          response = scanner.nextLine();
        } while (!response.equals("Y") && !response.equals("N"));
        if (response.equals("Y")) {
          System.out.print("===> ");
          while (!scanner.hasNext()) {
            System.out.print("===> ");
          }
          update[i] = scanner.nextLine();
        }
      }
      b.setResponsiblePartySSN(update[0]);
      b.setAddress(update[1]);
      b.setCity(update[2]);
      b.setState(update[3]);
      b.setPayMethodCode(update[4]);
      b.setCardNumber(update[5]);
      b.setTotalCharges(Double.parseDouble(update[6]));
      if (MaintainBillingRecords.updateBillingInfo(b)) {
        System.out.println("Record updated successfully.");
      } else {
        System.out.println("Something unexpected happened while attempting to update the record.");
      }
    }
  }

  public static void menuOptionCreatePaymentMethod() {
    System.out.println("+---------------------------------------------------------------------+");
    System.out.println("| CREATE PAYMENT METHOD RECORD                                        |");
    System.out.println(
        "+---------------------------------------------------------------------+\n\n");

    Scanner scanner = new Scanner(System.in);

    System.out.println("Enter a payment method code (4 characters max).");

    String response = "";
    do {
      System.out.print("===> ");
      while (!scanner.hasNext()) {
        System.out.print("===> ");
      }
      response = scanner.nextLine();
    } while (response.length() < 1 || response.length() > 4);

    String payMethodCode = response;

    System.out.println("Enter a payment method description (50 characters max).");

    response = "";
    do {
      System.out.print("===> ");
      response = scanner.nextLine();
    } while (response.length() > 50);

    String payMethodDesc = response;

    PaymentMethods paymentMethod =
        MaintainBillingRecords.createPaymentMethod(payMethodCode, payMethodDesc);

    if (null == paymentMethod) {
      System.out.println("Something unexpected happened while attempting to create the record.");
    } else {
      System.out.println("Record created successfully.");
    }
  }

  public static void menuOptionUpdatePaymentMethod() {
    System.out.println("+---------------------------------------------------------------------+");
    System.out.println("| UPDATE PAYMENT METHOD RECORD                                        |");
    System.out.println(
        "+---------------------------------------------------------------------+\n\n");

    PaymentMethods paymentMethod = PaymentMethods.select();

    if (null == paymentMethod) {
      System.out.println("No record was selected.");
    } else {
      Scanner scanner = new Scanner(System.in);

      System.out.println("Current payment method description: " + paymentMethod.getPayMethodDesc());
      System.out.println("Do you want to update this field? (Y/N)");

      String response = "";
      do {
        System.out.print("===> ");
        while (!scanner.hasNext()) {
          System.out.print("===> ");
        }
        response = scanner.nextLine();
      } while (!response.equals("Y") && !response.equals("N"));

      if (response.equals("Y")) {

        response = "";
        do {
          System.out.print("===> ");
          response = scanner.nextLine();
        } while (response.length() > 50);

        paymentMethod.setPayMethodDesc(response);
      }

      if (MaintainBillingRecords.updatePaymentMethod(paymentMethod)) {
        System.out.println("Record updated successfully.");
      } else {
        System.out.println("Something unexpected happened while attempting to update the record.");
      }
    }
  }

  public static void menuOptionDeletePaymentMethod() {
    System.out.println("+---------------------------------------------------------------------+");
    System.out.println("| DELETE PAYMENT METHOD RECORD                                        |");
    System.out.println(
        "+---------------------------------------------------------------------+\n\n");

    PaymentMethods paymentMethod = PaymentMethods.select();

    if (null == paymentMethod) {
      System.out.println("No record was selected.");
    } else {
      Scanner scanner = new Scanner(System.in);

      System.out.println(
          "Attempting to delete payment method record with code: "
              + paymentMethod.getPayMethodCode());
      System.out.println("Are you sure you want to delete this record? (Y/N)");

      String response = "";
      do {
        System.out.print("===> ");
        while (!scanner.hasNext()) {
          System.out.print("===> ");
        }
        response = scanner.nextLine();
      } while (!response.equals("Y") && !response.equals("N"));

      if (response.equals("Y")) {
        if (MaintainBillingRecords.deletePaymentMethod(paymentMethod)) {
          System.out.println("Record deleted successfully.");
        } else {
          System.out.println(
              "Something unexpected happened while attempting to delete the record.");
        }
      } else {
        System.out.println("Deletion was cancelled.");
      }
    }
  }
}
