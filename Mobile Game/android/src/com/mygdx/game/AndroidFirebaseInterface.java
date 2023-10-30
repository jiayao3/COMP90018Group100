package com.mygdx.game;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
            Log.d("score sent", String.valueOf(score));
            Log.d("uid", String.valueOf(score));
            FirebaseUser currentUser = Login.mAuth.getCurrentUser();
            String uid = currentUser.getUid();
            myRef.child("users").child(uid).child("highScore").setValue(score);
        }
    }
}
