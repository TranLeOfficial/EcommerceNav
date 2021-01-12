package com.example.ecommercenav;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.ecommercenav.Account.Login;
import com.example.ecommercenav.Cart.ShowCart;
import com.example.ecommercenav.Model.ProfileModel;
import com.example.ecommercenav.fragment.home.AdapterProducts;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public ImageView imageViewInfo;
    public TextView tvhoten, email;
    DrawerLayout drawer;
    private AppBarConfiguration mAppBarConfiguration;
    BottomNavigationView bottomMenuBar;
    NavigationView navigationView;
    FloatingActionButton fab;
    public AdapterProducts adapterProducts;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private ProfileModel profileModel;

   
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        loadDataHeader();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShowCart.class);
                startActivity(intent);
            }
        });

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_search, R.id.nav_cate, R.id.nav_alarm, R.id.nav_info, R.id.nav_profile)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
//        ============================================================BottomMenu====================================================
        bottomMenuBar = findViewById(R.id.bottom_menu);
        NavigationUI.setupWithNavController(bottomMenuBar, navController);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            firebaseAuth.signOut();
            checkUser();
            Toast.makeText(this, "Bạn đã đăng xuất thành công!", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.timkiemsanpham) {
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void checkUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
        } else {
            //loadMyInfo();
        }
    }

//    private void loadMyInfo()
//    {
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
//        reference.orderByChild("uid").equalTo(firebaseAuth.getUid())
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for (DataSnapshot ds: dataSnapshot.getChildren())
//                        {
//                            String hoten = ""+ds.child("hoten").getValue();
//                            String loaitaikhoan = ""+ds.child("loaitaikhoan").getValue();
//                            tvhoten.setText(hoten + "("+loaitaikhoan+")");
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    public void loadDataHeader() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);

        profileModel = new ProfileModel();
        String id = profileModel.getProfileID();
        String icon = profileModel.getProfileIcon();
        String name = profileModel.getProfileName();
        String phone = profileModel.getProfileEmail();
        imageViewInfo = view.findViewById(R.id.imageViewInfo);
        tvhoten = view.findViewById(R.id.hoten);
        email = view.findViewById(R.id.phone);

        tvhoten.setText(name);
        email.setText(firebaseUser.getEmail());
        //loadimage
        Glide.with(this).load(icon).into(imageViewInfo);


    }
}