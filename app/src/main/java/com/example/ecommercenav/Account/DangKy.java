package com.example.ecommercenav.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommercenav.MainActivity;
import com.example.ecommercenav.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DangKy extends AppCompatActivity {
    Button btnDangKy, btnHuy;
    EditText txtpass, txtRPass, txtTaiKhoan;
    Intent intent;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        intent = getIntent();
        mAuth = FirebaseAuth.getInstance();
        AnhXa();
        Event();
    }
    private void Event() {
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword(txtTaiKhoan.getText().toString(), txtpass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isComplete()) {
                            Toast.makeText(getApplicationContext(), "Dang ky thanh cong", Toast.LENGTH_LONG).show();
                            intent = new Intent(DangKy.this, Login.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DangKy.this, Login.class);
                startActivity(intent);
            }
        });
    }
    public  void DangkyTK(){

    }
    private void AnhXa() {
        btnDangKy = findViewById(R.id.btnDK);
        btnHuy = findViewById(R.id.huy);
        txtpass = findViewById(R.id.matkhau);
        txtTaiKhoan = findViewById(R.id.taikhoan);
    }
}