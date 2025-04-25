package assignment;

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
    
    public void admin_menu(){
        System.out.println("Hello");
    }
    
    @Override
    public String toString(){
        return super.toString() +
                ", adminID='" + adminID + '\'' +
                ", privilegeLevel=" + privilegeLevel;
    }
}
