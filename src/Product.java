public abstract class Product {
    private final String name;
    private final int price;

    protected Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public int getPrice() {
        return price;
    }

    public abstract int getShippingFee();
    public abstract String getDetail();

    public String describe(){
        return String.format("%-18s Rp%,11d   %-20s ship Rp%,d",
                name, price, getDetail(), getShippingFee());
    }

}
