package com.example.friendfinder.activities;

import android.net.Uri;

import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;

public class DynamicLinksUtil {
    public static InviteContent generateInviteContent() {
        return new InviteContent(
                "Hey check out my great app!",
                "It's like the best app ever.",
                generateContentLink());
    }

    public static Uri generateContentLink() {
        Uri baseUrl = Uri.parse("https://findFriends.page.link");
        String domain = "https://findFriends.page.link";

        DynamicLink link = FirebaseDynamicLinks.getInstance()
                .createDynamicLink()
                .setLink(baseUrl)
                .setDomainUriPrefix(domain)
                .setIosParameters(new DynamicLink.IosParameters.Builder("com.example.friendfinder").build())
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder("com.example.friendfinder").build())
                .buildDynamicLink();

        return link.getUri();
    }

}
