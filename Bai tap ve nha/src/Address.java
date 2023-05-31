import java.io.Serializable;

public class Address implements Serializable {
    private StringBuilder streetName;
    private StringBuilder houseNumber;
    private StringBuilder wardName;
    private StringBuilder districtName;
    private StringBuilder cityName;
    private StringBuilder countryName;
    private Address(Builder builder){
        this.streetName = builder.streetName;
        this.houseNumber = builder.houseNumber;
        this.wardName = builder.wardName;
        this.districtName = builder.districtName;
        this.cityName = builder.cityName;
        this.countryName = builder.countryName;
    }
    public StringBuilder getStreetName() {
        return streetName;
    }

    public StringBuilder getHouseNumber() {
        return houseNumber;
    }

    public StringBuilder getWardName() {
        return wardName;
    }

    public StringBuilder getDistrictName() {
        return districtName;
    }

    public void setStreetName(StringBuilder streetName) {
        this.streetName = streetName;
    }

    public void setHouseNumber(StringBuilder houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setWardName(StringBuilder wardName) {
        this.wardName = wardName;
    }
    public void setDistrictName(StringBuilder districtName){
        this.districtName = districtName;
    }

    public StringBuilder getCityName() {
        return cityName;
    }

    public void setCityName(StringBuilder cityName) {
        this.cityName = cityName;
    }

    public StringBuilder getCountryName() {
        return countryName;
    }

    public void setCountryName(StringBuilder countryName) {
        this.countryName = countryName;
    }

    @Override
    public String toString() {
        return  "\n\t\tSố nhà: " + houseNumber +
                "\n\t\tTên đường: " + streetName +
                "\n\t\tTên phường: " + wardName +
                "\n\t\tTên quận: " + districtName +
                "\n\t\tThành phố: " + cityName +
                "\n\t\tQuốc giá: " + countryName;
    }

    public static class Builder{
        private StringBuilder streetName;
        private StringBuilder houseNumber;
        private StringBuilder wardName;
        private StringBuilder districtName;
        private StringBuilder cityName;
        private StringBuilder countryName;

        public Builder setStreetnam(StringBuilder streetName){
            this.streetName = streetName;
            return this;
        }
        public Builder setHousename(StringBuilder houseNumber){
            this.houseNumber = houseNumber;
            return this;
        }
        public Builder setWardname(StringBuilder wardName){
            this.wardName = wardName;
            return this;
        }
        public Builder setDistricname(StringBuilder districtName){
            this.districtName = districtName;
            return this;
        }
        public Builder setCityname(StringBuilder cityname){
            this.cityName = cityname;
            return this;
        }
        public Builder setCountryName(StringBuilder countryName){
            this.countryName = countryName;
            return this;
        }
        public Address build(){
            return new Address(this);
        }
    }
}
