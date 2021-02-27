package com.example.friendfinder.models;

public class Friend extends Person {

    private Boolean isBestFriend;

    public Friend(int avatar, String name, String username, Boolean isLive, Boolean isBestFriend) {
        this.avatar = avatar;
        this.name = name;
        this.username = username;
        this.isLive = isLive;
        this.isBestFriend = isBestFriend;
    }

    public Boolean getBestFriend() {
        return isBestFriend;
    }

    public void setBestFriend(Boolean bestFriend) {
        isBestFriend = bestFriend;
    }

}
