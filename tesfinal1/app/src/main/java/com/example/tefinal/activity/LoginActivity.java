package com.example.tefinal.activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tefinal.R;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextUsername, editTextPassword;
    private ProgressBar progressBar;
    private Button buttonLogin;
//    private Button buttonRegister;
    private TextView keregis, judul1, judul2, judul3;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.edit_text_username);
        editTextPassword = findViewById(R.id.edit_text_password);
        buttonLogin = findViewById(R.id.button_login);
//        buttonRegister = findViewById(R.id.button_register);
        keregis = findViewById(R.id.keregis);
        judul1 = findViewById(R.id.judul1);
        judul2 = findViewById(R.id.judul2);
        judul3 = findViewById(R.id.judul3);
        progressBar = findViewById(R.id.progress_bar);




        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        checkLoginStatus();

        keregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
//
//        buttonRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    private void loginUser() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        String savedUsername = sharedPreferences.getString("username", "");
        String savedPassword = sharedPreferences.getString("password", "");

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (username.equals(savedUsername) && password.equals(savedPassword)) {
            progressBar.setVisibility(View.VISIBLE);


            editTextUsername.setVisibility(View.GONE);
            buttonLogin.setVisibility(View.GONE);
//            buttonRegister.setVisibility(View.GONE);
            judul1.setVisibility(View.GONE);
            judul2.setVisibility(View.GONE);
            judul3.setVisibility(View.GONE);
            keregis.setVisibility(View.GONE);
            editTextPassword.setVisibility(View.GONE);

            klosudahloginakanlngsunghome(true);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 3000); // 3 seconds delay
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkLoginStatus() {
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void klosudahloginakanlngsunghome(boolean isLoggedIn) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", isLoggedIn);
        editor.apply();
    }

}
