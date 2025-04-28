package assignment;

public abstract class Payment {
    // data properties
    protected final String paymentID;
    protected Order order;
    protected static String paymentMethod;
    
    // constructors
    protected Payment(){
        this(null);
    }
    protected Payment(Order order){
        this.paymentID = IDGenerator.generate("PAY");
        this.order = order;
    }

    // getters
    public String getPaymentID(){
        return paymentID;
    }
    
    public Order getOrder() {
        return order;
    }
    
    // setters
    public void setOrder(Order order) {
        this.order = order;
    }
    
    public static void processCardPayment(Order order, double amount) {
        System.out.println("\n=== Card Payment ===");
        
        CardPayment c1 = new CardPayment(order);
        paymentMethod = c1.getPaymentType();
        boolean success = c1.cardPayment(amount);
        System.out.println();
        if (success == true) {
            order.setOrderStatus(Order.OrderStatus.PAID);
            System.out.println(c1);
        }
    }

    public static void processTnGEwalletPayment(Order order, double amount) {
        System.out.println("\n=== TNG eWallet Payment ===");
        
        TnGEwalletPayment e1 = new TnGEwalletPayment(order);
        paymentMethod = e1.getPaymentType();
        boolean success = e1.ewalletPayment(amount);
        System.out.println();
        if (success == true) {
            order.setOrderStatus(Order.OrderStatus.PAID);
            System.out.println(e1);
        }
    }
    
    // other methods
    @Override
    public String toString() {
        return "Payment ID: " + paymentID + 
               "\nMethod: " + paymentMethod +
               "\nDate: " + (order != null ? order.getFormattedDate() : "No order attached");
    }
}