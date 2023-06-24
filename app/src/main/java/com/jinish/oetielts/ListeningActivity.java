package com.jinish.oetielts;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;

public class ListeningActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button playButton;
    private WebView soundCloudWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening);

        soundCloudWebView = findViewById(R.id.soundcloud_webview);
        soundCloudWebView.setWebViewClient(new WebViewClient());
        soundCloudWebView.getSettings().setJavaScriptEnabled(true);
        soundCloudWebView.loadUrl("https://soundcloud.com/ielts-target-band-8/test3part1");

        TextView titleText = findViewById(R.id.title_text);
        titleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click on the SoundCloud title, if needed
            }
        });

        // Initialize the MediaPlayer
        mediaPlayer = new MediaPlayer();

        // Set up the PDF file
        String pdfFile = "listening.pdf";
        // Display the PDF file using a PDF rendering library or WebView
        // Add your code here to handle PDF rendering or WebView display

        // Set up the audio file
        String audioFile = "listening.mp3";

        playButton = findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    stopAudio();
                } else {
                    playAudio(audioFile);
                }
            }
        });
    }

    private void playAudio(String audioFile) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(this, Uri.parse("file:///android_asset/" + audioFile));

            // Check file permissions
            File file = new File(Uri.parse("file:///android_asset/" + audioFile).getPath());
            boolean canRead = file.canRead();
            boolean canWrite = file.canWrite();

            Log.d("File Permissions", "Read permission: " + canRead);
            Log.d("File Permissions", "Write permission: " + canWrite);

            mediaPlayer.prepare();
            mediaPlayer.start();
            playButton.setText("Stop");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopAudio() {
        mediaPlayer.stop();
        playButton.setText("Play");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
    }
}
