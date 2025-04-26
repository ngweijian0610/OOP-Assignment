package assignment;

import java.time.LocalDate;

public class TestPayment {
    public static void main(String[] args) {
        testCardPayment();
//        testTnGEwalletPayment();
    }

    public static void testCardPayment() {
        System.out.println("=== Testing Card Payment ===");
        
        CardPayment c1 = new CardPayment();
        boolean success = c1.processPayment(100.50);
        System.out.println("Payment successful: " + success);
        System.out.println();
        System.out.println(c1);
    }

    public static void testTnGEwalletPayment() {
        System.out.println("\n=== Testing TNG eWallet Payment ===");
        
        TnGEwalletPayment e1 = new TnGEwalletPayment();
        boolean success = e1.processPayment(80.00);
        System.out.println("Payment successful: " + success);
        System.out.println();
        System.out.println(e1);
    }
}
