package assignment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Order {
    // data properties
    private final String orderID;
    private LocalDate date;
    private double totalAmount;
    private Payment payment;
    
    public enum OrderStatus { 
        PENDING, PAID, FAILED, CANCELLED
    }
    private OrderStatus orderStatus;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    // constructors
    public Order(){
        this(null);
    }
    
    public Order(Cart cart) {
        this.orderID = IDGenerator.generate("ORD");
        this.date = LocalDate.now();
        this.totalAmount = (cart != null) ? cart.getTotal() : 0.0;
        this.orderStatus = OrderStatus.PENDING;
    }
    
    public Order(Cart cart, Payment payment) {
        this(cart);
        this.payment = payment;
        if (payment != null) {
            // Process payment with the order amount
            boolean paymentSuccessful = payment.processPayment(this.totalAmount);
            if (paymentSuccessful) {
                this.orderStatus = OrderStatus.PAID;
            }
        }
    }
    
    // getters
    public String getOrderID() {
        return orderID;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public String getFormattedDate() {
        return date.format(DATE_FORMATTER);
    }
    
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
    
    public Payment getPayment() {
        return payment;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    // setters
    public void setTotalAmount(double totalAmount) {
        if (totalAmount >= 0) {
            this.totalAmount = totalAmount;
        } else {
            throw new IllegalArgumentException("Total amount cannot be negative.");
        }
    }
    
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
    
    public void setPayment(Payment payment) {
        this.payment = payment;
        if (payment != null) {
            payment.setOrder(this);
        }
    }
    
    // other methods
    public boolean processPayment() {
        if (payment == null) {
            return false;
        }
        
        boolean paymentSuccessful = payment.processPayment(this.totalAmount);
        if (paymentSuccessful) {
            this.orderStatus = OrderStatus.PAID;
        } else {
            this.orderStatus = OrderStatus.FAILED;
        }
        
        return paymentSuccessful;
    }
    
    public boolean cancelOrder() {
        if (orderStatus == OrderStatus.PAID) {
            // Refund de logic hmm
            return false;
        }
        
        this.orderStatus = OrderStatus.CANCELLED;
        return true;
    }
    
    @Override
    public String toString() {
        return  "Order ID: " + orderID +
                "\nOrder Date: " + getFormattedDate() +
                "\nTotal Amount: " + String.format("RM%.2f", totalAmount) +
                "\nOrder Status: " + orderStatus +
                "\nPayment Info:\n" + ((payment != null) ? payment.toString() : "No payment attached");
    }
}
