package assignment;

public class User {
    // data properties
    protected String username;
    protected String password;
    protected String role;
    
    // methods
    public User(){
        this(" ", " ", " ");
    }
    public User(String username, String password, String role){
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    // getter
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getRole(){
        return role;
    }
    
    // setter
    public void setUsername(){
        this.username = username;
    }
    public void setPassword(){
        this.password = password;
    }
    public void setRole(){
        this.role = role;
    }
    
    // other methods
    public void register(){
        
    }
    public void login(){
        
    }
    public void updateProfile(){
        
    }
    public void deactivateAccount(){
        
    }
}
