package assignment;

public class Customer extends User {
    // data properties
    private String customerID;
    private Cart cart;
    private static int counter = 1;
    
    // Constructor
     public Customer(String username, String password, String email){
        super(username, password, email, "customer");
        this.customerID = generateCustomerID();
        this.cart = new Cart();
    }
    
    public Customer(String username, String password, String email, String customerID){
        super(username, password, email, "customer");
        this.customerID = customerID;
        this.cart = new Cart();
    }
    
    private String generateCustomerID(){
        return "C" + String.format("%04d", counter++);
    }
    
    // getter
    public String getCustomerID(){
        return customerID;
    }
    
    public Cart getCart(){
        return cart;
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
        System.out.println("Thank you for your purchasem, " + username + "!");
        cart.clearCart();
    }
    
    @Override
    public String toString(){
        return super.toString() +
                ", customerID='" + customerID + '\'';    
    }
}
