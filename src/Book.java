public class Book extends Product{
    private final String author;

    public Book(String name, int price, String author) {
        super(name, price);
        this.author = author;
    }

    @Override
    public int getShippingFee() {
        return 15_000;
    }

    @Override
    public String getDetail() {
        return "by " + author;
    }
}
