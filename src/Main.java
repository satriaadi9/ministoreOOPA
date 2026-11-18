import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main {

    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {


        ArrayList<Product> catalog = new ArrayList<>();
        catalog.add(new Book("Java Fundamentals", 120_000, "A. Wijaya"));
        catalog.add(new Laptop("Laptop Pro 14", 12_500_000, 24));
        catalog.add(new Subscription("E-Learning Pass", 300_000, 6));
        catalog.add(new Book("Clean Design", 150_000, "R. Santoso"));
        catalog.add(new Laptop("Laptop Air 13", 9_800_000, 12));
        catalog.add(new Subscription("Cloud Storage 1TB", 50_000, 1));


        System.out.println("=== Welcome to UC MINI STORE ===");
        String name = readLine("Customer name: ");
        Customer customer = new Customer(name.isEmpty() ? "Guest" : name);
        Order order = new Order(customer);
        boolean flashSaleAdded = false;

        while (true) {
            System.out.println();
            System.out.println("=========== MAIN MENU ===========");
            System.out.println("1. Show products");
            System.out.println("2. Sort products by price");
            System.out.println("3. Add product to order");
            System.out.println("4. View order");
            System.out.println("5. Change membership level");
            System.out.println("6. Checkout");
            System.out.println("7. Add flash-sale item");
            System.out.println("0. Exit");

            int choice = readInt("Choose: ");
            System.out.println();

            switch (choice) {
                case 1 -> showProducts(catalog);
                case 2 -> sortByPrice(catalog);
                case 3 -> addToOrder(catalog, order);
                case 4 -> order.printSummary();
                case 5 -> changeLevel(customer);
                case 6 -> {
                    if (checkout(order)) {
                        order = new Order(customer);    // old order and its items are gone,
                    }                                   // the customer (and level) stays
                }
                case 7 -> {
                    if (flashSaleAdded) {
                        System.out.println("Flash-sale item is already in the catalog.");
                    } else {
                        addFlashSale(catalog);
                        flashSaleAdded = true;
                    }
                }
                case 0 -> {
                    System.out.println("Thank you for shopping, " + customer.getName() + "!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void showProducts(ArrayList<Product> catalog) {
        System.out.println("------------------------------ PRODUCTS ------------------------------");
        for (int i = 0; i < catalog.size(); i++) {
            System.out.println((i + 1) + ". " + catalog.get(i).describe());
        }
    }

    // ANONYMOUS CLASS implementing an INTERFACE (Comparator):
    // a one-off sorting rule, so it does not need its own named class
    static void sortByPrice(ArrayList<Product> catalog) {
        catalog.sort(new Comparator<Product>() {
            @Override
            public int compare(Product a, Product b) {
                return Integer.compare(a.getPrice(), b.getPrice());
            }
        });
        System.out.println("Sorted from cheapest to most expensive.");
        showProducts(catalog);
    }

    // ANONYMOUS CLASS extending an ABSTRACT CLASS:
    // a one-day product that will never be reused, so no Product subclass file is needed
    static void addFlashSale(ArrayList<Product> catalog) {
        Product mysteryBox = new Product("Mystery Box", 49_000) {
            @Override
            public int getShippingFee() {
                return 0;                    // free shipping today only
            }

            @Override
            public String getDetail() {
                return "flash sale, today";
            }
        };
        catalog.add(mysteryBox);
        System.out.println("Added: " + mysteryBox.getName());
        System.out.println("Its class has no name we wrote. Java calls it: "
                + mysteryBox.getClass().getName());
    }

    static void addToOrder(ArrayList<Product> catalog, Order order) {
        showProducts(catalog);
        int number = readInt("Product number: ");
        if (number < 1 || number > catalog.size()) {
            System.out.println("No such product.");
            return;
        }
        int qty = readInt("Quantity: ");
        if (qty < 1) {
            System.out.println("Quantity must be at least 1.");
            return;
        }
        Product product = catalog.get(number - 1);
        order.add(product, qty);
        System.out.println("Added " + qty + " x " + product.getName() + ".");
    }

    // ENUM values(): the menu is built from the enum itself,
    static void changeLevel(Customer customer) {
        System.out.println("Current level: " + customer.getLevel());
        MembershipLevel[] levels = MembershipLevel.values();
        for (int i = 0; i < levels.length; i++) {
            System.out.printf("%d. %-8s %2d%% off%n",
                    i + 1, levels[i], Math.round(levels[i].getDiscountRate() * 100));
        }
        int pick = readInt("Choose level: ");
        if (pick < 1 || pick > levels.length) {
            System.out.println("No such level.");
            return;
        }
        customer.setLevel(levels[pick - 1]);
        System.out.println(customer.getName() + " is now " + customer.getLevel() + ".");
    }

    static boolean checkout(Order order) {
        if (order.isEmpty()) {
            System.out.println("Order is empty.");
            return false;
        }
        order.printSummary();
        String answer = readLine("Pay now? (y/n): ");
        if (answer.equalsIgnoreCase("y")) {
            order.markPaid();
        } else {
            order.cancel();
        }
        System.out.println(order.getStatusMessage());
        System.out.println("A new empty order has been started.");
        return true;
    }

    // ---- input helpers ----
    static String readLine(String prompt) {
        System.out.print(prompt);
        return in.hasNextLine() ? in.nextLine().trim() : "0";
    }

    static int readInt(String prompt) {
        while (true) {
            String s = readLine(prompt);
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }
}
