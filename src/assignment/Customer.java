package assignment;

import java.util.*;

public class Customer extends User {
    // data properties
    private String customerID;
    private final Cart cart;
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
    
    public void orderProduct(Product product, int quantity){
        this.orderMenu();
    }
    
    public void removeFromCart(int itemNumber) {
        cart.removeItem(itemNumber);
    }
    
    public void clearCart(){
        cart.clearCart();
    }
    
    public void checkout(){
        System.out.println("Checking out...");
        cart.viewCart();
        System.out.println("Thank you for your purchase, " + username + "!");
        cart.clearCart();
    }
    
    public void customer_menu(){
        int choice;
        do {
            DisplayEffect.clearScreen();
            Scanner scanner = new Scanner(System.in);
            
            DisplayEffect.drawLine();
            System.out.println("    Computer Retail Management System");
            DisplayEffect.drawLine();
            System.out.println("1. View Product");
            System.out.println("2. View Cart");
            System.out.println("3. View Order History");
            System.out.println("4. Logout");
            DisplayEffect.drawLine();
            System.out.print("\nEnter your choice: ");
            choice = scanner.nextInt();

            switch (choice){
                case 1:
                    DisplayEffect.clearScreen();
                    Product.getProductDetails();
                    productSelectionMenu();
                    break;
                case 2:
                    DisplayEffect.clearScreen();
                    viewCart();
                    if (!cart.isEmptyCart())
                        cartSelectionMenu();
                    break;
                case 3:
                    break;
                case 4:
                    System.out.println("\nLogged out... Thank you!");
                    DisplayEffect.clearScreen();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }
    
    public void productSelectionMenu(){
        int choice;
        int quantity;
        String itemID;
        Product productDetails;
        Product product = new Product();
        Scanner scan = new Scanner(System.in);
        
        System.out.println("\n1. Add to cart");
        System.out.println("2. Order product");
        System.out.println("3. Back");
        System.out.print("\nEnter your choice: ");
        choice = scan.nextInt();
        
        switch (choice){
            case 1:
            case 2:
                System.out.print("\nSelect product: ");
                scan.nextLine();
                itemID = scan.nextLine();
                System.out.print("Enter quantity: ");
                quantity = scan.nextInt();
                productDetails = product.mapProductID(itemID);
                
                if (productDetails != null)
                    addToCart(productDetails, quantity);
                else 
                    System.out.println("Product not found!");
                
                if (productDetails != null && choice == 2){
                        orderMenu();
                        break;
                }
                
                DisplayEffect.clearScreen();
                Product.getProductDetails();
                productSelectionMenu();
                    
                break;     
            case 3:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    public void cartSelectionMenu(){
        Scanner scan = new Scanner(System.in);
        int choice;
        int ItemNumber;
        
        System.out.println("1. Place Order");
        System.out.println("2. Remove Item From Cart");
        System.out.println("3. Clear Cart");
        System.out.println("4. Back");
        System.out.print("\nEnter your choice: ");
        choice = scan.nextInt();
        
        switch (choice){
            case 1:
                orderMenu();
                break;
            case 2:
                System.out.print("\nItem to be removed: ");
                ItemNumber = scan.nextInt();
                removeFromCart(ItemNumber);
                DisplayEffect.clearScreen();
                viewCart();
                if (!cart.isEmptyCart())
                    cartSelectionMenu();
                break;
            case 3:
                clearCart();
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    public void orderMenu(){
        int choice;
        Order order = new Order(cart);
        Scanner scanner = new Scanner(System.in);
        
        DisplayEffect.clearScreen();
        DisplayEffect.drawLine();
        System.out.println("               PLACE ORDER");
        DisplayEffect.drawLine();
        viewCart();
        System.out.println("Do you want to confirm your order?");
        System.out.println("1. Yes, place order");
        System.out.println("2. No, go back");
        System.out.print("\nEnter your choice: ");
        choice = scanner.nextInt();
        
        switch (choice){
            case 1:
                DisplayEffect.clearScreen();
                order.processPayment();
                break;
            case 2:
                DisplayEffect.clearScreen();
                viewCart();
                if (!cart.isEmptyCart())
                    cartSelectionMenu();
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    @Override
    public String toString(){
        return super.toString() + ", customerID='" + customerID + '\'';    
    }
}
