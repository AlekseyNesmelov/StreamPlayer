package ru.leksiinesm.playerlib.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import ru.leksiinesm.playerlib.IMediaService;
import ru.leksiinesm.playerlib.notification.NotificationBuilderImpl;
import ru.leksiinesm.playerlib.player.StreamPlayer;
import ru.leksiinesm.playerlib.player.StreamPlayerImpl;
import ru.leksiinesm.storage.data.storage.DataStorageImpl;
import ru.leksiinesm.storage.data.storage.MutableDataStorage;

/**
 * Service for music stream playing
 */
// TODO draft
public class MediaPlayerService extends Service {

    private static final int FOREGROUND_ID = 1222;

    private final MutableDataStorage dataStorage = new DataStorageImpl(this);
    private final NotificationBuilderImpl notificationBuilder = new NotificationBuilderImpl();
    private final IMediaService.Stub binder = new IMediaService.Stub() {

        @Override
        public synchronized void start(String source) {
            started = true;
            player.start(source);
            dataStorage.setPlaying(true);
        }

        @Override
        public synchronized void stop() {
            player.stop();
            started = false;
            dataStorage.setPlaying(false);
            stopForeground(true);
        }

        @Override
        public synchronized boolean isPlaying() {
            return started;
        }
    };

    private StreamPlayer player;
    private boolean started = false;

    @Override
    public void onCreate() {
        super.onCreate();
        player = new StreamPlayerImpl(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(FOREGROUND_ID, notificationBuilder.build(getApplicationContext()));
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.release();
        dataStorage.setPlaying(false);
    }
}