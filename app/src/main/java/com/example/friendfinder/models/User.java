package com.example.friendfinder.models;

import java.util.List;

public class User extends Person {

    private List<Friend> friends;

    public User(int avatar, String name, String username, Boolean isLive, List<Friend> friends, int highestStreak, int totalDistanceTravelled, List<Badge> badges) {
        this.avatar = avatar;
        this.name = name;
        this.username = username;
        this.isLive = isLive;
        this.friends = friends;
        this.badges = badges;
        this.highestStreak = highestStreak;
        this.totalDistanceTravelled = totalDistanceTravelled;
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

    public Friend getFriend(int i)
    {
        return friends.get(i);
    }
}
