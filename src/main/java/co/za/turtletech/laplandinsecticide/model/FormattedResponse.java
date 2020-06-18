package co.za.turtletech.laplandinsecticide.model;

public class FormattedResponse {

    private String ID;
    private int code;
    private String message;

    public FormattedResponse() {
    }

    public FormattedResponse(String ID, int code, String message) {
        this.ID = ID;
        this.code = code;
        this.message = message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
