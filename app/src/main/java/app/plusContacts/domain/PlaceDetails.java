package app.plusContacts.domain;

public class PlaceDetails extends PlaceBasic {
    private String phone;

    public PlaceDetails(String palceID, String name, String address, String phone) {
        super(palceID, name, address);
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
