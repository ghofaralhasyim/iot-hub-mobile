package iot.umn.ac.id;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ButtonDBHelper extends SQLiteOpenHelper {

    public ButtonDBHelper(Context context) {
        super(context, ButtonDB.DB_NAME, null, ButtonDB.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqlDB) {
        String sqlQuery = String.format("CREATE TABLE %s (" +
                                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "%s TEXT)", ButtonDB.TABLE, ButtonDB.Columns.BUTTON);

        Log.d("TaskDBHelper","Query to form table: "+sqlQuery);
        sqlDB.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlDB, int oldVersion, int newVersion) {
        sqlDB.execSQL("DROP TABLE IF EXISTS "+ButtonDB.TABLE);
        onCreate(sqlDB);
    }
}
