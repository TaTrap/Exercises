import java.util.List;
import java.util.Scanner;

public class OwnerService implements LoginAndRegister{
    Scanner input = new Scanner(System.in);
    ShopService shopService = new ShopService();
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
        StringBuilder accessRights = new StringBuilder("Owner");
        long id = UserService.LoadId() + 1;
        User owner = new Owner(id, phonenumber, username, password, accessRights);
        List<User> owners = UserService.ReadFromFile();
        owners.add(owner);
        UserService.WriteToFile(owners);
    }
    @Override
    public void Login(){}
    public void LoadMenu(Owner owner){
        int choice = -1;
        while (choice != 0){
            System.out.println("Menu Owner:");
            System.out.println("\t 1. Tạo Shop");
            System.out.println("\t 2. Danh sách Shop");
            System.out.println("\t 3. Thông tin tài khoản");
            System.out.println("\t 0. Exit");
            System.out.print("Nhập lựa chọn: ");
            choice = input.nextInt();
            switch (choice){
                case 1:
                    shopService.Register(owner);
                    break;
                case 2:
                    MenuShop(owner);
                    break;
                case 3:
                    LoadInfo();
                    break;
                case 0:
                    choice = 0;
                    break;
                default:
                    System.out.println("No choice!");
            }
        }
    }
    public void LoadInfo(){
        System.out.println(UserService.owner.toString());
    }
    public void MenuShop(Owner owner){
        List<Shop> shops = ShopService.LoadShopOwner(owner.getShopList(), owner.getUsername());
        if (shops.isEmpty()){
            System.out.println("Chưa tạo Shop!");
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
            shopService.LoadMenuShopOwner(shop);
        }
    }
}
