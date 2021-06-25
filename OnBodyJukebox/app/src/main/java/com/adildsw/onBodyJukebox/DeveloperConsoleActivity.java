package com.adildsw.onBodyJukebox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class DeveloperConsoleActivity extends AppCompatActivity {

    String ip;
    Utils utils;

    TextView leftWristFSR, leftElbowFlex, leftKneeFlex;
    TextView rightWristFSR, rightElbowFlex, rightKneeFlex;

    Button toggleMonitoringBtn;

    boolean isMonitoring = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_console);

        leftWristFSR = findViewById(R.id.leftWristFSR);
        leftElbowFlex = findViewById(R.id.leftElbowFlex);
        leftKneeFlex = findViewById(R.id.leftKneeFlex);
        rightWristFSR = findViewById(R.id.rightWristFSR);
        rightElbowFlex = findViewById(R.id.rightElbowFlex);
        rightKneeFlex = findViewById(R.id.rightKneeFlex);

        toggleMonitoringBtn = findViewById(R.id.toggleMonitoring);

        utils = new Utils();
        utils.setNotificationBarColor(getWindow(), "#080808");

        ip = utils.getIPAddress(getApplicationContext());

        checkConnection(ip);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isMonitoring) {
                    updateUISensorValues();
                }
                else {
                    rightWristFSR.setText("NA");
                    leftWristFSR.setText("NA");
                    rightElbowFlex.setText("NA");
                    leftElbowFlex.setText("NA");
                    rightKneeFlex.setText("NA");
                    leftKneeFlex.setText("NA");
                }
                handler.postDelayed(this, 100);
            }
        }, 500);
    }

    public void openSettings(View view) {
        toggleMonitoring(false);
        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
    }

    public void updateUISensorValues() {
        String uri = ip + "/get_sensor_values";
        utils.getRequest(uri, getApplicationContext(), new HttpRequestListener() {
            @Override
            public void onError(String error) {
                rightWristFSR.setText("NA");
                leftWristFSR.setText("NA");
                rightElbowFlex.setText("NA");
                leftElbowFlex.setText("NA");
                rightKneeFlex.setText("NA");
                leftKneeFlex.setText("NA");
                setStatus(StatusMode.ERROR);
            }

            @Override
            public void onResponse(Object response) {
                if (response.toString().equals("NA")) {
                    rightWristFSR.setText("NA");
                    leftWristFSR.setText("NA");
                    rightElbowFlex.setText("NA");
                    leftElbowFlex.setText("NA");
                    rightKneeFlex.setText("NA");
                    leftKneeFlex.setText("NA");
                    setStatus(StatusMode.SENSOR_ERROR);
                }
                else {
                    String[] values = response.toString().split(",");
                    rightWristFSR.setText(values[0]);
                    leftWristFSR.setText(values[1]);
                    rightElbowFlex.setText(values[2]);
                    leftElbowFlex.setText(values[3]);
                    rightKneeFlex.setText(values[4]);
                    leftKneeFlex.setText(values[5]);

                    setStatus(StatusMode.CONNECTED);
                }
            }
        });
    }

    public void testGrainVibration(View view) {
        String tag = view.getTag().toString();
        int channel = Integer.parseInt(tag.substring(7));

        String uri = ip + "/play_grain?channel=" + channel;

        setStatus(StatusMode.CONNECTING);
        utils.getRequest(uri, getApplicationContext(), new HttpRequestListener() {
            @Override
            public void onError(String error) {
                setStatus(StatusMode.ERROR);
            }

            @Override
            public void onResponse(Object response) {
                if (response.toString().equals("OK")) {
                    setStatus(StatusMode.CONNECTED);
                }
                else {
                    setStatus(StatusMode.ERROR);
                }
            }
        });
    }

    public void toggleMonitoring(View view) {
        toggleMonitoring(!isMonitoring);
    }

    public void toggleMonitoring(boolean enabled) {
        isMonitoring = enabled;
        if (isMonitoring) {
            toggleMonitoringBtn.setText("Stop Monitoring");
        }
        else {
            toggleMonitoringBtn.setText("Start Monitoring");
        }
    }

    public void resetDSP(View view) {
        String uri = ip + "/reset_dsp";
        setStatus(StatusMode.CONNECTING);
        utils.getRequest(uri, getApplicationContext(), new HttpRequestListener() {
            @Override
            public void onError(String error) {
                setStatus(StatusMode.ERROR);
            }

            @Override
            public void onResponse(Object response) {
                if (response.toString().equals("OK")) {
                    setStatus(StatusMode.CONNECTED);
                }
                else {
                    setStatus(StatusMode.ERROR);
                }
            }
        });
    }

    public void checkConnection(String ip) {
        setStatus(StatusMode.CONNECTING);
        utils.getRequest(ip, getApplicationContext(), new HttpRequestListener() {
            @Override
            public void onError(String error) {
                setStatus(StatusMode.ERROR);
            }

            @Override
            public void onResponse(Object response) {
                if (response.toString().equals("OnBodyGym Server")) {
                    setStatus(StatusMode.CONNECTED);
                }
                else {
                    setStatus(StatusMode.ERROR);
                }
            }
        });
    }

    public void setStatus(StatusMode mode) {
        FrameLayout statusBar = findViewById(R.id.statusBar);
        TextView statusText = findViewById(R.id.statusText);

        switch(mode) {
            case CONNECTING:
                statusBar.setBackgroundColor(Color.parseColor("#000000"));
                statusText.setText("Connecting...");
                break;

            case CONNECTED:
                statusBar.setBackgroundColor(Color.parseColor("#3D9970"));
                statusText.setText("Connected");
                break;

            case ERROR:
                statusBar.setBackgroundColor(Color.parseColor("#FF4136"));
                statusText.setText("Error Connecting to Raspberry Pi Server");
                break;

            case SENSOR_ERROR:
                statusBar.setBackgroundColor(Color.parseColor("#FF4136"));
                statusText.setText("Error Retrieving Values from Sensors");
                break;

            case MUSIC_STOPPED:
                statusBar.setBackgroundColor(Color.parseColor("#FF851B"));
                statusText.setText("Music Stopped");
                break;
        }
    }
}