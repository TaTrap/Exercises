import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Oder implements Serializable {
    private final long id;
    private StringBuilder status;
    private double totalPrice;
    private StringBuilder customerName;
    private StringBuilder shopName;
    private LocalDateTime timeDelivery;
    private LocalDateTime timeOder;
    private List<Cartline> cartlines;
    private Address address;
    private StringBuilder reason;
    public Oder(long id, StringBuilder status, double totalPrice, StringBuilder customerName, StringBuilder shopName, LocalDateTime timeOder, List<Cartline> cartlines, Address address) {
        this.id = id;
        this.status = status;
        this.totalPrice = totalPrice;
        this.customerName = customerName;
        this.shopName = shopName;
        this.timeOder = timeOder;
        this.cartlines = cartlines;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public StringBuilder getStatus() {
        return status;
    }

    public void setStatus(StringBuilder status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public StringBuilder getCustomerName() {
        return customerName;
    }

    public void setCustomerName(StringBuilder customerName) {
        this.customerName = customerName;
    }

    public StringBuilder getShopName() {
        return shopName;
    }

    public void setShopName(StringBuilder shopName) {
        this.shopName = shopName;
    }

    public LocalDateTime getTimeDelivery() {
        return timeDelivery;
    }

    public void setTimeDelivery(LocalDateTime timeDelivery) {
        this.timeDelivery = timeDelivery;
    }

    public LocalDateTime getTimeOder() {
        return timeOder;
    }

    public void setTimeOder(LocalDateTime timeOder) {
        this.timeOder = timeOder;
    }

    public List<Cartline> getCartlines() {
        return cartlines;
    }

    public void setCartlines(List<Cartline> cartlines) {
        this.cartlines = cartlines;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public StringBuilder getReason() {
        return reason;
    }

    public void setReason(StringBuilder reason) {
        this.reason = reason;
    }
}
