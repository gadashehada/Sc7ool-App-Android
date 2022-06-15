package com.example.school;

import android.content.Intent;
import android.os.Bundle;

import com.example.school.ui.admin_login.AdminLoginFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    TextView tv_name , tv_email;
    ImageView im_user ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.about_school, R.id.nav_teacher_staff,
                R.id.nav_contact_us, R.id.nav_admin_login, R.id.nav_about_us)
                .setDrawerLayout(drawer)
                .build();

        View view = navigationView.getHeaderView(0);
        tv_name = view.findViewById(R.id.tv_nav_name);
        im_user = view.findViewById(R.id.im_nav_image);
        tv_email = view.findViewById(R.id.tv_email);

        if (AdminLoginFragment.user != null) {
            navigationView.getMenu().findItem(R.id.nav_admin_login).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);

            if (AdminLoginFragment.user.getType().equals("1")) {
                navigationView.getMenu().findItem(R.id.nav_add_lesson).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_add_new).setVisible(false);
            } else if (AdminLoginFragment.user.getType().equals("2")) {
                navigationView.getMenu().findItem(R.id.nav_add_lesson).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_add_new).setVisible(true);
            } else if (AdminLoginFragment.user.getType().equals("0")) {
                navigationView.getMenu().findItem(R.id.nav_add_lesson).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_add_new).setVisible(false);
            }
            tv_name.setText(AdminLoginFragment.user.getName());
            tv_email.setText(AdminLoginFragment.user.getEmail());
            Picasso.get().load(AdminLoginFragment.user.getImage_profile()).into(im_user);
        }

        navigationView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdminLoginFragment.user = null ;
                navigationView.getMenu().findItem(R.id.nav_add_lesson).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_add_new).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_admin_login).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
                drawer.closeDrawers();
                return true;
            }
        });

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
    }
}
