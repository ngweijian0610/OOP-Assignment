package assignment;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<CartItem> items;

    // Constructor
    public Cart(){
        items = new ArrayList<>();
    }
    
    // getter
    
    
    // setter
    
    
    // other methods
    // Add item to cart
    public void addItem(Product product, int quantity) {
        for(CartItem item : items){
            if(item.getProduct().getProductID() == (product.getProductID())){
                item.setQuantity(item.getQuantity() + quantity);
                System.out.println("Increased quantity of " + product.getProductName() + " removed from cart.");
                return;
            }
        }
        items.add(new CartItem(product, quantity));
        System.out.println("\nProduct (" + product.getProductName() + ") added to cart!");
    }
    
    // Remove item from cart
    public void removeItem(int productID) {
        for(CartItem item : items){
            if(item.getProduct().getProductID() == (productID)){
                items.remove(item);
                System.out.println(item.getProduct().getProductName() + " removed from cart." );
                return;
            }
        }
        System.out.println("Product not found in cart.");
    }
    
    // View cart contents
    public void viewCart(){
        int num = 1;
        
        if(items.isEmpty())
            System.out.println("Your cart is empty.");
        else {
            System.out.println("Items in your cart:");
            DisplayEffect.drawLine();
            System.out.println("");
            for(CartItem item : items) {
                System.out.println(num + ". " + item);
                num++;
            }
            System.out.println("");
            DisplayEffect.drawLine();
            System.out.println("Total: RM " + String.format("%.2f",getTotal()) + "\n");
        }
    }
    
    public double getTotal(){
        double total = 0;
        for (CartItem item : items)
            total += item.calculateSubtotal();
        return total;
    }
    
    public void clearCart(){
        items.clear();
        System.out.println("Cart cleared.");
    }
    
    public List<CartItem> getItems(){
        return items;
    }
}