package assignment;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.List;

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
    private static List<Order> orderHistory = new ArrayList<>();
    protected String custName;
    protected Cart cart;
    protected String orderDate;

    // constructors
    public Order(){
        this(null);
    }
    
    public Order(Cart cart) {
        this.orderID = IDGenerator.generate("ORD");
        this.date = LocalDate.now();
        this.cart = cart;

        if (cart != null)
            this.totalAmount = cart.getTotal();
        else
            this.totalAmount = 0.0;

        this.orderStatus = OrderStatus.PENDING;
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
    public void processPayment(){
        int choice;
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Select Payment Method");
        DisplayEffect.drawLine();
        System.out.println("1. Card Payment");
        System.out.println("2. TNG E-wallet Payment");
        System.out.println("3. Back");
        System.out.print("\nEnter your choice: ");
        choice = scanner.nextInt();
        
        switch (choice){
            case 1:
                Payment.processCardPayment(this, this.totalAmount);
                orderHistory.add(this);
                break;
            case 2:
                Payment.processTnGEwalletPayment(this, this.totalAmount);
                orderHistory.add(this);
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        
        if (orderStatus == OrderStatus.PAID){
            cart.clearCart();
        }
    }
    
    public boolean cancelOrder() {
        if (orderStatus == OrderStatus.PAID) {
            return false;
        }
        
        this.orderStatus = OrderStatus.CANCELLED;
        return true;
    }
    
    public static void viewOrderHistory() {
        if (orderHistory.isEmpty()) {
            System.out.println("\nNo orders found.");
        } else {
            System.out.println("\n--- Order History ---");
            for (Order order : orderHistory) {
                System.out.println(order);
                System.out.println(); // spacing
            }
        }
    }
    
    public String toString() {
        return  "Order ID: " + orderID +
                "\nOrder Date: " + getFormattedDate() +
                "\nTotal Amount: " + String.format("RM%.2f", totalAmount) +
                "\nOrder Status: " + orderStatus +
                "\nPayment Info:\n" + ((payment != null) ? payment.toString() : "No payment attached");
    }
}
