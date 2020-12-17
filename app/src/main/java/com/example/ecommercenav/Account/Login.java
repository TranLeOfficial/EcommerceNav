package com.example.ecommercenav.Account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommercenav.MainActivity;
import com.example.ecommercenav.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    Button btnSignup, btnLogin;
    EditText txtPhone, txtPass;
    Intent intent;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        progressDialog = new ProgressDialog(this);
        intent = getIntent();
        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Toast.makeText(Login.this,"bạn đăng nhập với "+ user.getEmail(),Toast.LENGTH_LONG).show();
                    intent = new Intent(Login.this,MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(Login.this,"bạn chưa đăng nhập",Toast.LENGTH_LONG).show();
                }
            }
        };
        AnhXa();
        Event();
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }
    protected void onStop(){
        super.onStop();
        if (mAuthStateListener != null){
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }
    private void Event() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
                Pattern pattern = Pattern.compile(emailRegEx);
                Matcher matcher = pattern.matcher(txtPhone.getText().toString());
                if (txtPhone.getText().toString().isEmpty()) {
                    Toast.makeText(Login.this, "Vui lòng nhập địa chỉ email", Toast.LENGTH_LONG).show();
                    return;
                } else if (!matcher.find()) {
                    Toast.makeText(Login.this, "Địa chỉ email không đúng", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //kiểm tra password
                    String pass=txtPass.getText().toString();
                    if (pass.length() < 6){
                        Toast.makeText(getApplicationContext(), "Mật khẩu sai", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else {
                        //loading
                        progressDialog.setMessage("Please wait...");
                        progressDialog.show();
                        mAuth.signInWithEmailAndPassword(txtPhone.getText().toString(), txtPass.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                                            intent = new Intent(Login.this, MainActivity.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(getApplicationContext(), " Thông tin tài khoản sai", Toast.LENGTH_LONG).show();
                                            progressDialog.dismiss();
                                        }
                                    }
                                });

                    }
                }

            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Login.this, DangKy.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        btnSignup = findViewById(R.id.btnSignup);
        btnLogin = findViewById(R.id.btnLogin);
        txtPhone = findViewById(R.id.inputPhone);
        txtPass = findViewById(R.id.inputPass);
    }

}
 