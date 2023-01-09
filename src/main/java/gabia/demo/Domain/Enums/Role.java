package gabia.demo.Domain.Enums;

public enum Role {
    ROLE_ADMIN("ADMIN"),
    ROLE_MEMBER("MEMBER");
    private String description;
    Role(String description){
        this.description = description;
    }
}
