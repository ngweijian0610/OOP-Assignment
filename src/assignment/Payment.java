package assignment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Payment {
    // data properties
    protected final String paymentID;
    protected double paidAmount;
    protected String paymentStatus;
    protected LocalDate paymentDate;
    
    protected static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    // constructors
    protected Payment(){
        this(0.0);
    }
    protected Payment(double paidAmount){
        this.paymentID = IDGenerator.generate("PAY");
        this.paidAmount = paidAmount;
        this.paymentStatus = "Pending";
        this.paymentDate = LocalDate.now();
    }
    
    // getter
    public String getPaymentID(){
        return paymentID;
    }
    public double getPaidAmount(){
        return paidAmount;
    }
    public String getPaymentStatus(){
        return paymentStatus;
    }
    public LocalDate getPaymentDate(){
        return paymentDate;
    }
    public String getFormattedPaymentDate() {
        return paymentDate.format(DATE_FORMATTER);
    }
    
    // setter
    public void setPaidAmount(double paidAmount){
        if (paidAmount < 0) {
            throw new IllegalArgumentException("Payment paidAmount cannot be negative");
        }
        this.paidAmount = paidAmount;
    }
    public void setPaymentStatus(String paymentStatus){
        if (paymentStatus == null || paymentStatus.trim().isEmpty()) {
            throw new IllegalArgumentException("Payment status cannot be null or empty");
        }
        this.paymentStatus = paymentStatus;
    }
    
    // other methods
    public abstract String getPaymentType();
    
    public abstract void processPayment();
    
    public void refund() {
        this.paidAmount = 0.0;
        this.paymentStatus = "Refunded";
    }
    
    public String toString() {
        return "Payment ID: " + paymentID +
               "\nPaid Amount: " + String.format("RM%.2f", paidAmount) +
               "\nMethod: " + getPaymentType() +
               "\nStatus: " + paymentStatus +
               "\nDate: " + getFormattedPaymentDate();
    }
}
