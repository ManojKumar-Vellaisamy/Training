import java.util.ArrayList;

public class Order {
    int id;
    int tableId;
    String status;                       // OPEN, PAID, CANCELLED
    ArrayList<OrderItem> items = new ArrayList<>();

    Order(int id, int tableId) {
        this.id = id;
        this.tableId = tableId;
        this.status = "OPEN";
    }

    void addItem(MenuItem menuItem, int qty) {
        // same item already irundha quantity mattum increase
        for (OrderItem oi : items) {
            if (oi.item.id == menuItem.id) {
                oi.quantity = oi.quantity + qty;
                return;
            }
        }
        items.add(new OrderItem(menuItem, qty));
    }

    boolean removeItem(int menuItemId) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).item.id == menuItemId) {
                items.remove(i);
                return true;
            }
        }
        return false;
    }

    double getTotal() {
        double total = 0;
        for (OrderItem oi : items) {
            total = total + oi.getTotal();
        }
        return total;
    }

    void showBill() {
        System.out.println("\n--- BILL: Order #" + id + " (Table " + tableId + ") [" + status + "] ---");
        if (items.isEmpty()) {
            System.out.println("(no items)");
        }
        for (OrderItem oi : items) {
            System.out.println(oi.item.name + " x" + oi.quantity + " = Rs." + oi.getTotal());
        }
        System.out.println("TOTAL: Rs." + getTotal());
    }
}
