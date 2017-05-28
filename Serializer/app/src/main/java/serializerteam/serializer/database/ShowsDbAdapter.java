package serializerteam.serializer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

    public boolean deleteShoppingListItem(int showId) {
        String where = DbContract.Entries.KEY_SHOW_ID + "=" + showId;
        return db.delete(DbContract.Entries.TABLE_NAME, where, null) > 0;
    }

    public List<Integer> getFavouriteShowsIds() {
        List<Integer> showIds = new ArrayList<>();
        String selectQuery = "SELECT " + DbContract.Entries.KEY_SHOW_ID + " FROM " + DbContract.Entries.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                showIds.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }
        return showIds;
    }

    public String getShowName(int id){
        String selectQuery = "SELECT " + DbContract.Entries.KEY_NAME+ " FROM " + DbContract.Entries.TABLE_NAME +" WHERE "+DbContract.Entries.KEY_SHOW_ID +"="+id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.getCount()==0) return "";
        cursor.moveToFirst();

        return cursor.getString(0);
    }

    public void closeDbContext() {
        dbHelper.close();
    }
}
