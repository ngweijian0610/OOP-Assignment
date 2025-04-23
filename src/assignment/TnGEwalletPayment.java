package assignment;

import java.util.Scanner;
import java.util.regex.Pattern;

public class TnGEwalletPayment extends Payment{
    // data properties
    private String phoneNumber;
    private String pin;
    
    // constructors
    public TnGEwalletPayment(){
        this(0.0, " ", " ");
    }
    public TnGEwalletPayment(double paidAmount, 
            String phoneNumber, String pin){
        super(paidAmount);
        this.phoneNumber = phoneNumber;
        this.pin = pin;
    }
    
    // getters
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public String getMaskedPhoneNumber() {
        return "*******" + phoneNumber.substring(phoneNumber.length() - 3);
    }
    public String getMaskedPin(){
        return "******";
    }
    
    // setters
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public void setPin(String pin){
        this.pin = pin;
    }
    
    // other methods
    @Override
    public String getPaymentType() {
        return "TNG eWallet";
    }
    
    @Override
    public void processPayment(){
        Scanner scanner = new Scanner(System.in);
        
        do {
            System.out.print("Enter your phone number (Malaysian format 01XXXXXXXXX): ");
            this.phoneNumber = scanner.nextLine();

            if (!isValidPhoneNumber(phoneNumber)) {
                System.out.println("Invalid phone number format. Please try again.");
            }
        } while (!isValidPhoneNumber(phoneNumber));
        
        int attempts = 0;
        boolean verified = false;
        
        while (attempts < 3){
            System.out.print("Enter your 6-digit PIN: ");
            String inputPin = scanner.nextLine();
            if (verifyPin(inputPin)) {
                verified = true;
                break;
            } else {
                System.out.println("Incorrect PIN.");
                attempts++;
            }
        }

        if (verified && paidAmount > 0) {
            this.paymentStatus = "Success";
            System.out.println("Payment successful.");
        } else {
            this.paymentStatus = "Failed";
            System.out.println("Payment failed.");
        }
    }
    
    // Validation methods
    private boolean isValidPhoneNumber(String number) {
        return Pattern.matches("01[0,1,2,3,4,6,7,8,9][0-9]{7,8}", number);
    }

    public boolean verifyPin(String input) {
        return this.pin != null && this.pin.equals(input);
    }
    
    @Override
    public String toString() {
        return super.toString() +
               "\nPhone: " + getMaskedPhoneNumber() +
               "\nPIN: " + getMaskedPin();
    }
}
