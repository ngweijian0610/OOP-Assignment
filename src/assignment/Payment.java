package assignment;

import java.time.LocalDate;

public abstract class Payment {
    // data properties
    protected String paymentID;
    protected double amount;
    protected String paymentStatus;
    protected LocalDate paymentDate;
    
    // constructors
    protected Payment(){
        this(" ", 0.0);
    }
    protected Payment(String paymentID, double amount){
        this.paymentID = paymentID;
        this.amount = amount;
        this.paymentStatus = "Pending";
        this.paymentDate = LocalDate.now();
    }
    
    // getter
    public String getPaymentID(){
        return paymentID;
    }
    public double getAmount(){
        return amount;
    }
    public String getPaymentStatus(){
        return paymentStatus;
    }
    public LocalDate getPaymentDate(){
        return paymentDate;
    }
    
    // setter
    public void setPaymentID(String paymentID){
        this.paymentID = paymentID;
    }
    public void setAmount(double amount){
        this.amount = amount;
    }
    public void setPaymentStatus(String paymentStatus){
        this.paymentStatus = paymentStatus;
    }
    
    // other methods
    public abstract String getPaymentType();
    
    public void processPayment() {
        if (this.amount > 0) {
            this.paymentStatus = "Success";
        } else {
            this.paymentStatus = "Failed";
        }
    }
    
    public void refund() {
        this.amount = 0.0;
        this.paymentStatus = "Refunded";
    }
    
    public String getSummary() {
        return "Payment ID: " + paymentID +
               "\nAmount: RM" + amount +
               "\nMethod: " + getPaymentType() +
               "\nStatus: " + paymentStatus +
               "\nDate: " + paymentDate;
    }
}
