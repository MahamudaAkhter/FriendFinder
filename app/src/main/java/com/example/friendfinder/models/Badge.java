package com.example.friendfinder.models;

public class Badge {
    private String name;
    private int
            baseImage;
    private int
            progress,
            bronzeThreshold,
            silverThreshold,
            goldThreshold,
            diamondThreshold;

    private Badge(String name, int baseImage, int progress, int bronzeThreshold, int silverThreshold, int goldThreshold, int diamondThreshold) {
        this.name = name;
        this.baseImage = baseImage;
        this.progress = progress;
        this.bronzeThreshold = bronzeThreshold;
        this.silverThreshold = silverThreshold;
        this.goldThreshold = goldThreshold;
        this.diamondThreshold = diamondThreshold;
    }





}
