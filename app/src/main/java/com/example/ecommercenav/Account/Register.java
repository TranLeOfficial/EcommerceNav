package com.example.ecommercenav.Account;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ecommercenav.MainActivity;
import com.example.ecommercenav.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.regex.Pattern;

import static com.google.android.material.internal.ContextUtils.getActivity;

public class Register extends AppCompatActivity {
    private ImageView imgProfile;
    private EditText regTenTaiKhoan, regDiaChi, regEmail, regSDT, regMatKhau, regNhapLaiMK;
    private Button btnRegister;
    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 300;
    //imgae pick constants
    private static final int IMAGE_PICK_GALLERY_CODE = 400;
    private static final int IMAGE_PICK_CAMERA_CODE = 500;
    //permision arrays
    private String[] cameraPermission;
    private String[] storagePermission;

    private Uri image_Uri;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        actionBar.setTitle("Đăng Ký Tài Khoản");
        AnhXa();
        EventAction();
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Đang load dữ liệu");
        progressDialog.setCanceledOnTouchOutside(false);
    }


    private void EventAction() {
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pick image
                showImagePickDialog();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Register Button
                InputData();
            }
        });
    }

    private String hoten, diachi, sodienthoai, email, password, re_pass;

    private void InputData() {
        //getdata
        hoten = regTenTaiKhoan.getText().toString().trim();
        diachi = regDiaChi.getText().toString().trim();
        sodienthoai = regSDT.getText().toString().trim();
        email = regEmail.getText().toString().trim();
        password = regMatKhau.getText().toString().trim();
        re_pass = regNhapLaiMK.getText().toString().trim();
        //validate data
        if (TextUtils.isEmpty(hoten)) {
            Toast.makeText(this, "Nhap ten...", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(diachi)) {
            Toast.makeText(this, "Nhap diac chi...", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(sodienthoai)) {
            Toast.makeText(this, "Nhap so dien thoai...", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Nhap email...", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "Mat khau phai lon hon 6 ky tu ...", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(re_pass)) {
            Toast.makeText(this, "Mat khau khong trung khop...", Toast.LENGTH_SHORT).show();
            return;
        }
        CreateAcount();
    }

    private void CreateAcount() {
        progressDialog.setMessage("Tao tai khoan...");
        progressDialog.show();

        //create account
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        saveFirebaseData();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(Register.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveFirebaseData() {
        progressDialog.setMessage("Luu lai thong tin tai khoan...");
        final String timestamp = "" + System.currentTimeMillis();
        if (image_Uri == null) {
            //setup data
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("uid", firebaseAuth.getUid());
            hashMap.put("email", "" + email);
            hashMap.put("hoten", "" + hoten);
            hashMap.put("diachi", "" + diachi);
            hashMap.put("phone", "" + sodienthoai);
            hashMap.put("profileImage", "");
            hashMap.put("makhachhang", "" + timestamp);
            hashMap.put("loaitaikhoan", "user");
            hashMap.put("online", "true");
            //save to db
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.child(firebaseAuth.getUid()).setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressDialog.dismiss();
                            startActivity(new Intent(Register.this, MainActivity.class ));
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            startActivity(new Intent(Register.this, MainActivity.class ));
                            finish();
                        }
                    });
        } else {
            //name and path of image
            String firePathAndName = "profile_images/" + "" + firebaseAuth.getUid();
            //upload image
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(firePathAndName);
            storageReference.putFile(image_Uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //geturl of uploaded image
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful()) ;
                            Uri downloadImageUri = uriTask.getResult();
                            if (uriTask.isSuccessful()) {
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("uid", firebaseAuth.getUid());
                                hashMap.put("email", "" + email);
                                hashMap.put("hoten", "" + hoten);
                                hashMap.put("diachi", "" + diachi);
                                hashMap.put("phone", "" + sodienthoai);
                                hashMap.put("profileImage", "");
                                hashMap.put("makhachhang", "" + timestamp);
                                hashMap.put("loaitaikhoan", "user");
                                hashMap.put("online", "true");
                                //save to db
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                                reference.child(firebaseAuth.getUid()).setValue(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                progressDialog.dismiss();
                                                startActivity(new Intent(Register.this, MainActivity.class ));
                                                finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                startActivity(new Intent(Register.this, MainActivity.class ));
                                                finish();
                                            }
                                        });
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                                   progressDialog.dismiss();
                            Toast.makeText(Register.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void showImagePickDialog() {
        String[] options = {"Camera", "Gallery"};
        //dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn ảnh")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            //camera click
                            if (checkCameraPermission()) {
                                pickFromCamera();
                            } else {
                                requestCameraPermission();
                            }
                        } else {
                            //gallery click
                            if (checkStoragePermission()) {
                                pickFromGallery();
                            } else {
                                requestStoragePermission();
                            }
                        }
                    }
                })
                .show();
    }

    private void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "Temp_Image_Tittle");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Temp_Image_Description");
        image_Uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_Uri);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && storageAccepted) {
                        pickFromCamera();
                    } else {
                        Toast.makeText(this, "camera permission are neccessary...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(this, "Storage permission are neccessary...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                image_Uri = data.getData();
                imgProfile.setImageURI(image_Uri);
            } else if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                imgProfile.setImageURI(image_Uri);
            }
        } else {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                image_Uri = data.getData();
                imgProfile.setImageURI(image_Uri);
            } else if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                imgProfile.setImageURI(image_Uri);
            }
            Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void AnhXa() {
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        imgProfile = findViewById(R.id.imgProfile);
        regTenTaiKhoan = findViewById(R.id.regTenTaiKhoan);
        regDiaChi = findViewById(R.id.regDiaChi);
        regEmail = findViewById(R.id.regEmail);
        regSDT = findViewById(R.id.regSDT);
        regMatKhau = findViewById(R.id.regMatKhau);
        regNhapLaiMK = findViewById(R.id.regNhapLaiMK);
        btnRegister = findViewById(R.id.btnRegister);
    }
}