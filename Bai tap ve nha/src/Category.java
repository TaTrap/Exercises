import java.io.*;
import java.net.URL;
import java.util.Scanner;

public class Category implements Serializable {
    private long id;
    private StringBuilder name;
    private StringBuilder image;
    public Category(long id, StringBuilder name, StringBuilder image){
        this.id = id;
        this.name = name;
        this.image = image;
    }
    public long getId(){
        return this.id;
    }

    public void setImage(StringBuilder image) {
        this.image = image;
    }

    public void setName(StringBuilder name) {
        this.name = name;
    }
    public StringBuilder getImage(){
        return this.image;
    }
    public StringBuilder getName(){
        return this.name;
    }
    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
