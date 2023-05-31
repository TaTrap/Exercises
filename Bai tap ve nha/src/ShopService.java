import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShopService {
    static Scanner scanner = new Scanner(System.in);
    AddressService addressService = new AddressService();
    static ProductService productService = new ProductService();
;    public void Register(User user){
        Owner owner = (Owner) user;
        StringBuilder phonenumber;
        StringBuilder name;
        do {
            phonenumber = UserService.InputPhonenumber();
            CheckPhonenumber(phonenumber);
        }while (UserService.isExits);
        do {
            name = UserService.InputUsername();
            CheckName(name);
        }while (UserService.isExits);
        Address address = addressService.createAddress();
        long id = LoadId() + 1;
        StringBuilder ownerName = owner.getUsername();
        owner.getShopList().add(name);
        UserService.overrideUser((User) owner);
        Shop shop = new Shop(id, name, address, phonenumber, ownerName);
        List<Shop> shops = ReadFromFile();
        shops.add(shop);
        WriteToFile(shops);
    }
    public static List<Shop> ReadFromFile() {
        List<Shop> shops = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream("shop.txt")) {
            ObjectInputStream oss = new ObjectInputStream(fis);
            shops = (List<Shop>) oss.readObject();
            oss.close();
        } catch (EOFException ignored) {
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return shops;
    }
    public static void WriteToFile(List<Shop> shops){
        try (FileOutputStream fos = new FileOutputStream("shop.txt")){
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(shops);
            oos.close();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static List<Shop> LoadShopOwner(List<StringBuilder> shopNames, StringBuilder userName){
        if (shopNames.isEmpty()){
        }else {
            List<Shop> returnShops = new ArrayList<>();
            List<Shop> shops = ReadFromFile();
            int index = 1;
            for (Shop shop: shops){
                if (shop.getOwnername().toString().contentEquals(userName)){
                    returnShops.add(shop);
                    System.out.println(index + ". " + shop.getName());
                    if (returnShops.size() == shopNames.size()){
                        return returnShops;
                    }
                }
            }
        }
        return new ArrayList<>();
    }
    public static List<Shop> LoadShopSeller(List<StringBuilder> shopNames, StringBuilder userName){
        if (shopNames.isEmpty()){
        }else {
            List<Shop> returnShops = new ArrayList<>();
            List<Shop> shops = ReadFromFile();
            int index = 1;
            for (Shop shop: shops){
                for (StringBuilder nameShop: shopNames){
                    if (nameShop.toString().contentEquals(shop.getName())){
                        returnShops.add(shop);
                        System.out.println(index + ". " + shop.getName());
                        if (returnShops.size() == shopNames.size()){
                            return returnShops;
                        }
                    }
                }
            }
        }
        return new ArrayList<>();
    }
    public void CheckPhonenumber(StringBuilder phonenumber){
        UserService.isExits = false;
        List<Shop> shops = ReadFromFile();
        for (Shop shop: shops){
            if (shop.getPhonenumber().toString().contentEquals(phonenumber)){
                System.out.println("Số điện thoại đã được đăng ký với shop khác!");
                UserService.isExits = true;
            }
        }
    }
    public void CheckName(StringBuilder name){
        UserService.isExits = false;
        List<Shop> shops = ReadFromFile();
        for (Shop shop: shops){
            if (shop.getName().toString().contentEquals(name)){
                System.out.println("Đã tồn tại shop này!");
                UserService.isExits = true;
            }
        }
    }
    public long LoadId(){
        List<Shop> shops = ReadFromFile();
        return shops.size();
    }
    public static void inviteSeller(Shop shop){
        StringBuilder sellerName;
        boolean isSellerExits;
        boolean isSellerReviced;
        do {
            sellerName = inputSellerName();
            isSellerExits = shop.getSellers().contains(sellerName);
            isSellerReviced = shop.getInviteSents().contains(sellerName);
            checkSellerName(sellerName);
            if (isSellerExits){
                System.out.println("Đã thêm Seller này!");
            }
            if (isSellerReviced){
                System.out.println("Đã gửi lời rồi!");
            }
        }while (isSellerExits || CheckResult.isResult() || isSellerReviced);
        shop.getInviteSents().add(sellerName);
        overrideShop(shop);
        SellerService.receivedShop(shop.getName(), CheckResult.getUser());
        System.out.println("Đã gửi lời mời thành công!");
    }
    public static void overrideShop(Shop shop){
        List<Shop> shops = ReadFromFile();
        Shop retunrShop = null;
        for (Shop shop1: shops){
            if (shop.getName().toString().contentEquals(shop1.getName())) {
                retunrShop = shop1;
            }
        }
        shops.remove(retunrShop);
        shops.add(shop);
        WriteToFile(shops);
    }
    public static StringBuilder inputSellerName(){
        System.out.print("Nhập tên Seller: ");
        return new StringBuilder(scanner.next());
    }
    public static void LoadMenuShopOwner(Shop shop){
        int choice = -1;
        while (choice != 0){
            System.out.println("Menu Shop " + shop.getName() + ":");
            System.out.println("\t 1. Thêm sản phẩm");
            System.out.println("\t 2. Danh sách sản phẩm");
            System.out.println("\t 3. Cài Đặt");
            System.out.println("\t 0. Exit");
            System.out.print("Nhập lựa chọn: ");
            choice = scanner.nextInt();
            switch (choice){
                case 1:
                    productService.AddProduct(shop);
                    break;
                case 2:
                    productService.getListProductShop(shop);
                    break;
                case 3:
                    LoadMenuShopSetting(shop);
                    break;
                case 0:
                    choice = 0;
                    break;
                default:
                    System.out.println("No choice!");
            }
        }
    }
    public static void LoadMenuShopSetting(Shop shop){// Case 3 LoadMenuShopOwner
        int choice = -1;
        while (choice != 0){
            System.out.println("Setting:");
            System.out.println("\t 1. Quản lý Seller");
            System.out.println("\t 0. Exit");
            System.out.print("Nhập lựa chọn: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1 -> SellerService.ViewMenuSeller(shop);
                case 0 -> choice = 0;
                default -> System.out.println("No choice!");
            }
        }
    }
    public static void LoadMenuShopSeller(Shop shop){
        int choice = -1;
        while (choice != 0){
            System.out.println("Menu Shop " + shop.getName() + ":");
            System.out.println("\t 1. Thêm sản phẩm");
            System.out.println("\t 2. Danh sách sản phẩm");
            System.out.println("\t 3. Lịch sử đơn hàng");
            System.out.println("\t 0. Exit");
            System.out.print("Nhập lựa chọn: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1 -> productService.AddProduct(shop);
                case 2 -> productService.getListProductShop(shop);
                case 3 -> OderService.menuOderShop(shop);
                case 0 -> choice = 0;
                default -> System.out.println("No choice!");
            }
        }
    }

    public static CheckResult checkSellerName(StringBuilder sellerName){
        List<User> users = UserService.ReadFromFile();
        for (User user: users){
            if (user.getUsername().toString().contentEquals(sellerName)){
                if (user.getAccessRights().toString().equals("Seller")){
                    return new CheckResult(false, user);
                }
            }
        }
        System.out.println("Seller không tồn tại!");
        return new CheckResult(true, null);
    }
    public static class CheckResult{
        private static boolean result;
        private static User user;
        public CheckResult(boolean result, User user){
            CheckResult.result = result;
            CheckResult.user = user;
        }

        public static boolean isResult() {
            return result;
        }

        public static User getUser() {
            return user;
        }
    }

}
