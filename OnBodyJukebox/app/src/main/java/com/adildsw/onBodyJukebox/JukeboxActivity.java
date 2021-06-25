package com.adildsw.onBodyJukebox;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class JukeboxActivity extends AppCompatActivity {

    private String ip;

    private String[] JUKEBOX_LIBRARY = {
            "Jnathym - Dioma",
            "Clarx & Harddope - Castle",
            "Tom Wilson - Be Myself",
            "ROY KNOX - I Wish",
            "Netrum & Halvorsen - Shivers",
            "Custom Music"
    };

    private int nowPlaying = -1;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        utils = new Utils();
        utils.setNotificationBarColor(getWindow(), "#080808");

        ip = utils.getIPAddress(getApplicationContext());
        checkConnection(ip);

        findViewById(R.id.music5).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                uploadCustomMusic();
                return false;
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

    public void openSettings(View view) {
        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
    }

    public void toggleMusic(View view) {
        String musicTag = view.getTag().toString();
        int musicIdx = Integer.parseInt(musicTag.substring(5));
        int musicPlayIconId = getResources().getIdentifier(
                musicTag + "play", "id", getPackageName());
        ImageView musicPlayIcon = findViewById(musicPlayIconId);
        Log.e("ERR", String.valueOf(musicIdx));

        if (nowPlaying == -1) {
            // Playing Music
            boolean res = musicController(musicIdx, true);
            if (res) {
                musicPlayIcon.setAlpha((float) 0.8);
                nowPlaying = musicIdx;
            }
        }
        else if (nowPlaying == musicIdx) {
            // Stopping Music
            boolean res = musicController(musicIdx, false);
            if (res) {
                musicPlayIcon.setAlpha((float) 0.0);
                nowPlaying = -1;
            }
        }
        else {
            // Playing New Music
            boolean res = musicController(musicIdx, true);
            if (res) {
                // Stopping Previous Music
                int currentlyPlayingMusicIconId = getResources().getIdentifier(
                        "music" + nowPlaying + "play", "id", getPackageName());
                ImageView currentlyPlayingMusicIcon = findViewById(currentlyPlayingMusicIconId);
                currentlyPlayingMusicIcon.setAlpha((float) 0.0);

                musicPlayIcon.setAlpha((float) 0.8);
                nowPlaying = musicIdx;
            }
        }
    }

    private boolean musicController(int musicIdx, boolean play) {
        String api = ip;
        if (play) {
            double volume = utils.getVolume("music_volume", getApplicationContext());
            api = api + "/play_music?music_idx=" + musicIdx + "&volume=" + volume;
        }
        else {
            api = api + "/stop_music";
        }

        setStatus(StatusMode.CONNECTING);

        utils.getRequest(api, getApplicationContext(), new HttpRequestListener() {
            @Override
            public void onError(String error) {
                Toast.makeText(JukeboxActivity.this, error, Toast.LENGTH_SHORT).show();
                setStatus(StatusMode.ERROR);
            }

            @Override
            public void onResponse(Object response) {
                if (play) {
                    if (musicIdx != 5) {
                        Toast.makeText(JukeboxActivity.this,
                                "Playing " + JUKEBOX_LIBRARY[musicIdx], Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (response.toString().equals("OK")) {
                            Toast.makeText(JukeboxActivity.this,
                                    "Playing " + JUKEBOX_LIBRARY[musicIdx]
                                            + ". Hold button to change custom music.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(JukeboxActivity.this,
                                    "No custom music loaded. Hold button to load custom music.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    setStatus(StatusMode.CONNECTED);
                }
                else {
                    setStatus(StatusMode.MUSIC_STOPPED);
                }
            }
        });

        return true;
    }

    public void uploadCustomMusic() {
        String api = ip + "/uploader";
        if (!api.startsWith("http://")) {
            api = "http://" + api;
        }

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(api));
        startActivity(browserIntent);
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
