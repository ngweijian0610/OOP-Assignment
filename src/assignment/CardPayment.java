package assignment;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class CardPayment extends Payment {
    // data properties
    private String cardHolderName;
    private String cardNumber;
    private String cvv;
    private LocalDate expiryDate;
    private static final int MAX_CVV_ATTEMPTS = 3;
    
    private static final DateTimeFormatter EXPIRY_DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/yy");
    
    // constructors
    public CardPayment(){
        this(" ", " ", " ", LocalDate.now().plusYears(1));
    }
    
    public CardPayment(Order order) {
        super(order);
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
        if (cardNumber == null || cardNumber.length() < 4) {
            return "Invalid card number";
        }
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }
    
    public String getMaskedCVV() {
        return "***";
    }
    
    public LocalDate getExpiryDate(){
        return expiryDate;
    }
    
    public String getFormattedExpiryDate(){
        return expiryDate.format(EXPIRY_DATE_FORMATTER);
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
    public String getPaymentType() {
        return "Card Payment";
    }
    
    public boolean cardPayment(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid payment amount.");
            return false;
        }
        
        Scanner scanner = new Scanner(System.in);
        
        // Get and validate cardholder name
        do {
            System.out.print("Enter card holder name: ");
            this.cardHolderName = scanner.nextLine();
        } while (!isValidCardHolderName(cardHolderName));
        
        // Get and validate card number
        do {
            System.out.print("Enter your 16-digit card number: ");
            this.cardNumber = scanner.nextLine();
            
            if (!isValidCardNumber(cardNumber)){
                System.out.println("Invalid card number. Must be 16 digits and pass Luhn validation. Please try again.");
            }
        } while (!isValidCardNumber(cardNumber));
        
        // Get and validate expiry date
        boolean validExpiryFormat = false;
        do {
            System.out.print("Enter card expiry date (MM/YY): ");
            String expiryInput = scanner.nextLine();
            
            if (isValidExpiryDateFormat(expiryInput)) {
                try {
                    // Parse the MM/YY date
                    YearMonth expiry = YearMonth.parse(expiryInput, EXPIRY_DATE_FORMATTER);
                    
                    // Convert 2-digit year to 4-digit year
                    int year = expiry.getYear();
                    int month = expiry.getMonthValue();
                    
                    // Create LocalDate with last day of the expiry month
                    this.expiryDate = LocalDate.of(year, month, expiry.lengthOfMonth());
                    
                    // Check if card is expired
                    if (isCardExpired()) {
                        System.out.println("Card expired. Please use another card.");
                        return false;
                    }
                    
                    validExpiryFormat = true;
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid expiry date format. Please use MM/YY format.");
                }
            } else {
                System.out.println("Invalid expiry date format. Please use MM/YY format.");
            }
        } while (!validExpiryFormat);
        
        // Get and validate cvv
        int attempts = 0;
        boolean verified = false;
        
        while (attempts < MAX_CVV_ATTEMPTS) {
            System.out.print("Enter your 3-digit CVV: ");
            String inputCVV = scanner.nextLine();
            
            if (isValidCVV(inputCVV)) {
                verified = true;
                break;
            } else {
                System.out.println("Invalid CVV. " + (MAX_CVV_ATTEMPTS - attempts - 1) + " attempts remaining.");
                attempts++;
            }
        }
        
         // Process payment result
        if (!verified) {
            System.out.println("Payment failed after " + MAX_CVV_ATTEMPTS + " incorrect CVV attempts.");
            return false;
        }
        
        System.out.println();
        System.out.println("Payment of RM " + String.format("%.2f", amount) + " successful.");
        return true;
    }
    
    // Validation methods
    private boolean isValidCardHolderName(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Card holder name cannot be empty.");
            return false;
        } else if (!name.matches("^[A-Za-z\\s]+$")) {
            System.out.println("Card holder name can only contain alphabets and spaces.");
            return false;
        } else if (name.length() > 26) {
            System.out.println("Card holder name cannot exceed 26 characters.");
            return false;
        }
        return true;
    }
    
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
    
    private boolean isValidExpiryDateFormat(String expiryDate) {
        return expiryDate != null && expiryDate.matches("^(0[1-9]|1[0-2])/\\d{2}$");
    }
    
    private boolean isCardExpired() {
        LocalDate currentDate = LocalDate.now();
        return expiryDate.isBefore(currentDate) || expiryDate.isEqual(currentDate);
    }
    
    private boolean isValidCVV(String inputCVV) {
        return inputCVV != null && inputCVV.matches("^\\d{3}$");
    }
    
    @Override
    public String toString() {
        return super.toString() +
               "\nCard Holder Name: " + cardHolderName +
               "\nCard Number: " + getMaskedCardNumber() +
               "\nCVV: " + getMaskedCVV() +
               "\nExpiry Date: " + getFormattedExpiryDate();
    }
}
