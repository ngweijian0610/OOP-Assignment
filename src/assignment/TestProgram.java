package assignment;

import java.util.Scanner;

public class TestProgram {
    
    public static void main(String[] args) {
        // User Authentication Section
        Scanner scanner = new Scanner(System.in);
        
        Customer customer = new Customer();
        customer.register();   
        for(User cust: customer.getUserList()){
            System.out.println("12345"+cust);
        }
        
    }
    
}
