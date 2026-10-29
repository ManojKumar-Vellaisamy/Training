public class Table {
    int id;
    int seats;
    boolean occupied;   // true = occupied, false = free

    Table(int id, int seats) {
        this.id = id;
        this.seats = seats;
        this.occupied = false;
    }

    void show() {
        String status = occupied ? "Occupied" : "Free";
        System.out.println("Table " + id + " | Seats: " + seats + " | " + status);
    }
}
