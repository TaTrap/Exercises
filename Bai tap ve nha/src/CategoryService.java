import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class CategoryService {
    Scanner input =  new Scanner(System.in);
    private  CategoryList categoryList = CategoryList.getInstance();
    public List<Category> ReadFromFile() {
        List<Category> categorys = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream("category.txt")) {
            ObjectInputStream oss = new ObjectInputStream(fis);
            categorys = (List<Category>) oss.readObject();
            oss.close();
        } catch (EOFException ignored) {
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return categorys;
    }
    public void WriteToFile(List<Category> categorys){
        try (FileOutputStream fos = new FileOutputStream("category.txt")){
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(categorys);
            oos.close();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public List<Category> GetListCategory(){
        categoryList.Load(ReadFromFile());
        int i = 1;
        for (Category category: categoryList.getCategoryList()){
            System.out.println(i + ". " + category.getName());
            i++;
        }
        return categoryList.getCategoryList();
    }

    public void AddCategory(){
        boolean isCategoryExits = true;
        Category category = InputCategory();
        categoryList.Load(ReadFromFile());
        for (Category categorys: categoryList.getCategoryList()){
            if (category.getName().equals(categorys.getName())){
                System.out.println("Category này đã tồn tại!");
                isCategoryExits = false;
            }
        }
        if (isCategoryExits){
            categoryList.Add(category);
            WriteToFile(categoryList.getCategoryList());
        }
    }
    public Category InputCategory(){
        System.out.print("Nhập tên Category: ");
        StringBuilder name = new StringBuilder(input.nextLine());
        System.out.println("Nhập link ảnh: ");
        StringBuilder image = new StringBuilder(input.nextLine());
        categoryList.Load(ReadFromFile());
        long id = categoryList.getSize() + 1;
        Category category = new Category(id, name, image);
        return category;
    }
}
