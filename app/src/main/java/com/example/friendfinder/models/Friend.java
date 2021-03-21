package com.example.friendfinder.models;

import java.util.List;

public class Friend extends Person {

    private Boolean isBestFriend;

    public Friend(String userID, int avatar, String displayName, String email, Boolean isLive, int highestStreak, int totalDistanceTravelled, List<Badge> badges, Boolean isBestFriend) {
        super(userID, avatar, displayName, email, isLive, highestStreak, totalDistanceTravelled, badges);
        this.isBestFriend = isBestFriend;
    }

    public Boolean getBestFriend() {
        return isBestFriend;
    }

    public void setBestFriend(Boolean bestFriend) {
        isBestFriend = bestFriend;
    }

}
