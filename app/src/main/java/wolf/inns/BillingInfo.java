package wolf.inns;

import java.util.ArrayList;

public class BillingInfo extends DatabaseObject {
  private int billingId;
  private String responsiblePartySSN;
  private String address;
  private String city;
  private String state;
  private String payMethodCode;
  private String cardNumber;
  private double totalCharges;

  public int getBillingId() {
    return billingId;
  }

  public void setBillingId(int billingId) {
    this.billingId = billingId;
  }

  public String getResponsiblePartySSN() {
    return responsiblePartySSN;
  }

  public void setResponsiblePartySSN(String responsiblePartySSN) {
    this.responsiblePartySSN = responsiblePartySSN;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getPayMethodCode() {
    return payMethodCode;
  }

  public void setPayMethodCode(String payMethodCode) {
    this.payMethodCode = payMethodCode;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public double getTotalCharges() {
    return totalCharges;
  }

  public void setTotalCharges(double totalCharges) {
    this.totalCharges = totalCharges;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof BillingInfo)) {
      return false;
    }
    BillingInfo b = (BillingInfo) o;
    return (billingId == b.billingId
        && responsiblePartySSN.equals(b.responsiblePartySSN)
        && address.equals(b.address)
        && city.equals(b.city)
        && state.equals(b.state)
        && payMethodCode.equals(b.payMethodCode)
        && cardNumber.equals(
            b.cardNumber)); // don't use totalCharges since that is set by system ops
  }

  /** Return array list of field values of object */
  public ArrayList<String> toStringArrayList() {

    ArrayList<String> fieldValues = new ArrayList<String>();

    fieldValues.add(Integer.toString(this.getBillingId()));
    fieldValues.add(this.getResponsiblePartySSN());
    fieldValues.add(this.getAddress());
    fieldValues.add(this.getCity());
    fieldValues.add(this.getState());
    fieldValues.add(this.getPayMethodCode());
    fieldValues.add(this.getCardNumber());
    fieldValues.add(Double.toString(this.getTotalCharges()));

    return fieldValues;
  }

  /** Return array list of field names of class */
  public ArrayList<String> getFieldNamesList() {

    ArrayList<String> fieldNames = new ArrayList<String>();

    fieldNames.add("Billing Id");
    fieldNames.add("Responsible Party SSN");
    fieldNames.add("Address");
    fieldNames.add("City");
    fieldNames.add("State");
    fieldNames.add("Payment Method Code");
    fieldNames.add("Card Number");
    fieldNames.add("Total Charges");

    return fieldNames;
  }

  /** Return an object */
  public static BillingInfo select() {
    ArrayList<BillingInfo> billingInfoList = MaintainBillingRecords.retrieveAllBillingInfos();
    ArrayList<DatabaseObject> databaseObjectList =
        (ArrayList<DatabaseObject>) ((ArrayList<?>) billingInfoList);

    BillingInfo selectedRecord =
        (BillingInfo) MenuUtilities.paginatedRecordSelection(databaseObjectList);

    return selectedRecord;
  }
}
