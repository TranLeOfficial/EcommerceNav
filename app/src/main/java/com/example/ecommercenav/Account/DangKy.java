package com.example.ecommercenav.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DangKy extends AppCompatActivity {
    Button btnDangKy, btnHuy;
    EditText txtpass, txtRPass, txtTaiKhoan;
    Intent intent;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        intent = getIntent();
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        AnhXa();
        Event();
    }
    private void Event() {
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //kiểm tra email
                String emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
                Pattern pattern = Pattern.compile(emailRegEx);
                Matcher matcher = pattern.matcher(txtTaiKhoan.getText().toString());
                if (txtTaiKhoan.getText().toString().isEmpty()) {
                    Toast.makeText(DangKy.this, "Vui lòng nhập địa chỉ email", Toast.LENGTH_LONG).show();
                    return;
                } else if (!matcher.find()) {
                    Toast.makeText(DangKy.this, "Địa chỉ email không đúng", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //kiểm tra password
                    String pass=txtpass.getText().toString();
                    if (pass.length() < 6){
                        Toast.makeText(getApplicationContext(), "Mật khẩu phải 6 ký tự trở lên", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else {
                        //đăng ký tài khoản
                        mAuth.createUserWithEmailAndPassword(txtTaiKhoan.getText().toString(), txtpass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isComplete()) {
                                    Toast.makeText(getApplicationContext(), "Dang ky thanh cong", Toast.LENGTH_LONG).show();
                                    intent = new Intent(DangKy.this, Login.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }
                //loading
                progressDialog.setMessage("Please wait...");
                progressDialog.show();

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
    private void AnhXa() {
        btnDangKy = findViewById(R.id.btnDK);
        btnHuy = findViewById(R.id.huy);
        txtpass = findViewById(R.id.matkhau);
        txtTaiKhoan = findViewById(R.id.taikhoan);
    }
}