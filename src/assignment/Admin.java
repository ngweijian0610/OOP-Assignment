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
     
    public Admin(String username, String password, String email, 
            String adminID, int privilegeLevel){
        super(username, password, email, "admin");
        this.adminID = adminID;
        this.privilegeLevel = privilegeLevel;
    }
    
    private String generateAdminID(){
        int maxID = 0;
        for (User user : userList) {
            if (user.isAdmin()) {
                Admin admin = (Admin) user;
                String adminID = admin.getAdminID();
                if (adminID != null && adminID.startsWith("A")) {
                    try {
                        int idNum = Integer.parseInt(adminID.substring(1));
                        if (idNum > maxID) {
                            maxID = idNum;
                        }
                    } catch (NumberFormatException e) {
                        // Skip if format is not expected
                    }
                }
            }
        }
        
        counter = maxID + 1;
        return "A" + String.format("%04d", counter);
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
    private void displayProductList() {
        List<Product> products = Product.loadProductsFromFile();
        Product.displayProducts(products);
    }
    
    public void adminLogin(){
        String choice;
        Scanner scanner = new Scanner(System.in);
        
        do {
            DisplayEffect.clearScreen();
            DisplayEffect.drawLine();
            System.out.println("          Welcome to Admin Side");
            DisplayEffect.drawLine();
            System.out.println("1. Login");
            System.out.println("2. Back");
            DisplayEffect.drawLine();
            System.out.print("\nEnter your choice: ");
            choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    DisplayEffect.clearScreen();
                    if (attemptAdminLogin()) {
                        adminMenu();
                    }
                    break;
                case "2":
                    DisplayEffect.clearScreen();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (!choice.equals("2"));
    }
    
    private boolean attemptAdminLogin() {
        Scanner sc = new Scanner(System.in);
        
        User.userList.add(new Admin("admin", "admin123", "admin@gmail.com"));
        
        DisplayEffect.drawLine();
        System.out.println("               ADMIN LOGIN");
        DisplayEffect.drawLine();
        System.out.print("\nEnter username: ");
        String username = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    if (user.isAdmin()) {
                        System.out.println("\nAdmin login successful! Welcome, " + user.getUsername());
                        setCurrentUser(user);
                        return true;
                    } else {
                        System.out.println("\nError: Please use customer login for customer accounts.");
                        return false;
                    }
                } else {
                    System.out.println("\nInvalid password.");
                    return false;
                }
            }
        }

        System.out.println("\nAdmin account not found.");
        return false;
    }
    
    public void adminMenu(){
        String choice;
        Scanner scanner = new Scanner(System.in);
        
        do {
            DisplayEffect.clearScreen();
            System.out.println("====================================================================================================");
            System.out.println("                                            Admin Panel");
            System.out.println("====================================================================================================");
            System.out.println();
            
            displayProductList();
            System.out.println();
            
            System.out.println("1. ADD new product");
            System.out.println("2. UPDATE product details");
            System.out.println("3. REMOVE product");
            System.out.println("4. Logout");
            System.out.print("\nEnter your choice: ");
            choice = scanner.nextLine();
            
            switch (choice){
                case "1":
                    addProduct();
                    break;
                case "2":
                    updateProduct();
                    break;
                case "3":
                    removeProduct();
                    break;
                case "4":
                    System.out.println("\nLogging out... Thank you!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (!choice.equals("4"));
    }
    
    public void addProduct() {
        Scanner scanner = new Scanner(System.in);

        DisplayEffect.clearScreen();
        System.out.println("\n--- Add New Product ---");

        // Get and validate product name
        String productName;
        do {
            System.out.print("Enter Product Name: ");
            productName = scanner.nextLine().trim();
            
            if (productName.isEmpty()) {
                System.out.println("Product name cannot be empty. Please try again.");
            } else if (productName.length() > 50) {
                System.out.println("Product name cannot exceed 50 characters. Please try again.");
            } 
        } while (productName.isEmpty() || productName.length() > 50);
        
        // Get and validate product category
        String category;
        do {
            System.out.print("Enter Product Category: ");
            category = scanner.nextLine().trim();
            
            if (category.isEmpty()) {
                System.out.println("Category cannot be empty. Please try again.");
            } else if (!category.equalsIgnoreCase("Laptop") && 
                       !category.equalsIgnoreCase("Computer") && 
                       !category.equalsIgnoreCase("Accessories")) {
                System.out.println("Invalid category. Must be Laptop, Computer, or Accessories. Please try again.");
            }
        } while (category.isEmpty() || 
                (!category.equalsIgnoreCase("Laptop") && 
                 !category.equalsIgnoreCase("Computer") && 
                 !category.equalsIgnoreCase("Accessories")));
        
        category = category.substring(0, 1).toUpperCase() + category.substring(1).toLowerCase();
        
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
                scanner.nextLine();
                
                if (warrantyMonth < 1){
                    System.out.println("Warranty Period must be at least 1 month. Please try again.");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Incorrect input (Please enter a valid number for Warranty Period).");
                scanner.nextLine();
            }
        }
        
        int maxProductId = 0;

        // Read existing product IDs
        try {
            Scanner fileScanner = new Scanner(new File("productList.txt"));
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (!line.trim().isEmpty()) {
                    String[] itemFields = line.split("\\|");
                    if (itemFields.length > 0) {
                        try {
                            int idNumber = Integer.parseInt(itemFields[0].trim());
                            if (idNumber > maxProductId)
                                maxProductId = idNumber;
                        } catch (NumberFormatException e) {
                            System.out.println("Warning: Invalid product ID format in line: " + line);
                        }
                    }
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Product list file not found. Assuming first product.");
            maxProductId = 0;
        }

        // After getting the largest product ID, generate new one
        int newProductId = maxProductId + 1;
        
        // Format price to always have 2 decimal places
        String formattedPrice = String.format("%.2f", productPrice);

        // Store product details into text file
        try {
            FileWriter writer = new FileWriter("productList.txt", true); // append mode
            writer.write(newProductId + "|" + productName + "|" + category + "|" + formattedPrice + "|" + warrantyMonth + "\n");
            writer.close();
            System.out.println("\nProduct added successfully with ID: " + newProductId);
        } catch (IOException e) {
            System.out.println("An error occurred while saving the product.");
        }
        
        adminMenu();
    }
    
    public void updateProduct() {
        Scanner scanner = new Scanner(System.in);

        DisplayEffect.clearScreen();
        System.out.println("\n--- Update Product ---");

        List<String> productList = new ArrayList<>();
        boolean productFound = false;

        // Load all existing products
        try {
            Scanner fileScanner = new Scanner(new File("productList.txt"));
            while (fileScanner.hasNextLine()) {
                productList.add(fileScanner.nextLine());
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Product list file not found.");
            return;
        }

        // Ask for product ID to update
        System.out.print("Enter Product ID to update: ");
        int targetId = scanner.nextInt();
        scanner.nextLine();
        
        boolean changed = false;

        for (int i = 0; i < productList.size(); i++) {
            String[] itemFields = productList.get(i).split("\\|"); // <-- fixed here
            if (itemFields[0].equals(String.valueOf(targetId))) {  // <-- also fix comparison
                productFound = true;

                // Show current details
                System.out.println("\nCurrent Product Details:");
                System.out.println("Name: " + itemFields[1]);
                System.out.println("Category: " + itemFields[2]);
                System.out.println("Price: " + itemFields[3]);
                System.out.println("Warranty Month: " + itemFields[4]);

                // Ask for new details (blank to keep current)
                System.out.println("\n--- Enter New Details (Press Enter to keep current value) ---");
                
                System.out.print("Enter New Product Name: ");
                String newName = scanner.nextLine();
                if (!newName.isEmpty()) {
                    while (newName.length() > 50) {
                        System.out.println("Invalid name! Must be less than or equal to 50 characters. Please try again.");
                        System.out.print("Enter New Product Name: ");
                        newName = scanner.nextLine();
                    }
                    if (!newName.equals(itemFields[1])) {
                        changed = true;
                    }
                } else {
                    newName = itemFields[1];
                }

                System.out.print("Enter New Product Category (Laptop/Computer/Accessories): ");
                String newCategory = scanner.nextLine();
                if (newCategory.isEmpty()) {
                    newCategory = itemFields[2];
                } else {
                    while (!newCategory.equalsIgnoreCase("Laptop") && 
                           !newCategory.equalsIgnoreCase("Computer") && 
                           !newCategory.equalsIgnoreCase("Accessories")) {
                        System.out.println("Invalid category. Must be Laptop, Computer, or Accessories. Please try again.");
                        System.out.print("Enter New Product Category (Laptop/Computer/Accessories): ");
                        newCategory = scanner.nextLine();
                        
                        if (newCategory.isEmpty()) {
                            newCategory = itemFields[2];
                            break;
                        }
                    }
                    
                    if (!newCategory.isEmpty()) {
                        newCategory = newCategory.substring(0, 1).toUpperCase() + newCategory.substring(1).toLowerCase();
                    }
                    
                    if (!newCategory.equals(itemFields[2])) {
                        changed = true;
                    }
                }

                String newPriceStr;
                double newPrice;
                while (true) {
                    System.out.print("Enter New Product Price: ");
                    newPriceStr = scanner.nextLine();
                    if (newPriceStr.isEmpty()) {
                        newPrice = Double.parseDouble(itemFields[3]);
                        break;
                    }
                    try {
                        newPrice = Double.parseDouble(newPriceStr);
                        if (newPrice < 0) {
                            System.out.println("Price cannot be negative. Please try again.");
                        } else {
                            if (newPrice != Double.parseDouble(itemFields[3])) {
                                changed = true;
                            }
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("Incorrect input (Please enter a valid number for Price).");
                    }
                }

                String newWarrantyStr;
                int newWarranty;
                while (true) {
                    System.out.print("Enter New Warranty Period (Month): ");
                    newWarrantyStr = scanner.nextLine();
                    if (newWarrantyStr.isEmpty()) {
                        newWarranty = Integer.parseInt(itemFields[4]);
                        break;
                    }
                    try {
                        newWarranty = Integer.parseInt(newWarrantyStr);
                        if (newWarranty < 1) {
                            System.out.println("Warranty Period must be at least 1 month. Please try again.");
                        } else {
                            if (newWarranty != Integer.parseInt(itemFields[4])) {
                                changed = true;
                            }
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("Incorrect input (Please enter a valid number for Warranty Period).");
                    }
                }

                // Rebuild the updated product line
                String formattedPrice = String.format("%.2f", newPrice);
                String updatedProduct = targetId + "|" + newName + "|" + newCategory + "|" + formattedPrice + "|" + newWarranty;

                // Replace old line with updated one
                productList.set(i, updatedProduct);

                break; // no need to continue loop
            }
        }

        if (!productFound) {
            System.out.println("\nProduct ID not found.");
            return;
        }

        // Write all updated products back to file
        if (!changed) {
            System.out.println("\nNo changes were made to the product.");
        } else {
            try {
                FileWriter writer = new FileWriter("productList.txt", false);
                for (String productLine : productList) {
                    writer.write(productLine + "\n");
                }
                writer.close();
                System.out.println("\nProduct updated successfully!");
            } catch (IOException e) {
                System.out.println("An error occurred while updating the product.");
            }
        }
        
        adminMenu();
    }

    public void removeProduct() {
        Scanner scanner = new Scanner(System.in);

        DisplayEffect.clearScreen();
        System.out.println("\n--- Remove Product ---");

        List<String> productList = new ArrayList<>();
        boolean productFound = false;
        int removeIndex = -1;

        // Load all existing products
        try {
            Scanner fileScanner = new Scanner(new File("productList.txt"));
            while (fileScanner.hasNextLine()) {
                productList.add(fileScanner.nextLine());
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Product list file not found.");
            return;
        }

        // Ask for product ID to remove
        System.out.print("Enter Product ID to remove: ");
        int targetId = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < productList.size(); i++) {
            String[] itemFields = productList.get(i).split("\\|");
            if (itemFields[0].equals(String.valueOf(targetId))) {
                productFound = true;
                removeIndex = i;

                // Confirm deletion
                System.out.println("\nAre you sure you want to delete this product?");
                System.out.println("Name: " + itemFields[1]);
                System.out.print("Type 'yes' to confirm: ");
                String confirmation = scanner.nextLine();

                if (confirmation.equalsIgnoreCase("yes")) {
                    productList.remove(i);

                    // After removing, adjust the IDs of products after this
                    for (int j = i; j < productList.size(); j++) {
                        String[] fields = productList.get(j).split("\\|");
                        int oldId = Integer.parseInt(fields[0]);
                        int newId = oldId - 1; // deduct 1
                        fields[0] = String.valueOf(newId);
                        productList.set(j, String.join("|", fields));
                    }

                    System.out.println("\nProduct removed and IDs updated successfully!");
                } else {
                    System.out.println("\nProduct removal cancelled.");
                }
                
                break;
            }
        }

        if (!productFound) {
            System.out.println("\nProduct ID not found.");
            return;
        }

        // Write updated products back to file
        try {
            FileWriter writer = new FileWriter("productList.txt", false); // overwrite
            for (String productLine : productList) {
                writer.write(productLine + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while updating the product list.");
        }
        
        adminMenu();
    }
    
    @Override
    public String toString(){
        return super.toString() +
                ", adminID='" + adminID + '\'' +
                ", privilegeLevel=" + privilegeLevel;
    }
}
