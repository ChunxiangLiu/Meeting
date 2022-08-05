package com.ximalife.library.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import java.io.IOException;

public class MediaPlayerUtils {

    public static MediaPlayer mPlayer;
    public static boolean isPause = false;

    public static void playSound(Context context, String filePath, Float rate, MediaPlayer.OnCompletionListener listener) {
        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
        } else {
            mPlayer.reset();
        }
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setOnCompletionListener(listener);
        mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mPlayer.reset();
                return false;
            }
        });
        try {
            mPlayer.reset();
            mPlayer.setDataSource(context, Uri.parse(filePath));
            mPlayer.prepare();
        } catch (Exception e) {
            throw new RuntimeException("读取文件异常：" + e.getMessage());
        }
        setPlaySpeed(rate);
        mPlayer.start();
        isPause = false;
    }


    public static void pause() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
            isPause = true;
        }
    }

    // 继续
    public static void resume() {
        if (mPlayer != null && isPause) {
            mPlayer.start();
            isPause = false;
        }
    }

    public static void release() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }


    public static void setPlaySpeed(float speed) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                if (mPlayer != null) {
                    PlaybackParams params = mPlayer.getPlaybackParams();
                    params.setSpeed(speed);
                    mPlayer.setPlaybackParams(params);
                }
            } catch (Exception e) {
                Log.e("test", "setPlaySpeed: ", e);
            }
        }
    }


}

