package com.nsw.a6vfilm.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by niushuowen on 2016/6/6.
 */
public class BitmapUtils {

    public static void saveBitmapToFile(String bitName, Bitmap mBitmap) {

        File ff = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/6vfilm/");
        if (!ff.exists()) {
            ff.mkdirs();
        }

        String local_file = ff.getAbsolutePath()+"/"+bitName;
        File f = new File(local_file);
        try {
            if (!f.createNewFile()) {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
