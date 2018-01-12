package com.kuruvatech.santoshkenchaamba;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.splunk.mint.Mint;

public class YouTubePlayerAcivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    public static final String API_KEY = "AIzaSyBRLKO5KlEEgFjVgf4M-lZzeGXW94m9w3U";

    //https://www.youtube.com/watch?v=<VIDEO_ID>
    public static final String VIDEO_ID = "nCiVjcjDmRk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mint.initAndStartSession(this, "49d903c2");
        Mint.enableLogging(true);
        Mint.setLogging(100, "*:W");
        // attaching layout xml
        setContentView(R.layout.activity_basic_player);

        // Initializing YouTube player view
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player_view);
        youTubePlayerView.initialize(API_KEY, this);

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        Toast.makeText(this, "Failed to initialize.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if(null== player) return;

        // Start buffering
        if (!wasRestored) {
            player.cueVideo(VIDEO_ID);
        }

        // Add listeners to YouTubePlayer instance
        player.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
            @Override
            public void onAdStarted() { }
            @Override
            public void onError(YouTubePlayer.ErrorReason arg0) { }
            @Override
            public void onLoaded(String arg0) { }
            @Override
            public void onLoading() { }
            @Override
            public void onVideoEnded() { }
            @Override
            public void onVideoStarted() { }
        });


        player.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
            @Override
            public void onBuffering(boolean arg0) { }
            @Override
            public void onPaused() { }
            @Override
            public void onPlaying() { }
            @Override
            public void onSeekTo(int arg0) { }
            @Override
            public void onStopped() { }
        });
    }

}