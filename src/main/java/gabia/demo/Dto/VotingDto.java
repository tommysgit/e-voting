package gabia.demo.Dto;

import gabia.demo.Domain.Agenda;
import gabia.demo.Domain.Enums.Vote;
import gabia.demo.Domain.User;
import gabia.demo.Domain.Voting;
import gabia.demo.Dto.Jpql.VoteSumDataDto;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
@Slf4j
public class VotingDto {
    public static final int VOTE_LIMIT = 10;

    @Builder
    @Getter
    public static class VoteReq{
        private int votingRightsCount;

        private Vote vote;

    }
    @Getter
    public static class VoteData {
        private User user;

        private Agenda agenda;

        private Vote vote;

        private int votingRightsCount;

        @Builder
        public VoteData(User user, Agenda agenda, VoteReq voteReq) {
            this.user = user;
            this.agenda = agenda;
            this.vote = voteReq.getVote();
            this.votingRightsCount = voteReq.getVotingRightsCount();
        }


        public Voting toEntity(){

            return Voting.builder().agenda(this.agenda).user(this.user).vote(this.vote).votingRightsCount(this.votingRightsCount).build();
        }

    }



}
