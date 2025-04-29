package assignment;

import java.util.*;

public class DisplayEffect {
    public static void clearScreen(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Press Enter to continue...");
        sc.nextLine();
        for (int i = 0; i < 50; i++)
            System.out.println();
    }
    
    public static void drawLine(){
        System.out.println("=========================================");
    }
}
