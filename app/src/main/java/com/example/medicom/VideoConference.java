package com.example.medicom;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.vidyo.VidyoClient.Connector.Connector;
import com.vidyo.VidyoClient.Connector.ConnectorPkg;

import java.util.ArrayList;
import java.util.List;

public class VideoConference extends AppCompatActivity implements Connector.IConnect {

    private FrameLayout videoView;
    private Connector videoConnector;
    private final String accessToken = "cHJvdmlzaW9uAERlbW9Vc2VyQDllYmIwYi52aWR5by5pbwA2Mzc2NjM4NDM0MAAAMjI2YjUwNDIwMGZiNzBiN2Q5MTg4NmMyYmU4ODQwMzljNTA5NjM3ZDNhZTA4YjA2OGJiMmEwOTExODdkNjkyMmI2YTlhMmUzYjgwZTU4NmVlNWY5MzVkMTNhZGNjZDZh";
    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener = null;

    private static final String[] mPermissions = new String[] {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };
    private final int PERMISSIONS_REQUEST_ALL = 1988;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_conference);

        videoView = findViewById(R.id.videoView);
        Log.d("VideoTEST", "Failed");


        ConnectorPkg.setApplicationUIContext(this);
        Log.d("VideoTEST", "HERE");
        if (ConnectorPkg.initialize()) {
            videoConnector = new Connector(
                    videoView,
                    Connector.ConnectorViewStyle.VIDYO_CONNECTORVIEWSTYLE_Default,
                    2,
                    "",
                    "",
                    0
            );

            if (Build.VERSION.SDK_INT > 22) {
                List<String> permissionsNeeded = new ArrayList<>();
                for (String permission : mPermissions) {
                    // Check if the permission has already been granted.
                    if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                        permissionsNeeded.add(permission);
                }
                if (permissionsNeeded.size() > 0) {
                    // Request any permissions which have not been granted. The result will be called back in onRequestPermissionsResult.
                    ActivityCompat.requestPermissions(this, permissionsNeeded.toArray(new String[0]), PERMISSIONS_REQUEST_ALL);
                } else {
                    // Begin listening for video view size changes.
                    this.startVideoViewSizeListener();
                }
            } else {
                // Begin listening for video view size changes.
                this.startVideoViewSizeListener();
            }

            videoConnector.showViewAt(videoView, 0, 0, videoView.getWidth(), videoView.getHeight());
            Log.d("VideoTEST", "success");
        }
        else {
            Log.d("VideoTEST", "Failed");
        }
    }

    private void startVideoViewSizeListener() {
        // Render the video each time that the video view (mVideoFrame) is resized. This will
        // occur upon activity creation, orientation changes, and when foregrounding the app.
        ViewTreeObserver viewTreeObserver = videoView.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    // Specify the width/height of the view to render to.
                    videoConnector.showViewAt(videoView, 0, 0, videoView.getWidth(), videoView.getHeight());
                    mOnGlobalLayoutListener = this;
                }
            });
        } else {
            Log.d("ERROR", "ERROR in startVideoViewSizeListener! Video will not be rendered.");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        // If the expected request code is received, begin rendering video.
        if (requestCode == PERMISSIONS_REQUEST_ALL) {
            for (int i = 0; i < permissions.length; ++i)

            // Begin listening for video view size changes.
            this.startVideoViewSizeListener();
        } else {
            Toast.makeText(this,"ERROR! Unexpected permission requested. Video will not be rendered.", Toast.LENGTH_LONG).show();
        }
    }

    public void connectRoom(View view) {
        videoConnector.connect(
                "prod.vidyo.io",
                accessToken,
                "DemoUser",
                "DemoRoom",
                this);
    }

    void showMsg(String msg) {
        Log.d("INFO MSG:", msg);
    }

    @Override
    public void onSuccess() {
        showMsg("Success");
    }

    @Override
    public void onFailure(Connector.ConnectorFailReason connectorFailReason) {
        showMsg(connectorFailReason.name());
    }

    @Override
    public void onDisconnected(Connector.ConnectorDisconnectReason connectorDisconnectReason) {
        showMsg(connectorDisconnectReason.name());
    }
}