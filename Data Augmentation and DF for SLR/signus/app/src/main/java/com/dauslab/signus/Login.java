package com.dauslab.signus;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class Login extends AppCompatActivity {

    public static final String USERNAME =  "USERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        getSupportActionBar().setTitle(R.string.title_activity_login);

        findViewById(R.id.firstButton).setOnClickListener(view -> {
            TextView username = findViewById(R.id.username);
            if (username.getText().toString().isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setMessage(R.string.no_username).setPositiveButton(R.string.ok, (dialog, id) -> {
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else {
                Intent intent = new Intent(this, WarmUpActivity.class);
                intent.putExtra(USERNAME, username.getText().toString());
                hideKeyboard();
                startActivity(intent);
            }
        });

        findViewById(R.id.about_button).setOnClickListener(view -> {
            startActivity(new Intent(this, AboutUsActivity.class));
        });

        requestPermission();
    }

    /**
     * Requesting permissions storage, audio and camera at once
     */
    public void requestPermission() {
        Dexter.withContext(this)
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check for permanent denial of any permission show alert dialog
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // open Settings activity
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions,
                                                                   PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .withErrorListener(dexterError -> Toast.makeText(getApplicationContext(), "Error occurred! ",
                        Toast.LENGTH_SHORT).show())
                .onSameThread()
                .check();
    }

    /**
     * Showing Alert Dialog with Settings option in case of deny any permission
     */
    private void showSettingsDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.message_need_permission));
        builder.setMessage(getString(R.string.message_permission));
        builder.setPositiveButton(getString(R.string.title_go_to_setting), (dialog, i) -> dialog.cancel());
        builder.show();
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}