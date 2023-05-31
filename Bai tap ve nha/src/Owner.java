import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Owner extends User implements Serializable {
    private long id;
    private StringBuilder username;
    private StringBuilder password;
    private StringBuilder phonenumber;
    private StringBuilder accessRights;
    private List<StringBuilder> shops;
    private Owner(){}
    public Owner(long id, StringBuilder phonenumber, StringBuilder username, StringBuilder password, StringBuilder accessRights){
        this.id = id;
        this.phonenumber = phonenumber;
        this.username = username;
        this.password = password;
        this.accessRights = accessRights;
    }

    public long getId() {
        return id;
    }

    public StringBuilder getUsername() {
        return username;
    }

    public void setUsername(StringBuilder username) {
        this.username = username;
    }

    public StringBuilder getPassword() {
        return password;
    }

    public void setPassword(StringBuilder password) {
        this.password = password;
    }

    public StringBuilder getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(StringBuilder phonenumber) {
        this.phonenumber = phonenumber;
    }

    public StringBuilder getAccessRights() {
        return accessRights;
    }

    public void setAccessRights(StringBuilder accessRights) {
        this.accessRights = accessRights;
    }

    public List<StringBuilder> getShopList() {
        if (this.shops == null){
            this.shops = new ArrayList<>();
        }
        return shops;
    }

    public void setShopList(List<StringBuilder> shops) {
        this.shops = shops;
    }

    @Override
    public String toString() {
        return  "1. Id: " + id +
                "\n2. Username: " + username +
                "\n3. Phonenumber: " + phonenumber;
    }
}
