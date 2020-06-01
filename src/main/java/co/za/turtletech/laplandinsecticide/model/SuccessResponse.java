package co.za.turtletech.laplandinsecticide.model;

public class SuccessResponse {

    private String ID;
    private int code;

    public SuccessResponse() {
    }

    public SuccessResponse(String ID, int code) {
        this.ID = ID;
        this.code = code;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
