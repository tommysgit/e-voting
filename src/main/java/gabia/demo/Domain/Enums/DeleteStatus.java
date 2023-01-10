package gabia.demo.Domain.Enums;

public enum DeleteStatus {
    Y(""),
    N("");
    private String description;
    DeleteStatus(String description){
        this.description = description;
    }
}
