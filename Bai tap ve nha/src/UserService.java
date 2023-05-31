import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserService implements LoginAndRegister{
    static Scanner scanner = new Scanner(System.in);
    AdminService adminService = new AdminService();
    OwnerService ownerService = new OwnerService();
    SellerService sellerService = new SellerService();
    static boolean isExits;
    static Owner owner;
    static Seller seller;
    public static StringBuilder InputUsername() {
        System.out.print("Nhập name: ");
        scanner.nextLine();
        String text = scanner.nextLine();
        return new StringBuilder(text);
    }
    public static StringBuilder InputPassword(){
        System.out.print("Nhập password: ");
        scanner.nextLine();
        String text = scanner.nextLine();
        return new StringBuilder(text);
    }
    public static StringBuilder InputPhonenumber(){
        System.out.print("Nhập số điện thoại: ");
        scanner.nextLine();
        String text = scanner.nextLine();
        return new StringBuilder(text);
    }
    @Override
    public void Login(){
        List<User> users = ReadFromFile();
        boolean isName = false;
        StringBuilder username = InputUsername();
        StringBuilder password = InputPassword();
        for (User user: users){
            if (user.getUsername().toString().contentEquals(username)){
                isName = true;
                if (user.getPassword().toString().contentEquals(password)){
                    System.out.println("You login!");
                    CheckUser(user);
                }else {
                    System.out.println("Mật khẩu sai");
                }
            }
        }
        if (!isName) {
            System.out.println("Tài khoản không tồn tại");
        }
    }
    @Override
    public void Register(){}
    public void LoadMenuRegister() {
        int choice = -1;
        while (choice != 0) {
            System.out.println("Đăng ký tài khoản:");
            System.out.println("\t 1. Owner");
            System.out.println("\t 2. Seller");
            System.out.println("\t 3. Admin");
            System.out.println("\t 0. Exit");
            System.out.print("Nhập lựa chọn: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1 -> {
                    ownerService.Register();
                    choice = 0;
                }
                case 2 -> {
                    sellerService.Register();
                    choice = 0;
                }
                case 3 -> {
                    adminService.Register();
                    choice = 0;
                }
                case 0 -> choice = 0;
                default -> System.out.println("No choice!");
            }
        }
    }

    public static List<User> ReadFromFile() {
        List<User> users = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream("user.txt")) {
            ObjectInputStream oss = new ObjectInputStream(fis);
            users = (List<User>) oss.readObject();
            oss.close();
        } catch (EOFException ignored) {
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return users;
    }
    public static void WriteToFile(List<User> customers){
        try (FileOutputStream fos = new FileOutputStream("user.txt")){
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(customers);
            oos.close();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void CheckPhonenumber(StringBuilder phonenumber){
        isExits = false;
        List<User> owners = ReadFromFile();
        for (User owner: owners){
            if (owner.getPhonenumber().toString().contentEquals(phonenumber)){
                System.out.println("Số điện thoại đã được đăng ký với tài khoản khác!");
                isExits = true;
            }
        }
    }
    public static void CheckUsername(StringBuilder username){
        isExits = false;
        List<User> owners = ReadFromFile();
        for (User owner: owners){
            if (owner.getUsername().toString().contentEquals(username)){
                System.out.println("Username này đã tồn tại!");
                isExits = true;
            }
        }
    }
    public void CheckUser(User user){
        if (user.getAccessRights().toString().equals("Admin")){
            adminService.LoadMenu();
        } else if (user.getAccessRights().toString().equals("Owner")) {
            owner = (Owner) user;
            ownerService.LoadMenu(owner);
        }else if (user.getAccessRights().toString().equals("Seller")) {
            seller = (Seller) user;
            sellerService.LoadMenu(seller);
        }
    }
    public static long LoadId(){
        List<User> user = ReadFromFile();
        return user.size();
    }
    public static void overrideUser(User user){
        List<User> users = ReadFromFile();
        User retunrUser = null;
        for (User user1: users){
            if (user.getUsername().toString().contentEquals(user1.getUsername())){
                retunrUser = user1;
            }
        }
        users.remove(retunrUser);
        users.add(user);
        WriteToFile(users);
    }
}
