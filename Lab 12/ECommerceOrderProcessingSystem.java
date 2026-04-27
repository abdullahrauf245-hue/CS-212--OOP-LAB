import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

class InvalidProductIdException extends Exception {
    public InvalidProductIdException(String message) {
        super(message);
    }
}

class OutOfStockException extends Exception {
    public OutOfStockException(String message) {
        super(message);
    }
}

class EmptyOrderException extends Exception {
    public EmptyOrderException(String message) {
        super(message);
    }
}

class Product {
    int id;
    String name;
    int stock;

    Product(int id, String name, int stock) {
        this.id = id;
        this.name = name;
        this.stock = stock;
    }
}

public class ECommerceOrderProcessingSystem {
    private final Map<Integer, Product> productCatalog = new HashMap<>();

    public ECommerceOrderProcessingSystem() {
        productCatalog.put(101, new Product(101, "Keyboard", 5));
        productCatalog.put(102, new Product(102, "Mouse", 10));
        productCatalog.put(103, new Product(103, "Headphones", 3));
    }

    public void processOrder(Map<Integer, Integer> order)
        throws EmptyOrderException, InvalidProductIdException, OutOfStockException {

      if (order == null || order.isEmpty()) {
            throw new EmptyOrderException("Order failed: order is empty. Add products first.");
        }

        for (Map.Entry<Integer, Integer> item : order.entrySet()) {
            int productId = item.getKey();
            int requestedQty = item.getValue();

            if (!productCatalog.containsKey(productId)) {
                throw new InvalidProductIdException("Order failed: product ID " + productId + " is invalid.");
            }

            Product p = productCatalog.get(productId);
            if (requestedQty > p.stock) {
                throw new OutOfStockException(
                    "Order failed: " + p.name + " has only " + p.stock + " items left, requested " + requestedQty + "."
                );
            }
        }

        for (Map.Entry<Integer, Integer> item : order.entrySet()) {
            Product p = productCatalog.get(item.getKey());
            p.stock -= item.getValue();
        }

        System.out.println("Order placed succesfully.");
    }

    public static void main(String[] args) {
        ECommerceOrderProcessingSystem system = new ECommerceOrderProcessingSystem();

        Map<Integer, Integer> validOrder = new LinkedHashMap<>();
        validOrder.put(101, 2);
        validOrder.put(102, 1);

        Map<Integer, Integer> invalidProductOrder = new LinkedHashMap<>();
        invalidProductOrder.put(999, 1);

        Map<Integer, Integer> outOfStockOrder = new LinkedHashMap<>();
        outOfStockOrder.put(103, 6);

        Map<Integer, Integer> emptyOrder = new LinkedHashMap<>();

        Map<String, Map<Integer, Integer>> demoOrders = new LinkedHashMap<>();
        demoOrders.put("Valid Order", validOrder);
        demoOrders.put("Invalid Product ID Order", invalidProductOrder);
        demoOrders.put("Out Of Stock Order", outOfStockOrder);
        demoOrders.put("Empty Order", emptyOrder);

        System.out.println("=== E-Commerce Order Processing System ===");

        for (Map.Entry<String, Map<Integer, Integer>> test : demoOrders.entrySet()) {
            System.out.println("\nChecking: " + test.getKey());
            try {
                system.processOrder(test.getValue());
            } catch (EmptyOrderException | InvalidProductIdException | OutOfStockException ex) {
                System.out.println(ex.getMessage());
            }
        }

        System.out.println("\nSystem kept running after handling all exceptions.");
    }
}
