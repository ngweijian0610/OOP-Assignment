package assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

public class Admin extends User {
    // data properties
    private String adminID;
    private int privilegeLevel;
    private static int counter = 1;
    
    // constructors
    public Admin(){
        super();
        this.adminID = "";
        this.privilegeLevel = 0;
        this.role = "admin";
    }
    
     public Admin(String username, String password, String email){
        super(username, password, email, "admin");
        this.adminID = generateAdminID();
        this.privilegeLevel = 1;
    }
     
    public Admin(String username, String password, String email, String adminID, int privilegeLevel){
        super(username, password, email, "admin");
        this.adminID = generateAdminID();
        this.privilegeLevel = privilegeLevel;
    }
    
    private String generateAdminID(){
        return "A" + String.format("%04d", counter++);
    }
    
    // getter
    public String getAdminID(){
        return adminID;
    }
    public int getPrivilegeLevel(){
        return privilegeLevel;
    }
    
    // setter
    public void setAdminID(String adminID){
        this.adminID = adminID;
    }
    public void setPrivilegeLevel(int privilegeLevel){
        this.privilegeLevel = privilegeLevel;
    }
    
    // other methods
    public void addProduct() {
        Scanner scanner = new Scanner(System.in);

        DisplayEffect.clearScreen();
        System.out.println("\n--- Add New Product ---");

        // Get product name
        System.out.print("Enter Product Name: ");
        String productName = scanner.nextLine();
        System.out.print("Enter Product Category: ");
        String category = scanner.nextLine();
        
        // Get product price
        double productPrice = 0.0;
        while (true) {
            try {
                System.out.print("Enter Product Price: ");
                productPrice = scanner.nextDouble();
                scanner.nextLine();
                if (productPrice < 0) {
                    System.out.println("Price cannot be negative. Please try again.");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Incorrect input (Please enter a valid number for Price).");
                scanner.nextLine();
            }
        }
        
        int warrantyMonth;
        while (true){
            try {
                System.out.print("Enter Warranty Period (Month): ");
                warrantyMonth = scanner.nextInt();
                
                if (warrantyMonth < 1){
                    System.out.println("Warranty Period cannot be negative. Please try again.");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Incorrect input (Please enter a valid number for Warranty Period).");
                scanner.nextLine();
            }
        }
        
        // Get product description
        scanner.nextLine();
        System.out.print("Enter Product Description: ");
        String productDescription = scanner.nextLine();
        
        int maxProductId = 0;

        // Read existing product IDs
        try {
            Scanner fileScanner = new Scanner(new File("C:/Users/Nelson/Downloads/productList.txt"));
            while (fileScanner.hasNextLine()) {
                String[] itemFields = fileScanner.nextLine().split(",");
                String productID = itemFields[0]; // Example: 1, 2, 3, ...
                try {
                    int idNumber = Integer.parseInt(productID); // directly parse to int
                    if (idNumber > maxProductId) {
                        maxProductId = idNumber;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid product ID format: " + productID);
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Product list file not found. Assuming first product.");
        }

        // After getting the largest product ID, generate new one
        int newProductId = maxProductId + 1;

        // Store product details into text file
        try {
            FileWriter writer = new FileWriter("C:/Users/Nelson/Downloads/productList.txt", true); // append mode
            writer.write(newProductId + "," + productName + "," + category + "," + productPrice + "," + warrantyMonth + "," + productDescription + "\n");
            writer.close();
            System.out.println("\nProduct added successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the product.");
        }
    }
    
    public void updateProduct(){  // Product product
        
    }
    public void removeProduct(){
        
    }
    public void manageUser(){
        
    }
    
    public void admin_menu(){
        int choice;
        Scanner scanner = new Scanner(System.in);
        
        DisplayEffect.clearScreen();
        DisplayEffect.drawLine();
        System.out.println("ADMIN");
        DisplayEffect.drawLine();
        System.out.println("1. Add new product");
        DisplayEffect.drawLine();
        System.out.print("\nEnter your choice: ");
        choice = scanner.nextInt();
        
        switch (choice){
            case 1:
                addProduct();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    @Override
    public String toString(){
        return super.toString() +
                ", adminID='" + adminID + '\'' +
                ", privilegeLevel=" + privilegeLevel;
    }
}
