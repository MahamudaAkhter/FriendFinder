package com.mahamuda.friendfinder.activities;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.mahamuda.friendfinder.R;
import com.mahamuda.friendfinder.models.User;

import org.jetbrains.annotations.Nullable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class DynamicLinksUtil extends AppCompatActivity {

    private FirebaseUser firebaseUser;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_friend_list);
        link();
    }

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
                .setIosParameters(new DynamicLink.IosParameters.Builder("com.mahamuda.friendfinder").build())
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder("com.mahamuda.friendfinder").build())
                .buildDynamicLink();

        return link.getUri();
    }

    public void dsnfioksj(){
        if(generateContentLink() != null){
            //user.setFriends();
            firebaseUser.getUid();
        }
    }

    public void link(){
        FirebaseDynamicLinks.getInstance().getDynamicLink(getIntent()).addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
            @Override
            public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                Log.i("InviteFriends", "I have a dynamic link!");

                // Get deep link from result (may be null if no link is found)
                Uri deepLink = null;
                if(pendingDynamicLinkData != null){
                    deepLink = pendingDynamicLinkData.getLink();
                }

                // Handle the deep link by extracting the shared page I care about
                if(deepLink != null){
                    Log.i("InviteFriends", "Here is the deep link URL: \n" + deepLink.toString());
                    //generateInviteContent();
                    //String currentPage = deepLink.getQueryParameter("currPage");
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("InviteFriends", "Could not retrieve dynamic link data :(");
            }
        });
    }
}
