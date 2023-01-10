package gabia.demo.Domain.Enums;

public enum Vote {
    AGREE("찬성"),
    DISAGREE("반대"),
    ABSTENTION("기권");
    private String description;
    Vote(String description){
        this.description = description;
    }
}
