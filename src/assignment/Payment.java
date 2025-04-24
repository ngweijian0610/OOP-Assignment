package assignment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Payment {
    // data properties
    protected final String paymentID;
    protected LocalDate paymentDate;
    
    protected static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    // constructors
    protected Payment(){
        this.paymentID = IDGenerator.generate("PAY");
        this.paymentDate = LocalDate.now();
    }
    
    // getter
    public String getPaymentID(){
        return paymentID;
    }
    public LocalDate getPaymentDate(){
        return paymentDate;
    }
    public String getFormattedPaymentDate() {
        return paymentDate.format(DATE_FORMATTER);
    }
    
    // setter
    
    // other methods
    public abstract String getPaymentType();
    
    public abstract boolean processPayment(double amount);
    
    public String toString() {
        return "Payment ID: " + paymentID +
               "\nMethod: " + getPaymentType() +
               "\nDate: " + getFormattedPaymentDate();
    }
}
