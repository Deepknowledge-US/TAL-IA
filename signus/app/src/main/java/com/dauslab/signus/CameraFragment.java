package com.dauslab.signus;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link androidx.fragment.app.Fragment} subclass.
 * Use the {@link CameraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CameraFragment extends CameraVideoFragment {

    @BindView(R.id.mTextureView)
    AutoFitTextureView mTextureView;
    @BindView(R.id.mRecordVideo)
    ImageView mRecordVideo;
    @BindView(R.id.recordingStatus)
    TextView recordingStatus;
    @BindView(R.id.mVideoView)
    VideoView mVideoView;
    Unbinder unbinder;
    private String mOutputFilePath;
    private int videoNumber;
    private String username;
    Timer timer;

    public CameraFragment(int videoNumber, String username) {
        this.videoNumber = videoNumber;
        this.username = username;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */


    public static CameraFragment newInstance(int videoNumber, String username) {
        CameraFragment fragment = new CameraFragment(videoNumber, username);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public int getTextureResource() {
        return R.id.mTextureView;
    }

    public void startRecord() {
        if (!mIsRecordingVideo && mRecordVideo != null) {
            startRecordingVideo(videoNumber);
            recordingStatus.setText(R.string.recording);
            mRecordVideo.setVisibility(View.VISIBLE);
            timer = new Timer();
            Handler mHandler = new Handler(Looper.getMainLooper());
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (getActivity() != null && mRecordVideo != null) {
                        mHandler.post(() -> mRecordVideo.setVisibility(mRecordVideo.getVisibility() == View.INVISIBLE ? View.VISIBLE : View.INVISIBLE));
                    }
                }
            }, 1000, 1000);
            recordingStatus.setTextColor(getResources().getColor(R.color.colorPrimary));
            //Receive out put file here
            mOutputFilePath = getCurrentFile().getAbsolutePath();
        }
    }

    public void stopRecord(boolean finish) {
        if (mIsRecordingVideo) {
            try {
                stopRecordingVideo();
                prepareViews();
                saveVideo(finish);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void saveVideo(boolean finish) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        Uri file = Uri.fromFile(new File(mOutputFilePath));
        StorageReference storageRef = storage.getReference();

        StorageReference riversRef = storageRef.child("videos/" + username + "/" + file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);

        uploadTask.addOnFailureListener(exception -> {
            FirebaseCrashlytics.getInstance().recordException(exception);
            Toast.makeText(getContext(), "Error al subir el video", Toast.LENGTH_LONG).show();
        }).addOnSuccessListener(taskSnapshot -> {
            if (finish)
                this.getActivity().finish();
        });
    }

    private void prepareViews() {
        if (mVideoView.getVisibility() == View.GONE) {
            mVideoView.setVisibility(View.VISIBLE);
            mTextureView.setVisibility(View.GONE);
            setMediaForRecordVideo();
        }
    }

    private void setMediaForRecordVideo() {
        // Set media controller
        mVideoView.setMediaController(new MediaController(getActivity()));
        mVideoView.requestFocus();
        mVideoView.setVideoPath(mOutputFilePath);
        mVideoView.seekTo(100);
        mVideoView.setOnCompletionListener(mediaPlayer -> {
            mVideoView.setVisibility(View.GONE);
            mTextureView.setVisibility(View.VISIBLE);
            recordingStatus.setText(R.string.stopped);
            mRecordVideo.setVisibility(View.GONE);
            if (timer != null)
                timer.cancel();
            recordingStatus.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
