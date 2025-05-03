package assignment;

public class CartItem {
    private int number;
    private Product product;
    private int quantity;
    
    // Constructor
    public CartItem() {
        this(0, null, 0);
    }
    
    public CartItem(int number, Product product, int quantity){
        this.number = number;
        this.product = product;
        setQuantity(quantity);
    }
    
    // Getters
    public int getNumber(){
        return number;
    }
    
    public Product getProduct(){
        return product;
    }
    
    public int getQuantity(){
        return quantity;
    }
    
    // Setters
    public void setNumber(int number){
        this.number = number;
    }
    public void setProduct(Product product){
        this.product = product;
    }
    
    public void setQuantity(int quantity){
        if (quantity > 0){
            this.quantity = quantity;
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
