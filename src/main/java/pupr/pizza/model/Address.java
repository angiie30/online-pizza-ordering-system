package pupr.pizza.model;

import java.util.Objects;

public class Address {
    private String address1;
    private String address2;
    private String city;
    private String region;
    private String postalCode;

    public Address() {}

    public Address(String address1, String address2, String city, String region, String postalCode) {
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.region = region;
        this.postalCode = postalCode;
    }

    // Getters & setters
    public String getAddress1() { return address1; }
    public void setAddress1(String address1) {
        this.address1 = address1;
    }
    
    public String getAddress2() { return address2; }
    public void setAddress2(String address2) {
        this.address2 = address2;
    }
    
    public String getCity() { return city; }
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getRegion() { return region; }
    public void setRegion(String region) {
        this.region = region;
    }
    
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address a = (Address) o;
        return Objects.equals(address1, a.address1) &&
                Objects.equals(address2, a.address2) &&
                Objects.equals(city, a.city) &&
                Objects.equals(region, a.region) &&
                Objects.equals(postalCode, a.postalCode);
    }

    @Override public int hashCode() {
        return Objects.hash(address1, city, region, postalCode);
    }
}