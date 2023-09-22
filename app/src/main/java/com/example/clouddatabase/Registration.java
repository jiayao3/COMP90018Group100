package com.example.clouddatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class Registration extends AppCompatActivity {

    TextInputEditText input_email, input_password;
    Button reg_btn;
    FirebaseAuth mAuth;
    TextView login_here;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        // Getting the values for each input
        input_email = findViewById(R.id.email);
        input_password = findViewById(R.id.password);
        reg_btn = findViewById(R.id.register_btn);
        login_here = findViewById(R.id.login_opt);

        // Checking if the button is clicked
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;

                // Obtaining the typed inputs
                email = String.valueOf(input_email.getText());
                password = String.valueOf(input_password.getText());

                // Checking if the inputs are empty
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Registration.this, "Email cannot be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Registration.this, "Password cannot be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Code taken from firebase original documentation
                // https://firebase.google.com/docs/auth/android/password-auth#java_3
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Registration.this, "Account Succesfully Created.",
                                            Toast.LENGTH_SHORT).show();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Registration.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        // Checking if "login here!" is clicked
        login_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If the button is clicked, it will bring the user to the login page
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                // Ends the current activity
                finish();
            }
        });
    }
}