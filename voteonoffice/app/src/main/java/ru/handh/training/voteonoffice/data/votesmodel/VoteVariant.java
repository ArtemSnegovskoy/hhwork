package ru.handh.training.voteonoffice.data.votesmodel;

import android.os.Parcel;
import android.os.Parcelable;

public class VoteVariant implements Parcelable{

    private int variantId;
    private String variantImgURL;
    private String variantName;
    // теперь эта переменная работает как счетчик, но база может обновлять только раз в секунду, нужно бить на осколки
    // как в статье https://firebase.google.com/docs/firestore/solutions/counters
    private int variantVoteStatus;

    public VoteVariant(int variantId, String variantName, String variantImgURL, int variantVoteStatus) {
        this.variantId = variantId;
        this.variantName = variantName;
        this.variantImgURL = variantImgURL;
        this.variantVoteStatus = variantVoteStatus;
    }

    public VoteVariant (){

    }

    public int getVariantId() {
        return variantId;
    }

    public void setVariantId(int variantId) {
        this.variantId = variantId;
    }

    public String getVariantImgURL() {
        return variantImgURL;
    }

    public void setVariantImgURL(String variantImgURL) {
        this.variantImgURL = variantImgURL;
    }

    public String getVariantName() {
        return variantName;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
    }

    public int getVariantVoteStatus() {
        return variantVoteStatus;
    }

    public void setVariantVoteStatus(int variantVoteStatus) {
        this.variantVoteStatus = variantVoteStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.variantId);
        dest.writeString(this.variantImgURL);
        dest.writeString(this.variantName);
        dest.writeInt(this.variantVoteStatus);
    }

    protected VoteVariant(Parcel in) {
        this.variantId = in.readInt();
        this.variantImgURL = in.readString();
        this.variantName = in.readString();
        this.variantVoteStatus = in.readInt();
    }

    public static final Creator<VoteVariant> CREATOR = new Creator<VoteVariant>() {
        @Override
        public VoteVariant createFromParcel(Parcel source) {
            return new VoteVariant(source);
        }

        @Override
        public VoteVariant[] newArray(int size) {
            return new VoteVariant[size];
        }
    };
}

