package ru.handh.training.voteonoffice.data.votesmodel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Vote{

    private String voteUUID; // база данных сама может генерировать айди, а нужен ли он тут? всеравно сортировка по дате
    private String voteDate;
    private String voteTitle;
    private String voteDescription;
    private boolean voteStatus;
    private List<VoteVariant> voteVariants;

    public Vote(String voteUUID,
                String voteDate, String voteTitle,
                String voteDescription,
                Boolean voteStatus,
                List<VoteVariant> voteVariants) {
        this.voteUUID = voteUUID;
        this.voteDate = voteDate;
        this.voteTitle = voteTitle;
        this.voteDescription = voteDescription;
        this.voteStatus = voteStatus;
        this.voteVariants = voteVariants;
    }

    public Vote(String voteDate,
                String voteTitle,
                String voteDescription,
                List<VoteVariant> voteVariants) {

        this.voteDate = voteDate;
        this.voteTitle = voteTitle;
        this.voteDescription = voteDescription;
        this.voteVariants = voteVariants;
    }

    public Vote() {}

    public String getVoteUUID() {
        return voteUUID;
    }

    public void setVoteUUID(String voteUUID) {
        this.voteUUID = voteUUID;
    }

    public String getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(String voteDate) {
        this.voteDate = voteDate;
    }

    public String getVoteTitle() {
        return voteTitle;
    }

    public void setVoteTitle(String voteTitle) {
        this.voteTitle = voteTitle;
    }

    public String getVoteDescription() {
        return voteDescription;
    }

    public void setVoteDescription(String voteDescription) {
        this.voteDescription = voteDescription;
    }

    public boolean isVoteStatus() {
        return voteStatus;
    }

    public void setVoteStatus(boolean voteStatus) {
        this.voteStatus = voteStatus;
    }

    public List<VoteVariant> getVoteVariants() {
        return voteVariants;
    }

    public void setVoteVariants(List<VoteVariant> voteVariants) {
        this.voteVariants = voteVariants;
    }


}
