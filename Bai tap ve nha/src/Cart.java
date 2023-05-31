import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {
    private long id;
    private double totalPrice;
    private List<Cartline> cartlines;
    private long idCustomer;
    public Cart(){}
    public Cart(List<Cartline> cartlines, long id, long idCustomer){
        this.id = id;
        this.cartlines = cartlines;
        this.idCustomer = idCustomer;
    }

    public long getId() {
        return id;
    }
    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Cartline> getCartlines() {
        if (this.cartlines == null){
            this.cartlines = new ArrayList<>();
        }
        return cartlines;
    }

    public void setCartlines(List<Cartline> cartlines) {
        this.cartlines = cartlines;
    }

    public long getIdCustomer() {
        return idCustomer;
    }
}
