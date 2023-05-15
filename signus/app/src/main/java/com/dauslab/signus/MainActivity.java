package com.dauslab.signus;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private int videoNumber = 0;
    private final int MAX_VIDEO = 5;
    private CameraFragment cameraFragment;
    private String username = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        getSupportActionBar().setTitle(R.string.title_activity_login);

        username = getIntent().getStringExtra(Login.USERNAME);

        this.videoNumber = 0;

        ((TextView) findViewById(R.id.max_video)).setText((MAX_VIDEO + 1) + "");


        findViewById(R.id.nextButton).setOnClickListener(view -> {
            if (videoNumber >= MAX_VIDEO) {
                if (cameraFragment != null)
                    cameraFragment.stopRecord(true);
                startActivity(new Intent(this, FinishActivity.class));
            } else {
                videoNumber++;
                ((TextView) findViewById(R.id.current_video)).setText((videoNumber + 1) + "");
                loadVideo();
                showHideButtons();
            }
        });

        findViewById(R.id.restartButton).setOnClickListener(view -> {
            loadVideo();
            showHideButtons();
        });

        loadVideo();
        showHideButtons();
    }


    private void loadVideo() {
        LinearLayout cameraFragmentContainer = findViewById(R.id.cameraFragmentContainer);
        cameraFragmentContainer.removeAllViews();
        if (cameraFragment != null)
            cameraFragment.stopRecord(false);
        VideoView videoContainer = findViewById(R.id.youtubeFragmentContainer);
        videoContainer.stopPlayback();
        videoContainer.setVisibility(View.INVISIBLE);
        videoContainer.setOnPreparedListener(mediaPlayer -> {
            TextView videoDuration = findViewById(R.id.videoDuration);
            if (mediaPlayer.getDuration() > 0) {
                videoDuration.setVisibility(View.VISIBLE);
                long minutes = Math.round(mediaPlayer.getDuration() / 60000.0);
                videoDuration.setText(String.format(minutes <= 1 ? getString(R.string.video_duration_singular) : getString(R.string.video_duration_plural), minutes));
            }else{
                videoDuration.setVisibility(View.GONE);
            }
        });
        cameraFragment = CameraFragment.newInstance(videoNumber, username);
        getSupportFragmentManager().beginTransaction().add(cameraFragmentContainer.getId(), cameraFragment, "").commit();
        try {
            File localFile = File.createTempFile("video_original", ".mp4");
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference("videos_originales/Bloque " + (videoNumber + 1) + "_LR.mp4");
            storageRef.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                new Handler().postDelayed(() -> cameraFragment.startRecord(), 500);
                videoContainer.setVisibility(View.VISIBLE);
                videoContainer.setVideoPath(localFile.getPath());
                videoContainer.requestFocus();
                videoContainer.start();
            }).addOnFailureListener(exception -> {
                FirebaseCrashlytics.getInstance().recordException(exception);
                Toast.makeText(MainActivity.this, "Se ha producido un error cargando el video " + videoNumber, Toast.LENGTH_SHORT).show();
            });
        } catch (Exception ignore) {
        }
    }

    private void showHideButtons() {
        if (videoNumber >= MAX_VIDEO) {
            ((Button) findViewById(R.id.nextButton)).setText(R.string.finish);
        } else {
            ((Button) findViewById(R.id.nextButton)).setText(R.string.next);
        }
    }

}