public enum MembershipLevel {
    BASIC(0.00),
    SILVER(0.05),
    GOLD(0.10);

    private final double discountRate;
    MembershipLevel(double discountRate) {
        this.discountRate = discountRate;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public int discountFor(int subtotal){
        return (int) Math.round(subtotal*discountRate);
    }
}
