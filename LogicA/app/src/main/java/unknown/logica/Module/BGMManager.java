package unknown.logica.Module;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

import static unknown.logica.Settings.MUSIC_ENABLE_VALUE;
import static unknown.logica.Settings.SAVED_VALUES;

/**
 *Created by Tim on 10/27/16.
 */

public class BGMManager {
    private static BGMManager instance;
    private static MediaPlayer player;
    private static int currentMusic;

    private BGMManager(Context context, int src){
        player = MediaPlayer.create(context, src);
        currentMusic = src;
        player.start();
        player.setLooping(true);
        player.pause();
    }

    public static BGMManager getInstance(Context context, int src){
        if(instance == null){
            instance = new BGMManager(context, src);
        }

        if(currentMusic != src){
            player.stop();
            player.reset();
            instance = new BGMManager(context, src);
        }

        return instance;
    }

    public void startMusic(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SAVED_VALUES, Activity.MODE_PRIVATE);

        if (sharedPreferences.getBoolean(MUSIC_ENABLE_VALUE, true)) {
            player.start();
        }
        else {
            player.pause();
        }
    }

    public void pauseMusic() {
        player.pause();
    }

    public boolean isSameMusic(int music){
        return music == currentMusic;
    }

}
