package com.mygdx.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {

    FirebaseAuth auth;
    Button logout_btn, exit_btn;
    TextView show_email;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth = FirebaseAuth.getInstance();

        // Initialise
        logout_btn = findViewById(R.id.logout);
        exit_btn = findViewById(R.id.exit_btn);
        show_email = findViewById(R.id.details);
        user = auth.getCurrentUser();

        // If the user is not logged in, bring up the login page
        if (user == null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }

        // If the user is logged in, show the email
        else {
            show_email.setText(user.getEmail());
        }

        // Checked if the user clicked the logout button
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LauncherActivity.class);
                startActivity(intent);
                finish();
            }
        });

        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If the button is clicked, it will bring the user to the main activity
                Intent intent = new Intent(getApplicationContext(), LauncherActivity.class);
                startActivity(intent);
                // Ends the current activity
                finish();
            }
        });
    }
}