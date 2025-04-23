package assignment;

import java.util.Scanner;
import java.util.List;
import java.util.regex.Pattern;

public class User {
    // data properties
    protected String username;
    protected String password;
    protected String email;
    protected String role;
    protected boolean isActive;
    
    // constructors
    public User(){
        this(" ", " ", " ", " ");
    }
    public User(String username, String password, String email, String role){
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.isActive = true;
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
    
    public boolean isActive(){
        return isActive;
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
    
    public void setActive(boolean active){
        this.isActive = active;
    }
    
    public boolean isAdmin(){
        return role.equalsIgnoreCase("admin");
    }
    
    @Override
    public String toString() {
        return "User{" +
               "username='" + username + '\'' +
               ", email='" + email + '\'' +
               ", role='" + role + '\'' +
               ", active='" + isActive + '\'' +
               '}';
    }
    
    //validation
    //email validation
    private static boolean isValidEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    
    //password validation
    private static boolean isValidPassword(String password){
        return password.length() >= 8 && password.length() <= 16;
    }
    
    private static boolean isValidUsername(String username){
        return username.length() >= 6 && username.length() <= 30;
    }
    
    //register
    public static User register(List<User> userList){
        Scanner sc = new Scanner(System.in);
        
        String username;
        String password;
        String email;
        String role;
        
        while (true) {
            System.out.print("Enter username: ");
            username = sc.nextLine();
            if (!isValidUsername(username)){
                System.out.println("Username must be between 6-30 characters long.");
            } else if (userExists(username, userList)) {
                System.out.println("Username already exists. Please choose another.");
            } else {
                break;
            }
        }
        
        while (true){
            System.out.print("Enter password (min 8 characters): ");
            password = sc.nextLine();
            if(!isValidPassword(password)){
                System.out.println("Password must be between 8-16 characters long.");
            } else {
                break;
            }
        }
        
        while (true){
            System.out.print("Enter email: ");
            email = sc.nextLine();
            if (!isValidEmail (email)){
                System.out.println("Invalid email format. Please enter a valid email."); 
            } else {
                break; 
            }
        }
       
        while (true) {
            System.out.print("Enter role (admin/customer): ");
            role = sc.nextLine().trim().toLowerCase();
            if (role.equals("admin") || role.equals("customer")){
                break;
            } else{
                System.out.println("Invalid role. Please choose 'admin' or 'customer'. ");
            }
        }
        User newUser;
        
        if (role.equals("admin")){
            newUser = new Admin(username, password, email);
        } else {
            newUser = new Customer(username, password, email);
        }
        
        userList.add(newUser);
        System.out.println("User registered successfully: " + newUser);
        return newUser;
    }
    
    //login
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
            } else {
                System.out.println("Invalid password.");
                return null;
            }
        }
        
        System.out.println("Login failed. Invalid username or password.");
        return null;
    }
    public void updateProfile(){
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Updating profile for user: " + this.username);
        
        //Update username
        System.out.println("Enter new username: ");
        String newUsername = sc.nextLine();
        if (!newUsername.isEmpty()){
            if(!isValidUsername(newUsername)){
                System.out.println("Password must be between 6-30 characters long.");
            } else {
                this.username = newUsername;
                System.out.println("Username update to: " + this.username);   
            }
        }
        
        //Update password
        System.out.println("Enter new password: ");
        String newPassword = sc.nextLine();
        if(!newPassword.isEmpty()){
            if(!isValidPassword(newPassword)){
                System.out.println("Password must be between 8-16 characters long.");
            } else {            
                this.password = newPassword;
                System.out.println("Password updated");
            }
        }
        
        //Update email
        System.out.println("Enter new email: ");
        String newEmail = sc.nextLine();
        if(!newEmail.isEmpty()){
            if (!isValidEmail(newEmail)){
                System.out.println("Invalid email format. Please enter a valid email."); 
            } else {
            this.email = newEmail;
            System.out.println("Email update to: " + this.email);
            }
        }
        
        System.out.println("Profile updated successfully.");
    }
    
    //deactivate account
    public void deactivateAccount(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Are you sure you want to deactivate your account? (yes/no): ");
        String choice = sc.nextLine().trim().toLowerCase();
        
        if(choice.equals("yes")){
            this.isActive = false;
            System.out.println("Your account has been deactivated.");
        } else {
            System.out.println("Account deactivation cancelled.");
        }
    }
    
    private static boolean userExists(String username, List<User> userList){
        for (User user : userList){
            if (user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }
}
