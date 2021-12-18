package com.aptech.andfireconnect2001f;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class DisplayActivity extends AppCompatActivity {

    TextView txtDEmail;
    EditText txtDName, txtDQuantity, txtDRate;
    Button btnDInsert, btnDUpdate, btnDDelete, btnDLogout;
    List<itemView> listItem;
    ListView lstData;
    FirebaseUser fUser;
    FirebaseAuth fAuth;
    FirebaseDatabase fd;
    itemAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        InitView();
        String email = getIntent().getExtras().getString("email");
        if (email.length()>0) {
            txtDEmail.setText(String.valueOf(email));
        }

    }
    private void InitView(){
        txtDEmail = findViewById(R.id.txtDEmail);
        txtDName = findViewById(R.id.txtDName);
        txtDQuantity = findViewById(R.id.txtDQuantity);
        txtDRate = findViewById(R.id.txtDRate);
        btnDInsert = findViewById(R.id.btnDInsert);
        btnDUpdate = findViewById(R.id.btnDUpdate);
        btnDDelete = findViewById(R.id.btnDDelete);
        btnDLogout = findViewById(R.id.btnDLogout);

        fAuth = FirebaseAuth.getInstance();
        //fUser = fAuth.getCurrentUser();

        btnDLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signOut();
                startActivity(new Intent(DisplayActivity.this,MainActivity.class));
                finish();
            }
        });

        btnDDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnDUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnDInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtDName.getText().toString();
                String quantity = txtDQuantity.getText().toString();
                String rate = txtDRate.getText().toString();

                item Item = new item(name,quantity,rate);

                FirebaseDatabase.getInstance().getReference("Items").push().setValue(Item)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Snackbar.make(btnDInsert,"Record is added",Snackbar.LENGTH_LONG)
                                        .show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(btnDInsert,"Error in inserting record",Snackbar.LENGTH_LONG)
                                .show();
                    }
                });


            }
        });

    }

}