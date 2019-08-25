package com.example.venuebooking_hitam;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseUser currentUser;

    private String email, uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentUser = mAuth.getCurrentUser();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser userx = firebaseAuth.getCurrentUser();

                if (userx != null) {
                    uid = userx.getUid();
                } else {
                    startActivity(new Intent(MainActivity.this, LoginPage.class));
                }
            }
        };

        if (currentUser!=null) {
            email = currentUser.getEmail();
            uid = currentUser.getUid();
        }
    }

    public void mainMenuClick(View view) {
        if (view.getId() == R.id.venuesCard) {
            startActivity(new Intent(MainActivity.this, RoomsPage.class));
        } else if (view.getId() == R.id.summaryCard) {
            startActivity(new Intent(MainActivity.this, SummaryPickDate.class));
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        mAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.info_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(MainActivity.this, InfoPage.class));
        return super.onOptionsItemSelected(item);
    }
}
