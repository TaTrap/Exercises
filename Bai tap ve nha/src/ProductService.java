import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductService {
    Scanner scanner = new Scanner(System.in);
    CategoryService categoryService = new CategoryService();
    CartService cartService = new CartService();
    public StringBuilder InputName() {
        System.out.print("Nhập tên sản phẩm: ");
        return new StringBuilder(scanner.nextLine());
    }
    public String InputPrice(){
        System.out.print("Nhập giá sản phẩm: ");
        return scanner.nextLine();
    }
    public StringBuilder InputColor(){
        String color;
        System.out.print("Nhập màu sắc (NO nếu như không có): ");
        color = scanner.nextLine();
        if (color.equals("NO")){
        }else {
            return new StringBuilder(color);
        }
        return null;
    }
    public StringBuilder InputSize(){
        String size;
        System.out.print("Nhập kích cỡ (NO nếu như không có): ");
        size = scanner.nextLine();
        if (size.equals("NO")){
        }else {
            return new StringBuilder(size);
        }
        return null;
    }
    public long inputQuantity(){
        System.out.print("Nhập số lượng sản phẩm: ");
        return scanner.nextLong();
    }
    public double inputCost(){
        System.out.print("Nhập giá gốc của sản phẩm: ");
        return scanner.nextDouble();
    }
    public StringBuilder inputDescribe(){
        System.out.println("Nhập mô tả sản phẩm:");
        return new StringBuilder(scanner.nextLine());
    }
    public StringBuilder inputCategoryRegShop(){
        List<Category> categories = categoryService.GetListCategory();
        int index;
        do {
            System.out.print("Chọn tên ngành sản phẩm: ");
            index = scanner.nextInt();
        }while (index > categories.size() || index < 0);
        Category category = categories.get(--index);
        return category.getName();
    }
    public StringBuilder inputCategoryMenuHomePagae(int[] choice){
        List<Category> categories;
        int index;
        int choice1 = -1;
        Category category = null;
        while (choice1 != 0){
            do {
                categories = categoryService.GetListCategory();
                System.out.println("0. Exit");
                System.out.print("Chọn tên ngành sản phẩm: ");
                index = scanner.nextInt();
            }while (index > categories.size() || index < 0);
            if (index == 0){
                choice1 = 0;
                choice[0] = 0;
            }else {
                category = categories.get(--index);
                return category.getName();
            }
        }
        return new StringBuilder();
    }
    public long InputId(List<Product> products){
        return products.size();
    }
    public void AddProduct(Shop shop){
        StringBuilder name = InputName();
        double price = Double.parseDouble(InputPrice());
        StringBuilder color = InputColor();
        StringBuilder size = InputSize();
        StringBuilder nameShop = shop.getName();
        long quantity = inputQuantity();
        double cost = inputCost();
        StringBuilder nameCategory = inputCategoryRegShop();
        scanner.nextLine();
        StringBuilder describe = inputDescribe();
        String linktxt = nameCategory + ".txt";
        List<Product> products = ReadFromFile(linktxt);
        long id = InputId(products);
        addCategory(shop, nameCategory);
        Product.Store store = new Product.Store(quantity, cost);
        List<Product.Store> stores = new ArrayList<>();
        stores.add(store);
        Product product = new Product.Builder()
                .setId(++id)
                .setName(name)
                .setPrice(price)
                .setColor(color)
                .setSize(size)
                .setNameShop(nameShop)
                .setDescribe(describe)
                .setNameCategory(nameCategory)
                .setStore(stores)
                .build();
        products.add(product);
        WriteToFile(products, linktxt);
    }
    public void getListProductShop(Shop shop){
        int choice = -1;
        while (choice != 0){
            int index;
            LoadViewCategoryShop(shop);
            do {
                System.out.print("Nhập lựa chọn: ");
                index = scanner.nextInt();
            }while (index < 0 || index - 1 > shop.getCategorys().size());
            if (index == 1){
                LoadViewAllProductShop(shop);
            } else if (index == 0) {
                choice = 0;
            } else {// in ra list product trong Category của shop
                int choicee = -1;
                while (choicee !=0){
                    List<Product> products = ReadFromFile(shop.getCategorys().get(index - 2) + ".txt");
                    List<Product> productsShop = new ArrayList<>();
                    int count = 1;
                    for (Product product: products){
                        if (shop.getName().toString().contentEquals(product.getNameShop())){
                            productsShop.add(product);
                            System.out.println(count + ". " + product.getName());
                            count++;
                        }
                    }
                    System.out.println("0. Exit");
                    System.out.println("Nhập sản phẩm muốn xem: ");
                    int index1 = scanner.nextInt();
                    if (index1 == 0){
                        choicee = 0;
                    }else {
                        selectProductShopCategory(productsShop, index1);
                    }
                }
            }
        }
    }
    public void selectProductCategoryHomePage(StringBuilder nameCategory, Customer customer){
        int choice = -1;
        int index;
        int count = 1;
        List<Product> products = ReadFromFile(nameCategory + ".txt");
        while (choice !=0){
            for (Product product: products){
                System.out.println(count + ". " + product.getName());
                count++;
            }
            System.out.println("0. Exit");
            do {
                System.out.print("Nhập lựa chọn: ");
                index = scanner.nextInt();
            }while (index < 0 || index > products.size());
            if (index == 0){
                choice = 0;
            }else {
                Product product = products.get(--index);
                viewProductCategoryHomePage(product, customer);
            }
        }
    }
    public void viewProductCategoryHomePage(Product product, Customer customer){
        int choice = -1;
        int index;
        while (choice != 0){
            System.out.println("Tên sản phẩm: " + product.getName());
            System.out.println("Giá: " + product.getPrice());
            if (isListAllNull(product.getColors())){
                System.out.println("Màu sắc: " + product.getColors());
            }
            if (isListAllNull(product.getSizes())){
                System.out.println("Kích cỡ: " + product.getSizes());
            }
            if (!product.getStore().isEmpty()){
                System.out.println("Số lượng hàng còn: " + getQuantityProduct(product));
            }
            System.out.println("Số đã bán: " + product.getHistoryOder().size());
            System.out.println("Mô tả: " + product.getDescribe());
            System.out.println("Ngành hàng: " + product.getNameCategory());
            System.out.println("Tên cửa hàng: " + product.getNameShop());
            System.out.print("\t1. Mua hàng");
            System.out.print("\t2. Giỏ hàng");
            System.out.println("\t 0. Exit");
            do {
                System.out.print("Nhập lựa chọn: ");
                index = scanner.nextInt();
            }while (index <0 || index > 2);
            switch (index) {
                case 1 -> buyProductCategoryHomePage(product, customer);
                case 2 -> cartService.menuCart(customer);
                case 0 -> choice = 0;
            }
        }
    }
    public static boolean isListAllNull(List<?> list) {
        for (Object element : list) {
            if (element != null) {
                return true;
            }
        }
        return false;
    }

    public void buyProductCategoryHomePage(Product product, Customer customer){
        int choice = -1;
        int index = -1;
        StringBuilder indexColor = new StringBuilder();
        StringBuilder indexSize = new StringBuilder();
        while (choice != 0){
            if (getQuantityProduct(product) != 0){
                if (isListAllNull(product.getColors())){
                    indexColor = selectColor(product);
                }
                if (isListAllNull(product.getSizes())){
                    indexSize = selectSize(product);
                }
                long quantity = selectQuantity(product);
                cartService.addCardLine(product, customer, indexColor, indexSize, quantity);
                choice = 0;
            }else {
                System.out.println("Hết hàng!");
                System.out.println("0. Exit");
                do {
                    System.out.print("Nhập lựa chọn: ");
                    index = scanner.nextInt();
                }while (index == 0);
                if (index == 0){
                    choice = 0;
                }
            }
        }
    }
    public StringBuilder selectColor(Product product){
        int index = 1;
        System.out.println("Chọn màu sắc: ");
        for (StringBuilder color: product.getColors()){
            System.out.println(index + ". " + color);
        }
        do {
            System.out.print("Nhập lựa chọn: ");
            index = scanner.nextInt();
        }while (index < 1 || index > product.getColors().size());
        return product.getColors().get(--index);
    }
    public StringBuilder selectSize(Product product){
        int index = 1;
        System.out.println("Chọn kích cỡ: ");
        for (StringBuilder size: product.getSizes()){
            System.out.println(index + ". " + size);
        }
        do {
            System.out.print("Nhập lựa chọn: ");
            index = scanner.nextInt();
        }while (index < 1 || index > product.getSizes().size());
        return product.getSizes().get(--index);
    }
    public long selectQuantity(Product product){
        long index;
        do {
            System.out.print("Nhập số lượng muốn mua: ");
            index = scanner.nextLong();
        }while (index < 1 || index > getQuantityProduct(product));
        return index;
    }
    public static long getQuantityProduct(Product product){
        long quantity = 0;
        for (Product.Store store: product.getStore()){
            quantity += store.getQuantity();
        }
        return quantity;
    }
    public void selectProductShopCategory(List<Product> products, int index){
        int[] choice = {-1};
        while (choice[0] !=0){
            Product product = products.get(--index);
            System.out.println(product.toString());
            do {
                System.out.print("Nhập lựa chọn: ");
                index = scanner.nextInt();
            }while (index < 0 || index > 11);
            if (index == 0){
                choice[0] = 0;
            }else if (index == 10){

            }else if (index == 11){
                settingMenuProduct(choice, product);
            }
        }
    }
    public void settingMenuProduct( int[] choices,Product product){
        int choice = -1;
        while (choice != 0){
            System.out.println("Cài đặt:");
            System.out.println("\t 1. Nhập thêm hàng");
            System.out.println("\t 2. Đổi tên");
            System.out.println("\t 3. Đổi giá bán");
            System.out.println("\t 4. Thêm/xoá màu sắc");
            System.out.println("\t 5. Thêm/xoá kích cỡ");
            System.out.println("\t 6. Xoá sản phẩm");
            System.out.println("\t 0. Exit");
            System.out.print("Nhập lựa chọn: ");
            choice = scanner.nextInt();
            switch (choice){
                case 1:
                    importProduct(product);
                    break;
                case 2:

                    break;
                case 6:
                    deleteProduct(product);
                    choice = 0;
                    choices[0] = 0;
                    break;
                case 0:
                    choice = 0;
                    break;
                default:
                    System.out.println("No choice!");
            }
        }
    }
    public void importProduct(Product product){//Case 1 settingMenuProduct
        long quantity = inputQuantity();
        double cost = inputCost();
        Product.Store store = new Product.Store(quantity, cost);
        product.getStore().add(store);
        overrideProduct(product);
    }
    public void deleteProduct(Product product){//Case 6 settingMenuProduct
        String linktxt = product.getNameCategory() + ".txt";
        List<Product> products = ReadFromFile(linktxt);
        Product product2 = null;
        for (Product product1: products){
            if (product.getId() == product1.getId()){
                product2 = product1;
            }
        }
        products.remove(product2);
        WriteToFile(products, linktxt);
    }
    public static List<Product> ReadFromFile(String linktxt) {
        List<Product> products = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(linktxt)) {
            ObjectInputStream oss = new ObjectInputStream(fis);
            products = (List<Product>) oss.readObject();
            oss.close();
        } catch (EOFException ignored) {
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return products;
    }
    public static void WriteToFile(List<Product> products, String linktxt){
        try (FileOutputStream fos = new FileOutputStream(linktxt)){
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(products);
            oos.close();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void addCategory(Shop shop, StringBuilder nameCategory){
        for (StringBuilder name: shop.getCategorys()){
            if (name.toString().contentEquals(nameCategory)){
                return;
            }
        }
        shop.getCategorys().add(nameCategory);
        ShopService.overrideShop(shop);
    }
    public void GetCategoryShop(Shop shop){
        int index = 2;
        for (StringBuilder shopName: shop.getCategorys()){
            System.out.println(index + ". " + shopName);
            index++;
        }
    }
    public void LoadViewCategoryShop(Shop shop){
        if (shop.getCategorys().isEmpty()) {
            System.out.println("Chưa thêm sản phẩm nào!");
            System.out.println("0. Exit");
        }else {
            System.out.println("1. Tất cả các sản phẩm");
            GetCategoryShop(shop);
            System.out.println("0. Exit");
        }
    }
    public void LoadViewAllProductShop(Shop shop){
        int count = 1;
        for (StringBuilder category: shop.getCategorys()){
            List<Product> products = ReadFromFile(category + ".txt");
            for (Product product: products){
                System.out.println(count + ". "+ product.getName());
            }
        }
    }
    public static void overrideProduct(Product product){
        List<Product> products = ReadFromFile(product.getNameCategory() + ".txt");
        Product retunrProduct = null;
        for (Product product1: products){
            if (product.getName().toString().contentEquals(product1.getName())) {
                retunrProduct = product1;
            }
        }
        products.remove(retunrProduct);
        products.add(product);
        WriteToFile(products, product.getNameCategory() + ".txt");
    }
}
