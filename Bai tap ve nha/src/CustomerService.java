import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerService implements LoginAndRegister{
    Scanner scanner = new Scanner(System.in);
    OderService oderService = new OderService();
    boolean isPhonenumber;
    boolean isUsername;
    public List<Customer> ReadFromFile() {
        List<Customer> customers = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream("customer.txt")) {
            ObjectInputStream oss = new ObjectInputStream(fis);
            customers = (List<Customer>) oss.readObject();
            oss.close();
        } catch (EOFException ignored) {
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return customers;
    }
    public void WriteToFile(List<Customer> customers){
        try (FileOutputStream fos = new FileOutputStream("customer.txt")){
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(customers);
            oos.close();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void overrideCustomer(Customer customer){
        List<Customer> customers = ReadFromFile();
        Customer customer2 = null;
        for (Customer customer1: customers){
            if (customer.getId() == customer1.getId()){
                customer2 = customer1;
            }
        }
        customers.remove(customer2);
        customers.add(customer);
        WriteToFile(customers);
    }
    @Override
    public void Login(){
        List<Customer> customers = ReadFromFile();
        boolean isName = false;
        StringBuilder username = InputUsername();
        StringBuilder password = InputPassword();
        for (Customer customer: customers){
            if (customer.getUsername().toString().equals(username.toString())){
                isName = true;
                if (customer.getPassword().toString().equals(password.toString())){
                    System.out.println("You login!");
                    StorefrontService storefrontService = new StorefrontService();
                    storefrontService.MenuHomePage(customer);
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
    public void Register(){
        StringBuilder phonenumber;
        StringBuilder username;
        do {
            phonenumber = InputPhonenumber();
            CheckPhonenumber(phonenumber);
        }while (isPhonenumber);
        do {
            username = InputUsername();
            CheckUsername(username);
        }while (isUsername);
        StringBuilder password = InputPassword();
        Customer customer = new Customer.Builder()
                .SetId(loadId())
                .SetPhonenumber(phonenumber)
                .SetUsername(username)
                .SetPassword(password)
                .build();
        List<Customer> customers = ReadFromFile();
        customers.add(customer);
        WriteToFile(customers);
    }
    public StringBuilder InputPhonenumber(){
        System.out.print("Nhập số điện thoại: ");
        return new StringBuilder(scanner.nextLine());
    }
    public void CheckPhonenumber(StringBuilder phonenumber){
        isPhonenumber = false;
        List<Customer> customers = ReadFromFile();
        for (Customer customer: customers){
            if (customer.getPhonenumber().toString().equals(phonenumber.toString())){
                System.out.println("Số điện thoại đã được đăng ký với tài khoản khác!");
                isPhonenumber = true;
            }
        }
    }

    public StringBuilder InputUsername() {
        System.out.print("Nhập username: ");
        return new StringBuilder(scanner.nextLine());
    }
    public void CheckUsername(StringBuilder username){
        isUsername = false;
        List<Customer> customers = ReadFromFile();
        for (Customer customer: customers){
            if (customer.getUsername().toString().equals(username.toString())){
                System.out.println("Username này đã tồn tại!");
                isUsername = true;
            }
        }
    }
    public StringBuilder InputPassword(){
        System.out.print("Nhập password: ");
        return new StringBuilder(scanner.nextLine());
    }
    public long loadId(){
        List<Customer> customers = ReadFromFile();
        return customers.size() + 1;
    }
    public void menuInfo(Customer customer){
        int choice = -1;
        while (choice != 0){
            System.out.println("Tài khoản:");
            System.out.println("\t 1. Thông tin tài khoản");
            System.out.println("\t 2. Đơn mua");
            System.out.println("\t 0. Exit");
            System.out.print("Nhập lựa chọn: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1 -> loadInfoCustomer(customer);
                case 2 -> oderService.menuOderCustomer(customer);
                case 0 -> choice = 0;
                default -> System.out.println("No choice!");
            }
        }
    }
    public void loadInfoCustomer(Customer customer){
        int choice = -1;
        while (choice != 0){
            System.out.println(customer.getId());
            System.out.println(" 1. Tên tài khoản: " + customer.getUsername());
            System.out.println(" 2. Số điện thoại: " + customer.getPhonenumber());
            System.out.println(" 3. Giới tính: " + customer.getGender());
            System.out.println(" 4. Địa chỉ: " + checkAddressCustomer(customer));
            System.out.println(" 5. Đổi mật khẩu");
            System.out.println(" 0. Exit");
            System.out.print("Nhập lựa chọn: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> changeNameCustomer(customer);
                case 2 -> changePhonenumberCustomer(customer);
                case 3 -> changeGenderCustomer(customer);
                case 4 -> changeAddressCustomer(customer);
                case 5 -> changePasswordCustomer(customer);
                case 0 -> choice = 0;
                default -> System.out.println("No choice!");
            }
        }
    }
    public void changeNameCustomer(Customer customer){
        int index;
        short choice = -1;
        while (choice != 0){
            System.out.println("1. Đổi tên");
            System.out.println("0. Exit");
            do {
                System.out.print("Nhập lựa chọn: ");
                index = scanner.nextInt();
                scanner.nextLine();
            }while (index < 0 || index > 1);
            switch (index){
                case 1 -> {
                    changeName(customer);
                    choice = 0;
                }
                case 0 -> choice = 0;
            }
        }
    }
    public void changeName(Customer customer){
        StringBuilder username;
        do {
            username = InputUsername();
            CheckUsername(username);
        }while (isUsername);
        customer.setUsername(username);
        System.out.println("Đổi tên tài khoản thành công!");
        overrideCustomer(customer);
    }
    public void changePhonenumberCustomer(Customer customer){
        short index;
        short choice = -1;
        while (choice != 0){
            System.out.println("1. Đổi số điện thoại");
            System.out.println("0. Exit");
            do {
                System.out.print("Nhập lựa chọn: ");
                index = scanner.nextShort();
                scanner.nextLine();
            }while (index < 0 || index > 1);
            switch (index){
                case 1 -> {
                    changePhonenumber(customer);
                    choice = 0;
                }
                case 0 -> choice = 0;
            }
        }
    }
    public void changePhonenumber(Customer customer){
        StringBuilder phonenumber;
        do {
            phonenumber = InputPhonenumber();
            CheckPhonenumber(phonenumber);
        }while (isPhonenumber);
        customer.setPhonenumber(phonenumber);
        System.out.println("Đổi số điện thoại thành công!");
        overrideCustomer(customer);
    }
    public void changeGenderCustomer(Customer customer){
        short index;
        short choice = -1;
        while (choice != 0){
            System.out.println("1. Đổi giới tính");
            System.out.println("0. Exit");
            do {
                System.out.print("Nhập lựa chọn: ");
                index = scanner.nextShort();
                scanner.nextLine();
            }while (index < 0 || index > 1);
            switch (index){
                case 1 ->{
                    changeGender(customer);
                    choice = 0;
                }
                case 0 -> choice = 0;
            }
        }
    }
    public void changeGender(Customer customer){
        short index;
        short choice = -1;
        while (choice != 0){
            System.out.println("1. Nam");
            System.out.println("2. Nữ");
            System.out.println("3. Khác");
            System.out.println("0. Exit");
            do {
                System.out.print("Nhập lựa chọn: ");
                index = scanner.nextShort();
                scanner.nextLine();
            }while (index < 0 || index > 3);
            switch (index){
                case 1 -> {
                    customer.setGender(new StringBuilder("Nam"));
                    System.out.println("Đổi giới tính thành công!");
                    overrideCustomer(customer);
                    choice = 0;
                }
                case 2 -> {
                    customer.setGender(new StringBuilder("Nữ"));
                    System.out.println("Đổi giới tính thành công!");
                    overrideCustomer(customer);
                    choice = 0;
                }
                case 3 -> {
                    customer.setGender(new StringBuilder("Khác"));
                    System.out.println("Đổi giới tính thành công!");
                    overrideCustomer(customer);
                    choice = 0;
                }
                case 0 -> choice = 0;
            }
        }
    }
    public void changeAddressCustomer(Customer customer){
        short index;
        short choice = -1;
        while (choice != 0){
            System.out.println("1. Đổi địa chỉ");
            System.out.println("0. Exit");
            do {
                System.out.print("Nhập lựa chọn: ");
                index = scanner.nextShort();
                scanner.nextLine();
            }while (index < 0 || index > 1);
            switch (index){
                case 1 -> {
                    changeAddress(customer);
                    choice = 0;
                }
                case 0 -> choice = 0;
            }
        }
    }
    public void changeAddress(Customer customer){
        Address address = AddressService.createAddress();
        customer.setAddress(address);
        overrideCustomer(customer);
        System.out.println("Đổi địa chỉ thành công!");
    }
    public void changePasswordCustomer(Customer customer){
        StringBuilder password;
        StringBuilder passwordNew;
        do {
            System.out.print("Nhập mật khẩu cũ: ");
            password = new StringBuilder(scanner.nextLine());
        }while (!customer.getPassword().toString().contentEquals(password));
        do {
            System.out.print("Nhập mật khẩu mới: ");
            password = new StringBuilder(scanner.nextLine());
            System.out.print("Nhập lại mật khẩu mới: ");
            passwordNew = new StringBuilder(scanner.nextLine());
        }while (!password.toString().contentEquals(passwordNew));
        customer.setPassword(passwordNew);
        System.out.println("Đổi mật khẩu thành công!");
        overrideCustomer(customer);
    }
    public String checkAddressCustomer(Customer customer){
        if (customer.getAddress() == null){
            return "Chưa thêm";
        }
        return customer.getAddress().toString();
    }
}
