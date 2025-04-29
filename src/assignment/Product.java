package assignment;

import java.util.Objects;
import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;

public class Product implements Comparable<Product> {
    // data properties
    private int productID;
    private String productName;
    private String productDescription;
    private double price;
    private String category;
    private int warrantyMonths;
    
    private static int count = 1000;
    
    // enum for sorting criteria
    public enum SortCriteria {
        ID, NAME, CATEGORY, PRICE
    }
    
    // current sorting criteria (default to ID)
    private static SortCriteria currentSortCriteria = SortCriteria.ID;
    private static boolean ascending = true;
    
    public Product(){
        this(" ", " ", 0.0, " ", 0);
    }
    
    public Product(String productName, String productDescription, double price, String category, int warrantyMonths){
        this.productID = count;
        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;
        this.category = category;
        this.warrantyMonths = warrantyMonths;
        count++;
    }
    
    // Constructor with specific ID
    public Product(int productID, String productName, String productDescription, double price, String category, int warrantyMonths){
        this.productID = productID;
        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;
        this.category = category;
        this.warrantyMonths = warrantyMonths;
    }
    
    // getter
    public int getProductID(){
        return productID;
    }
    public String getProductName(){
        return productName;
    }
    public String getProductDescription(){
        return productDescription;
    }
    public double getPrice(){
        return price;
    }
    public String getCategory(){
        return category;
    }
    public int getWarrantyMonths(){
        return warrantyMonths;
    }
    
    // setter
    public void setProductID(int productID){
        this.productID = productID;
    }
    public void setProductName(String productName){
        this.productName = productName;
    }
    
    public void setProductDescription(String productDescription){
        this.productDescription = productDescription;
    }
    
    public void setPrice(double price){
        if (price >= 0){
            this.price = price;
        } else {
            System.out.println("Price cannot be negative.");
        }
    }
    
    public void setCategory(String category){
        this.category = category;
    }
    
    public void setWarrantyMonths(int warrantyMonths){
        if (warrantyMonths >= 0) {
            this.warrantyMonths = warrantyMonths;
        } else {
            System.out.println("Warranty months cannot be negative.");
        }
    }
    
    // other methods
    public static void setSortCriteria(SortCriteria criteria, boolean asc) {
        currentSortCriteria = criteria;
        ascending = asc;
    }
    
    @Override
    public int compareTo(Product other) {
        int result = 0;
        
        switch(currentSortCriteria) {
            case ID:
                result = Integer.compare(this.productID, other.productID);
                break;
            case NAME:
                result = this.productName.compareToIgnoreCase(other.productName);
                break;
            case CATEGORY:
                result = this.category.compareToIgnoreCase(other.category);
                break;
            case PRICE:
                result = Double.compare(this.price, other.price);
                break;
            default:
                result = Integer.compare(this.productID, other.productID);
        }
        
        return ascending ? result : -result;
    }
    
    // Load products from file into a list
    public static List<Product> loadProductsFromFile() {
        List<Product> products = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("productList.txt"));
            while (scanner.hasNextLine()) {
                String[] fields = scanner.nextLine().split("\\|");
                int id = Integer.parseInt(fields[0]);
                String name = fields[1];
                String category = fields[2];
                double price = Double.parseDouble(fields[3]);
                int warranty = fields.length > 4 ? Integer.parseInt(fields[4]) : 0;
                String description = fields.length > 5 ? fields[5] : "No description";
                
                products.add(new Product(id, name, description, price, category, warranty));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Can't open Product File!");
        }
        return products;
    }
    
    // Display products with optional sorting
    public static void displayProducts(List<Product> products) {
        Collections.sort(products); // Sort using the current criteria
        
        System.out.println("Product List:");
        System.out.println("+---------+-------------------------------------+-----------------+-----------+");
        System.out.printf("| %-7s | %-35s | %-15s | %-10s|\n", "Item ID", "Item Name", "Category", "Price(RM) ");
        System.out.println("+---------+-------------------------------------+-----------------+-----------+");
        
        for (Product product : products) {
            System.out.printf("| %-7d | %-35s | %-15s | %-10.2f|\n", 
                product.getProductID(), 
                product.getProductName(), 
                product.getCategory(), 
                product.getPrice());
        }
        
        System.out.println("+---------+-------------------------------------+-----------------+-----------+");
    }
    
    // Method to show product sorting menu
    public static void productSortMenu() {
        Scanner scanner = new Scanner(System.in);
        List<Product> products = loadProductsFromFile();
        
        int choice;
        do {
            System.out.println("\nSort Products By:");
            System.out.println("1. Product ID");
            System.out.println("2. Product Name");
            System.out.println("3. Category");
            System.out.println("4. Price");
            System.out.println("5. Back");
            System.out.print("\nEnter your choice: ");
            
            try {
                choice = scanner.nextInt();
                
                if (choice == 5) {
                    return;
                }
                
                if (choice < 1 || choice > 5) {
                    System.out.println("Invalid choice. Please enter 1-5.");
                    continue;
                }
                
                // Sort order validation
                boolean asc = true;
                int orderChoice;
                do {
                    System.out.println("\nSort Order:");
                    System.out.println("1. Ascending");
                    System.out.println("2. Descending");
                    System.out.print("\nEnter your choice: ");
                    
                    try {
                        orderChoice = scanner.nextInt();
                        if (orderChoice == 1 || orderChoice == 2) {
                            asc = (orderChoice == 1);
                            break;
                        } else {
                            System.out.println("Invalid choice. Please enter 1 or 2.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Please enter a number (1 or 2).");
                        scanner.next();
                    }
                } while (true);
                
                // Apply sorting
                switch (choice) {
                    case 1:
                        setSortCriteria(SortCriteria.ID, asc);
                        break;
                    case 2:
                        setSortCriteria(SortCriteria.NAME, asc);
                        break;
                    case 3:
                        setSortCriteria(SortCriteria.CATEGORY, asc);
                        break;
                    case 4:
                        setSortCriteria(SortCriteria.PRICE, asc);
                        break;
                }

                DisplayEffect.clearScreen();
                displayProducts(products);
                    
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number (1-5).");
                scanner.next(); // Clear the invalid input
                choice = 0;     // Reset choice to show menu again
            }
        } while (choice != 5);
    }
    
    public static void getProductDetails(){
        List<Product> products = loadProductsFromFile();
        displayProducts(products);
        
//        try {
//
//            Scanner scanner = new Scanner(new File("productList.txt"));
//
//            System.out.println("Product List:");
//
//            System.out.println("+---------+-------------------------------------+-----------------+-----------+");
//
//            System.out.printf( "| %-7s | %-35s | %-15s | %-10s|\n", "Item ID", "Item Name", "Category", "Price(RM) ");
//
//            System.out.println("+---------+-------------------------------------+-----------------+-----------+");
//
//            while(scanner.hasNextLine()){ //hasNextLine: if scan until \n, will quit the looping 
//
//                    String[] itemFields = scanner.nextLine().split("\\|");
//
//                    System.out.printf("| %-7s | %-35s | %-15s | %-10s|\n", itemFields[0], itemFields[1], itemFields[2], itemFields[3]);
//
//            }
//
//            System.out.println("+---------+-------------------------------------+-----------------+-----------+");
//
//        } catch (FileNotFoundException e) {
//
//            System.out.println("Can't open Product File!");
//
//        } 
    }
    
    public Product mapProductID(String itemID){
        List<Product> products = loadProductsFromFile();
        for (Product product : products) {
            if (String.valueOf(product.getProductID()).equals(itemID)) {
                return product;
            }
        }
        
        return null;
        
//        try {
//            Scanner scanner = new Scanner(new File("productList.txt"));
//            while (scanner.hasNextLine()){
//                String[] fields = scanner.nextLine().split("\\|");
//                if (fields[0].equals(itemID)){
//                    String productName = fields[1];
//                    String category = fields[2];
//                    double price = Double.parseDouble(fields[3]);
//                    int warranty = fields.length > 5 ? Integer.parseInt(fields[4]) : 0;
//                    String description = fields.length > 4 ? fields[5] : "No description";
//
//                    return new Product(productName, description, price, category, warranty);
//                }
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println("Can't open Product File!");
//        }
//        return null;
    }
    
    @Override
    public String toString(){
        return  "Product ID: " + productID +
                "\nProduct Name: " + productName +
                "\nDescription: " + productDescription +
                "\nPrice: RM " + String.format("%.2f", price) +
                "\nCategory: " + category +
                "\nWarranty Months: " + warrantyMonths + "months";
    }
    
    public void applyDiscount(double percentage) {
        if (percentage > 0 && percentage <= 100) {
            this.price = this.price - (this.price * percentage / 100);
        } else {
            System.out.println("Invalid discount percentage. Must be between 0 and 100.");
        }
    }
    
    // wtf does this do (Good Question)
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return Objects.equals(productID, product.productID);
    }
    
    // wtf does this do (Good Question)
    @Override
    public int hashCode(){
        return Objects.hash(productID);
    }
}
