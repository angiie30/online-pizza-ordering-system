package pupr.pizza.model;

public class Customer extends User {
    private Address defaultAddress;
    protected String phone;

    public Customer() {}

    public Customer(Long id, String name, String email, String phone, Address defaultAddress) {
        super(id, name, email, phone, phone);
        this.phone = phone;
        this.defaultAddress = defaultAddress;
    }

    public Address getDefaultAddress() { return defaultAddress; }

    public void setDefaultAddress(Address defaultAddress) {
        this.defaultAddress = defaultAddress;
    }
    
     public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}