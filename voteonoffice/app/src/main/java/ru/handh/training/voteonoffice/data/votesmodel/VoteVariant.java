package ru.handh.training.voteonoffice.data.votesmodel;

public class VoteVariant {

    private int variantId;
    private String variantImgURL;
    private String variantName;
    private boolean variantVoteStatus;

    public VoteVariant(int variantId, String variantName, String variantImgURL, boolean variantVoteStatus) {
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

    public boolean isVariantVoteStatus() {
        return variantVoteStatus;
    }

    public void setVariantVoteStatus(boolean variantVoteStatus) {
        this.variantVoteStatus = variantVoteStatus;
    }
}
