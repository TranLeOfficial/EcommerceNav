package com.example.ecommercenav.Account;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.ecommercenav.R;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    Button btnDangKy;
    EditText txtTenTk, txtDiaChi, txtSDT, txtMK, txtNhapLaiMK;
    Intent intent;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        actionBar.setTitle("Đăng Ký Tài Khoản");
        intent = getIntent();
        mAuth = FirebaseAuth.getInstance();
        AnhXa();
        Event();
    }


    private void Event() {


    }


    private void AnhXa() {
        btnDangKy = findViewById(R.id.btnRegister);
        txtTenTk = findViewById(R.id.regTenTaiKhoan);
        txtDiaChi = findViewById(R.id.regDiaChi);
        txtSDT = findViewById(R.id.regSDT);
        txtMK = findViewById(R.id.regMatKhau);
        txtNhapLaiMK = findViewById(R.id.regNhapLaiMK);
    }
}