package assignment;

import java.time.LocalDate;


public class Payment {
    // data properties
    private String paymentID;
    private double paidAmount;
    private LocalDate paymentDate;
    private String paymentStatus;
    private String paymentMethod;
    
    // methods
    public Payment(){
        this(" ", 0.0, " ", " ");
    }
    public Payment(String paymentID, double paidAmount, String paymentStatus, String paymentMethod){
        this.paymentID = paymentID;
        this.paidAmount = paidAmount;
        this.paymentDate = new Date();
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
    }
    
    // getter
    public String getPaymentID(){
        return paymentID;
    }
    public double getPaidAmount(){
        return paidAmount;
    }
    public Date getPaymentDate(){
        return paymentDate;  // for now liddat gua mcm got Date de datatype
    }
    public String getPaymentStatus(){
        return paymentStatus;
    }
    public String getPaymentMethod(){
        return paymentMethod;
    }
    
    // setter
    public void setPaymentID(String paymentID){
        this.paymentID = paymentID;
    }
    public void setPaidAmount(double paidAmount){
        this.paidAmount = paidAmount;
    }
    public void setPaymentStatus(String paymentStatus){
        this.paymentStatus = paymentStatus;
    }
    public void setPaymentMethod(String paymentMethod){
        this.paymentMethod = paymentMethod;
    }
    
    // other methods
    
}
