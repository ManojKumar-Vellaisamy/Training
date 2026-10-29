public class MenuItem {
    int id;
    String name;
    double price;

    MenuItem(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    void show() {
        System.out.println(id + ". " + name + " - Rs." + price);
    }
}
