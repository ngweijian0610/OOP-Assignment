package assignment;

import java.util.Objects;
import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;

public class Product {
    // data properties
    private int productID;
    private String productName;
    private String productDescription;
    private double price;
    private String category;
    private int warrantyMonths;
    
    private static int count = 1000;
    
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
    public static void getProductDetails(){
        try {
            Scanner scanner = new Scanner(new File("C:/Users/Nelson/Downloads/productList.txt"));
            System.out.println("Product List:");
            System.out.println("+---------+-------------------------------------+-----------------+-----------+");
            System.out.printf( "| %-7s | %-35s | %-15s | %-10s|\n", "Item ID", "Item Name", "Category", "Price(RM) ");
            System.out.println("+---------+-------------------------------------+-----------------+-----------+");
            while(scanner.hasNextLine()){ //hasNextLine: if scan dao \n, will quit the looping 
                    String[] itemFields = scanner.nextLine().split("\\,");
                    System.out.printf("| %-7s | %-35s | %-15s | %-10s|\n", itemFields[0], itemFields[1], itemFields[2], itemFields[3]);
            }
            System.out.println("+---------+-------------------------------------+-----------------+-----------+");
        } catch (FileNotFoundException e) {
            System.out.println("Can't open Product File!");
        } 
    }
    
    public Product mapProductID(String itemID){
        try {
            Scanner scanner = new Scanner(new File("C:/Users/Nelson/Downloads/productList.txt"));
            while (scanner.hasNextLine()){
                String[] fields = scanner.nextLine().split(",");
                if (fields[0].equals(itemID)){
                    String productName = fields[1];
                    String category = fields[2];
                    double price = Double.parseDouble(fields[3]);
                    int warranty = fields.length > 5 ? Integer.parseInt(fields[4]) : 0;
                    String description = fields.length > 4 ? fields[5] : "No description";

                    return new Product(productName, description, price, category, warranty);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Can't open Product File!");
        }
        return null;
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
    
    // wtf does this do
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return Objects.equals(productID, product.productID);
    }
    
    // wtf does this do
    @Override
    public int hashCode(){
        return Objects.hash(productID);
    }
}
