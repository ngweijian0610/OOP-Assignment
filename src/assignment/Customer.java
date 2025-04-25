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

        this.customerID = IDGenerator.generate("CUST");

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
    
    public void removeFromCart(int productID) {
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
    
    public void UserAuthentication(){
        int choice;
        Scanner scanner = new Scanner(System.in);
        
        do {            
            DisplayEffect.drawLine();
            System.out.println("    Welcome to Computer Retail System    ");
            DisplayEffect.drawLine();
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("\nEnter your choice: ");
            choice = scanner.nextInt();
            DisplayEffect.clearScreen();

            switch (choice){
                case 1:
                    User user = super.login(userList);
                    if (user != null)
                        customer_menu();
                    break;
                case 2:
                    User newuser = super.register(userList);
                    if (newuser != null)
                        customer_menu();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }
    
    public void customer_menu(){
        int choice;
        do {
            DisplayEffect.clearScreen();
            Scanner scanner = new Scanner(System.in);
            
            DisplayEffect.drawLine();
            System.out.println("    Computer Retail Management System    ");
            DisplayEffect.drawLine();
            System.out.println("1. View Product");
            System.out.println("2. View Cart");
            System.out.println("3. Place Order");
            System.out.println("4. View Order History");
            System.out.println("5. Exit");
            DisplayEffect.drawLine();
            System.out.print("\nEnter your choice: ");
            choice = scanner.nextInt();

            switch (choice){
                case 1:
                    DisplayEffect.clearScreen();
                    Product.getProductDetails();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    System.out.println("Exiting... Thank you!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }
    
    @Override
    public String toString(){
        return super.toString() + ", customerID='" + customerID + '\'';    
    }
}
