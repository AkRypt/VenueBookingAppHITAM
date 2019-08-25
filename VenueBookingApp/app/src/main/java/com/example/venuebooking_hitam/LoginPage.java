package com.example.venuebooking_hitam;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {

    private EditText loginEmail, loginPassword;
    private Button loginButton;
    private ProgressBar loading;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private String email, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_page);

        loginEmail = findViewById(R.id.loginPageEmail);
        loginPassword = findViewById(R.id.loginPagePassword);
        loginButton = findViewById(R.id.loginPageSubmit);
        loading = findViewById(R.id.loginProgress);
        loading.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginEmail.getText().length() > 0 && loginPassword.getText().length() > 0) {
                    loading.setVisibility(View.VISIBLE);
                    loginButton.setVisibility(View.GONE);
                    loginUser();
                } else {
                    Toast.makeText(LoginPage.this, "Please fill the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void loginUser() {
        email = loginEmail.getText().toString().trim();
        password = loginPassword.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            currentUser = mAuth.getCurrentUser();
                            finish();
                        } else {
                            loading.setVisibility(View.GONE);
                            loginButton.setVisibility(View.VISIBLE);
                            Toast.makeText(LoginPage.this, "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
