import java.util.ArrayList;

public class Order {
    private final String orderId;
    private final Customer customer;
    private final ArrayList<OrderItem> items=new ArrayList<>();
    private OrderStatus status = OrderStatus.PENDING;

    private int counter = 0;

    public Order(Customer customer){
        counter++;
        this.orderId = String.format("ORD-%03d", counter);
        //ORD-001
        this.customer = customer;
    }

    public void add(Product product, int quantity){
        items.add(new OrderItem(product,quantity));
    }

    public boolean isEmpty(){
        return items.isEmpty();
    }

    public int getSubtotal(){
        int sum=0;
        for(OrderItem item:items){
            sum+=item.getSubtotal();
        }
        return sum;
    }

    public int getShipping(){
        int sum=0;
        for(OrderItem item:items){
            sum+=item.getShipping();
        }
        return sum;
    }

    public int getDiscount(){
        return customer.getLevel().discountFor(getSubtotal());
    }

    public int getTotal(){
        return getSubtotal() - getDiscount() + getShipping();
    }

    public void markPaid(){
        status = OrderStatus.PAID;
    }
    public void cancel(){
        status = OrderStatus.CANCELLED;
    }

    public String getStatusMessage(){
        return switch (status){
            case PENDING -> "Waiting for payment..";
            case PAID -> "Payment confirmed, thank you!";
            case CANCELLED -> "Order cancelled";
        };
    }

    public void printSummary(){
        MembershipLevel level=customer.getLevel();
        System.out.println("--- " + orderId + " | " + customer.getName()+ "(" + level +") ---");
        if(items.isEmpty()){
            System.out.println("empty order");
        }
        else {
            for(OrderItem item:items){
                System.out.printf("  %d x %-18s Rp%,12d%n",
                        item.getQuantity(),item.getProduct().getName(),item.getSubtotal());
            }
            System.out.printf("  %-22s Rp%,12d%n", "Subtotal", getSubtotal());
            System.out.printf("  %-22s Rp%,12d%n", "Discount "+ level + " " + Math.round(level.getDiscountRate()*100)+ "%", getDiscount());
            System.out.printf("  %-22s Rp%,12d%n", "Shipping", getShipping());
            System.out.printf("  %-22s Rp%,12d%n", "TOTAL", getTotal());
        }
        System.out.println("  Status: " + status + " - " + getStatusMessage());
    }

}
