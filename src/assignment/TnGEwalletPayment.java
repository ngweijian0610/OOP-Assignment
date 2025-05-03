package assignment;

import java.util.Scanner;
import java.util.regex.Pattern;

public class TnGEwalletPayment extends Payment{
    // data properties
    private String phoneNumber;
    private String pin;
    private static final int MAX_PIN_ATTEMPTS = 3;
    private static final String PHONE_REGEX = "01[0,1,2,3,4,6,7,8,9][0-9]{7,8}";
    
    // constructors
    public TnGEwalletPayment(){
        this(" ", " ");
    }
    
    public TnGEwalletPayment(Order order) {
        super(order);
    }
    
    public TnGEwalletPayment(String phoneNumber, String pin){
        super();
        this.phoneNumber = phoneNumber;
        this.pin = pin;
    }
    
    // getters
    public String getPhoneNumber(){
        return phoneNumber;
    }
    
    public String getMaskedPhoneNumber() {
        if (phoneNumber == null || phoneNumber.length() < 3) {
            return "Invalid phone number";
        }
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
    public String getPaymentType() {
        return "TNG eWallet";
    }
    
    public boolean ewalletPayment(double amount){
        if (amount <= 0) {
            System.out.println("Invalid payment amount.");
            return false;
        }
        
        Scanner scanner = new Scanner(System.in);
        
        // Get and validate phone number
        do {
            System.out.print("Enter your phone number (Malaysian format 01XXXXXXXXX): ");
            this.phoneNumber = scanner.nextLine();

            if (!isValidPhoneNumber(phoneNumber)) {
                System.out.println("Invalid phone number format. Please try again.");
            }
        } while (!isValidPhoneNumber(phoneNumber));
        
        // Get and validate pin
        int attempts = 0;
        boolean verified = false;
        
        while (attempts < MAX_PIN_ATTEMPTS){
            System.out.print("Enter your 6-digit PIN: ");
            String inputPin = scanner.nextLine();
            
            if (isValidPin(inputPin)) {
                verified = true;
                break;
            } else {
                System.out.println("Invalid PIN. " + (MAX_PIN_ATTEMPTS - attempts - 1) + " attempts remaining.");
                attempts++;
            }
        }
        
        // Process payment result
        if (!verified) {
            System.out.println("Payment failed after " + MAX_PIN_ATTEMPTS + " incorrect PIN attempts.");
            return false;
        } 
        
        System.out.println();
        System.out.println("Payment of RM " + String.format("%.2f", amount) + " successful.");
        return true;
    }
    
    // Validation methods
    private boolean isValidPhoneNumber(String number) {
        return Pattern.matches(PHONE_REGEX, number);
    }
    
    public boolean isValidPin(String inputPin) {
        return inputPin != null && inputPin.matches("^\\d{6}$");
    }
    
    public String toString() {
        return super.toString() +
               "\nPhone: " + getMaskedPhoneNumber() +
               "\nPIN: " + getMaskedPin();
    }
}
