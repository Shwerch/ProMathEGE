package com.pro.math.EGE;

import android.content.Context;
import android.media.MediaPlayer;

public class Music {
    private static MediaPlayer mediaPlayer;
    private static boolean playing = false;
    public static void Play(Context context,int resID) {
        if (playing)
            Stop();
        mediaPlayer = MediaPlayer.create(context,resID);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        playing = true;
    }
    public static void Stop() {
        if (playing) {
            mediaPlayer.stop();
            mediaPlayer.release();
            playing = false;
        }
    }
}
