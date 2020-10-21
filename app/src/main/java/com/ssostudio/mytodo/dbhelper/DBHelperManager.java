package com.ssostudio.mytodo.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import com.ssostudio.mytodo.MainActivity;
import com.ssostudio.mytodo.model.ToDoModel;

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

    private void createTodoTable(SQLiteDatabase db) {
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

    private void dropTodoTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS todo");
    }

    public void onToDoAdd(ToDoModel toDoModel) {
        try {

            _db = getWritableDatabase();

            String sql = "INSERT INTO todo (todo_title, todo_max_count, todo_now_count, " +
                    "last_update_date, add_date, todo_type, todo_tag" +
                    ") VALUES (?,?,?,?,?,?,?)";

            _statement = _db.compileStatement(sql);

            _statement.bindString(1, toDoModel.getTodo_title());
            _statement.bindLong(2, toDoModel.getTodo_max_count());
            _statement.bindLong(3, 0);
            _statement.bindLong(4, toDoModel.getLast_update_date());
            _statement.bindLong(5, toDoModel.getAdd_date());
            _statement.bindLong(6, toDoModel.getTodo_type());
            _statement.bindString(7, toDoModel.getTodo_tag());

            _statement.execute();

            _statement.close();
            _db.close();

        } catch (Exception e) {

        }
    }
}
