package assignment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class IDGenerator {
    private static final HashMap<String, Integer> counters = new HashMap<>();
    private static final HashMap<String, String> lastDates = new HashMap<>();
    
    public static String generate(String prefix) {
        // Get current date in yyyyMMdd format
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        
        // Check last date for this prefix
        String lastDate = lastDates.get(prefix);
        
        // If date changed, reset counter
        if (lastDate == null || !lastDate.equals(today)) {
            counters.put(prefix, 1);
            lastDates.put(prefix, today);
        }
        
        // Get current counter, then increment
        int current = counters.get(prefix);
        counters.put(prefix, current + 1);
        
        // Return formatted ID: e.g., PAY20250423001
        return prefix + today + String.format("%03d", current);
    }
}
