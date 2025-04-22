package assignment;

public class Customer extends User {
    // data properties
    private String customerID;
//  private Cart cart;
    
    // methods
    public Customer(){
        super();
        this.customerID = "";
        this.role = "customer";
    }
    
    public Customer(String username, String password, String email, String customerID){
        super(username, password, email, "customer");
        this.customerID = customerID;
    }
    
    public Customer(String username, String password, String email){
        super(username, password, email, "customer");
        this.customerID = "";
    }
    
    // getter
    public String getCustomerID(){
        return customerID;
    }
    
    // setter
    public void setCustomerID(String customerID){
        this.customerID = customerID;
    }
}
