package assignment;

import java.util.*;

public class Customer extends User {
    // data properties
    private String customerID;
    private final Cart cart;
    private static List<Order> orderHistory = new ArrayList<>();
    User user = new User();
    
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
    
    public static List<Order> getOrderHistory() {
        return orderHistory;
    }
    
    // setter
    public void setCustomerID(String customerID){
        this.customerID = customerID;
    }
    
    public static void setOrderHistory(List<Order> orders) {
        orderHistory = orders;
    }
    
    public void saveOrder(Order order) {
        orderHistory.add(order);
    }
    
    public void viewCart(){
        cart.viewCart();
    }
    
    public void addToCart(Product product, int quantity){
        cart.addItem(product, quantity);
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
    
    public void customerAuthentication(){
        DisplayEffect.clearScreen();
        user.userAuthentication();
    }
    
    public void customerMenu(){
        String choice;
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
            choice = scanner.nextLine();

            switch (choice){
                case "1":
                    DisplayEffect.clearScreen();
                    Product.getProductDetails();
                    productSelectionMenu();
                    break;
                case "2":
                    DisplayEffect.clearScreen();
                    viewCart();
                    if (!cart.isEmptyCart())
                        cartSelectionMenu();
                    break;
                case "3":
                    validateOrderHistory(super.getCurrentUser());
                    break;
                case "4":
                    System.out.println("\nLogged out... Thank you!");
                    DisplayEffect.clearScreen();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != "4");
    }
    
    public void productSelectionMenu(){
        String choice;
        int quantity;
        String itemID;
        Product productDetails;
        Product product = new Product();
        Scanner scan = new Scanner(System.in);
        
        System.out.println("\n1. Add to cart");
        System.out.println("2. Order product");
        System.out.println("3. Sort products");
        System.out.println("4. Back");
        System.out.print("\nEnter your choice: ");
        choice = scan.nextLine();
        
        switch (choice){
            case "1":
            case "2":
                System.out.print("\nSelect product: ");
                itemID = scan.nextLine();
                System.out.print("Enter quantity: ");
                quantity = scan.nextInt();
                productDetails = product.mapProductID(itemID);
                
                if (productDetails != null)
                    if (quantity > 0)
                        addToCart(productDetails, quantity);
                    else
                        System.out.println("Quantity at least 1");
                else 
                    System.out.println("Product not found!");
                
                if (productDetails != null && choice.equals("2")){
                    orderMenu();
                    break;
                }
                
                DisplayEffect.clearScreen();
                Product.getProductDetails();
                productSelectionMenu();
                break;     
            case "3":
                Product.productSortMenu();
                DisplayEffect.clearScreen();
                Product.getProductDetails();
                productSelectionMenu();
                break;
            case "4":
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
                DisplayEffect.clearScreen();
                Product.getProductDetails();
                productSelectionMenu();
        }
    }
    
    public void cartSelectionMenu(){
        Scanner scan = new Scanner(System.in);
        String choice;
        int ItemNumber;
        
        System.out.println("1. Place Order");
        System.out.println("2. Remove Item From Cart");
        System.out.println("3. Clear Cart");
        System.out.println("4. Back");
        System.out.print("\nEnter your choice: ");
        choice = scan.nextLine();
        
        switch (choice){
            case "1":
                orderMenu();
                break;
            case "2":
                System.out.print("\nItem to be removed: ");
                ItemNumber = scan.nextInt();
                removeFromCart(ItemNumber);
                DisplayEffect.clearScreen();
                viewCart();
                if (!cart.isEmptyCart())
                    cartSelectionMenu();
                break;
            case "3":
                clearCart();
                break;
            case "4":
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
                DisplayEffect.clearScreen();
                viewCart();
                if (!cart.isEmptyCart())
                    cartSelectionMenu();
        }
    }
    
    public void orderMenu(){
        String choice;
        Order order = new Order(cart, this.username);
        Scanner scanner = new Scanner(System.in);
        
        DisplayEffect.clearScreen();
        DisplayEffect.drawLine();
        System.out.println("               PLACE ORDER");
        DisplayEffect.drawLine();
        viewCart();
        if (cart.isEmptyCart()) {
            System.out.println("Cart is empty. Can't place order.");
            return;
        }
        
        System.out.println("Do you want to confirm your order?");
        System.out.println("1. Yes, place order");
        System.out.println("2. No, go back");
        System.out.print("\nEnter your choice: ");
        choice = scanner.nextLine();
        
        switch (choice){
            case "1":
                DisplayEffect.clearScreen();
                order.processPayment();
                if (order.getOrderStatus() == Order.OrderStatus.PAID) {
                    order.setCustomerPurchased(super.getCurrentUser());
                    saveOrder(order);
                }
                break;
            case "2":
                DisplayEffect.clearScreen();
                viewCart();
                if (!cart.isEmptyCart())
                    cartSelectionMenu();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                orderMenu();
        }
    }
    
    public void validateOrderHistory(User user) {
        boolean found = false;
        for (Order order : orderHistory) {
            if (order.getCustomerPurchased().equals(user)) {
                found = true;
                System.out.println("Found orders for " + user + ":");
                System.out.println(order); // Display each order that matches the username
            }
        }

        if (!found) {
            System.out.println("No orders found for the logged-in customer.");
        }
    }
    
    @Override
    public String toString(){
        return super.toString() + ", customerID='" + customerID + '\'';    
    }
}