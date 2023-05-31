import java.util.Scanner;

public class BackofficeService {
    Scanner input = new Scanner(System.in);
    AdminService adminService = new AdminService();
    UserService userService = new UserService();
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
                    userService.Login();
                    break;
                case 2:
                    userService.LoadMenuRegister();
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
