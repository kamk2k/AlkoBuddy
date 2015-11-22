package com.kamk2k.alkobuddy.presenter.utils;

import android.app.Activity;
import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.inject.Inject;

/**
 * Created by PC on 2015-02-28.
 */
public class StorageControler {

    Context mContext;

    Thread worker;
    WriteObjectToFile writer;

    @Inject
    public StorageControler(Context context) {
        mContext = context;

        // Construct a writer to hold and save the data
        writer = new WriteObjectToFile();

        // Construct a worker thread to handle the writer
        worker = new Thread(writer);

    }


    public void saveObjectData(Object object, String key) {

        if (object == null) {
            return;
        } else {

            writer.setParams(new WriteParams(object, key));
            worker.run();

        }

    }


    public Object readObjectData(String key) {

        Object returnData = (Object) readObjectFromFile(key);

        return returnData;

    }


    public void clearObjectData(String key) {

        writer.setParams(new WriteParams(null, key));
        worker.run();

    }

    private class WriteObjectToFile implements Runnable {

        WriteParams params;

        public void setParams(WriteParams params) {
            this.params = params;
        }

        public void run() {
            writeObjectToFile(params.getObject(), params.getFilename());
        }

        private boolean writeObjectToFile(Object object, String filename) {

            boolean success = true;

            ObjectOutputStream objectOut = null;
            try {

                FileOutputStream fileOut = mContext.openFileOutput(filename, Activity.MODE_PRIVATE);
                objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(object);
                fileOut.getFD().sync();

            } catch (IOException e) {
                success = false;
                e.printStackTrace();
            } finally {
                if (objectOut != null) {
                    try {
                        objectOut.close();
                    } catch (IOException e) {
                        // do nothing
                    }

                }
            }

            return success;
        }

    }


    private Object readObjectFromFile(String filename) {

        ObjectInputStream objectIn = null;
        Object object = null;
        try {

            FileInputStream fileIn = mContext.getApplicationContext().openFileInput(filename);
            objectIn = new ObjectInputStream(fileIn);
            object = objectIn.readObject();

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (objectIn != null) {
                try {
                    objectIn.close();
                } catch (IOException e) {

                }
            }
        }

        return object;
    }


    private static class WriteParams {

        Object object;
        String filename;

        public WriteParams(Object object, String filename) {
            super();
            this.object = object;
            this.filename = filename;
        }

        public Object getObject() {
            return object;
        }

        public String getFilename() {
            return filename;
        }

    }

}