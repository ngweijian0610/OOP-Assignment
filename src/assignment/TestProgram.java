package assignment;

import java.util.Scanner;

public class TestProgram {
    
    public static void main(String[] args) {
        // User Authentication Section
        Scanner scanner = new Scanner(System.in);
        
        Customer customer = new Customer();
        customer.register();   
        
        customer.login();
    }   
}