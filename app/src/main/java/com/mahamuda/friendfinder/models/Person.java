package com.mahamuda.friendfinder.models;

import com.google.firebase.database.Exclude;

import java.util.List;

public abstract class Person {
    protected int avatar;
    protected String email, username, displayName;
    protected Boolean isLive;
    protected List<Badge> badges;
    protected int highestStreak, totalDistanceTravelled;
    protected String userID;
    protected int phoneNumber;

    public Person(String userID, int avatar, String displayName, String email, Boolean isLive, int highestStreak, int totalDistanceTravelled, List<Badge> badges) {
        this.userID = userID;
        this.avatar = avatar;
        this.displayName = displayName;
        //this.username = username;
        this.email = email;
        this.isLive = isLive;
        this.highestStreak = highestStreak;
        this.totalDistanceTravelled = totalDistanceTravelled;
        this.badges = badges;
    }

    public Person(){}

    @Exclude
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getLive() {
        return isLive;
    }

    public void setLive(Boolean live) {
        isLive = live;
    }

    public int getHighestStreak() {
        return highestStreak;
    }

    public void setHighestStreak(int highestStreak) {
        this.highestStreak = highestStreak;
    }

    public int getTotalDistanceTravelled() {
        return totalDistanceTravelled;
    }

    public void IncrementTotalDistanceTravelled(int totalDistanceTravelled) {
        this.totalDistanceTravelled += totalDistanceTravelled;
    }

    public List<Badge> getBadges() {
        return badges;
    }

    public void setBadges(List<Badge> badges) {
        this.badges = badges;
    }

}