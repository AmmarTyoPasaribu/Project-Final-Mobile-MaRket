package com.example.tefinal.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tefinal.R;
import com.example.tefinal.activity.MainActivity;

public class ProfileActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE = 1;
    private static final int MAX_USERNAME_LENGTH = 15;
    private static final int MAX_PASSWORD_LENGTH = 8;

    private ImageView imageViewProfile;
    private EditText editTextUsername, editTextPassword;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imageViewProfile = findViewById(R.id.image_view_profile);
        editTextUsername = findViewById(R.id.edit_text_username);
        editTextPassword = findViewById(R.id.edit_text_password);

        // Mengambil dan menampilkan foto profil dari SharedPreferences
        loadProfileDataFromSharedPreferences();

        Button buttonDone = findViewById(R.id.button_done);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });

        imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void loadProfileDataFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String password = sharedPreferences.getString("password", "");

        // Menampilkan username dan password dari SharedPreferences
        editTextUsername.setText(username);
        editTextPassword.setText(password);
    }

    private void showConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setMessage("Kamu yakin ingin menggantinya?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveChangesToSharedPreferences();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            imageViewProfile.setImageURI(selectedImageUri);
        }
    }

    private void saveChangesToSharedPreferences() {
        String newUsername = editTextUsername.getText().toString().trim();
        String newPassword = editTextPassword.getText().toString().trim();

        if (newUsername.length() > MAX_USERNAME_LENGTH && newPassword.length() > MAX_PASSWORD_LENGTH) {
            Toast.makeText(this, "Username max 15 huruf & Password max 8 huruf", Toast.LENGTH_SHORT).show();
            return;
        } else if (newUsername.length() > MAX_USERNAME_LENGTH) {
            Toast.makeText(this, "Username tidak boleh melebihi 15 karakter", Toast.LENGTH_SHORT).show();
            return;
        } else if (newPassword.length() > MAX_PASSWORD_LENGTH) {
            Toast.makeText(this, "Password tidak boleh melebihi 8 karakter", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Simpan username dan password ke SharedPreferences
        editor.putString("username", newUsername);
        editor.putString("password", newPassword);
        editor.apply();

        // Tampilkan pesan sukses atau sejenisnya
        Toast.makeText(this, "Changes saved successfully", Toast.LENGTH_SHORT).show();

        // Kembali ke MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        if (selectedImageUri != null) {
            intent.putExtra("fotoprofile", selectedImageUri.toString());
        }
        startActivity(intent);
        finish();
    }
}
