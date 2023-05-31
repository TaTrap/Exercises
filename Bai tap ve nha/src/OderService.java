import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class OderService {
    static Scanner scanner = new Scanner(System.in);
    public static List<Oder> ReadFromFile() {
        List<Oder> oders = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream("oder.txt")) {
            ObjectInputStream oss = new ObjectInputStream(fis);
            oders = (List<Oder>) oss.readObject();
            oss.close();
        } catch (EOFException ignored) {
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return oders;
    }
    public static void WriteToFile(List<Oder> oders){
        try (FileOutputStream fos = new FileOutputStream("oder.txt")){
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(oders);
            oos.close();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void overrideOder(Oder oder){
        List<Oder> oders = ReadFromFile();
        Oder oder2 = null;
        for (Oder oder1: oders){
            if (oder.getId() == oder1.getId()){
                oder2 = oder1;
            }
        }
        oders.remove(oder2);
        oders.add(oder);
        WriteToFile(oders);
    }
    public void menuOderPay(List<Cartline> cartlinesSelect, Customer customer, double totalPrice,
                            List<Cart> carts, Cart cart, List<Integer> indexs){
        int index;
        int choice = -1;
        while (choice != 0){
            System.out.println("1. Tên người nhận: " + customer.getUsername());
            System.out.println("2. Số điện thoại: " + customer.getPhonenumber());
            System.out.println("3. Địa chỉ: " + getAddress(customer));
            System.out.println("Sản phẩm đã chọn: ");
            for (Cartline cartline: cartlinesSelect){
                System.out.println("\t" + cartline.getProductName() + " | SL: " + cartline.getQuantity() + " | Tổng tiền: " + cartline.getTotalPrice());
            }
            System.out.println("Đơn vị giao hàng: GHTK | Phí giao hàng: 0 VND");
            System.out.println("Phương thức thanh toán: Thanh toán khi nhân hàng");
            System.out.println("\t\t\t4. Mua\t0. Exit");
            do {
                System.out.print("Nhập lựa chọn: ");
                index = scanner.nextInt();
            }while (index < 0 | index > 4);
            switch (index){
                case 0 -> choice = 0 ;
                case 4 -> {
                    payCustomer(cartlinesSelect, totalPrice, customer, carts, cart, indexs);
                    choice = 0;
                }
            }
        }
    }
    public String getAddress(Customer customer){
        if (customer.getAddress() == null){
        }else {
            return customer.getAddress().toString();
        }
        return "Chưa thêm!";
    }
    public void payCustomer(List<Cartline> cartlinesSelect, double totalPrice, Customer customer,
                            List<Cart> carts, Cart cart, List<Integer> indexs){
        if (customer.getAddress() == null){
            System.out.println("Chưa có địa chỉ giao hàng!");
        }else {
            List<Cartline> cartlinesOder = cartlinesSelect;
            carts.remove(cart);
            List<StringBuilder> shops = new ArrayList<>(getShopFromOder(cartlinesSelect, cart));
            long id = loadIdOder() + 1;
            for (StringBuilder shopName: shops){
                createOder(cartlinesOder, id, totalPrice, customer, shopName);
                id++;
            }
            carts.add(cart);
            cartlinesSelect.clear();
            indexs.clear();
            CartService.WriteToFile(carts);
            System.out.println("Đã mua hàng thành công!");
        }

    }
    public long loadIdOder(){
        List<Oder> oders = ReadFromFile();
        return oders.size();
    }
    public Set<StringBuilder> getShopFromOder(List<Cartline> cartlinesSelect, Cart cart){
        Set<StringBuilder> nameShops = new HashSet<>();
        List<Cartline> cartlines = new ArrayList<>();
        for (Cartline cartline: cartlinesSelect){
            nameShops.add(cartline.getShopName());
            for (Cartline cartline1: cart.getCartlines()){
                if (cartline1.getId() == cartline.getId()){
                    cartlines.add(cartline1);
                }
            }
            cart.getCartlines().removeAll(cartlines);
        }
        return nameShops;
    }
    public void createOder(List<Cartline> cartlinesOder, long id, double totalPrice, Customer customer, StringBuilder shopName){
        List<Cartline> cartlines = new ArrayList<>();
        for (Cartline cartline: cartlinesOder){
            if (cartline.getShopName().toString().contentEquals(shopName)){
                cartlines.add(cartline);
            }
        }
        StringBuilder status = new StringBuilder("WaitConfirm");
        LocalDateTime localDateTime = LocalDateTime.now();
        Oder oder = new Oder(id, status, totalPrice, customer.getUsername(),
                shopName, localDateTime, cartlines, customer.getAddress());
        List<Oder> oders = ReadFromFile();
        oders.add(oder);
        WriteToFile(oders);
    }
    public void menuOderCustomer(Customer customer){
        int choice = -1;
        List<Oder> odersCustomer = getListOderCustomer(customer);
        while (choice != 0){
            System.out.println("Đơn hàng:");
            System.out.println("\t 1. Chờ xác nhận");
            System.out.println("\t 2. Vận chuyển");
            System.out.println("\t 3. Hoàn thành");
            System.out.println("\t 4. Huỷ hàng");
            System.out.println("\t 5. Trả hàng");
            System.out.println("\t 0. Exit");
            System.out.print("Nhập lựa chọn: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1 -> {
                    List<Oder> oders = oderWaitConfirm(odersCustomer);
                    loadOderCustomer(oders);
                }
                case 2 -> {
                    List<Oder> oders = oderTransported(odersCustomer);
                    loadOderCustomer(oders);
                }
                case 3 -> {
                    List<Oder> oders = oderComplete(odersCustomer);
                    loadOderCustomer(oders);
                }
                case 4 -> {
                    List<Oder> oders = oderCancel(odersCustomer);
                    loadOderCustomer(oders);
                }
                case 5 -> {
                    List<Oder> oders = oderReturns(odersCustomer);
                    loadOderCustomer(oders);
                }
                case 0 -> choice = 0;
                default -> System.out.println("No choice!");
            }
        }
    }
    public List<Oder> getListOderCustomer(Customer customer){
        List<Oder> oders = ReadFromFile();
        List<Oder> odersCustomer = new ArrayList<>();
        for (Oder oder: oders){
            if (oder.getCustomerName().toString().contentEquals(customer.getUsername())){
                odersCustomer.add(oder);
            }
        }
        return odersCustomer;
    }
    public static List<Oder> oderWaitConfirm(List<Oder> odersCustomer){
        List<Oder> oders = new ArrayList<>();
        for (Oder oder: odersCustomer){
            if (oder.getStatus().toString().equals("WaitConfirm")){
                oders.add(0, oder);
            }
        }
        return oders;
    }
    public static List<Oder> oderTransported(List<Oder> odersCustomer){
        List<Oder> oders = new ArrayList<>();
        for (Oder oder: odersCustomer){
            if (oder.getStatus().toString().equals("Transported")){
                oders.add(0, oder);
            }
        }
        return oders;
    }
    public static List<Oder> oderComplete(List<Oder> odersCustomer){
        List<Oder> oders = new ArrayList<>();
        for (Oder oder: odersCustomer){
            if (oder.getStatus().toString().equals("Complete")){
                oders.add(0, oder);
            }
        }
        return oders;
    }
    public static List<Oder> oderCancel(List<Oder> odersCustomer){
        List<Oder> oders = new ArrayList<>();
        for (Oder oder: odersCustomer){
            if (oder.getStatus().toString().equals("Cancel")){
                oders.add(0, oder);
            }
        }
        return oders;
    }
    public static List<Oder> oderReturns(List<Oder> odersCustomer){
        List<Oder> oders = new ArrayList<>();
        for (Oder oder: odersCustomer){
            if (oder.getStatus().toString().equals("Returns")){
                oders.add(0, oder);
            }
        }
        return oders;
    }
    public void loadOderCustomer(List<Oder> oders){
        int choice = -1;
        while (choice != 0){
            int index = 1;
            for (Oder oder: oders){
                System.out.println(index + ". " + oder.getShopName() + ": ");
                for (Cartline cartline: oder.getCartlines()){
                    System.out.println("\t" + cartline.getProductName() + " | SL: " +
                            cartline.getQuantity() + " | Tổng tiền: " + cartline.getTotalPrice());
                }
                index++;
                System.out.println("\tTổng tiền: " + CartService.getTotalPriceCart(oder.getCartlines()));
            }
            System.out.println("0. Exit");
            int count;
            do {
                System.out.print("Nhập lại lựa chọn: ");
                count = scanner.nextInt();
            }while (count < 0 || count > index);
            if (count == 0){
                choice = 0;
            }else {
                selectOderCustomer(count, oders);
            }
        }
    }
    public static void selectOderCustomer(int count, List<Oder> oders){
        Oder oder = oders.get(--count);
        switch (oder.getStatus().toString()){
            case "WaitConfirm" -> loadOderWaitConfirmCustomer(oder, oders);
            case "Transported" -> loadOderTransportedCustomer(oder, oders);
            case "Complete" -> loadOderComplete(oder);
            case "Cancel" -> loadOderCancel(oder);
        }
    }
    public static void loadOderWaitConfirmCustomer(Oder oder, List<Oder> oders){
        int choice = -1;
        int index;
        while (choice != 0){
            System.out.println("Mã đơn hàng: " + oder.getId());
            System.out.println("Thời gian mua: " + oder.getTimeOder());
            System.out.println("Địa chỉ: " + oder.getAddress());
            System.out.println("Tên shop: " + oder.getShopName() + ":");
            for (Cartline cartline: oder.getCartlines()){
                System.out.println("\t" + cartline.getProductName() + " | SL: " +
                        cartline.getQuantity() + " | Tổng tiền: " + cartline.getTotalPrice());
            }
            System.out.println("\tTổng tiền: " + CartService.getTotalPriceCart(oder.getCartlines()));
            System.out.println("1. Huỷ đơn hàng");
            System.out.println("0. Exit");
            do {
                System.out.print("Nhập lại lựa chọn: ");
                index = scanner.nextInt();
                scanner.nextLine();
            }while (index < 0 || index > 1);
            switch (index){
                case 0 -> choice = 0;
                case 1 -> {
                    cancelOderWaitConfirm(oder, oders);
                    choice = 0;
                }
            }
        }
    }
    public static void cancelOderWaitConfirm(Oder oder, List<Oder> oders){
        String reason;
        System.out.println("Nhập lí do huỷ đơn hàng:");
        reason = scanner.nextLine();
        oder.setReason(new StringBuilder(reason));
        oder.setStatus(new StringBuilder("Cancel"));
        oders.remove(oder);
        overrideOder(oder);
    }
    public static void loadOderTransportedCustomer(Oder oder, List<Oder> oders){
        int choice = -1;
        int index;
        while (choice != 0){
            System.out.println("Mã đơn hàng: " + oder.getId());
            System.out.println("Thời gian mua: " + oder.getTimeOder());
            System.out.println("Địa chỉ: " + oder.getAddress());
            System.out.println("Tên shop: " + oder.getShopName() + ":");
            for (Cartline cartline: oder.getCartlines()){
                System.out.println("\t" + cartline.getProductName() + " | SL: " +
                        cartline.getQuantity() + " | Tổng tiền: " + cartline.getTotalPrice());
            }
            System.out.println("\tTổng tiền: " + CartService.getTotalPriceCart(oder.getCartlines()));
            System.out.println("1. Xác nhận đã nhận được đơn hàng!");
            System.out.println("0. Exit");
            do {
                System.out.print("Nhập lại lựa chọn: ");
                index = scanner.nextInt();
            }while (index < 0 || index > 1);
            switch (index){
                case 0 -> choice = 0;
                case 1 -> {
                    acceptOderTransported(oder, oders);
                    choice = 0;
                }
            }
        }
    }
    public static void acceptOderTransported(Oder oder, List<Oder> oders){
        oder.setStatus(new StringBuilder("Complete"));
        LocalDateTime localDateTime = LocalDateTime.now();
        oder.setTimeDelivery(localDateTime);
        oders.remove(oder);
        overrideOder(oder);
    }
//    public static void reloadQuantityProductShop(Oder oder){
//        List<Cartline> cartlines = oder.getCartlines();
//        List<String> linktxts = new ArrayList<>();
//        for (Cartline cartline: cartlines){
//            linktxts.add(cartline.getCategoryProductName() + ".txt");
//        }
//        List<Product> products = new ArrayList<>();
//        for (String linktxt: linktxts){
//            products.addAll(ProductService.ReadFromFile(linktxt));
//        }
//        for (Cartline cartline: cartlines){
//            for (Product product: products){
//                if (product.getId() == cartline.getProductId() &&
//                product.getName().toString().contentEquals(cartline.getProductName()) &&
//                product.getNameShop().toString().contentEquals(cartline.getShopName()) &&
//                product.getNameCategory().toString().contentEquals(cartline.getCategoryProductName())){
//                    List<Product.Store> stores = product.getStore();
//                }
//            }
//        }
//    }
    public static void menuOderShop(Shop shop){
        int choice = -1;
        List<Oder> odersShop = getListOderShop(shop);
        while (choice != 0){
            System.out.println("Đơn hàng:");
            System.out.println("\t 1. Chờ xác nhận");
            System.out.println("\t 2. Vận chuyển");
            System.out.println("\t 3. Hoàn thành");
            System.out.println("\t 4. Huỷ hàng");
            System.out.println("\t 5. Trả hàng");
            System.out.println("\t 0. Exit");
            System.out.print("Nhập lựa chọn: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1 -> {
                    List<Oder> oders = oderWaitConfirm(odersShop);
                    loadOderShop(oders);
                }
                case 2 -> {
                    List<Oder> oders = oderTransported(odersShop);
                    loadOderShop(oders);
                }
                case 3 -> {
                    List<Oder> oders = oderComplete(odersShop);
                    loadOderShop(oders);
                }
                case 4 -> {
                    List<Oder> oders = oderCancel(odersShop);
                    loadOderShop(oders);
                }
                case 5 -> {
                    List<Oder> oders = oderReturns(odersShop);
                    loadOderShop(oders);
                }
                case 0 -> choice = 0;
                default -> System.out.println("No choice!");
            }
        }
    }
    public static List<Oder> getListOderShop(Shop shop){
        List<Oder> oders = ReadFromFile();
        List<Oder> odersShop = new ArrayList<>();
        for (Oder oder: oders){
            if (oder.getShopName().toString().contentEquals(shop.getName())){
                odersShop.add(0, oder);
            }
        }
        return odersShop;
    }
    public static void loadOderShop(List<Oder> oders){
        int choice = -1;
        int count;
        while (choice != 0){
            int index = 1;
            for (Oder oder: oders){
                System.out.println(index + ". " + oder.getShopName() + ": ");
                for (Cartline cartline: oder.getCartlines()){
                    System.out.println("\t" + cartline.getProductName() + " | SL: " +
                            cartline.getQuantity() + " | Tổng tiền: " + cartline.getTotalPrice());
                }
                index++;
                System.out.println("\tTổng tiền: " + CartService.getTotalPriceCart(oder.getCartlines()));
            }
            System.out.println("0. Exit");
            do {
                System.out.print("Nhập lại lựa chọn: ");
                count = scanner.nextInt();
            }while (count < 0 || count > index);
            if (count == 0){
                choice = 0;
            }else {
                selectOderShop(count, oders);
            }
        }
    }
    public static void selectOderShop(int count, List<Oder> oders){
        Oder oder = oders.get(--count);
        switch (oder.getStatus().toString()){
            case "WaitConfirm" -> loadOderWaitConfirmShop(oder, oders);
            case "Transported" -> loadOderTransportedShop(oder);
            case "Complete" -> loadOderComplete(oder);
            case "Cancel" -> loadOderCancel(oder);
        }
    }
    public static void loadOderWaitConfirmShop(Oder oder, List<Oder> oders){
        int choice = -1;
        int index;
        while (choice != 0){
            System.out.println("Mã đơn hàng: " + oder.getId());
            System.out.println("Thời gian mua: " + oder.getTimeOder());
            System.out.println("Địa chỉ: " + oder.getAddress());
            System.out.println("Tên shop: " + oder.getShopName() + ":");
            for (Cartline cartline: oder.getCartlines()){
                System.out.println("\t" + cartline.getProductName() + " | SL: " +
                        cartline.getQuantity() + " | Tổng tiền: " + cartline.getTotalPrice());
            }
            System.out.println("\tTổng tiền: " + CartService.getTotalPriceCart(oder.getCartlines()));
            System.out.println("1. Xác nhận đơn hàng đã giao cho đơn vị vận chuyển");
            System.out.println("0. Exit");
            do {
                System.out.print("Nhập lại lựa chọn: ");
                index = scanner.nextInt();
            }while (index < 0 || index > 1);
            switch (index){
                case 0 -> choice = 0;
                case 1 -> {
                    acceptOderWaitConfirm(oder, oders);
                    choice = 0;
                }
            }
        }
    }
    public static void loadOderTransportedShop(Oder oder){
        int choice = -1;
        int index;
        while (choice != 0){
            System.out.println("Mã đơn hàng: " + oder.getId());
            System.out.println("Thời gian mua: " + oder.getTimeOder());
            System.out.println("Địa chỉ: " + oder.getAddress());
            System.out.println("Tên shop: " + oder.getShopName() + ":");
            for (Cartline cartline: oder.getCartlines()){
                System.out.println("\t" + cartline.getProductName() + " | SL: " +
                        cartline.getQuantity() + " | Tổng tiền: " + cartline.getTotalPrice());
            }
            System.out.println("\tTổng tiền: " + CartService.getTotalPriceCart(oder.getCartlines()));
            System.out.println("0. Exit");
            do {
                System.out.print("Nhập lại lựa chọn: ");
                index = scanner.nextInt();
            }while (index != 0);
            choice = 0;
        }
    }
    public static void loadOderComplete(Oder oder){
        int choice = -1;
        int index;
        while (choice != 0){
            System.out.println("Mã đơn hàng: " + oder.getId());
            System.out.println("Thời gian mua: " + oder.getTimeOder());
            System.out.println("Địa chỉ: " + oder.getAddress());
            System.out.println("Tên shop: " + oder.getShopName() + ":");
            for (Cartline cartline: oder.getCartlines()){
                System.out.println("\t" + cartline.getProductName() + " | SL: " +
                        cartline.getQuantity() + " | Tổng tiền: " + cartline.getTotalPrice());
            }
            System.out.println("\tTổng tiền: " + CartService.getTotalPriceCart(oder.getCartlines()));
            System.out.println("Thời gian giao thành công: " + oder.getTimeDelivery());
            System.out.println("0. Exit");
            do {
                System.out.print("Nhập lại lựa chọn: ");
                index = scanner.nextInt();
            }while (index != 0);
            switch (index){
                case 0 -> choice = 0;
            }
        }
    }
    public static void loadOderCancel(Oder oder){
        int choice = -1;
        int index;
        while (choice != 0){
            System.out.println("Mã đơn hàng: " + oder.getId());
            System.out.println("Thời gian mua: " + oder.getTimeOder());
            System.out.println("Địa chỉ: " + oder.getAddress());
            System.out.println("Tên shop: " + oder.getShopName() + ":");
            for (Cartline cartline: oder.getCartlines()){
                System.out.println("\t" + cartline.getProductName() + " | SL: " +
                        cartline.getQuantity() + " | Tổng tiền: " + cartline.getTotalPrice());
            }
            System.out.println("\tTổng tiền: " + CartService.getTotalPriceCart(oder.getCartlines()));
            System.out.println("Lí do huỷ : " + oder.getReason());
            System.out.println("0. Exit");
            do {
                System.out.print("Nhập lại lựa chọn: ");
                index = scanner.nextInt();
            }while (index != 0);
            switch (index){
                case 0 -> choice = 0;
            }
        }
    }
    public static void acceptOderWaitConfirm(Oder oder, List<Oder> oders){
        oder.setStatus(new StringBuilder("Transported"));
        oders.remove(oder);
        overrideOder(oder);
    }
}
