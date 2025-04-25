package assignment;

import java.time.LocalDate;
import java.util.Scanner;

public class CardPayment extends Payment {
    // data properties
    private String cardHolderName;
    private String cardNumber;
    private String cvv;
    private LocalDate expiryDate;
    
    // constructors
    public CardPayment(){
        this(" ", " ", " ", LocalDate.now().plusYears(1));
    }
    public CardPayment(String cardHolderName, String cardNumber, String cvv, LocalDate expiryDate){
        super();
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }
    
    // getters
    public String getCardHolderName(){
        return cardHolderName;
    }
    public String getCardNumber() {
        return cardNumber;
    }
    public String getMaskedCardNumber(){
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }
    public String getCVV(){
        return cvv;
    }
    public String getMaskedCVV() {
        return "***";
    }
    public LocalDate getExpiryDate(){
        return expiryDate;
    }
    
    // setters
    public void setCardHolderName(String cardHolderName){
        this.cardHolderName = cardHolderName;
    }
    public void setCardNumber(String cardNumber){
        this.cardNumber = cardNumber;
    }
    public void setCVV(String cvv){
        this.cvv = cvv;
    }
    public void setExpiryDate(LocalDate expiryDate){
        this.expiryDate = expiryDate;
    }
    
    // other methods
    @Override
    public String getPaymentType() {
        return "Card Payment";
    }
    
    @Override
    public boolean processPayment(double amount) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter card holder name: ");
        this.cardHolderName = scanner.nextLine();
        
        do {
            System.out.print("Enter your 16-digit card number: ");
            this.cardNumber = scanner.nextLine();
            
            if (!isValidCardNumber(cardNumber)){
                System.out.println("Invalid card number. Must be 16 digits and Luhn valid. Please try again.");
            }
        } while (!isValidCardNumber(cardNumber));
        
        int attempts = 0;
        boolean verified = false;
        
        while (attempts < 3) {
            System.out.print("Enter your 3-digit CVV: ");
            String inputCVV = scanner.nextLine();
            
            if (verifyCVV(inputCVV)) {
                verified = true;
                break;
            } else {
                System.out.println("Incorrect CVV.");
                attempts++;
            }
        }
            
        if (!verified) {
            System.out.println("Payment failed after 3 incorrect CVV attempts.");
            return false;
        }

        System.out.print("Enter expiry year (YYYY): ");
        int year = scanner.nextInt();
        System.out.print("Enter expiry month (1-12): ");
        int month = scanner.nextInt();
        scanner.nextLine();  // flush

        this.expiryDate = LocalDate.of(year, month, 1);

        if (expiryDate.isBefore(LocalDate.now())) {
            System.out.println("Card expired. Payment failed.");
            return false;
        } else {
            System.out.println("Payment successful.");
            return true;
        }
    }
    
    // Validation methods
    private boolean isValidCardNumber(String cardNum){
        if (cardNum == null || !cardNum.matches("\\d{16}")) return false;

        int sum = 0;
        boolean doubleIt = false;

        for (int i = cardNum.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(cardNum.charAt(i));  // Step 1: Start from the RIGHT-most digit and move LEFT

            if (doubleIt) {  // Step 2: Double every second digit
                digit *= 2;
                if (digit > 9) digit -= 9;  // If the result is more than 9, subtract 9 from it
            }

            sum += digit;  // Step 3: Add ALL the digits together (both changed and unchanged)
            doubleIt = !doubleIt;
        }

        return sum % 10 == 0;  // Step 4: If the total is divisible by 10 (ends in 0), the card is VALID
    }
    
    private boolean verifyCVV(String inputCVV){
        return this.cvv != null && this.cvv.equals(inputCVV) && inputCVV.matches("\\d{3}");
    }
    
    @Override
    public String toString() {
        return super.toString() +
               "\nCard Holder Name: " + cardHolderName +
               "\nCard Number: " + getMaskedCardNumber() +
               "\nCVV: " + getMaskedCVV() +
               "\nExpiry Date: " + expiryDate;
    }
}
