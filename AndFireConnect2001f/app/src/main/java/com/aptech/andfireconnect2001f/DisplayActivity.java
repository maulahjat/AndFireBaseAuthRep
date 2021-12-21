package com.aptech.andfireconnect2001f;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {

    TextView txtDEmail;
    TextView lblDid;
    EditText txtDName, txtDQuantity, txtDRate;
    Button btnDInsert, btnDUpdate, btnDDelete, btnDLogout;
    List<itemView> listItem;
    ListView lstData;
    FirebaseAuth fAuth;
    itemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        listItem = new ArrayList<>();
        InitView();
        String email = getIntent().getExtras().getString("email");
        if (email.length()>0) {
            txtDEmail.setText(String.valueOf(email));
        }
    }
    private void InitView(){
        lblDid = findViewById(R.id.lblDid);
        txtDEmail = findViewById(R.id.txtDEmail);
        txtDName = findViewById(R.id.txtDName);
        txtDQuantity = findViewById(R.id.txtDQuantity);
        txtDRate = findViewById(R.id.txtDRate);
        btnDInsert = findViewById(R.id.btnDInsert);
        btnDUpdate = findViewById(R.id.btnDUpdate);
        btnDDelete = findViewById(R.id.btnDDelete);
        btnDLogout = findViewById(R.id.btnDLogout);
        lstData = findViewById(R.id.lstData);
        fetchData();
        lstData.setAdapter(adapter);
        fAuth = FirebaseAuth.getInstance();
        lstData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemView itemObj =(itemView) adapter.getItem(position);
                Snackbar.make(lstData,String.format("your have selected item named %S",
                        itemObj.getItemName().toUpperCase()),Snackbar.LENGTH_LONG)
                        .show();
                lblDid.setText(itemObj.getID().toString());
                txtDName.setText(itemObj.getItemName().toString());
                txtDQuantity.setText(itemObj.getQuantity().toString());
                txtDRate.setText(itemObj.getRate().toString());
            }
        });
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
                String id = lblDid.getText().toString();
                FirebaseDatabase.getInstance().getReference("Items").child(id).removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Snackbar.make(btnDInsert,"Successfully deleted",Snackbar.LENGTH_LONG)
                                        .show();
                                ClearData();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(btnDInsert,"Error while deleting....",Snackbar.LENGTH_LONG)
                                .show();
                    }
                });
                ClearData();
            }
        });
        btnDUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = lblDid.getText().toString();
                String name = txtDName.getText().toString();
                String quantity = txtDQuantity.getText().toString();
                String rate = txtDRate.getText().toString();

                item itm = new item(name,quantity,rate);
                FirebaseDatabase.getInstance().getReference("Items").child(id).setValue(itm)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Snackbar.make(btnDInsert,"Record is updated",Snackbar.LENGTH_LONG)
                                        .show();
                                ClearData();;
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(btnDInsert,"Error in updating",Snackbar.LENGTH_LONG)
                                .show();
                    }
                });
                ClearData();
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
                                listItem.clear();
                                fetchData();
                                ClearData();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(btnDInsert,"Error in inserting record",Snackbar.LENGTH_LONG)
                                .show();
                        ClearData();
                    }
                });


            }
        });
    }

    private void fetchData() {
        FirebaseDatabase.getInstance().getReference("Items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (lstData != null) {
                    lstData.invalidateViews();
                    listItem.clear();
                }
                for (DataSnapshot d:snapshot.getChildren()){
                    if (d.getKey() == null) {
                        continue;
                    }else{
                        String id = d.getKey();
                        //Toast.makeText(DisplayActivity.this, d.child("itemName").getValue().toString(), Toast.LENGTH_SHORT).show();
                        String name = d.child("itemName").getValue().toString();
                        String quantity = d.child("quantity").getValue().toString();
                        String rate = d.child("rate").getValue().toString();

                        itemView view = new itemView(id,name,quantity,rate);
                        listItem.add(view);
                    }
                    adapter = new itemAdapter(getApplicationContext(),listItem);
                    adapter.notifyDataSetChanged();
                    lstData.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void ClearData(){
        txtDName.setText("");
        txtDQuantity.setText("");
        txtDRate.setText("");
    }
}