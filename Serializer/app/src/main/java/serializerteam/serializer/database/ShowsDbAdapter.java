package serializerteam.serializer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import serializerteam.serializer.model.SettingsEntity;
import serializerteam.serializer.model.ShowEntity;

public class ShowsDbAdapter {
    private SQLiteDatabase db;
    private Context context;
    private DbHelper dbHelper;

    public ShowsDbAdapter(Context context) {
        this.context = context;
    }

    public ShowsDbAdapter getDbContext() {
        dbHelper = new DbHelper(context, DbContract.DB_SHOWS, null, DbContract.DB_VERSION);
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            db = dbHelper.getReadableDatabase();
        }

        return this;
    }

    public boolean deleteFavourites() {
        return db.delete(DbContract.Entries.TABLE_NAME, null, null) > 0;
    }

    public int addShowToFavourites(String name, int showId) {
        ContentValues insertedItem = new ContentValues();
        insertedItem.put(DbContract.Entries.KEY_NAME, name);
        insertedItem.put(DbContract.Entries.KEY_SHOW_ID, showId);
        return (int) db.insert(DbContract.Entries.TABLE_NAME, null, insertedItem);
    }

    public List<ShowEntity> getFavourites() {
        String[] columns = {DbContract.Entries._ID,
                DbContract.Entries.KEY_NAME,
                DbContract.Entries.KEY_SHOW_ID};
        Cursor cursor = db.query(DbContract.Entries.TABLE_NAME, columns, null, null, null, null, null);
        List<ShowEntity> showsList = new ArrayList<>();
        while(cursor != null && cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DbContract.Entries._ID));
            int showId = cursor.getInt(cursor.getColumnIndexOrThrow(DbContract.Entries.KEY_SHOW_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DbContract.Entries.KEY_NAME));
            showsList.add(new ShowEntity(id,name,showId));
        }
        if(cursor != null) {
            cursor.close();
        }
        return showsList;
    }

    public boolean deleteFavourite(int showId) {
        String where = DbContract.Entries.KEY_SHOW_ID + "=" + showId;
        return db.delete(DbContract.Entries.TABLE_NAME, where, null) > 0;
    }

    public int saveSettings(boolean areNotificationsOn, int time) {
        db.delete(DbContract.Settings.TABLE_NAME, null, null);

        ContentValues insertedItem = new ContentValues();
        insertedItem.put(DbContract.Settings.KEY_NOTIFICATIONS_ON, areNotificationsOn);
        insertedItem.put(DbContract.Settings.KEY_TIME, time);

        return (int)db.insert(DbContract.Settings.TABLE_NAME, null, insertedItem);
    }

    public SettingsEntity getSettings() {
        String[] columns = {DbContract.Settings._ID,
                DbContract.Settings.KEY_NOTIFICATIONS_ON,
                DbContract.Settings.KEY_TIME};
        Cursor cursor = db.query(DbContract.Settings.TABLE_NAME, columns, null, null, null, null, null);
        SettingsEntity settingsEntity = new SettingsEntity(-1, true, 60);


        if(cursor != null && cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DbContract.Settings._ID));
            boolean areNotificationsOn = cursor.getInt(cursor.getColumnIndexOrThrow(DbContract.Settings.KEY_NOTIFICATIONS_ON)) > 0;
            int time = cursor.getInt(cursor.getColumnIndexOrThrow(DbContract.Settings.KEY_TIME));
            settingsEntity = new SettingsEntity(id, areNotificationsOn, time);
            cursor.close();
        }

        return settingsEntity;
    }

    public void closeDbContext() {
        dbHelper.close();
    }
}
