import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Shop implements Serializable {
    private long id;
    private StringBuilder name;
    private Address address;
    private StringBuilder phonenumber;
    private StringBuilder ownername;
    private List<StringBuilder> sellers;
    private List<StringBuilder> categorys;
    private List<StringBuilder> products;
    private List<StringBuilder> oder;
    private List<StringBuilder> inviteSents;

    public Shop(long id, StringBuilder name, Address address, StringBuilder phonenumber, StringBuilder ownername) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phonenumber = phonenumber;
        this.ownername = ownername;
    }

    public long getId() {
        return id;
    }

    public StringBuilder getName() {
        return name;
    }

    public void setName(StringBuilder name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public StringBuilder getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(StringBuilder phonenumber) {
        this.phonenumber = phonenumber;
    }

    public StringBuilder getOwnername() {
        return ownername;
    }

    public void setOwnername(StringBuilder ownername) {
        this.ownername = ownername;
    }

    public List<StringBuilder> getSellers() {
        if (this.sellers == null){
            this.sellers = new ArrayList<>();
        }
        return sellers;
    }

    public void setSellers(List<StringBuilder> sellers) {
        this.sellers = sellers;
    }

    public List<StringBuilder> getCategorys() {
        if (this.categorys == null){
            this.categorys = new ArrayList<>();
        }
        return this.categorys;
    }

    public void setCategorys(List<StringBuilder> categorys) {
        this.categorys = categorys;
    }

    public List<StringBuilder> getProducts() {
        return products;
    }

    public void setProducts(List<StringBuilder> products) {
        this.products = products;
    }

    public List<StringBuilder> getOder() {
        return oder;
    }

    public void setOder(List<StringBuilder> oder) {
        this.oder = oder;
    }

    public List<StringBuilder> getInviteSents() {
        if (this.inviteSents == null){
            this.inviteSents = new ArrayList<>();
        }
        return inviteSents;
    }

    public void setInviteSents(List<StringBuilder> inviteSents) {
        this.inviteSents = inviteSents;
    }
}
