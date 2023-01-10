package gabia.demo.Domain.Enums;

public enum VotingSort {
    LIMIT("선착순 제한경쟁"),
    NON_LIMIT("나머지");
    private String description;

     VotingSort(String description){
        this.description = description;
    }

}
