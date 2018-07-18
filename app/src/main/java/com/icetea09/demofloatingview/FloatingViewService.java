package com.icetea09.demofloatingview;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

public class FloatingViewService extends Service {

    private WindowManager mWindowManager;
    private ImageView mImgFloatingView;
    private VideoView v2;
    private boolean mIsFloatingViewAttached = false;
    String camURL = "rtsp://admin:admin123@192.168.1.64:554";

    @Override
    public IBinder onBind(Intent intent) {
        //Not use this method
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!mIsFloatingViewAttached) {
            mWindowManager.addView(v2, v2.getLayoutParams());
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        v2 = new VideoView(this);
        ViewGroup.LayoutParams btnParameters = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v2.setLayoutParams(btnParameters);
        mImgFloatingView = new ImageView(this);
        mImgFloatingView.setImageResource(R.mipmap.ic_launcher);
        Uri video = Uri.parse(camURL);
        v2.setVideoURI(video);
        v2.start();
        v2.requestFocus();
        v2.setKeepScreenOn(true);
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT == 23) {
            Log.d("If BUILDVERSION", String.valueOf(Build.VERSION.SDK_INT));
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        } else if (Build.VERSION.SDK_INT == 25) {
            Log.d("ELSE IF BUILDVERSION", String.valueOf(Build.VERSION.SDK_INT));
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        } else {
            Log.d("ELS EBUILDVERSION", String.valueOf(Build.VERSION.SDK_INT));
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE  | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);


        params.gravity = Gravity.TOP | Gravity.LEFT;

        mWindowManager.addView(v2, params);

        v2.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        mWindowManager.updateViewLayout(v2, params);
                        return true;
                }
                return false;
            }
        });

        mIsFloatingViewAttached = true;
    }

    public void removeView() {
        if (v2 != null) {
            mWindowManager.removeView(v2);
            mIsFloatingViewAttached = false;
        }
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(), "onDestroy", Toast.LENGTH_SHORT);
        super.onDestroy();
        removeView();
    }
}
