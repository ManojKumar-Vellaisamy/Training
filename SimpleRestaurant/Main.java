import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    // data (database illa, ArrayList la store aagum)
    static ArrayList<Table> tables = new ArrayList<>();
    static ArrayList<MenuItem> menu = new ArrayList<>();
    static ArrayList<Order> orders = new ArrayList<>();
    static ArrayList<Payment> payments = new ArrayList<>();

    static int nextMenuId = 1;
    static int nextOrderId = 1;
    static int nextPaymentId = 1;

    // ---------- input helper ----------
    static int readInt(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    static double readDouble(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }

    // ---------- find methods ----------
    static Table findTable(int id) {
        for (Table t : tables) {
            if (t.id == id) return t;
        }
        return null;
    }

    static MenuItem findMenuItem(int id) {
        for (MenuItem m : menu) {
            if (m.id == id) return m;
        }
        return null;
    }

    static Order findOrder(int id) {
        for (Order o : orders) {
            if (o.id == id) return o;
        }
        return null;
    }

    // ---------- main ----------
    public static void main(String[] args) {
        // default data
        tables.add(new Table(1, 5));
        tables.add(new Table(2, 2));
        tables.add(new Table(3, 7));

        menu.add(new MenuItem(nextMenuId++, "Masala Dosa", 80));
        menu.add(new MenuItem(nextMenuId++, "Paneer Butter Masala", 180));
        menu.add(new MenuItem(nextMenuId++, "Veg Biryani", 150));
        menu.add(new MenuItem(nextMenuId++, "Chicken Biryani", 220));
        menu.add(new MenuItem(nextMenuId++, "Filter Coffee", 40));
        menu.add(new MenuItem(nextMenuId++, "Gulab Jamun", 60));

        int choice;
        do {
            System.out.println("\n##### RESTAURANT MANAGEMENT #####");
            System.out.println("1. Tables");
            System.out.println("2. Menu");
            System.out.println("3. Orders");
            System.out.println("4. Payments");
            System.out.println("0. Exit");
            choice = readInt("Enter choice: ");

            switch (choice) {
                case 1: tableMenu(); break;
                case 2: menuMenu(); break;
                case 3: orderMenu(); break;
                case 4: paymentMenu(); break;
                case 0: System.out.println("Thank you!"); break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    // ================= TABLE =================
    static void tableMenu() {
        int choice;
        do {
            System.out.println("\n=== TABLE MANAGEMENT ===");
            System.out.println("1. View All Tables");
            System.out.println("2. Free a Table");
            System.out.println("0. Back");
            choice = readInt("Enter choice: ");

            if (choice == 1) {
                for (Table t : tables) t.show();
            } else if (choice == 2) {
                Table t = findTable(readInt("Table ID: "));
                if (t == null) {
                    System.out.println("Table not found.");
                } else {
                    t.occupied = false;
                    System.out.println("Table is now free.");
                }
            } else if (choice != 0) {
                System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    // ================= MENU =================
    static void showMenu() {
        System.out.println("--- MENU ---");
        for (MenuItem m : menu) m.show();
    }

    static void menuMenu() {
        int choice;
        do {
            System.out.println("\n=== MENU MANAGEMENT ===");
            System.out.println("1. View Menu");
            System.out.println("2. Add Item");
            System.out.println("3. Remove Item");
            System.out.println("0. Back");
            choice = readInt("Enter choice: ");

            if (choice == 1) {
                showMenu();
            } else if (choice == 2) {
                System.out.print("Item name: ");
                String name = sc.nextLine().trim();
                double price = readDouble("Price: Rs.");
                if (name.isEmpty() || price <= 0) {
                    System.out.println("Invalid name or price.");
                } else {
                    menu.add(new MenuItem(nextMenuId++, name, price));
                    System.out.println("Item added.");
                }
            } else if (choice == 3) {
                MenuItem m = findMenuItem(readInt("Item ID to remove: "));
                if (m == null) {
                    System.out.println("Item not found.");
                } else {
                    menu.remove(m);
                    System.out.println("Item removed.");
                }
            } else if (choice != 0) {
                System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    // ================= ORDER =================
    static void orderMenu() {
        int choice;
        do {
            System.out.println("\n=== ORDER MANAGEMENT ===");
            System.out.println("1. New Order");
            System.out.println("2. Add Item to Order");
            System.out.println("3. Remove Item from Order");
            System.out.println("4. View Bill");
            System.out.println("5. Cancel Order");
            System.out.println("6. View All Orders");
            System.out.println("0. Back");
            choice = readInt("Enter choice: ");

            switch (choice) {
                case 1: newOrder(); break;
                case 2: addItemToOrder(readInt("Order ID: ")); break;
                case 3: removeItemFromOrder(); break;
                case 4: viewBill(); break;
                case 5: cancelOrder(); break;
                case 6: viewAllOrders(); break;
                case 0: break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    static void newOrder() {
        for (Table t : tables) t.show();
        int tableId = readInt("Choose table ID: ");

        Table t = findTable(tableId);
        if (t == null) {
            System.out.println("Table not found.");
            return;
        }
        if (t.occupied) {
            System.out.println("Table is already occupied.");
            return;
        }

        t.occupied = true;                       // table occupy
        Order o = new Order(nextOrderId++, tableId);
        orders.add(o);
        System.out.println("Order #" + o.id + " created for Table " + tableId);

        String more;
        do {
            addItemToOrder(o.id);
            System.out.print("Add another item? (y/n): ");
            more = sc.nextLine().trim();
        } while (more.equalsIgnoreCase("y"));
    }

    static void addItemToOrder(int orderId) {
        Order o = findOrder(orderId);
        if (o == null || !o.status.equals("OPEN")) {
            System.out.println("Open order not found.");
            return;
        }
        showMenu();
        MenuItem m = findMenuItem(readInt("Menu item ID: "));
        if (m == null) {
            System.out.println("Menu item not found.");
            return;
        }
        int qty = readInt("Quantity: ");
        if (qty <= 0) {
            System.out.println("Quantity must be at least 1.");
            return;
        }
        o.addItem(m, qty);
        System.out.println("Item added.");
    }

    static void removeItemFromOrder() {
        Order o = findOrder(readInt("Order ID: "));
        if (o == null || !o.status.equals("OPEN")) {
            System.out.println("Open order not found.");
            return;
        }
        int itemId = readInt("Menu item ID to remove: ");
        if (o.removeItem(itemId)) {
            System.out.println("Item removed.");
        } else {
            System.out.println("Item is not in this order.");
        }
    }

    static void viewBill() {
        Order o = findOrder(readInt("Order ID: "));
        if (o == null) {
            System.out.println("Order not found.");
        } else {
            o.showBill();
        }
    }

    static void cancelOrder() {
        Order o = findOrder(readInt("Order ID: "));
        if (o == null || !o.status.equals("OPEN")) {
            System.out.println("Open order not found.");
            return;
        }
        o.status = "CANCELLED";
        findTable(o.tableId).occupied = false;   // table free
        System.out.println("Order cancelled, table freed.");
    }

    static void viewAllOrders() {
        if (orders.isEmpty()) {
            System.out.println("No orders yet.");
        }
        for (Order o : orders) {
            System.out.println("Order #" + o.id + " | Table " + o.tableId
                    + " | Total: Rs." + o.getTotal() + " | " + o.status);
        }
    }

    // ================= PAYMENT =================
    static void paymentMenu() {
        int choice;
        do {
            System.out.println("\n=== PAYMENT ===");
            System.out.println("1. Pay Order Bill");
            System.out.println("2. View All Payments");
            System.out.println("0. Back");
            choice = readInt("Enter choice: ");

            if (choice == 1) {
                payBill();
            } else if (choice == 2) {
                if (payments.isEmpty()) System.out.println("No payments yet.");
                for (Payment p : payments) p.show();
            } else if (choice != 0) {
                System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    static void payBill() {
        Order o = findOrder(readInt("Order ID: "));
        if (o == null || !o.status.equals("OPEN")) {
            System.out.println("Open order not found.");
            return;
        }
        if (o.items.isEmpty()) {
            System.out.println("Order has no items.");
            return;
        }

        double amount = o.getTotal();
        System.out.println("Amount to pay: Rs." + amount);
        System.out.println("1. Cash  2. Card  3. UPI");
        int m = readInt("Payment method: ");

        String method;
        if (m == 1) method = "Cash";
        else if (m == 2) method = "Card";
        else if (m == 3) method = "UPI";
        else {
            System.out.println("Invalid method.");
            return;
        }

        Payment p = new Payment(nextPaymentId++, o.id, amount, method);
        payments.add(p);

        o.status = "PAID";
        findTable(o.tableId).occupied = false;   // table free

        System.out.println(method + " payment successful. Table is now free.");
        p.show();
    }
}
