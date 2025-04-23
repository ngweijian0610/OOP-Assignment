package assignment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Order {
    // data properties
    private final String orderID;
    private LocalDate orderDate;
    private double totalAmount;
    private Payment payment;
    
    public enum OrderStatus { PENDING, PAID, SHIPPED, DELIVERED }
    private OrderStatus orderStatus;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    // constructors
    public Order(){
        this(0.0);
    }
    public Order(double totalAmount) {
        this.orderID = IDGenerator.generate("ORD");
        this.orderDate = LocalDate.now();
        this.totalAmount = totalAmount;
//        this.totalAmount = cart.calculateTotal();
        this.orderStatus = OrderStatus.PENDING;
    }
    public Order(double totalAmount, Payment payment) {
        this(totalAmount);
        this.payment = payment;
        this.orderStatus = OrderStatus.PAID;
    }
    
    // getter
    public String getOrderID() {
        return orderID;
    }
    public LocalDate getOrderDate() {
        return orderDate;
    }
    public String getFormattedOrderDate() {
        return orderDate.format(DATE_FORMATTER);
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
    
    // setter
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
    
    // other methods
    public String toString() {
        return  "Order ID: " + orderID +
                "\nOrder Date: " + getFormattedOrderDate() +
                "\nTotal Amount: " + String.format("RM%.2f", totalAmount) +
                "\nOrder Status: " + orderStatus +
                "\nPayment Info:\n" + ((payment != null) ? payment.toString() : "No payment attached");
    }
}
