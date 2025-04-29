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
    
    public void admin_login(){
        DisplayEffect.clearScreen();
        User.login();
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
                String[] itemFields = fileScanner.nextLine().split("|");
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
            writer.write(newProductId + "|" + productName + "|" + category + "|" + productPrice + "|" + warrantyMonth + "|" + productDescription + "\n");
            writer.close();
            System.out.println("\nProduct added successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the product.");
        }
    }
    
    public void updateProduct() {
        Scanner scanner = new Scanner(System.in);

        DisplayEffect.clearScreen();
        System.out.println("\n--- Update Product ---");

        List<String> productList = new ArrayList<>();
        boolean productFound = false;

        // Load all existing products
        try {
            Scanner fileScanner = new Scanner(new File("C:/Users/Nelson/Downloads/productList.txt"));
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
                System.out.println("Description: " + itemFields[5]);

                // Ask for new details (blank to keep current)
                System.out.println("\n--- Enter New Details (Press Enter to keep current value) ---");

                System.out.print("Enter New Product Name: ");
                String newName = scanner.nextLine();
                if (newName.isEmpty()) newName = itemFields[1];

                System.out.print("Enter New Product Category: ");
                String newCategory = scanner.nextLine();
                if (newCategory.isEmpty()) newCategory = itemFields[2];

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
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("Incorrect input (Please enter a valid number for Warranty Period).");
                    }
                }

                System.out.print("Enter New Product Description: ");
                String newDescription = scanner.nextLine();
                if (newDescription.isEmpty()) newDescription = itemFields[5];

                // Rebuild the updated product line
                String updatedProduct = targetId + "|" + newName + "|" + newCategory + "|" + newPrice + "|" + newWarranty + "|" + newDescription;

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
        try {
            FileWriter writer = new FileWriter("C:/Users/Nelson/Downloads/productList.txt", false); // overwrite mode
            for (String productLine : productList) {
                writer.write(productLine + "\n");
            }
            writer.close();
            System.out.println("\nProduct updated successfully!");
            admin_menu();
        } catch (IOException e) {
            System.out.println("An error occurred while updating the product.");
        }
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
            Scanner fileScanner = new Scanner(new File("C:/Users/Nelson/Downloads/productList.txt"));
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
        scanner.nextLine(); // clear buffer

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
            FileWriter writer = new FileWriter("C:/Users/Nelson/Downloads/productList.txt", false); // overwrite
            for (String productLine : productList) {
                writer.write(productLine + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while updating the product list.");
        }
    }
    
    public void admin_menu(){
        int choice;
        Scanner scanner = new Scanner(System.in);
        
        DisplayEffect.clearScreen();
        DisplayEffect.drawLine();
        System.out.println("ADMIN");
        DisplayEffect.drawLine();
        System.out.println("1. Add new product");
        System.out.println("2. Update product details");
        System.out.println("3. Remove product");
        System.out.println("4. Logout");
        DisplayEffect.drawLine();
        System.out.print("\nEnter your choice: ");
        choice = scanner.nextInt();
        
        switch (choice){
            case 1:
                addProduct();
                break;
            case 2:
                updateProduct();
                break;
            case 3:
                removeProduct();
                break;
            case 4:
                System.out.println("\nLogged out... Thank you!");
                DisplayEffect.clearScreen();
                return;
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
