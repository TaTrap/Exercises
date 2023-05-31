import java.util.List;

public class CategoryList {
    private static CategoryList instance;
    private List<Category> categoryList;
    private CategoryList(){}
    public static CategoryList getInstance(){
        if (instance == null){
            instance = new CategoryList();
        }
        return instance;
    }
    public void Add(Category category){
        categoryList.add(category);
    }
    public void Load(List<Category> categories){
        this.categoryList = categories;
    }
    public List<Category> getCategoryList(){
        return categoryList;
    }
    public int getSize(){
        return categoryList.size();
    }
}
