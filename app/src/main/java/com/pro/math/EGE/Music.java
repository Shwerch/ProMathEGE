package com.pro.math.EGE;

import android.content.Context;
import android.media.MediaPlayer;

public class Music {
    private static MediaPlayer mediaPlayer;
    private static boolean Paused = false;
    private static boolean Playing = false;
    private static final boolean Active = false;
    private static final int[] Music = new int[] {};
    public static void Play(Context context) {
        if (Active && !Playing) {
            mediaPlayer = MediaPlayer.create(context,Music[(int)(Math.random()*Music.length)]);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
            Playing = true;
        }
    }
    public static void Pause() {
        if (Active && Playing && !Paused) {
            mediaPlayer.stop();
            Paused = true;
        }
    }
    public static void Continue() {
        if (Active && Playing && Paused) {
            mediaPlayer.start();
            Paused = false;
        }
    }
    public static void Stop() {
        if (Playing && Active) {
            mediaPlayer.stop();
            mediaPlayer.release();
            Playing = false;
        }
    }
}
