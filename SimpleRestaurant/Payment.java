public class Payment {
    int id;
    int orderId;
    double amount;
    String method;   // Cash, Card, UPI

    Payment(int id, int orderId, double amount, String method) {
        this.id = id;
        this.orderId = orderId;
        this.amount = amount;
        this.method = method;
    }

    void show() {
        System.out.println("Payment #" + id + " | Order #" + orderId
                + " | Rs." + amount + " | " + method + " | SUCCESS");
    }
}
