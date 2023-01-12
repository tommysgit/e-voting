package gabia.demo.Domain.Enums;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_MEMBER("ROLE_MEMBER");
    private String description;
    Role(String description){
        this.description = description;
    }
}
