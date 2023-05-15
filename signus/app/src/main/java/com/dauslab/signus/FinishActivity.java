package com.dauslab.signus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FinishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        findViewById(R.id.finish_button).setOnClickListener(view -> finish());

        findViewById(R.id.about_button).setOnClickListener(view -> {
            startActivity(new Intent(this, AboutUsActivity.class));
        });
    }
}