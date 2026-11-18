public class Subscription extends Product{
    private final int durationMonths;

    public Subscription(String name, int price, int durationMonths) {
        super(name, price);
        this.durationMonths = durationMonths;
    }

    @Override
    public int getShippingFee() {
        return 0;
    }

    @Override
    public String getDetail(){
        return durationMonths + "-month(s) access";
    }
}
