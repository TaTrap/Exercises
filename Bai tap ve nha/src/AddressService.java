import java.util.Scanner;

public class AddressService {
    static Scanner scanner = new Scanner(System.in);
    public static Address createAddress(){
        StringBuilder houseNumber = inputHouseNumber();
        StringBuilder streetName = inputStreetName();
        StringBuilder wardName = inputWardName();
        StringBuilder districtName = inputDistrictName();
        StringBuilder cityName = inputCityName();
        StringBuilder countryName = inputCountryName();
        return new Address.Builder()
                .setHousename(houseNumber)
                .setStreetnam(streetName)
                .setWardname(wardName)
                .setDistricname(districtName)
                .setCityname(cityName)
                .setCountryName(countryName)
                .build();
    }
    public static StringBuilder inputHouseNumber(){
        System.out.println("Nhập số nhà: ");
        return new StringBuilder(scanner.nextLine());
    }
    public static StringBuilder inputStreetName(){
        System.out.println("Nhập tên đường: ");
        return new StringBuilder(scanner.nextLine());
    }

    public static StringBuilder inputWardName(){
        System.out.println("Nhập tên phường: ");
        return new StringBuilder(scanner.nextLine());
    }
    public static StringBuilder inputDistrictName(){
        System.out.println("Nhập tên quận: ");
        return new StringBuilder(scanner.nextLine());
    }
    public static StringBuilder inputCityName(){
        System.out.println("Nhập tên thành phố: ");
        return new StringBuilder(scanner.nextLine());
    }
    public static StringBuilder inputCountryName(){
        System.out.println("Nhập tên quốc gia: ");
        return new StringBuilder(scanner.nextLine());
    }
}
