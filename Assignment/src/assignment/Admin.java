package assignment;

public class Admin extends User {
    // data properties
    private String adminID;
    private int privilegeLevel;
    
    // methods
    public Admin(){
        this(" ", 0);
    }
    public Admin(String adminID, int privilegeLevel){
        this.adminID = adminID;
        this.privilegeLevel = privilegeLevel;
    }
    
    // getter
    public String getAdminID(){
        return adminID;
    }
    public int getPrivilegeLevel(){
        return privilegeLevel;
    }
    
    // setter
    public void setAdminID(){
        this.adminID = adminID;
    }
    public void setPrivilegeLevel(){
        this.privilegeLevel = privilegeLevel;
    }
    
    // other methods
    public void viewAllOrders(){
        
    }
    public void addProduct(){
        
    }
    public void updateProduct(){  // Product product
        
    }
    public void removeProduct(){
        
    }
    public void manageInventory(){
        
    }
    public void generateReport(){
        
    }
    public void manageUser(){
        
    }
}
