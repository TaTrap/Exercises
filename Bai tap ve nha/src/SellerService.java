import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SellerService implements LoginAndRegister{
    Scanner input = new Scanner(System.in);
    static Scanner scanner = new Scanner(System.in);
    @Override
    public void Register(){
        StringBuilder phonenumber;
        StringBuilder username;
        do {
            phonenumber = UserService.InputPhonenumber();
            UserService.CheckPhonenumber(phonenumber);
        }while (UserService.isExits);
        do {
            username = UserService.InputUsername();
            UserService.CheckUsername(username);
        }while (UserService.isExits);
        StringBuilder password = UserService.InputPassword();
        StringBuilder accessRights = new StringBuilder("Seller");
        long id = UserService.LoadId() + 1;
        User seller = new Seller(id, username, password, phonenumber, accessRights);
        List<User> sellers = UserService.ReadFromFile();
        sellers.add(seller);
        UserService.WriteToFile(sellers);
    }
    @Override
    public void Login(){}
    public static List<Seller> ReadFromFile(String linktxt) {
        List<Seller> sellers = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(linktxt)) {
            ObjectInputStream oss = new ObjectInputStream(fis);
            sellers = (List<Seller>) oss.readObject();
            oss.close();
        } catch (EOFException ignored) {
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return sellers;
    }
    public void LoadMenu(Seller seller){
        int choice = -1;
        while (choice != 0){
            System.out.println("Menu Seller:");
            System.out.println("\t 1. Danh sách Shop");
            System.out.println("\t 2. Cài đặt");
            System.out.println("\t 0. Exit");
            System.out.print("Nhập lựa chọn: ");
            choice = input.nextInt();
            switch (choice){
                case 1:
                    MenuShop(seller);
                    break;
                case 2:
                    LoadMenuSetting(seller);
                    break;
                case 0:
                    choice = 0;
                    break;
                default:
                    System.out.println("No choice!");
            }
        }
    }
    public void LoadMenuSetting(Seller seller){
        int choice = -1;
        while (choice != 0){
            System.out.println("Setting:");
            System.out.println("\t 1. Danh sách lời mời");
            System.out.println("\t 2. Danh sách sản phẩm");
            System.out.println("\t 3. Cài Đặt");
            System.out.println("\t 0. Exit");
            System.out.print("Nhập lựa chọn: ");
            choice = input.nextInt();
            switch (choice){
                case 1:
                    acecptInvinted(seller);
                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 0:
                    choice = 0;
                    break;
                default:
                    System.out.println("No choice!");
            }
        }
    }
    public void MenuShop(Seller seller){
        List<Shop> shops = ShopService.LoadShopSeller(seller.getShops(), seller.getUsername());
        if (shops.isEmpty()){
            System.out.println("Chưa có Shop!");
        }else {
            SelectShop(shops);
        }
    }
    public void SelectShop(List<Shop> shops) {
        int index;
        do {
            System.out.print("Chọn Shop: ");
            index = input.nextInt();
        } while (index > shops.size() || index < 0);
        if (index != 0){
            Shop shop = shops.get(--index);
            ShopService.LoadMenuShopSeller(shop);
        }
    }
    public static void ViewMenuSeller(Shop shop) {
        int index;
        LoadViewSellerShop(shop);
        do {
            System.out.print("Nhập lựa chọn: ");
            index = scanner.nextInt();
        } while (index < 0 || index - 1 > shop.getSellers().size());
        if (index == 1) {
           ShopService.inviteSeller(shop);
        } else if (index == 0){
        }else {
        }
    }
    public static void LoadViewSellerShop(Shop shop){
        if(shop.getSellers().isEmpty()){
            System.out.println("1. Mời Seller");
            System.out.println("0. Exit");
        }else {
            System.out.println("1. Mời Seller");
            GetSellerShop(shop);
            System.out.println("0. Exit");
        }
    }
    public static void GetSellerShop(Shop shop){
        int index = 1;
        for (StringBuilder sellerName: shop.getSellers()){
            System.out.println(index + ". " + sellerName);
            index++;
        }
    }
    public static void receivedShop(StringBuilder shopName, User user){
        Seller seller = (Seller) user;
        seller.getInvitationReceiveds().add(shopName);
        UserService.overrideUser((User) seller);
    }
    public void menuInvintedReviced(Seller seller){
        int index = 1;
        if (seller.getInvitationReceiveds().isEmpty()){
            System.out.println("Chưa có lời mời!");
            System.out.println("0. Exit");
        }else {
            for (StringBuilder shopName: seller.getInvitationReceiveds()){
                System.out.println(index + ". " + shopName);
            }
        }
    }
    public int inputIndexSelectShopReviced(){
        System.out.print("Nhập lựa chọn: ");
        return input.nextInt();
    }
    public void slectReviced(Seller seller, int index){
        StringBuilder shopeName = seller.getInvitationReceiveds().get(--index);
        seller.getShops().add(shopeName);
        seller.getInvitationReceiveds().remove(index);
        Shop shop = getShopReviced(shopeName);
        shop.getInviteSents().remove(seller.getUsername());
        shop.getSellers().add(seller.getUsername());
        ShopService.overrideShop(shop);
        UserService.overrideUser((User) seller);
    }
    public Shop getShopReviced(StringBuilder shopName){
        List<Shop> shops = ShopService.ReadFromFile();
        for (Shop shop: shops){
            if (shop.getName().toString().contentEquals(shopName)){
                return shop;
            }
        }
        return null;
    }
    public void acecptInvinted(Seller seller){
        int index;
        menuInvintedReviced(seller);
        do {
            index = inputIndexSelectShopReviced();
        }while (index < 0 || index > seller.getInvitationReceiveds().size());
        if (index == 0){
        }else {
            slectReviced(seller ,index);
        }
    }
}
