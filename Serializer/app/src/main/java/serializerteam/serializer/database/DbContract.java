package serializerteam.serializer.database;


import android.provider.BaseColumns;

public class DbContract {
    private DbContract() {

    }

    public static final String DB_SHOWS = "shows.db";
    public static final int DB_VERSION = 1;

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Entries.TABLE_NAME + " (" +
                    Entries._ID + " INTEGER PRIMARY KEY," +
                    Entries.KEY_NAME + " TEXT," +
                    Entries.KEY_SHOW_ID + ")";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Entries.TABLE_NAME;

    public static class Entries implements BaseColumns {
        public static final String TABLE_NAME = "shows";
        public static final String KEY_NAME = "name";
        public static final String KEY_SHOW_ID = "showId";
    }
}
