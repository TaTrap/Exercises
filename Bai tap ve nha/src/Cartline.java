import java.io.Serializable;

public class Cartline implements Serializable {
    private final long id;
    private long quantity;
    private double totalPrice;
    private final long productId;
    private StringBuilder productName;
    private StringBuilder shopName;
    private StringBuilder colorProduct;
    private StringBuilder sizeProduct;
    private StringBuilder categoryProductName;
    public Cartline(long id, long quantity, long productId, StringBuilder colorProduct, StringBuilder sizeProduct, StringBuilder categoryProductName, StringBuilder productName, StringBuilder shopaname) {
        this.id = id;
        this.quantity = quantity;
        this.productId = productId;
        this.colorProduct = colorProduct;
        this.sizeProduct = sizeProduct;
        this.categoryProductName = categoryProductName;
        this.productName = productName;
        this.shopName = shopaname;
    }

    public long getId() {
        return id;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getProductId() {
        return productId;
    }

    public StringBuilder getColorProduct() {
        return colorProduct;
    }

    public void setColorProduct(StringBuilder colorProduct) {
        this.colorProduct = colorProduct;
    }

    public StringBuilder getSizeProduct() {
        return sizeProduct;
    }

    public void setSizeProduct(StringBuilder sizeProduct) {
        this.sizeProduct = sizeProduct;
    }

    public StringBuilder getCategoryProductName() {
        return categoryProductName;
    }

    public void setCategoryProductName(StringBuilder categoryProductName) {
        this.categoryProductName = categoryProductName;
    }

    public StringBuilder getProductName() {
        return productName;
    }

    public void setProductName(StringBuilder productName) {
        this.productName = productName;
    }

    public StringBuilder getShopName() {
        return shopName;
    }

    public void setShopName(StringBuilder shopName) {
        this.shopName = shopName;
    }

}
