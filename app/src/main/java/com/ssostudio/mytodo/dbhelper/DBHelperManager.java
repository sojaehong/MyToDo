package com.ssostudio.mytodo.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import com.ssostudio.mytodo.MainActivity;

public class DBHelperManager extends SQLiteOpenHelper {

    private SQLiteDatabase _db = null;
    private SQLiteStatement _statement = null;
    private final static String DB_NAME = "MyToDoDB.db";
    private final static String DB_FULL_PATH = MainActivity._activity.getApplicationContext().getDatabasePath(DB_NAME).getAbsolutePath();
    private static Context _context;

    public DBHelperManager(@Nullable Context context) {
        super(context, DB_FULL_PATH, null, 0);
        _context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTodoTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        dropTodoTable(db);
        onCreate(db);
    }

    private void createTodoTable(SQLiteDatabase db){
        db.execSQL("CREATE TABLE todo (doto_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "todo_title TEXT," +
                "todo_max_count INTEGER," +
                "todo_now_count INTEGER," +
                "last_update_date INTEGER," +
                "add_date INTEGER," +
                "todo_type INTEGER," +
                "todo_tag TEXT," +
                "todo_note TEXT" +
                ")");
    }

    private void dropTodoTable(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS todo");
    }
}
