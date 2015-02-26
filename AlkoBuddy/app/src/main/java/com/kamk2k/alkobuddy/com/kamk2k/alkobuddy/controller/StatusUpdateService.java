package com.kamk2k.alkobuddy.com.kamk2k.alkobuddy.controller;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.kamk2k.alkobuddy.Constants;

public class StatusUpdateService extends Service {

    private static final int UPDATE_PERIOD = 10000;

    private UpdateThread mUpdateThread;

    private class UpdateThread extends Thread {
        private final String TAG = UpdateThread.class.getSimpleName();

        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    sendUpdate();
                    Thread.sleep(UPDATE_PERIOD);
                } catch (InterruptedException e) {
                    Log.d(TAG, "UpdateThread interrupted");
                }
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mUpdateThread = new UpdateThread();
        mUpdateThread.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if(mUpdateThread != null)   mUpdateThread.interrupt();
        super.onDestroy();
    }

    private void sendUpdate() {
        Intent intent = new Intent();
        intent.setAction(Constants.UPDATE_INTENT);
        sendBroadcast(intent);
    }
}
