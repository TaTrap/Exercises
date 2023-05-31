import  java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminService implements LoginAndRegister {
    Scanner input = new Scanner(System.in);
    CategoryService categoryService = new CategoryService();
    public StringBuilder InputUsername() {
        System.out.print("Nhập username: ");
        return new StringBuilder(input.nextLine());
    }
    public StringBuilder InputPassword(){
        System.out.print("Nhập password: ");
        return new StringBuilder(input.nextLine());
    }
    @Override
    public void Login(){
    }
    @Override
    public void Register(){
        StringBuilder username = InputUsername();
        StringBuilder password = InputPassword();
        User admin = new Admin(username, password);
        List<User> admins = ReadFromFile();
        admins.add(admin);
        WriteToFile(admins);
    }

    public List<User> ReadFromFile() {
        List<User> admins = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream("user.txt")) {
            ObjectInputStream oss = new ObjectInputStream(fis);
            admins = (List<User>) oss.readObject();
            oss.close();
        } catch (EOFException ignored) {
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return admins;
    }
    public void WriteToFile(List<User> admins){
        try (FileOutputStream fos = new FileOutputStream("user.txt")){
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(admins);
            oos.close();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void LoadMenu(){
        int choice = -1;
        while (choice != 0){
            System.out.println("Menu Admin:");
            System.out.println("\t 1. Thêm Category");
            System.out.println("\t 2. Register");
            System.out.println("\t 0. Exit");
            System.out.print("Nhập lựa chọn: ");
            choice = input.nextInt();
            switch (choice){
                case 1:
                    categoryService.AddCategory();
                    break;
                case 2:

                    break;
                case 0:
                    choice = 0;
                    break;
                default:
                    System.out.println("No choice!");
            }
        }
    }
}
