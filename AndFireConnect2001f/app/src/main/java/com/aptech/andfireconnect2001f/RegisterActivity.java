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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    EditText txtEmail, txtPass1, txtPass2;
    Button btnRegis, btnLogin;
    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth = FirebaseAuth.getInstance();
        CreateLayout();
    }

    private  void CreateLayout(){
        txtEmail = findViewById(R.id.txtREmail);
        txtPass1 = findViewById(R.id.txtRPass1);
        txtPass2 = findViewById(R.id.txtRPass2);

        btnRegis = findViewById(R.id.btnRRegis);
        btnLogin = findViewById(R.id.btnRLogin);

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                finish();
            }
        });
    }

    void Register(){
        String email = txtEmail.getText().toString();
        String pass1 = txtPass1.getText().toString();
        String pass2 = txtPass2.getText().toString();

        if (pass2.trim().equals(pass1.trim())) {
            auth.createUserWithEmailAndPassword(email,pass1)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = auth.getCurrentUser();
                                user.sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                auth.signOut();
                                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                                finish();;
                                            }
                                        });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegisterActivity.this, "Failure to register user", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(this, "Password and confirm password not match", Toast.LENGTH_SHORT).show();
            ClearData();
            return;
        }
    }
    void ClearData(){
        txtEmail.setText("");
        txtPass1.setText("");
        txtPass2.setText("");
    }
}