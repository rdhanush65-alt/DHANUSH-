import java.util.*;

public class PocketMartsAI {

    static Scanner sc = new Scanner(System.in);

    static class Product {
        int id;
        String name, category;
        double price;
        int stock;

        Product(int id, String name, String category, double price, int stock) {
            this.id = id;
            this.name = name;
            this.category = category;
            this.price = price;
            this.stock = stock;
        }

        public String toString() {
            return id + " | " + name + " | " + category +
                   " | Rs." + price + " | Stock: " + stock;
        }
    }

    static ArrayList<Product> products = new ArrayList<>();
    static LinkedHashMap<Product, Integer> cart = new LinkedHashMap<>();

    static void loadProducts() {
        products.add(new Product(1, "Rice 5kg", "Grocery", 320, 20));
        products.add(new Product(2, "Wheat Flour 2kg", "Grocery", 120, 25));
        products.add(new Product(3, "Milk 1L", "Dairy", 60, 30));
        products.add(new Product(4, "Bread", "Bakery", 45, 20));
        products.add(new Product(5, "Apple 1kg", "Fruits", 180, 15));
        products.add(new Product(6, "Biscuits", "Snacks", 30, 40));
        products.add(new Product(7, "Soap", "Personal Care", 55, 25));
        products.add(new Product(8, "Shampoo", "Personal Care", 160, 15));
        products.add(new Product(9, "Cooking Oil 1L", "Grocery", 150, 20));
        products.add(new Product(10, "Coffee 200g", "Beverages", 110, 18));
    }

    static void showProducts() {
        System.out.println("\n========== PRODUCT LIST ==========");
        for (Product p : products) {
            System.out.println(p);
        }
    }

    static void searchProduct() {
        sc.nextLine();
        System.out.print("Enter product name/category: ");
        String keyword = sc.nextLine().toLowerCase();

        boolean found = false;
        System.out.println("\n========== SEARCH RESULTS ==========");

        for (Product p : products) {
            if (p.name.toLowerCase().contains(keyword)
                    || p.category.toLowerCase().contains(keyword)) {
                System.out.println(p);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No product found.");
        }
    }

    static Product findProduct(int id) {
        for (Product p : products) {
            if (p.id == id) {
                return p;
            }
        }
        return null;
    }

    static void addToCart() {
        showProducts();

        System.out.print("\nEnter Product ID: ");
        int id = sc.nextInt();

        Product p = findProduct(id);

        if (p == null) {
            System.out.println("Invalid Product ID.");
            return;
        }

        System.out.print("Enter quantity: ");
        int quantity = sc.nextInt();

        if (quantity <= 0) {
            System.out.println("Invalid quantity.");
            return;
        }

        int existing = cart.getOrDefault(p, 0);

        if (existing + quantity > p.stock) {
            System.out.println("Not enough stock available.");
            return;
        }

        cart.put(p, existing + quantity);
        System.out.println(quantity + " x " + p.name + " added to cart.");
    }

    static double cartTotal() {
        double total = 0;

        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            total += entry.getKey().price * entry.getValue();
        }

        return total;
    }

    static void viewCart() {
        System.out.println("\n========== YOUR CART ==========");

        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();

            System.out.println(
                    p.name + " x " + qty +
                    " = Rs." + (p.price * qty)
            );
        }

        System.out.println("-------------------------------");
        System.out.println("Total: Rs." + cartTotal());
    }

    static void removeFromCart() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        viewCart();

        System.out.print("\nEnter Product ID to remove: ");
        int id = sc.nextInt();

        Product p = findProduct(id);

        if (p != null && cart.containsKey(p)) {
            cart.remove(p);
            System.out.println(p.name + " removed from cart.");
        } else {
            System.out.println("Product is not in the cart.");
        }
    }

    static void aiRecommendation() {
        System.out.println("\n========== POCKETMARTS AI ==========");
        System.out.println("AI Recommendation Engine");

        if (cart.isEmpty()) {
            System.out.println("Recommended for you:");
            System.out.println("1. Rice 5kg - Grocery");
            System.out.println("2. Cooking Oil 1L - Grocery");
            System.out.println("3. Milk 1L - Dairy");
            System.out.println("4. Biscuits - Snacks");
            return;
        }

        String category = cart.keySet().iterator().next().category;

        System.out.println("Based on your cart, you may also like:");

        boolean found = false;

        for (Product p : products) {
            if (p.category.equalsIgnoreCase(category)
                    && !cart.containsKey(p)) {
                System.out.println("- " + p.name + " - Rs." + p.price);
                found = true;
            }
        }

        if (!found) {
            System.out.println("- Milk 1L - Rs.60");
            System.out.println("- Biscuits - Rs.30");
        }
    }

    static void checkout() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        sc.nextLine();

        System.out.print("Enter customer name: ");
        String name = sc.nextLine();

        System.out.print("Enter mobile number: ");
        String mobile = sc.nextLine();

        double subtotal = cartTotal();
        double discount = subtotal >= 500 ? subtotal * 0.10 : 0;
        double gst = (subtotal - discount) * 0.05;
        double finalAmount = subtotal - discount + gst;

        System.out.println("\n====================================");
        System.out.println("          POCKETMARTS AI");
        System.out.println("             BILL");
        System.out.println("====================================");
        System.out.println("Customer : " + name);
        System.out.println("Mobile   : " + mobile);
        System.out.println("------------------------------------");

        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();

            System.out.printf("%-20s x%-3d Rs.%.2f%n",
                    p.name, qty, p.price * qty);

            p.stock -= qty;
        }

        System.out.println("------------------------------------");
        System.out.printf("Subtotal : Rs.%.2f%n", subtotal);
        System.out.printf("Discount : Rs.%.2f%n", discount);
        System.out.printf("GST 5%%   : Rs.%.2f%n", gst);
        System.out.printf("TOTAL    : Rs.%.2f%n", finalAmount);
        System.out.println("====================================");
        System.out.println("Thank you for shopping with PocketMarts!");
        System.out.println("====================================");

        cart.clear();
    }

    static void adminStock() {
        System.out.println("\n========== STOCK MANAGEMENT ==========");

        showProducts();

        System.out.print("\nEnter Product ID: ");
        int id = sc.nextInt();

        Product p = findProduct(id);

        if (p == null) {
            System.out.println("Invalid Product ID.");
            return;
        }

        System.out.print("Enter stock to add: ");
        int quantity = sc.nextInt();

        if (quantity > 0) {
            p.stock += quantity;
            System.out.println("Stock updated successfully.");
        } else {
            System.out.println("Invalid quantity.");
        }
    }

    static void menu() {
        while (true) {
            System.out.println("\n======================================");
            System.out.println("          POCKETMARTS AI");
            System.out.println("       SMART SHOPPING SYSTEM");
            System.out.println("======================================");
            System.out.println("1. View Products");
            System.out.println("2. Search Product");
            System.out.println("3. Add Product to Cart");
            System.out.println("4. View Cart");
            System.out.println("5. Remove Product from Cart");
            System.out.println("6. AI Recommendations");
            System.out.println("7. Checkout & Generate Bill");
            System.out.println("8. Add Stock");
            System.out.println("9. Exit");
            System.out.println("======================================");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    showProducts();
                    break;

                case 2:
                    searchProduct();
                    break;

                case 3:
                    addToCart();
                    break;

                case 4:
                    viewCart();
                    break;

                case 5:
                    removeFromCart();
                    break;

                case 6:
                    aiRecommendation();
                    break;

                case 7:
                    checkout();
                    break;

                case 8:
                    adminStock();
                    break;

                case 9:
                    System.out.println("Thank you for using PocketMarts AI.");
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public static void main(String[] args) {
        loadProducts();
        menu();
    }
}
