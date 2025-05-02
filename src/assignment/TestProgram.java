package assignment;

import java.util.Scanner;

public class TestProgram {
    
    public static void main(String[] args) {
        User.userList.add(new Admin("admin", "admin123", "admin@gmail.com"));
        
        // User Authentication Section
        String choice;
        Scanner scanner = new Scanner(System.in);
        
        OUTER:
        while (true) {
            DisplayEffect.drawLine();
            System.out.println("               Select Role");
            DisplayEffect.drawLine();
            System.out.println("1. Customer");
            System.out.println("2. Admin");
            System.out.println("3. Exit");
            DisplayEffect.drawLine();
            System.out.print("\nEnter your choice: ");
            choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    Customer customer = new Customer();
                    customer.customerAuthentication();
                    break;
                case "2":
                    Admin admin = new Admin();
                    admin.adminLogin();
                    break;
                case "3":
                    System.out.println("\n---------- Exiting Program ----------\n");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    DisplayEffect.clearScreen();
                    break;
            }
        }
    }   
}
