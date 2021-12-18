package com.aptech.andfireconnect2001f;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText txtMEmail, txtMPass;
    Button btnMLogin, btnMRegister;
    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CreateLayout();
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user!=null) {
            startActivity(new Intent(MainActivity.this,DisplayActivity.class));
        }
    }

    private  void CreateLayout(){
        txtMEmail = findViewById(R.id.txtMEmail);
        txtMPass = findViewById(R.id.txtMPass);
        btnMLogin = findViewById(R.id.btnMLogin);
        btnMRegister = findViewById(R.id.btnMRegister);

        btnMLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        btnMRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
    private void loginUser(){
        String email = txtMEmail.getText().toString();
        String pass = txtMPass.getText().toString();

        auth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            if (user.isEmailVerified()) {
                                //intent
                                String userEmail = user.getEmail().toString();
                                Toast.makeText(MainActivity.this, "Successfully logged in, email verified", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(MainActivity.this,DisplayActivity.class));
                                //finish();;
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Successfully logged in, email not verified", Toast.LENGTH_SHORT).show();
                                user.sendEmailVerification();
                                auth.signOut();
                                clearData();
                                Toast.makeText(MainActivity.this, "Verification Email sent, logged in again after verification", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Error in login", Toast.LENGTH_SHORT).show();
                Snackbar.make(btnMLogin, "Message is deleted", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                clearData();
                                Snackbar snackbar1 = Snackbar.make(btnMLogin, "record   deleted!", Snackbar.LENGTH_SHORT);
                                snackbar1.show();
                            }
                        }).show();
            }
        });
    }
    private  void clearData(){
        txtMPass.setText("");
        txtMEmail.setText("");


    }

}