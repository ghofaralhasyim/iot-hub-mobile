package iot.umn.ac.id;

import android.provider.BaseColumns;

public class ButtonDB {
    public static final String DB_NAME = "iot.umn.ac.id.buttonDB.button";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "buttons";

    public class Columns {
        public static final String BUTTON = "button";
        public static final String _ID = BaseColumns._ID;
    }
}
