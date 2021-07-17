package com.mahamuda.friendfinder.models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class User extends Person {

    //String uid, String displayName, String email, int phoneNumber

    private List<Friend> friends = new ArrayList<>();

    public User(String userID, int avatar, String displayName, String email, Boolean isLive, int highestStreak, int totalDistanceTravelled, List<Badge> badges, List<Friend> friends) {
        super(userID, avatar, displayName, email, isLive, highestStreak, totalDistanceTravelled, badges);
        this.friends = friends;
    }

    public User(){
        super();
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

    public FirebaseUser getCurrentUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user;
    }
}
