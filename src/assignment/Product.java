package assignment;

public class Product {
    // data properties
    private String productID;
    private String productName;
    private String productDescription;
    private double price;
    private String category;
    private int warrantyPeriod;
    
    // methods
    public Product(){
        this(" ", " ", " ", 0.0, " ", 0);
    }
    public Product(String productID, String productName, String productDescription, double price, String category, int warrantyPeriod){
        this.productID = productID;
        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;
        this.category = category;
        this.warrantyPeriod = warrantyPeriod;
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
    public int getWarrantyPeriod(){
        return warrantyPeriod;
    }
    
    // setter
    public void setProductID(String productID){
        this.productID = productID;
    }
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
    public void setWarrantyPeriod(int warrantyPeriod){
        this.warrantyPeriod = warrantyPeriod;
    }
    
    // other methods
    
}
