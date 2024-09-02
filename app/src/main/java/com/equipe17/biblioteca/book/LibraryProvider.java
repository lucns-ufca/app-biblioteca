package com.equipe17.biblioteca.book;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LibraryProvider {

    private static LibraryProvider instance;

    public static LibraryProvider getInstance(Context context) {
        if (instance == null) {
            synchronized (LibraryProvider.class) {
                instance = new LibraryProvider(context);
            }
        }
        return instance;
    }

    private Context context;
    private Map<String, Library> libraries;

    private LibraryProvider(Context context) {
        this.context = context;
        libraries = new HashMap<>();
    }

    public void readLibrary(String libraryName, OnLibraryAvailableListener onLibraryAvailableListener) {
        if (libraries.containsKey(libraryName)) {
            onLibraryAvailableListener.onAvailable(libraries.get(libraryName));
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Book> books = new LinkedList<>();
                AssetManager assetManager = context.getAssets();
                try (InputStream inputStream = assetManager.open(libraryName + "/books.json")) {
                    byte[] bytes = new byte[inputStream.available()];
                    int read = inputStream.read(bytes);
                    String jsonString = new String(bytes, 0, read);
                    JSONArray jsonArray = new JSONArray(jsonString);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Book book = new Book();
                        book.title = jsonObject.getString("title");
                        book.description = jsonObject.getString("description");
                        book.release = stringToDate(jsonObject.getString("release_date_time"));
                        book.cover = decodeBitmapAssets(libraryName + "/covers/" + jsonObject.getString("cover"));
                        books.add(book);
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                Library library = new Library(libraryName, books);
                libraries.put(libraryName, library);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        onLibraryAvailableListener.onAvailable(library);
                    }
                });
            }
        }).start();
    }

    private Bitmap decodeBitmapAssets(String path) {
        AssetManager assetManager = context.getAssets();
        try (InputStream inputStream = assetManager.open(path)) {
            return  BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String dateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        return dateFormat.format(date);
    }

    private Date stringToDate(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        try {
            return format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
