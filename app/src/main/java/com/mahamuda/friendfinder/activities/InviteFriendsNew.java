package com.mahamuda.friendfinder.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.mahamuda.friendfinder.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static android.content.ContentValues.TAG;

public class InviteFriendsNew extends AppCompatActivity {
    Button inviteBtn;
    Activity activity;

    /*public InviteFriendsNew(Activity A) {
        this.activity = A;
    }*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inviteBtn = findViewById(R.id.btnInvite);

//        dynamicLinksHandler();
    }

    public void dynamicLinksHandler() {
        //Intent i = activity.getIntent();
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();

                            Log.e(TAG, "My refer link: " + deepLink.toString());

                            //String inviteLink = deepLink.toString();

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user == null
                                    && deepLink != null
                                    && deepLink.getBooleanQueryParameter("invitedby", false)) {
                                String referrerUid = deepLink.getQueryParameter("invitedby");
                                createAnonymousAccountWithReferrerInfo("Referred user id: " + referrerUid);

                                Log.e(TAG, "referrerUid" + referrerUid);
                            }
                        } else {
                            System.out.println("Im in the else part for te reffered user");

                            /*try {
                                inviteLink =  inviteLink.substring(inviteLink.lastIndexOf("=") + 1);
                                Log.e(TAG, "Substring " + inviteLink);

                                String userId = inviteLink.substring(0, inviteLink.indexOf("-"));
                                String friendsId = inviteLink.substring(inviteLink.indexOf("-") +1);

                                Log.e(TAG, "User ID:  " + userId + "------- Friends ID" + friendsId);


                            }catch(Exception e){
                                Log.e(TAG, "Error " + e.toString());
                            }*/
                        }


                        // Handle the deep link. For example, open the linked
                        // content, or apply promotional credit to the user's
                        // account.
                        // ...

                        // ...
                    }

                    private void createAnonymousAccountWithReferrerInfo(final String referrerUid) {
                        FirebaseAuth.getInstance()
                                .signInAnonymously()
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        // Keep track of the referrer in the RTDB. Database calls
                                        // will depend on the structure of your app's RTDB.
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        DatabaseReference userRecord =
                                                FirebaseDatabase.getInstance().getReference()
                                                        .child("users")
                                                        .child(user.getUid());
                                        userRecord.child("referred_by").setValue(referrerUid);
                                    }
                                });
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
