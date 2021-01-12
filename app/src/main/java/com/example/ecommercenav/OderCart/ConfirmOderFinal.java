package com.example.ecommercenav.OderCart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommercenav.MainActivity;
import com.example.ecommercenav.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmOderFinal extends AppCompatActivity {

    private EditText shipmentName, shipmentPhone, shipmentEmail, shipmentAddress;
    private Button shipmentBtn;
    private String totalAmount = "";

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_oder_final);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        actionBar.setTitle("Thêm Thông Tin Đặt Hàng");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        totalAmount = getIntent().getStringExtra("Total Price");
        AnhXa();
        eventAction();

    }

    private void eventAction() {
        shipmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check();
            }
        });
    }

    private void Check() {
        if (TextUtils.isEmpty(shipmentName.getText().toString())) {
            Toast.makeText(this, "Ban Phai Nhap Ten!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(shipmentPhone.getText().toString())) {
            Toast.makeText(this, "Ban Phai Nhap So Dien Thoai!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(shipmentEmail.getText().toString())) {
            Toast.makeText(this, "Ban Phai Nhap Email!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(shipmentAddress.getText().toString())) {
            Toast.makeText(this, "Ban Phai Nhap Dia Chi!", Toast.LENGTH_SHORT).show();
        } else {
            ConfirmOder();
        }
    }

    private String saveCurrentTime, saveCurrentDate;

    private void ConfirmOder() {
        Calendar calendarForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendarForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendarForDate.getTime());
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("CartOders")
                .child(firebaseUser.getUid());
        HashMap<String, Object> odersMap = new HashMap<>();
        odersMap.put("total_mount", totalAmount);
        odersMap.put("oder_name", shipmentName.getText().toString());
        odersMap.put("oder_phone", shipmentPhone.getText().toString());
        odersMap.put("oder_email", shipmentEmail.getText().toString());
        odersMap.put("oder_address", shipmentAddress.getText().toString());
        odersMap.put("date", saveCurrentDate);
        odersMap.put("time", saveCurrentTime);
        odersMap.put("state", "Not Shipped");

        reference.updateChildren(odersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    FirebaseDatabase.getInstance().getReference()
                            .child("CartList")
                            .child("UserView")
                            .child(firebaseUser.getUid())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(ConfirmOderFinal.this, "Đặt Hàng Thành Công!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ConfirmOderFinal.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });


    }

    private void AnhXa() {
        shipmentName = findViewById(R.id.shipmentName);
        shipmentPhone = findViewById(R.id.shipmentPhone);
        shipmentEmail = findViewById(R.id.shipmentEmail);
        shipmentAddress = findViewById(R.id.shipmentAddress);
        shipmentBtn = findViewById(R.id.shipmentBtn);
    }
}