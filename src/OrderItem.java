public class OrderItem {
    private final Product product;
    private final int quantity;

    OrderItem(Product product, int quantity){
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct(){
        return product;
    }
    public int getQuantity(){
        return quantity;
    }
    public int getSubtotal(){
        return product.getPrice() * quantity;
    }
    public int getShipping(){
        return product.getShippingFee()*quantity;
    }
}
