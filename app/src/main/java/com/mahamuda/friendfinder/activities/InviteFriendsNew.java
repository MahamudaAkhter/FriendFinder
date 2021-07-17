package com.mahamuda.friendfinder.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.mahamuda.friendfinder.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static android.content.ContentValues.TAG;

public class InviteFriendsNew extends AppCompatActivity {
    Button inviteBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inviteBtn = findViewById(R.id.btnInvite);

        //dynamicLinksHandler();
    }

    public void dynamicLinksHandler(Intent intent) {
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(intent)
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();

                            Log.e(TAG, "My refer link: " + deepLink.toString());

                            String inviteLink = deepLink.toString();

                            try {
                                inviteLink =  inviteLink.substring(inviteLink.lastIndexOf("=") + 1);
                                Log.e(TAG, "Substring " + inviteLink);

                                String userId = inviteLink.substring(0, inviteLink.indexOf("-"));
                                String friendsId = inviteLink.substring(inviteLink.indexOf("-") +1);

                                Log.e(TAG, "User ID:  " + userId + "------- Friends ID" + friendsId);

                            }catch(Exception e){
                                Log.e(TAG, "Error " + e.toString());
                            }
                        }


                        // Handle the deep link. For example, open the linked
                        // content, or apply promotional credit to the user's
                        // account.
                        // ...

                        // ...
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "getDynamicLink:onFailure", e);
                    }
                });
    }
}
