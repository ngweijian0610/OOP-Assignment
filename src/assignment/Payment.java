package assignment;

public abstract class Payment implements Payable {
    // data properties
    protected final String paymentID;
    protected Order order;
    
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
    
    // other methods
    @Override
    public String toString() {
        if (order == null) {
            return  "Payment ID: " + paymentID + 
                    "\nMethod: " + getPaymentType() +
                    "\nDate: No order attached";
        } else {
            return  "Payment ID: " + paymentID +
                    "\nMethod: " + getPaymentType() +
                    "\nDate: " + order.getFormattedDate();
        }
    }
}
