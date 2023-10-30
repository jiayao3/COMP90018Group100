package com.mygdx.game;

import android.provider.ContactsContract;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AndroidFirebaseInterface implements FirebaseInterface{

    private FirebaseAuth mAuth;
    private static DatabaseReference myRef;

    @Override
    public String getAuthUserId() {
        FirebaseUser currentUser = Login.mAuth.getCurrentUser();
        return currentUser.getUid();
    }

    @Override
    public void sendScore(int score) {
        // if user already has an account and register
        if (Profile.myRef == null) {
            myRef = FirebaseDatabase.getInstance().getReference();
        }

        if (Login.mAuth != null) {
//            Log.d("score sent", String.valueOf(score));
//            Log.d("uid", String.valueOf(score));
            FirebaseUser currentUser = Login.mAuth.getCurrentUser();
            String uid = currentUser.getUid();
            final DatabaseReference scoreRef = myRef.child("users").child(uid).child("highScore");

            scoreRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Long currentHighScore = dataSnapshot.getValue(Long.class);
                        if (score > currentHighScore) {
                            // Update the "highScore" if the new score is higher
                            scoreRef.setValue(score);
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle any errors that occurred.
                    Log.e("Database Read Error", "Error: " + databaseError.getMessage());
                }
            });
        }
    }
}
