import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CartService {
    Scanner scanner = new Scanner(System.in);
    CartlineService cartlineService = new CartlineService();
    OderService oderService = new OderService();
    public static List<Cart> ReadFromFile() {
        List<Cart> carts = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream("cart.txt")) {
            ObjectInputStream oss = new ObjectInputStream(fis);
            carts = (List<Cart>) oss.readObject();
            oss.close();
        } catch (EOFException ignored) {
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return carts;
    }
    public static void WriteToFile(List<Cart> carts){
        try (FileOutputStream fos = new FileOutputStream("cart.txt")){
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(carts);
            oos.close();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void addCardLine(Product product, Customer customer, StringBuilder indexColor, StringBuilder indexSize, long quantity){
        List<Cart> carts = ReadFromFile();
        List<Cartline> cartlines;
        Cartline cartline = null;
        Cart cart = null;
        if (carts.size() == 0){
            cartline = new Cartline(1, quantity, product.getId(), indexColor, indexSize,
                    product.getNameCategory(),
                    product.getName(),
                    product.getNameShop());
            cartlines = new ArrayList<>();
            cartlines.add(cartline);
            cart = new Cart(cartlines, 1, customer.getId());
            carts.add(cart);
            WriteToFile(carts);
            System.out.println("Đã thêm sản phẩm thành công");
        }else {
            for (Cart cart1: carts){
                if (cart1.getIdCustomer() == customer.getId()){
                    cart = cart1;
                    break;
                }
            }
            if (cart == null){
                cartline = new Cartline(1, quantity, product.getId(), indexColor, indexSize,
                        product.getNameCategory(),
                        product.getName(),
                        product.getNameShop());
                cartlines = new ArrayList<>();
                cartlines.add(cartline);
                cart = new Cart(cartlines, carts.size() + 1, customer.getId());
                carts.add(cart);
                WriteToFile(carts);
                System.out.println("Đã thêm sản phẩm thành công");
            }else {
                for (Cartline cartline1: cart.getCartlines()){
                    if (product.getId() == cartline1.getProductId() &&
                        indexColor.toString().contentEquals(cartline1.getColorProduct()) &&
                        indexSize.toString().contentEquals(cartline1.getSizeProduct())
                    ){
                        cartline = cartline1;
                        break;
                    }
                }
                if (cartline == null){
                    carts.remove(cart);
                    cartline = new Cartline(cart.getCartlines().size() + 1, quantity, product.getId(), indexColor, indexSize,
                            product.getNameCategory(),
                            product.getName(),
                            product.getNameShop());
                    cart.getCartlines().add(cartline);
                    carts.add(cart);
                    WriteToFile(carts);
                    System.out.println("Đã thêm sản phẩm thành công");
                }else {
                    carts.remove(cart);
                    cart.getCartlines().remove(cartline);
                    long oldQuantity = cartline.getQuantity();
                    cartline.setQuantity(quantity + oldQuantity);
                    cart.getCartlines().add(cartline);
                    carts.add(cart);
                    WriteToFile(carts);
                    System.out.println("Đã thêm sản phẩm thành công");
                }
            }
        }
    }
    public void menuCart(Customer customer){
        int choice = -1;
        List<Cartline> cartlinesSelect = new ArrayList<>();
        List<Integer> indexs = new ArrayList<>();
        while (choice != 0){
            List<Cart> carts = ReadFromFile();
            List<Product> products = new ArrayList<>();
            Cart cart = new Cart();
            int index = 1;
            Product product;
            for (Cart cart1: carts){
                if (cart1.getIdCustomer() == customer.getId()){
                    cart = cart1;
                    break;
                }
            }
            if (cart.getCartlines().size() == 0){
                do {
                    System.out.println("Chưa thêm sản phẩm!");
                    System.out.println("0. Exit");
                    System.out.print("Nhập lựa chọn: ");
                    index = scanner.nextInt();
                }while (index != 0);
            }else {
                for (Cartline cartline: cart.getCartlines()){
                    product = cartlineService.getProductCartLine(cartline.getId(), cartline.getCategoryProductName());
                    products.add(product);
                    cartline.setTotalPrice(cartlineService.getTotalPriceCartLine(cartline.getQuantity(), product.getPrice()));
                    System.out.println(index + ". " + product.getName() + " | SL: " + cartline.getQuantity() + " | Tổng tiền: " +  cartline.getTotalPrice());
                }
                System.out.print("\t Sản phẩm đã chọn: " + indexs);
                System.out.println("\t Tổng tiền: " + getTotalPriceCart(cartlinesSelect));
                String idPay = String.valueOf(cart.getCartlines().size() + 1);
                System.out.print("\t" + idPay + " . Thanh toán");
                do {
                    System.out.println("\t0. Exit");
                    System.out.print("Nhập lựa chọn: ");
                    index = scanner.nextInt();
                }while (index < 0 || index > cart.getCartlines().size() + 1);
            }
            if (index == 0){
                choice = 0;
            }else if (index == cart.getCartlines().size() + 1){
                oderService.menuOderPay(cartlinesSelect, customer,
                        getTotalPriceCart(cartlinesSelect),
                        carts, cart, indexs);
            }else {
                cartlineService.menuSettingProductCartLine(cartlinesSelect, carts, cart, products,cart.getCartlines(), index, indexs);
            }
        }
    }
    public static double getTotalPriceCart(List<Cartline> cartlinesSelect){
        double totalPrice = 0;
        if (cartlinesSelect.size() == 0){
            return totalPrice;
        }
        for (Cartline cartline: cartlinesSelect){
            totalPrice += cartline.getTotalPrice();
        }
        return totalPrice;
    }

}
