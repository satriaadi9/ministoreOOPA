public class Customer {
    private final String name;
    private MembershipLevel level;

    public Customer(String name){
        this.name = name;
        level=MembershipLevel.BASIC;
    }

    public String getName() {
        return name;
    }
    public MembershipLevel getLevel() {
        return level;
    }
    public void setLevel(MembershipLevel level) {
        this.level = level;
    }
}
