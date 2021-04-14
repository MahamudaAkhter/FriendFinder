package com.example.friendfinder.models;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;

public class FireStoreRepo {

    FirebaseFirestore db;

    Map<String, Object> document = new HashMap<>();

    private static FireStoreRepo INSTANCE = null;

    private static User user;

    private FireStoreRepo() {}

    public static FireStoreRepo GetInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FireStoreRepo();
        }
        return(INSTANCE);
    }

    public static void createUser(String userID, String displayName, String email) {
        user = new User(userID, 0, displayName, email,
                false, 0 , 0, null, null);
    }

    public boolean UpdateUserDocument(Location location){
        document.put("User Id", 300 );
        document.put("Avatar", user.getAvatar());
        document.put("Display Name" , user.getDisplayName());
        document.put("Email", user.getEmail());
        //document.put("Phone Number", user.getPhoneNumber());
        document.put("Is Live", user.getLive());
        document.put("Highest Streak", user.getHighestStreak());
        document.put("Total Distance Travelled" , user.getTotalDistanceTravelled());
        document.put ("Badges" , user.getBadges());
        document.put("Friends List" , user.getFriends());
        document.put("live_location" , new GeoPoint(location.getLatitude(), location.getLongitude()));
        updateDocument();
        return true;
    }

    public void updateDocument() {
        db = FirebaseFirestore.getInstance();

        CollectionReference userIdRef = db.collection("users");

        userIdRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                User user = documentSnapshot.toObject(User.class);
                user.setUserID(documentSnapshot.getId() );
                db.collection("users").document(user.getUserID()).set(document);
            }
        });

    }

    public void getDocument() {
        db = FirebaseFirestore.getInstance();
        db.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("TAG", document.getId() + " => " + document.getData());
                            //name = document.getString("username");
                            //userID = document.getString("User ID");
                        }
                    } else {
                        Log.w("TAG", "Error getting documents.", task.getException());
                    }
                });
    }
}
