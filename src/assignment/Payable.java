package assignment;

public interface Payable {
    String getPaymentType();
    
    boolean processPayment(double amount);
}
