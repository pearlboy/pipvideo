package com.icetea09.demofloatingview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnShowView;
    private VideoView v1,v2;
    private boolean mIsFloatingViewShow; //Flag variable used to identify if the Floating View is visible or not
    private Button btn_one, btn_two;
    String  UrlPath1 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
    String  UrlPath2 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4";;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  mBtnShowView = (Button) findViewById(R.id.btn_show_floating_view);
        btn_one = findViewById(R.id.btn_v1);
        btn_two = findViewById(R.id.btn_v2);
        v1 = findViewById(R.id.local_videoView1);
        v2 = findViewById(R.id.local_videoView2);

       // Log.d("VIDEOPATH", UrlPath);

        mBtnShowView.setOnClickListener(this);
        btn_two.setOnClickListener(this);
        btn_one.setOnClickListener(this);
        mIsFloatingViewShow = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btn_show_floating_view:
//                if (mIsFloatingViewShow) {
//                    hideFloatingView();
//                    mIsFloatingViewShow = false;
//                    mBtnShowView.setText(R.string.show_floating_view);
//                } else {
//                    showFloatingView();
//                    mIsFloatingViewShow = true;
//                    mBtnShowView.setText(R.string.hide_floating_view);
//                }
//                break;
            case R.id.btn_v1:
                Toast.makeText(this, "BTN ONE is Working", Toast.LENGTH_SHORT).show();
                Uri video1 = Uri.parse(UrlPath1);
                v1.setVideoURI(video1);
                v1.start();
                v1.requestFocus();
                v1.setKeepScreenOn(true);
                break;
            case R.id.btn_v2:
                Toast.makeText(this, "BTN Two is Working", Toast.LENGTH_SHORT).show();
                Uri video2 = Uri.parse(UrlPath2);
                v2.setVideoURI(video2);
                v2.start();
                v2.requestFocus();
                v2.setKeepScreenOn(true);
                break;

        }
    }

    private void showFloatingView() {
        startService(new Intent(getApplicationContext(), FloatingViewService.class));
    }

    private void hideFloatingView() {
        stopService(new Intent(getApplicationContext(), FloatingViewService.class));
    }

}
