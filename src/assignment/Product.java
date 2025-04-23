package assignment;

public class Product {
    // data properties
    private final String productID;
    private String productName;
    private String productDescription;
    private double price;
    private String category;
    private int warrantyMonths;
    
    // constructors
    public Product(){
        this(" ", " ", 0.0, " ", 0);
    }
    public Product(String productName, String productDescription, double price, String category, int warrantyMonths){
        this.productID = IDGenerator.generate("PRO");
        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;
        this.category = category;
        this.warrantyMonths = warrantyMonths;
    }
    
    // getter
    public String getProductID(){
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
    public void setProductName(String productName){
        this.productName = productName;
    }
    public void setProductDescription(String productDescription){
        this.productDescription = productDescription;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public void setWarrantyMonths(int warrantyMonths){
        this.warrantyMonths = warrantyMonths;
    }
    
    // other methods
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
        }
}
}
