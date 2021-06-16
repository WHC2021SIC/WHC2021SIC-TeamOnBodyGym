package com.adildsw.onBodyJukebox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.Slider;

import org.jetbrains.annotations.NotNull;

public class SettingsActivity extends AppCompatActivity {

    private String PORT = "11996";

    private Utils utils;

    private TextView ipAddressTV;
    private Slider musicSlider;
    private Slider grainSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ipAddressTV = findViewById(R.id.ipAddress);
        musicSlider = findViewById(R.id.musicSlider);
        grainSlider = findViewById(R.id.grainSlider);

        utils = new Utils();
        utils.setNotificationBarColor(getWindow(), "#080808");

        String ip = utils.getIPAddress(getApplicationContext());
        ipAddressTV.setText(ip);
        checkConnection(ip);

        musicSlider.setValue(utils.getVolume("music_volume", getApplicationContext()));
        musicSlider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull @NotNull Slider slider) {
                // Do Nothing
            }

            @Override
            public void onStopTrackingTouch(@NonNull @NotNull Slider slider) {
                setMusicVolume(slider.getValue());
            }
        });

        grainSlider.setValue(utils.getVolume("grain_volume", getApplicationContext()));
        grainSlider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull @NotNull Slider slider) {
                // Do Nothing
            }

            @Override
            public void onStopTrackingTouch(@NonNull @NotNull Slider slider) {
                setGrainVolume(slider.getValue());
            }
        });

    }

    public void openDeveloperConsole(View view) {
        startActivity(new Intent(getApplicationContext(), DeveloperConsoleActivity.class));
    }

    public void openJukebox(View view) {
        startActivity(new Intent(getApplicationContext(), JukeboxActivity.class));
    }

    public void setConnection(View view) {
        setConnection(ipAddressTV.getText().toString());
    }

    public void setConnection(String ip) {
        ip = utils.validateIP(ip);
        utils.setIPAddress(ip, getApplicationContext());
        checkConnection(ip);
        Toast.makeText(this, "IP set to " + ip, Toast.LENGTH_SHORT).show();
    }

    public void setMusicVolume(Float volume) {
        utils.setVolume(volume, "music_volume", getApplicationContext());

        String uri = utils.getIPAddress(getApplicationContext());
        uri = uri + "/set_music_volume?volume=" + volume;

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

    public void setGrainVolume(Float volume) {
        utils.setVolume(volume, "grain_volume", getApplicationContext());

        String uri = utils.getIPAddress(getApplicationContext());
        uri = uri + "/set_grain_volume_multiplier?volume=" + volume;

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
        String uri = utils.validateIP(ip);
        setStatus(StatusMode.CONNECTING);
        utils.getRequest(uri, getApplicationContext(), new HttpRequestListener() {
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

            case MUSIC_STOPPED:
                statusBar.setBackgroundColor(Color.parseColor("#FF851B"));
                statusText.setText("Music Stopped");
                break;
        }
    }
}