package assignment;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.regex.Pattern;

public class User {
    // data properties
    protected static List<User> userList = new ArrayList<>();
    protected String username;
    protected String password;
    protected String email;
    protected String role;
    protected boolean isActive;
    static User newUser;
    private static User currentUser;
            
    // constructors
    public User(){
        this(" ", " ", " ", " ");
    }
    public User(String username, String password, String email, String role){
        this.email = email;
        this.password = password;
        this.username = username;
        this.role = role;
        this.isActive = true;
    }
    
    // getter
    public String getUsername(){
        return this.username;
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
    public static User getCurrentUser() {
        return currentUser;
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
    public static void setCurrentUser(User user) {
        currentUser = user;
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
    protected static boolean isValidEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    
    //password validation
    protected static boolean isValidPassword(String password){
        return password.length() >= 8 && password.length() <= 16;
    }
    
    protected static boolean isValidUsername(String username){
        return username.length() >= 6 && username.length() <= 30;
    }
    
    public static void userAuthentication(){
        int choice;
        Scanner scanner = new Scanner(System.in);
        
        do {            
            DisplayEffect.drawLine();
            System.out.println("    Welcome to Computer Retail System    ");
            DisplayEffect.drawLine();
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("\nEnter your choice: ");
            choice = scanner.nextInt();
            DisplayEffect.clearScreen();

            switch (choice){
                case 1:
                    login();
                    break;
                case 2:
                    User newuser = register();
                    if (newuser != null) {
                        DisplayEffect.clearScreen();
                        login();
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }
    
    //register
    public static User register(){
        Scanner sc = new Scanner(System.in);
        
        String username;
        String password;
        String email;
        String role;
        
        while (true){
            DisplayEffect.drawLine();
            System.out.println("                 Register                  ");
            DisplayEffect.drawLine();
            System.out.print("\nEnter username: ");
            username = sc.nextLine();
            if (!isValidUsername(username))
                System.out.println("Username must be between 6-30 characters long.");
            else if (userExists(username, userList))
                System.out.println("Username already exists. Please choose another.");
            else
                break;
            
            DisplayEffect.clearScreen();
        }
        
        while (true){
            System.out.print("Enter password (min 8 characters): ");
            password = sc.nextLine();
            if(!isValidPassword(password))
                System.out.println("Password must be between 8-16 characters long.");
            else
                break;
        }
        
        while (true){
            System.out.print("Enter email: ");
            email = sc.nextLine();
            if (!isValidEmail (email))
                System.out.println("Invalid email format. Please enter a valid email."); 
            else
                break;
        }
                
        newUser = new Customer(username, password, email);
        userList.add(newUser);
        System.out.println("\nUser registered successfully!");
        return newUser;
    }
    
    //login
    public static User login() {
    Scanner sc = new Scanner(System.in);
    Customer customer = new Customer();
    Admin admin = new Admin();

    newUser = new Admin("admin", "admin123", "admin@gmail.com");
    userList.add(newUser);

    while (true) {
        DisplayEffect.drawLine();
        System.out.println("                   Login");
        DisplayEffect.drawLine();
        System.out.print("\nEnter username: ");
        String username = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        boolean foundUser = false;
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                foundUser = true;
                if (user.getPassword().equals(password)) {
                    System.out.println("\nLogin successful! Welcome, " + user.getUsername());
                    setCurrentUser(user);
                    if (user.isAdmin()) {
                        admin.admin_menu();
                    } else {
                        customer.customer_menu();
                    }
                    return user;
                } else {
                    System.out.println("\nInvalid password.");
                }
                break;
            }
        }

        if (!foundUser) {
            System.out.println("\nUser not exists.");
            DisplayEffect.clearScreen();
            userAuthentication();
        }

        DisplayEffect.clearScreen();
    }
}
    
    public void updateProfile(){
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Updating profile for user: " + this.username);
        
        //Update username
        System.out.println("Enter new username: ");
        String newUsername = sc.nextLine();
        if (!newUsername.isEmpty()){
            if(!isValidUsername(newUsername))
                System.out.println("Password must be between 6-30 characters long.");
            else {
                this.username = newUsername;
                System.out.println("Username update to: " + this.username);   
            }
        }
        
        //Update password
        System.out.println("Enter new password: ");
        String newPassword = sc.nextLine();
        if(!newPassword.isEmpty()){
            if(!isValidPassword(newPassword))
                System.out.println("Password must be between 8-16 characters long.");
            else {            
                this.password = newPassword;
                System.out.println("Password updated");
            }
        }
        
        //Update email
        System.out.println("Enter new email: ");
        String newEmail = sc.nextLine();
        if(!newEmail.isEmpty()){
            if (!isValidEmail(newEmail))
                System.out.println("Invalid email format. Please enter a valid email."); 
            else {
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
        } else
            System.out.println("Account deactivation cancelled.");
    }
    
    private static boolean userExists(String username, List<User> userList){
        for (User user : userList){
            if (user.getUsername().equals(username))
                return true;
        }
        return false;
    }
}