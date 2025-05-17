package tsipora.rakhovskaya.application2025;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Looper;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;

public class HelperDB extends SQLiteOpenHelper {
    public static final String DB_NAME = "info.db";
    public static final String TBL_ENTRIES = "entries";
    public static final String ENTRY_ID = "id";
    public static final String ENTRY_DATE = "date";
    public static final String ENTRY_MOOD = "mood";
    public static final String ENTRY_ENTRY = "entry";

    public HelperDB(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TBL_ENTRIES + "(" + ENTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + ENTRY_DATE + " TEXT," + ENTRY_MOOD + " TEXT," + ENTRY_ENTRY + " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    @SuppressLint("RestrictedApi")
    public void addEntryAsync(DiaryEntry de, View view, Context context) {
        new Thread(() -> {
            synchronized (this) {
                addEntry(de); // Insert into the database in the background
            }

            // Switch back to the main UI thread to show the Snackbar
            new Handler(Looper.getMainLooper()).post(() -> {
                // Create the Snackbar and make it swipeable
                Snackbar snackbar = Snackbar.make(view, "Entry added", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", v -> {
                            new Thread(() -> {
                                deleteEntry(de.getId());
                                new Handler(Looper.getMainLooper()).post(() -> {
                                    Toast.makeText(context, "Entry deleted", Toast.LENGTH_SHORT).show();
                                });
                            }).start();
                        })
                        .setActionTextColor(Color.WHITE);



                // Show the Snackbar
                snackbar.show();
            });
        }).start();
    }

    public synchronized void addEntry(DiaryEntry de) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ENTRY_DATE, de.getDate());
        cv.put(ENTRY_MOOD, de.getMood());
        cv.put(ENTRY_ENTRY, de.getEntry());

        long newRowId = db.insert(TBL_ENTRIES, null, cv); // The auto-generated ID is returned here

        de.setId(String.valueOf(newRowId)); // Set the id to the generated value

        // No need to close the db immediately, ensure it's closed properly after all operations
        db.close();
    }

    public synchronized void deleteEntry(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_ENTRIES, ENTRY_ID + "=?", new String[]{id});
        db.close(); // Close the database after operations are complete
    }
    public ArrayList<DiaryEntry> getEntries(String mood) {
        ArrayList<DiaryEntry> entries = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TBL_ENTRIES + " WHERE " + ENTRY_MOOD + " = ?";
        String[] selectionArgs = {mood};
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                DiaryEntry de = new DiaryEntry();
                de.setId(cursor.getString(cursor.getColumnIndexOrThrow(ENTRY_ID)));
                de.setDate(cursor.getString(cursor.getColumnIndexOrThrow(ENTRY_DATE)));
                de.setMood(mood);
                de.setEntry(cursor.getString(cursor.getColumnIndexOrThrow(ENTRY_ENTRY)));
                entries.add(de);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return entries;
    }
    public ArrayList<DiaryEntry> getAllEntries() {
        ArrayList<DiaryEntry> entries = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TBL_ENTRIES ;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()){
                DiaryEntry de = new DiaryEntry();
                de.setId(cursor.getString(cursor.getColumnIndexOrThrow(ENTRY_ID)));
                de.setDate(cursor.getString(cursor.getColumnIndexOrThrow(ENTRY_DATE)));
                de.setMood(cursor.getString(cursor.getColumnIndexOrThrow(ENTRY_MOOD)));
                de.setEntry(cursor.getString(cursor.getColumnIndexOrThrow(ENTRY_ENTRY)));
                entries.add(de);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return entries;
    }

}
