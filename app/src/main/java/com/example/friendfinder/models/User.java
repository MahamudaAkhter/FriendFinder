package com.example.friendfinder.models;

import java.util.List;

public class User extends Person {

    private List<Friend> friends;

    //String uid, String displayName, String email, int phoneNumber

    public User(String userID, int avatar, String displayName, String email, Boolean isLive, int highestStreak, int totalDistanceTravelled, List<Badge> badges, List<Friend> friends) {
        super(userID, avatar, displayName, email, isLive, highestStreak, totalDistanceTravelled, badges);
        this.friends = friends;
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

    public Friend getFriend(int i) {
        return friends.get(i);
    }
}
