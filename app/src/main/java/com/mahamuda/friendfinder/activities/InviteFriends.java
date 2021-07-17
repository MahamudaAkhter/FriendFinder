package com.mahamuda.friendfinder.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.appinvite.FirebaseAppInvite;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.mahamuda.friendfinder.R;

import org.jetbrains.annotations.Nullable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static android.content.ContentValues.TAG;

public class InviteFriends extends AppCompatActivity {

    private static final int INVITE_REQUEST = 100;
    private Button btnInvite;
    private TextView txtResult;

    private FirebaseAnalytics analytics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_friend_list);

        btnInvite = findViewById(R.id.btnInvite);
        txtResult = findViewById(R.id.txtDynamicLinkResult);

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        if(pendingDynamicLinkData != null){
                            //init analytics if you want get analytics from your dynamic links
                            analytics = FirebaseAnalytics.getInstance(InviteFriends.this);

                            Uri deepLink = pendingDynamicLinkData.getLink();
                            txtResult.append("\nonSuccessCalled " + deepLink.toString());
                            //Logic here - add friends

                            FirebaseAppInvite invite = FirebaseAppInvite.getInvitation(pendingDynamicLinkData);
                            if(invite != null){
                                String invitationId = invite.getInvitationId();
                                if(!TextUtils.isEmpty(invitationId)){
                                    txtResult.append("\n Invitation ID " + invitationId);
                                }
                            }
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                txtResult.append("\nonFailure");
            }
        });
    }

    public void shareLongDynamicLink(){
        Intent intent = new Intent();
        String msg = "fshfishfw" + buildDynamicLink();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        intent.setType("text/plain");
        startActivity(intent);
        System.out.println("I am long dynamic link");

    }

    private String buildDynamicLink() {
        String path = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setDomainUriPrefix("https://findfriends.page.link/")
                .setLink(Uri.parse("https://findfriends.page.link/"))
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                 //.setSocialMetaTagParameters(new DynamicLink.SocialMetaTagParameters.Builder().setTitle("Share this App"))
                //.setGoogleAnalyticsParameters(new DynamicLink.GoogleAnalyticsParameters.Builder().setSource("AndroidApp"))
                .buildDynamicLink().getUri().toString();

        //System.out.println("path: " + path);

        return path;
                /*"https://findfriends.page.link/?"
                "link=" +
                "https://findFriends.page.link" +
                "&apn=com.mahamuda.friendfinder" +
                "&st=" +
                "Invite your freinds!" +
                "&sd=" +
                "Invite your friends and have fun"*/

        //https://your_subdomain.page.link/?link=your_deep_link&apn=package_name[&amv=minimum_version][&afl=fallback_link]


            /*Uri baseUrl = Uri.parse("https://findfriends.page.link");
            String domain = "https://findfriends.page.link";

            DynamicLink link = FirebaseDynamicLinks.getInstance()
                    .createDynamicLink()
                    .setLink(baseUrl)
                    .setDomainUriPrefix(domain)
                    .setIosParameters(new DynamicLink.IosParameters.Builder("com.mahamuda.friendfinder").build())
                    .setAndroidParameters(new DynamicLink.AndroidParameters.Builder("com.mahamuda.friendfinder").build())
                    .buildDynamicLink();
        System.out.println("above link" + link.toString());
        System.out.println(link.getUri().toString());*/
        //return link;
    }

    public void shareShortDynamicLink(){
        Task<ShortDynamicLink> createLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLongLink(Uri.parse(buildDynamicLink()))
                .buildShortDynamicLink()
                .addOnCompleteListener(this, new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if(task.isSuccessful()){
                            //Short Link created
                            Uri shortLink = task.getResult().getShortLink();
                            // Flow chart link is a debugging URL
                            Uri flowChartLink = task.getResult().getPreviewLink();

                            Log.d(TAG, shortLink.toString());
                            Log.d(TAG, flowChartLink.toString());

                            /*System.out.println("Short link " + shortLink);
                            System.out.println("Debug: " + flowChartLink);*/

                            //Intent intent = new Intent(this, InviteFriends.class);
                            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("text/plain");
                            intent.putExtra(Intent.EXTRA_TEXT, buildDynamicLink());
                            if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivity(Intent.createChooser(intent, "Share Link"));
                                //startActivity(intent);

                                /*Intent intent = new Intent(this, LoginSingupActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);*/

                            }

                            /*Intent sendIntent = new Intent(getBaseContext().getApplicationContext(),InviteFriends.class);
                            sendIntent
                                    .setAction(Intent.ACTION_SEND)
                                    .putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                                    .setType("text/plain");

                            Intent shareIntent = Intent.createChooser(sendIntent, null);
                            startActivity(shareIntent);*/

                            /*Context context = getApplicationContext();
                            Intent intent = new Intent(context, InviteFriends.class);
                            String msg = "Invite your friends" + buildDynamicLink();
                            intent.setAction(Intent.ACTION_SEND);
                            intent.putExtra(Intent.EXTRA_TEXT, msg);
                            intent.setType("text/plain");
                            startActivity(intent);*/
                            System.out.println("im in the intent");
                        } else{
//                            txtResult.append("\nError building short link");
                            System.out.println("im in the else part");
                        }
                    }
                });
    }

    /*@Override
    public void onStart() {
        super.onStart();

        if (getIntent() != null && getIntent().getData() != null) {
            Uri data = getIntent().getData();
            processReferralIntent(data);
        }
    }*/

   /* private void processReferralIntent(Uri uri) {
        Log.d(TAG, "invite:processReferralIntent");

        Log.d(TAG, "invite:deepLink="+uri.toString());

        // uri.getPath() = /_invites/resourceId
        String urlName = uri.getPath();
        // not sure if first "/" is provided, handle properly just in case
        if (urlName.startsWith("/")) {
            urlName = urlName.substring(1);
        }
        if (urlName.startsWith("_invite/")){
            String resourceId = urlName.replaceFirst("_invite/", "");

            Log.d(TAG, "invite:deepLink.resourceId="+resourceId);
            // use resourceId to load the relevant information, such as retrieve info from database or API
        }
    }*/

    public void onInviteClicked() {
        Intent intent = new AppInviteInvitation.IntentBuilder("invite")
                .setMessage("heeeeeeeyuuu")
                .setDeepLink(Uri.parse("https://findFriends.page.link"))
                .setCallToActionText("dsfdsfsdfs")
                .build();
        startActivityForResult(intent, INVITE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == INVITE_REQUEST){
            if(resultCode == RESULT_OK){
                String [] ids = AppInviteInvitation.getInvitationIds(requestCode, data);
                for (String id : ids) {

                    System.out.println(Log.d(TAG, "id of sent invitation: " + id));
                }
            }
        } else {
            // Failed to send invitations
            }
        }
    }

   /* @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_friend_list);

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
                    // TODO -- use the current page to extract the deep link
                    String currentPage = deepLink.getQueryParameter("currPage");
                    // TODO -- create a pop up where link will be there
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("InviteFriends", "Could not retrieve dynamic link data :(");
            }
        });
    }*/

    //FIREBASE INVITES
    //TODO -- good one
        /*FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                        }


                        // Handle the deep link. For example, open the linked content,
                        // or apply promotional credit to the user's account.
                        // ...

                        // ...
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "getDynamicLink:onFailure", e);
                    }
                }); /*

        /*Replace List with list from firebase using sorting from firebase*/
/*
    public void onShareClicked() {
        Uri link = DynamicLinksUtil.generateContentLink();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, link.toString());

        startActivity(Intent.createChooser(intent, "Share Link"));
    }
}*/


