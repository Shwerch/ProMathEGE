package com.pro.math.EGE;

import android.content.Context;
import android.media.MediaPlayer;

public class Music {
    private static MediaPlayer mediaPlayer;
    private static boolean Playing = false;
    private static final boolean Active = true;
    private static final int[] Music = new int[] {R.raw.v};
    public static void Play(Context context) {
        if (!Active)
            return;
        else if (Playing)
            Stop();
        mediaPlayer = MediaPlayer.create(context,Music[(int)(Math.random()*Music.length)]);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        Playing = true;
    }
    public static void Stop() {
        if (Playing && Active) {
            mediaPlayer.stop();
            mediaPlayer.release();
            Playing = false;
        }
    }
}
