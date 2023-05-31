import java.util.List;
import java.util.Scanner;

public class CartlineService {
    Scanner scanner = new Scanner(System.in);
    public Product getProductCartLine(long id, StringBuilder categoryProductName){
        Product product = null;
        List<Product> products = ProductService.ReadFromFile(categoryProductName + ".txt");
        for (Product product1: products){
            if (product1.getId() == id){
                return product1;
            }
        }
        return product;
    }
    public double getTotalPriceCartLine(long quantity, double price){
        return quantity * price;
    }
    public void menuSettingProductCartLine(List<Cartline> cartlinesSelect, List<Cart> carts, Cart cart, List<Product> products ,List<Cartline> cartlines,int index, List<Integer> indexs){
        Product product = null;
        Cartline cartline = cartlines.get(--index);
        for (Product product1: products){
            if (cartline.getProductId() == product1.getId()){
                product = product1;
                break;
            }
        }
        int index1;
        do {
            System.out.println("1. Chọn");
            System.out.println("2. Thay đổi số lượng");
            System.out.println("3. Bỏ chọn");
            System.out.println("4. Xoá");
            System.out.println("0. Exit");
            System.out.print("Nhập lựa chọn: ");
            index1 = scanner.nextInt();
        }while (index1 < 0 || index1 > 4);
        switch (index1){
            case 1:
                selectCartLine(cartlinesSelect, cartline, index, indexs);
                break;
            case 2:
                changeQuantityProductCartLine(carts, cart, product, cartline, cartlines);
                break;
            case 3:
                unselectCartLine(cartlinesSelect, cartline, index, indexs);
                break;
            case 4:
                removeCartLine(carts, cart, cartline, cartlines, index, indexs, cartlinesSelect);
            case 0:
                break;
        }
    }
    public void selectCartLine(List<Cartline> cartlinesSelect, Cartline cartline, int index, List<Integer> indexs){
        for (Cartline cartline1: cartlinesSelect){
            if (cartline1.getId() == cartline.getId()){
                System.out.println("Đã chọn sản phẩm này rôi!");
                return;
            }
        }
        System.out.println("Chọn sản phẩm thành công!");
        indexs.add(++index);
        cartlinesSelect.add(cartline);
    }
    public void changeQuantityProductCartLine(List<Cart> carts, Cart cart, Product product, Cartline cartline, List<Cartline> cartlines){
        int count;
        cartlines.remove(cartline);
        carts.remove(cart);
        long quantity = ProductService.getQuantityProduct(product);
        do {
            System.out.println("Số sản phầm còn: " + quantity);
            System.out.println("Nhập số lượng: ");
            count = scanner.nextInt();
        }while (count < 1 || count > quantity);
        cartline.setQuantity(count);
        cartlines.add(cartline);
        cart.setCartlines(cartlines);
        carts.add(cart);
        CartService.WriteToFile(carts);
    }
    public void unselectCartLine(List<Cartline> cartlinesSelect, Cartline cartline, int index, List<Integer> indexs){
        if (!indexs.contains(++index)){
            System.out.println("Chưa chọn sản phẩm này!");
            return;
        }
        Cartline cartline2 = null;
        for (Cartline cartline1: cartlinesSelect){
            if (cartline1.getId() == cartline.getId()){
                cartline2 = cartline1;
                break;
            }
        }
        int index1 = indexs.indexOf(index);
        System.out.println("Bỏ chọn sản phẩm thành công!");
        indexs.remove(index1);
        cartlinesSelect.remove(cartline2);
    }
    public void removeCartLine(List<Cart> carts, Cart cart, Cartline cartline
            , List<Cartline> cartlines, int index, List<Integer> indexs
            , List<Cartline> cartlinesSelect){
        cartlines.remove(cartline);
        carts.remove(cart);
        cart.setCartlines(cartlines);
        carts.add(cart);
        CartService.WriteToFile(carts);
        System.out.println("Xoá thành công!");
        if (!indexs.contains(++index)){
            return;
        }
        Cartline cartline2 = null;
        for (Cartline cartline1: cartlinesSelect){
            if (cartline1.getId() == cartline.getId()){
                cartline2 = cartline1;
                break;
            }
        }
        int index1 = indexs.indexOf(index);
        indexs.remove(index1);
        cartlinesSelect.remove(cartline2);
    }
}
