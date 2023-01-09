package gabia.demo.Domain.enums;

public enum Role {
    ROLE_ADMIN("ADMIN"),
    ROLE_MEMBER("MEMBER");
    private String description;
    Role(String description){
        this.description = description;
    }
}
