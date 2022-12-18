package id.ac.umn.iot;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {MainData.class, BTData.class},version = 1,exportSchema = false)

public abstract class RoomDB extends RoomDatabase {

    private static RoomDB database;

    private static String DATABASE_NAME="db_iot_hubs";

    public  synchronized static RoomDB getInstance(Context context){
        if(database==null){
            database = Room.databaseBuilder(context,RoomDB.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
    public abstract MainDao mainDao();
    public abstract BTDao btDao();
}
