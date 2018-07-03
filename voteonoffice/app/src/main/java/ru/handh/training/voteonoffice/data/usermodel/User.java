package ru.handh.training.voteonoffice.data.usermodel;

import java.util.List;

public class User {

    private String userEmail;
    // тру = админ, фалс = юзер
    private Boolean userRole;
    private List<UserVotes> userVotesList;

    public User(String userEmail, Boolean userRole, List<UserVotes> userVotesList) {
        this.userEmail = userEmail;
        this.userRole = userRole;
        this.userVotesList = userVotesList;
    }

    public User() {}

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Boolean getUserRole() {
        return userRole;
    }

    public void setUserRole(Boolean userRole) {
        this.userRole = userRole;
    }

    public List<UserVotes> getUserVotesList() {
        return userVotesList;
    }

    public void setUserVotesList(List<UserVotes> userVotesList) {
        this.userVotesList = userVotesList;
    }
}
