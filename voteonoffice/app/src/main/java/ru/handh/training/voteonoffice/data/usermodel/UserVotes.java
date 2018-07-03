package ru.handh.training.voteonoffice.data.usermodel;

public class UserVotes {

    private String voteUUID; // по этой строке мы находим голосовалку

    // так как пользователь может проголосовать только за 1 вариант
    // то в этой строке мы сохраняем айди выбранного варианта
    private int voteVariantID;

    public UserVotes(String voteUUID, int voteVariantID) {
        this.voteUUID = voteUUID;
        this.voteVariantID = voteVariantID;
    }

    public UserVotes() {}

    public String getVoteUUID() {
        return voteUUID;
    }

    public void setVoteUUID(String voteUUID) {
        this.voteUUID = voteUUID;
    }

    public int getVoteVariantID() {
        return voteVariantID;
    }

    public void setVoteVariantID(int voteVariantID) {
        this.voteVariantID = voteVariantID;
    }
}
