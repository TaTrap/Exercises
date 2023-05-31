import java.io.*;
import java.util.Scanner;

public class User{
    public long id;
    private StringBuilder username;
    private StringBuilder password;
    private StringBuilder accessRights;
    private StringBuilder phonenumber;
    public User(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public StringBuilder getAccessRights() {
        return accessRights;
    }

    public StringBuilder getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(StringBuilder phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setAccessRights(StringBuilder accessRights) {
        this.accessRights = accessRights;
    }
}
