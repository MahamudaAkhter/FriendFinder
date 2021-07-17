package com.mahamuda.friendfinder.models;

import java.util.ArrayList;
import java.util.List;

public class Friend extends Person {

    private Boolean isBestFriend;
    private List<Friend> friends = new ArrayList<>();
    private static User user;

    public Friend(String userID, int avatar, String displayName, String email, Boolean isLive, int highestStreak, int totalDistanceTravelled, List<Badge> badges, Boolean isBestFriend) {
        super(userID, avatar, displayName, email, isLive, highestStreak, totalDistanceTravelled, badges);
        this.isBestFriend = isBestFriend;

        addFriends();
    }

    public Boolean getBestFriend() {
        return isBestFriend;
    }

    public void setBestFriend(Boolean bestFriend) {
        isBestFriend = bestFriend;
    }

    public void addFriends(){
        //friends.add(new Friend("1",0, "Alex", "Alex123", false, 0, 0, null,false));
        //friends.add(new Friend("2", 0, "Beth", "Beth234", false, 0, 0, null,false));
        //friends.add(new Friend("3", 0, "Carl", "Carl345", false, 0, 0, null,false));
        //friends.add(new Friend("4", 0, "Dean", "Dean456", false, 0, 0, null,false));

        //user.setFriends(friends);
    }

}
