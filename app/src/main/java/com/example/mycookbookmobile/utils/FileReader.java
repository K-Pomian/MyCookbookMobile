package com.example.mycookbookmobile.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileReader {
    private static final String TAG = FileReader.class.getSimpleName();

    private Context context;

    public FileReader(Context context) {
        this.context = context;
    }

    public String getRecipe(String dishName) {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open("recipes/" + dishName.toLowerCase() + ".txt");
        } catch (IOException e) {
            Log.e(TAG, "Could not find the file", e);
        }

        return readFile(inputStream);
    }

    public Drawable getImage(String dishName) {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open("images/" + dishName.toLowerCase() + ".jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Drawable.createFromStream(inputStream, null);
    }

    public String getSqlScript(String filename) {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open("scripts/" + filename.toLowerCase() + ".sql");
        } catch (IOException e) {
            Log.e(TAG, "Could not find the file", e);
        }

        return readFile(inputStream);
    }

    private String readFile(InputStream inputStream) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            reader.close();
            inputStream.close();
            return stringBuilder.toString();
        } catch (IOException e) {
            Log.e(TAG, "Could not read the file", e);
        }
        return null;
    }
}
