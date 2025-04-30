package assignment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.ArrayList;
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
    protected Cart cart;
    private User user;
    private User customerPurchased;
    protected String orderDate;

    // constructors
    public Order(Cart originalCart, String customerUsername) {
        this.orderID = IDGenerator.generate("ORD");
        this.date = LocalDate.now();
        
        // Create a deep copy of the cart
        this.cart = new Cart();
        
        // Copy each item from the original cart to the order's cart
        if (originalCart != null) {
            for (CartItem item : originalCart.getItems()) {
                // Create a copy of each items with the same product & quantity
                this.cart.addItemsSilently(item.getProduct(), item.getQuantity());
            }
            this.totalAmount = originalCart.getTotal();
        } else {
            this.totalAmount = 0.0;
        }
        
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
    
    public User getCustomerPurchased() {
        return customerPurchased;
    }
    
    public Payment getPayment() {
        return payment;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public Cart getCart() {
        return this.cart;
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
    
    public void setCustomerPurchased(User user){
        this.customerPurchased = user;
    }
    
    // other methods
    public boolean processPayment(){
        String choice;
        Scanner scanner = new Scanner(System.in);
        
        DisplayEffect.drawLine();
        System.out.println("          Select Payment Method");
        DisplayEffect.drawLine();
        System.out.println("1. Card Payment");
        System.out.println("2. TNG E-wallet Payment");
        System.out.println("3. Back");
        DisplayEffect.drawLine();
        System.out.print("\nEnter your choice: ");
        choice = scanner.nextLine();
        
        switch (choice){
            case "1":
                Payment.processCardPayment(this, this.totalAmount);
                break;
            case "2":
                Payment.processTnGEwalletPayment(this, this.totalAmount);
                break;
            case "3":
                return false;
            default:
                System.out.println("Invalid choice. Please try again.");
                DisplayEffect.clearScreen();
                processPayment();
        }
        
        return true;
    }
    
    public boolean cancelOrder() {
        if (orderStatus == OrderStatus.PAID) {
            return false;
        }
        
        this.orderStatus = OrderStatus.CANCELLED;
        return true;
    }
    
    public String toString() {
        return  "--- Order Details ---" +
                "\nOrder ID: " + orderID +
                "\nOrder Date: " + getFormattedDate() +
                "\nTotal Amount: " + String.format("RM%.2f", totalAmount) +
                "\nOrder Status: " + orderStatus +
                "\n\n--- Payment Details ---\n" + ((payment != null) ? payment.toString() : "No payment attached");
    }
}
