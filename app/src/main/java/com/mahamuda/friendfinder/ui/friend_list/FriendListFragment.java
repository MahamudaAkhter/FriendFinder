package com.mahamuda.friendfinder.ui.friend_list;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.mahamuda.friendfinder.FriendActionListDialogFragment;
import com.mahamuda.friendfinder.R;
import com.mahamuda.friendfinder.activities.InviteFriends;
import com.mahamuda.friendfinder.activities.InviteFriendsNew;
import com.mahamuda.friendfinder.adapters.RecyclerViewAdapter;
import com.mahamuda.friendfinder.interfaces.FriendListItemClickListener;
import static android.content.ContentValues.TAG;

public class FriendListFragment extends Fragment implements FriendListItemClickListener, GoogleApiClient.OnConnectionFailedListener {

    private FriendListViewModel friendListViewModel;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    SearchView searchView;
    Button inviteBtn;
    Intent intent;

    private static InviteFriends inviteFriends;
    private FirebaseUser firebaseUser;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //InviteContent content = DynamicLinksUtil.generateInviteContent();
        friendListViewModel =
                new ViewModelProvider(this).get(FriendListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_friend_list, container, false);
        // final TextView textView = root.findViewById(R.id.text_dashboard);
        friendListViewModel.getText().observe(getViewLifecycleOwner(), s -> {
//                textView.setText(s);
        });

        recyclerView = root.findViewById(R.id.friend_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewAdapter = new RecyclerViewAdapter(friendListViewModel.getUser(), this);
        recyclerView.setAdapter(recyclerViewAdapter);

        searchView = root.findViewById(R.id.friend_list_search_view);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerViewAdapter.getFilter().filter(newText);
                return true;
            }
        });

        intent = new Intent();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        inviteBtn = root.findViewById(R.id.btnInvite);
        inviteFriends = new InviteFriends();
        InviteFriendsNew inviteFriendsNew = new InviteFriendsNew();

        inviteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createLink();
                inviteFriendsNew.dynamicLinksHandler(intent);
            }
        });

        return root;
    }

    @Override
    public void onListItemClick(int position) {
        FriendActionListDialogFragment.newInstance(4).show(getActivity().getSupportFragmentManager(), "dialog");
    }

    /*public void onShareClicked() {
        Uri link = DynamicLinksUtil.generateContentLink();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, link.toString());

        startActivity(Intent.createChooser(intent, "Share Link"));
    }*/

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void createLink(){
        Log.e("InviteFriendsNew", "Create link");

        // Creating dynamic link programmatically
        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://findfriends.page.link"))
                .setDomainUriPrefix("https://findfriends.page.link")
                // Open links with this app on Android
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                // Open links with com.example.ios on iOS
                //.setIosParameters(new DynamicLink.IosParameters.Builder("com.example.ios").build())
                .buildDynamicLink();

        // My long refer link is https://findfriends.page.link?apn=com.mahamuda.friendfinder&ibi=com.example.ios&link=https%3A%2F%2Ffindfriends.page.link

        Uri dynamicLinkUri = dynamicLink.getUri();
        Log.e(TAG, "Long Refer " + dynamicLinkUri);

        createInviteLink(firebaseUser.getUid(), firebaseUser.getUid());
    }

    public void createInviteLink(String userId, String friendsId){
        String shareLinkText = "https://findfriends.page.link/?"+
                "link=https://findfriends.page.link/invite.php?userid="+
                userId+"-"+friendsId+
                "&apn="+"com.mahamuda.friendfinder"+
                "&st="+"Invite"+
                "&si="+"https://static.wikia.nocookie.net/despicableme/images/c/ca/Bob-from-the-minions-movie.jpg/revision/latest/top-crop/width/360/height/450?cb=20151224154354";

        // My long refer link is https://findfriends.page.link?apn=com.mahamuda.friendfinder&ibi=com.example.
        // ios&link=https%3A%2F%2Ffindfriends.page.link

        Log.e(TAG, "Share link: " + shareLinkText);

        // Shorten the link
        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLongLink(Uri.parse(shareLinkText))
                .buildShortDynamicLink()
                .addOnCompleteListener(new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            // Short link created
                            Uri shortLink = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();

                            Log.e("InviteFriendsNew", "Short Link: " + shortLink);
                            Log.e("InviteFriendsNew", "Debug Link: " + flowchartLink);

                            intent.setAction(Intent.ACTION_SEND);
                            // TODO add message feature
                            //https://firebase.google.com/docs/dynamic-links/use-cases/rewarded-referral
                            intent.putExtra(Intent.EXTRA_TEXT, shortLink.toString());
                            intent.setType("text/plain");
                            startActivity(intent);

                        } else {
                            // Error
                            Log.e("InviteFriendsNew", " Error: " + task.getException());
                        }
                    }
                });
    }
}
