import java.util.Scanner;

public class StorefrontService {
    Scanner input = new Scanner(System.in);
    CustomerService customerService = new CustomerService();
    CategoryService categoryService = new CategoryService();
    ProductService productService = new ProductService();
    CartService cartService = new CartService();
    public void LoadLoginAndRegister(){
        int choice = -1;

        while (choice != 0){
            System.out.println("Shopee by Tatrap:");
            System.out.println("\t 1. Login");
            System.out.println("\t 2. Register");
            System.out.println("\t 0. Exit");
            System.out.print("Nhập lựa chọn: ");
            choice = input.nextInt();
            switch (choice){
                case 1:
                    customerService.Login();
                    break;
                case 2:
                    customerService.Register();
                    break;
                case 0:
                    choice = 0;
                    break;
                default:
                    System.out.println("No choice!");
            }
        }
    }
    public void MenuHomePage(Customer customer){
        int choice = -1;
        while (choice != 0){
            System.out.println("Shopee by Tatrap:");
            System.out.println("\t 1. Danh mục các sản phẩm");
            System.out.println("\t 2. Giỏ hàng");
            System.out.println("\t 3. Tài khoản");
            System.out.println("\t 0. Exit");
            System.out.print("Nhập lựa chọn: ");
            choice = input.nextInt();
            switch (choice) {
                case 1 -> menuCategory(customer);
                case 2 -> cartService.menuCart(customer);
                case 3 -> customerService.menuInfo(customer);
                case 0 -> choice = 0;
                default -> System.out.println("No choice!");
            }
        }
    }
    public void menuCategory(Customer customer){
        int[] choice = {-1};
        while (choice[0] != 0){
            StringBuilder nameCategory = productService.inputCategoryMenuHomePagae(choice);
            if (choice[0] != 0){
                productService.selectProductCategoryHomePage(nameCategory, customer);
            }
        }
    }
}
