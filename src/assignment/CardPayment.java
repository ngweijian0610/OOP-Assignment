package assignment;

import java.time.LocalDate;

public class CardPayment extends Payment {
    // data properties
    private String cardHolderName;
    private String cardNumber;
    private String cvv;
    private LocalDate expiryDate;
    
    // constructors
    public CardPayment(){
        this(" ", 0.0, " ", " ", " ", " ");
    }
    public CardPayment(String paymentID, double amount, 
            String cardHolderName, String cardNumber, String cvv, LocalDate expiryDate){
        super(paymentID, amount);
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
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }
    public String getCVV(){
        return cvv;
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
    public String getPaymentType() {
        return "Card Payment";
    }
    
    public void processPayment() {
        boolean validCard = cardNumber != null && cardNumber.length() == 16;
        boolean validCVV = cvv != null && cvv.length() == 3;
        boolean validDate = expiryDate != null && expiryDate.isAfter(LocalDate.now());

        if (validCard && validCVV && validDate) {
            this.paymentStatus = "Success";
        } else {
            this.paymentStatus = "Failed";
        }
    }
}
