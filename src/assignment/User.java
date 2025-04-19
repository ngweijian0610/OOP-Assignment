package assignment;

import java.util.Scanner;
import java.util.List;

public class User {
    // data properties
    protected String username;
    protected String password;
    protected String email;
    protected String role;
    
    // methods
    public User(){
        this(" ", " ", " ", " ");
    }
    public User(String username, String password, String email, String role){
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
    
    // getter
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
     public String getEmail(){
        return email;
    }
    public String getRole(){
        return role;
    }
    
    // setter
    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setRole(String role){
        this.role = role;
    }
    
    public boolean isAdmin(){
        return role.equalsIgnoreCase("admin");
    }
    
    public String toString() {
        return "User{" +
               "username='" + username + '\'' +
               ", email='" + email + '\'' +
               ", role='" + role + '\'' +
               '}';
    }
    
    // other methods
    public static User register(){
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        
        System.out.print("Enter role (admin/customer): ");
        String role = sc.nextLine().trim().toLowerCase();
        
        User newUser;
        
        if (role.equals("admin")){
            newUser = new Admin(username, password, email);
        } else {
            newUser = new Customer(username, password, email);
        }
        
        System.out.println("User registered successfully: " + newUser);
        return newUser;
    }
    
    public static User login(List<User> userList){
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        
        for (User user : userList){
            if (user.getUsername().equals(username)
                && user.getPassword().equals(password)){
                    System.out.println("Login successful! Welcome, " + user.getUsername()
                                        + " (" + user.getRole() +")");
                    return user;
            }
        }
        
        System.out.println("Login failed. Invalid username or password.");
        return null;
    }
    public void updateProfile(){
        
    }
    public void deactivateAccount(){
        
    }
}
