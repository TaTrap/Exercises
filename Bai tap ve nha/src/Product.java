import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Product implements Serializable {
    public static Store Store;
    private final long id;
    private StringBuilder name;
    private double price;
    private List<StringBuilder> colors;
    private List<StringBuilder> sizes;
    private StringBuilder nameShop;
    private StringBuilder nameCategory;
    private StringBuilder describe;
    private List<Store> store;
    private List<StringBuilder> historyImport;
    private List<StringBuilder> historyEdit;
    private List<StringBuilder> historyOder;
    private Product(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.price = builder.price;
        this.colors = builder.colors;
        this.sizes = builder.sizes;
        this.nameShop = builder.nameShop;
        this.nameCategory = builder.nameCategory;
        this.store = builder.store;
        this.describe = builder.describe;
    }

    public long getId() {
        return id;
    }

    public StringBuilder getName() {
        return name;
    }

    public void setName(StringBuilder name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<StringBuilder> getColors() {
        return colors;
    }

    public void setColors(List<StringBuilder> colors) {
        this.colors = colors;
    }

    public List<StringBuilder> getSizes() {
        return sizes;
    }

    public void setSizes(List<StringBuilder> sizes) {
        this.sizes = sizes;
    }

    public StringBuilder getNameShop() {
        return nameShop;
    }

    public void setNameShop(StringBuilder nameShop) {
        this.nameShop = nameShop;
    }

    public StringBuilder getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(StringBuilder nameCategory) {
        this.nameCategory = nameCategory;
    }

    public List<StringBuilder> getHistoryImport() {
        return historyImport;
    }

    public void setHistoryImport(List<StringBuilder> historyImport) {
        this.historyImport = historyImport;
    }

    public List<StringBuilder> getHistoryEdit() {
        return historyEdit;
    }

    public void setHistoryEdit(List<StringBuilder> historyEdit) {
        this.historyEdit = historyEdit;
    }

    public List<StringBuilder> getHistoryOder() {
        if (this.historyOder == null){
            this.historyOder = new ArrayList<>();
        }
        return historyOder;
    }

    public void setHistoryOder(List<StringBuilder> historyOder) {
        this.historyOder = historyOder;
    }

    public List<Product.Store> getStore() {
        return store;
    }

    public void setStore(List<Product.Store> store) {
        this.store = store;
    }

    public StringBuilder getDescribe() {
        return describe;
    }

    public void setDescribe(StringBuilder describe) {
        this.describe = describe;
    }

    public List<String> getStoreQuantityAndCost(){
        List<String> stringListStoreQuantityAndCost = new ArrayList<>();
        for (Store store1: getStore()){
            StringBuilder quantity = new StringBuilder();
            StringBuilder cost = new StringBuilder();
            quantity.append(store1.getQuantity());
            cost.append(store1.getCost());
            stringListStoreQuantityAndCost.add(quantity +  "," + cost);
        }
        return stringListStoreQuantityAndCost;
    }

    @Override
    public String toString() {
        return "Product:" +
                "\n\t1. Id: " + id +
                "\n\t2. Tên sản phẩm: " + name +
                "\n\t3. Giá sản phẩm: " + price +
                "\n\t4. Màu sắc: " + colors +
                "\n\t5. Kích cỡ: " + sizes +
                "\n\t6. Kho: " +  getStoreQuantityAndCost() +
                "\n\t7. Mô tả: " + describe +
                "\n\t8. Ngành hàng: " + nameCategory +
                "\n\t9. Tên cửa hàng: " + nameShop +
                "\n\t10. Lịch sử" +
                "\n\t11. Cài đặt" +
                "\n\t0. Exit";
    }

    public static class Builder {
        private long id;
        private StringBuilder name;
        private double price;
        private List<StringBuilder> colors;
        private List<StringBuilder> sizes;
        private StringBuilder nameShop;
        private StringBuilder nameCategory;
        private List<Store> store;
        private StringBuilder describe;
        public Builder setId(long id){
            this.id = id;
            return this;
        }
        public Builder setName(StringBuilder name){
            this.name = name;
            return this;
        }
        public Builder setPrice(Double price){
            this.price = price;
            return this;
        }
        public Builder setColor(StringBuilder color){
            if (colors == null) {
                colors = new ArrayList<>();
            }
            colors.add(color);
            return this;
        }
        public Builder setSize(StringBuilder size){
            if (sizes == null) {
                sizes = new ArrayList<>();
            }
            sizes.add(size);
            return this;
        }
        public Builder setNameShop(StringBuilder nameShop){
            this.nameShop = nameShop;
            return this;
        }
        public Builder setNameCategory(StringBuilder nameCategory){
            this.nameCategory = nameCategory;
            return this;
        }
        public Builder setStore(List<Store> store){
            this.store = store;
            return this;
        }
        public Builder setDescribe(StringBuilder describe){
            this.describe = describe;
            return this;
        }
        public Product build(){return new Product(this);}
    }
    public static class Store implements Serializable{
        private long quantity;
        private double cost;
        public Store(long quantity, double cost){
            this.quantity = quantity;
            this.cost = cost;
        }
        public long getQuantity() {
            return quantity;
        }

        public void setQuantity(long quantity) {
            this.quantity = quantity;
        }

        public double getCost() {
            return cost;
        }
    }
}
