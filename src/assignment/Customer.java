package assignment;

import java.util.*;

public class Customer extends User {
    // data properties
    private String customerID;
    private Cart cart;
    private List<User> userList = new ArrayList<>();
    
    public Customer(){
        super();
        this.customerID = IDGenerator.generate("CUST");
        this.cart = new Cart();
    }
    
    // Constructor for register
    public Customer(String username, String password, String email, String customerID){
        super(username, password, email, "customer");
        this.customerID = customerID;
        this.cart = new Cart();
    }
    
    public Customer(String username, String password, String email){
        super(username, password, email, "customer");
        this.customerID = IDGenerator.generate("CUST");
        this.cart = new Cart();
    }
     
    // getter
    public String getCustomerID(){
        return customerID;
    }
    
    public Cart getCart(){
        return cart;
    }
    
    public List<User> getUserList(){
        return userList;
    }
    
    // setter
    public void setCustomerID(String customerID){
        this.customerID = customerID;
    }
    
    public void viewCart(){
        cart.viewCart();
    }
    
    public void addToCart(Product product, int quantity){
        cart.addItem(product, quantity);
    }
    
    public void removeFromCart(String productID) {
        cart.removeItem(productID);
    }
    
    public void clearCart(){
        cart.clearCart();
    }
    
    public void checkout(){
        System.out.println("Chcking out...");
        cart.viewCart();
        System.out.println("Thank you for your purchase, " + username + "!");
        cart.clearCart();
    }
    
    public void register(){
        super.register(userList);
    }
    
    public void login(){
        super.login(userList);
    }
    
    @Override
    public String toString(){
        return super.toString() +
                ", customerID='" + customerID + '\'';    
    }
}
