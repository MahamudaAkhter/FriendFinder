package com.example.friendfinder.models;

import java.util.Dictionary;
import java.util.List;

public abstract class Person {
    protected int avatar;
    protected String name, username;
    protected Boolean isLive;
    protected List<Badge> badges;
    protected int highestStreak, totalDistanceTravelled;

//    public Person(String name, String username, Boolean isLive, int highestStreak, int totalDistanceTravelled) {
//        this.name = name;
//        this.username = username;
//        this.isLive = isLive;
//        this.highestStreak = highestStreak;
//        this.totalDistanceTravelled = totalDistanceTravelled;
//    }
//
//    public Person(int avatar, String name, String username, Boolean isLive, List<String> friends, int highestStreak, int totalDistanceTravelled, List<Badge> badges) {
//        this.avatar = avatar;
//        this.name = name;
//        this.username = username;
//        this.isLive = isLive;
//        this.friends = friends;
//        this.badges = badges;
//        this.highestStreak = highestStreak;
//        this.totalDistanceTravelled = totalDistanceTravelled;
//    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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