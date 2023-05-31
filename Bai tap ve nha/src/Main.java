import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int choice = -1;
        Scanner input = new Scanner(System.in);
        StorefrontService storefrontService = new StorefrontService();
        BackofficeService backofficeService = new BackofficeService();
        while (choice != 0){
            System.out.println("Shopee by Tatrap:");
            System.out.println("\t 1. Storefront");
            System.out.println("\t 2. Backoffice");
            System.out.println("\t 0. Exit");
            System.out.print("Nhập lựa chọn: ");
            choice = input.nextInt();
            switch (choice){
                case 1:
                    storefrontService.LoadLoginAndRegister();
                    break;
                case 2:
                    backofficeService.LoadLoginAndRegister();
                    break;
                case 0:
                    System.exit(0);
                default:
                    System.out.println("No choice!");
            }
        }
    }
}