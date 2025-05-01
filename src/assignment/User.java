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
        String choice;
        Scanner scanner = new Scanner(System.in);
        
        do {            
            DisplayEffect.drawLine();
            System.out.println("    Welcome to Computer Retail System    ");
            DisplayEffect.drawLine();
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Back");
            DisplayEffect.drawLine();
            System.out.print("\nEnter your choice: ");
            choice = scanner.nextLine();

            switch (choice){
                case "1":
                    DisplayEffect.clearScreen();
                    login();
                    break;
                case "2":
                    DisplayEffect.clearScreen();
                    User newuser = register();
                    if (newuser != null) {
                        DisplayEffect.clearScreen();
                        login();
                    }
                    break;
                case "3":
                    DisplayEffect.clearScreen();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    DisplayEffect.clearScreen();
            }
        } while (choice.equals("3"));
    }
    
    //register
    public static User register(){
        Scanner sc = new Scanner(System.in);
        
        String username;
        String password;
        String email;
        
        DisplayEffect.drawLine();
        System.out.println("            CUSTOMER REGISTER");
        DisplayEffect.drawLine();
        System.out.println();
        
        while (true){
            System.out.print("Enter username: ");
            username = sc.nextLine();
            if (!isValidUsername(username))
                System.out.println("Username must be between 6-30 characters long.");
            else if (userExistsByUsername(username))
                System.out.println("Username already exists. Please choose another.");
            else
                break;
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
            else if (userExistsByEmail(email))
                System.out.println("Email already exists. Please choose another");
            else
                break;
        }
        
        String customerID = IDGenerator.generate("CUS");
        newUser = new Customer(username, password, email, "customer", customerID);
        userList.add(newUser);
        System.out.println("\nUser registered successfully with ID: " + customerID);
        return newUser;
    }
    
    //login
    public static User login() {
        Scanner sc = new Scanner(System.in);
        Customer customer = new Customer();
        
        User.userList.add(new Admin("admin", "admin123", "admin@gmail.com"));

        while (true) {
            DisplayEffect.drawLine();
            System.out.println("             CUSTOMER LOGIN");
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
                        if (user.isAdmin()) {
                            System.out.println("\nError: Please use admin login for admin accounts.");
                            DisplayEffect.clearScreen();
                            return null;
                        } else if (!user.isActive()) {
                            System.out.println("\nThis account has been deactivated and cannot be used.");
                            DisplayEffect.clearScreen();
                            return null;
                        } else {
                            System.out.println("\nLogin successful! Welcome, " + user.getUsername());
                            setCurrentUser(user);
                            customer.customerMenu();
                            return user;
                        }
                    } else {
                        System.out.println("\nInvalid password.");
                        DisplayEffect.clearScreen();
                        return null;
                    }
                }
            }

            if (!foundUser) {
                System.out.println("\nUser not exists.");
            }

            DisplayEffect.clearScreen();
            return null;
        }
    }
    
    public void updateProfile(){
        Scanner sc = new Scanner(System.in);
        boolean updated = false;
        
        System.out.println("\n--- Enter New Details (Press Enter to keep current value) ---");
        
        // Update username
        while (true) {
            System.out.print("Enter new username: ");
            String newUsername = sc.nextLine();
            
            if (newUsername.isEmpty()) {
                break; // Keep current username
            }
            
            if (!isValidUsername(newUsername)) {
                System.out.println("Username must be between 6-30 characters long.");
                continue;
            }
            
            if (userExistsByUsername(newUsername) && !newUsername.equals(this.username)) {
                System.out.println("Username already exists. Please choose another.");
                continue;
            }
            
            this.username = newUsername;
            updated = true;
            break;
        }
        
        //Update password
        while (true) {
            System.out.print("Enter new password: ");
            String newPassword = sc.nextLine();
            
            if (newPassword.isEmpty()) {
                break; // Keep current password
            }
            
            if (!isValidPassword(newPassword)) {
                System.out.println("Password must be between 8-16 characters long.");
                continue;
            }

            this.password = newPassword;
            updated = true;
            break;
        }
        
        //Update email
        while (true) {
            System.out.print("Enter new email: ");
            String newEmail = sc.nextLine();
            
            if (newEmail.isEmpty()) {
                break; // Keep current email
            }
            
            if (!isValidEmail(newEmail)) {
                System.out.println("Invalid email format. Please enter a valid email.");
                continue;
            }

            this.email = newEmail;
            updated = true;
            break;
        }
        
        if (updated) {
            System.out.println("\nProfile updated successfully.");
        } else {
            System.out.println("\nNo changes were made to your profile.");
        }
    }
    
    //deactivate account
    public boolean deactivateAccount(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Are you sure you want to deactivate your account? (yes/no): ");
        String choice = sc.nextLine().trim().toLowerCase();
        
        if(choice.equals("yes")){
            this.isActive = false;
            for (int i = 0; i < userList.size(); i++) {
                User user = userList.get(i);
                if (user.getUsername().equals(this.username)) {
                    user.setActive(false);
                    userList.set(i, user);
                    break;
                }
            }
            System.out.println("\nAccount deactivated. Logging out ...");
            return true;
        } else
            System.out.println("\nAccount deactivation cancelled.");
            return false;
    }
    
    private static boolean userExistsByUsername(String username){
        for (User user : userList){
            if (user.getUsername().equals(username))
                return true;
        }
        return false;
    }
    
    private static boolean userExistsByEmail(String email) {
        for (User user : userList) {
            if (user.getEmail().equalsIgnoreCase(email))
                return true;
        }
        return false;
    }
}