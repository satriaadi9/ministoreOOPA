public class Laptop extends Product {
    private final int warrantyMonths;

    public Laptop(String name, int price, int warrantyMonths){
        super(name, price);
        this.warrantyMonths = warrantyMonths;
    }

    @Override
    public int getShippingFee() {
        return 75_000;
    }

    @Override
    public String getDetail() {
        return warrantyMonths + "-month(s) warranty";
    }
}
