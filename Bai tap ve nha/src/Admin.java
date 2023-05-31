import java.io.Serializable;

public class Admin extends User implements Serializable {
    private long id;
    private StringBuilder username;
    private StringBuilder password;
    private StringBuilder accessRights;
    public Admin(StringBuilder username, StringBuilder password){
        this.username = username;
        this.password = password;
        this.accessRights = new StringBuilder("Admin");
    }
    public StringBuilder getUsername() {
        return this.username;
    }
    public StringBuilder getPassword(){
        return this.password;
    }
    public StringBuilder getAccessRights(){return this.accessRights;}
}
