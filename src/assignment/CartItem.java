package assignment;

public class CartItem {
    private Product product;
    private int quantity;
    
    // Constructor
    public CartItem(Product product, int quantity){
        this.product = product;
        setQuantity(quantity);
    }
    
    // Getters
    public Product getProduct(){
        return product;
    }
    
    public int getQuantity(){
        return quantity;
    }
    
    // Setters
    public void setProduct(Product product){
        this.product = product;
    }
    
    public void setQuantity(int quantity){
        if (quantity > 0){
            this.quantity = quantity;
        } else {
            System.out.println("Quantity must be at least 1.");
            this.quantity = 1;
        }
    }
    
    // other methods
    public double calculateSubtotal(){
        return product.getPrice() * quantity;
    }
    
    @Override
    public String toString(){
        return product.getProductName() +
                " x " + quantity + 
                " = RM " + String.format("%.2f", calculateSubtotal());
    }
}
