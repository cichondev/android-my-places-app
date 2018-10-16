package app.plusContacts.domain;

public class PlaceBasic {
    private String palceID;
    private String name;
    private String address;

    public PlaceBasic(String palceID, String name, String address) {
        this.palceID = palceID;
        this.name = name;
        this.address = address;
    }

    public String getPalceID() {
        return palceID;
    }

    public void setPalceID(String palceID) {
        this.palceID = palceID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
