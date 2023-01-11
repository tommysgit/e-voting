package gabia.demo.Dto;

import gabia.demo.Domain.Enums.Role;
import lombok.Builder;
import lombok.Getter;


public class UserDto {
    @Builder
    @Getter
    public static class SignUpReq {
        private Role role;

        private String name;

        private String id;

        private String password;

        private int userVotingRights;
    }

    @Getter
    public static class SignInReq{
        private String id;

        private String password;
    }

    @Builder
    @Getter
    public static class SignInRes{

        private Long userIdx;

        private String id;

        private String name;

        private String token;
    }
}
