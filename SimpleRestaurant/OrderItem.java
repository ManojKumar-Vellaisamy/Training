public class OrderItem {
    MenuItem item;
    int quantity;

    OrderItem(MenuItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    double getTotal() {
        return item.price * quantity;
    }
}
