import java.io.Serializable;

public class Customer implements Serializable {
    private final long id;
    private StringBuilder username;
    private StringBuilder password;
    private StringBuilder phonenumber;
    private Address address;
    private StringBuilder gender;
    private Customer(Builder builder){
        this.id = builder.id;
        this.username = builder.username;
        this.password = builder.password;
        this.phonenumber = builder.phonenumber;
        this.address = builder.address;
        this.gender = builder.gender;
    }
    public void setUsername(StringBuilder username){
        this.username = username;
    }
    public long getId() {
        return id;
    }

    public void setPassword(StringBuilder password) {
        this.password = password;
    }

    public void setPhonenumber(StringBuilder phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setGender(StringBuilder gender) {
        this.gender = gender;
    }

    public StringBuilder getUsername() {
        return username;
    }

    public StringBuilder getPassword() {
        return password;
    }

    public StringBuilder getPhonenumber() {
        return phonenumber;
    }

    public Address getAddress() {
        return address;
    }

    public StringBuilder getGender() {
        if (this.gender == null){
            return new StringBuilder("Chưa thêm");
        }
        return gender;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

    public static class Builder{
        private long id;
        private StringBuilder username;
        private StringBuilder password;
        private StringBuilder phonenumber;
        private Address address;
        private StringBuilder gender;
        public Builder SetId(long id){
            this.id = id;
            return this;
        }
        public Builder SetUsername(StringBuilder username){
            this.username = new StringBuilder(username);
            return this;
        }
        public Builder SetPassword(StringBuilder password){
            this.password = new StringBuilder(password);
            return this;
        }
        public Builder SetPhonenumber(StringBuilder phonenumber){
            this.phonenumber = new StringBuilder(phonenumber);
            return this;
        }
        public Builder SetAddress(Address address){
            this.address = address;
            return this;
        }
        public Builder SetGender(StringBuilder gender){
            this.gender = new StringBuilder(gender);
            return this;
        }
        public Customer build(){
            return new Customer(this);
        }
    }
}
