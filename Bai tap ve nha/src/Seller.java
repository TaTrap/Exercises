import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Seller extends User implements Serializable {
    private long id;
    private StringBuilder username;
    private StringBuilder password;
    private StringBuilder phonenumber;
    private StringBuilder accessRights;
    private List<StringBuilder> shops;
    private List<StringBuilder> invitationReceiveds;
    private Seller(){}

    public Seller(long id, StringBuilder username, StringBuilder password, StringBuilder phonenumber, StringBuilder accessRights) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phonenumber = phonenumber;
        this.accessRights = accessRights;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public StringBuilder getUsername() {
        return username;
    }

    @Override
    public void setUsername(StringBuilder username) {
        this.username = username;
    }

    @Override
    public StringBuilder getPassword() {
        return password;
    }

    @Override
    public void setPassword(StringBuilder password) {
        this.password = password;
    }

    @Override
    public StringBuilder getPhonenumber() {
        return phonenumber;
    }

    @Override
    public void setPhonenumber(StringBuilder phonenumber) {
        this.phonenumber = phonenumber;
    }

    @Override
    public StringBuilder getAccessRights() {
        return accessRights;
    }

    @Override
    public void setAccessRights(StringBuilder accessRights) {
        this.accessRights = accessRights;
    }

    public List<StringBuilder> getShops() {
        if (this.shops == null){
            this.shops = new ArrayList<>();
        }
        return shops;
    }

    public void setShops(List<StringBuilder> shops) {
        this.shops = shops;
    }

    public List<StringBuilder> getInvitationReceiveds() {
        if (this.invitationReceiveds == null){
            this.invitationReceiveds = new ArrayList<>();
        }
        return invitationReceiveds;
    }

    public void setInvitationReceiveds(List<StringBuilder> invitationReceiveds) {
        this.invitationReceiveds = invitationReceiveds;
    }
}
