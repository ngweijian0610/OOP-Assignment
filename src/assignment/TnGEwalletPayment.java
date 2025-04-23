package assignment;

import java.util.Scanner;
import java.util.regex.Pattern;

public class TnGEwalletPayment extends Payment{
    // data properties
    private String phoneNo;
    private String pin;
    
    // constructors
    public TnGEwalletPayment(){
        this(" ", 0.0, " ", " ");
    }
    public TnGEwalletPayment(String paymentID, double amount, String phoneNo, String pin){
        super(paymentID, amount);
        this.phoneNo = phoneNo;
        this.pin = pin;
    }
    
    // getters
    public String getPhoneNo(){
        return phoneNo;
    }
    // dk wan bo
//    public String getMaskedPhoneNumber() {
//        return "*******" + phoneNo.substring(phoneNo.length() - 4);
//    }
    public String getPin(){
        return pin;
    }
    // dk wan bo
//    public String getMaskedPin(){
//        return "******";
//    }
    
    // setters
    public void setPhoneNo(String phoneNo){
        this.phoneNo = phoneNo;
    }
    public void setPin(String pin){
        this.pin = pin;
    }
    
    // other methods
    public String getPaymentType() {
        return "TNG eWallet";
    }
    
    public void processPayment(){
        boolean validPhone = phoneNo != null && phoneNo.matches("\\d{10,12}");
        boolean validPin = pin != null && pin.matches("\\d{6}");

        if (validPhone && validPin && amount > 0) {
            this.paymentStatus = "Success";
        } else {
            this.paymentStatus = "Failed";
        }
    }
    
    public String toString() {
        return super.toString() +
               "\nPhone: " + getMaskedPhoneNumber() +
               "\nPIN: " + getPin();
    }
}
