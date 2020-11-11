package com.ssostudio.mytodo.dbhelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import androidx.annotation.Nullable;

import com.ssostudio.mytodo.MainActivity;
import com.ssostudio.mytodo.model.ToDoModel;
import com.ssostudio.mytodo.model.ToDoModelList;
import com.ssostudio.mytodo.utility.DateManager;

import java.util.ArrayList;

public class DBHelperManager extends SQLiteOpenHelper {

    private SQLiteDatabase _db = null;
    private SQLiteStatement _statement = null;
    private final static String DB_NAME = "MyToDoDB.db";
    private final static String DB_FULL_PATH = MainActivity._activity.getApplicationContext().getDatabasePath(DB_NAME).getAbsolutePath();
    private static Context _context;

    public DBHelperManager(@Nullable Context context) {
        super(context, DB_FULL_PATH, null, 1);
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
            Log.d("today" , "add Error " + e.getMessage());
        }
    }

    public ArrayList<ToDoModel> onTodayToDoSelect(){

        ArrayList<ToDoModel> list = new ArrayList<>();

        try{

            _db = getReadableDatabase();

            long todayStart = DateManager.dayStartTimestamp(DateManager.getTimestamp());
            long todayEnd = DateManager.dayEndTimestamp(DateManager.getTimestamp());

            String sql = "SELECT * FROM todo WHERE todo_type = 0 AND add_date BETWEEN " + todayStart +" AND " + todayEnd ;

            Cursor cursor = _db.rawQuery(sql, null);

            while (cursor.moveToNext()) {
                ToDoModel toDoModel = new ToDoModel();
                toDoModel.setTodo_id(cursor.getLong(0));
                toDoModel.setTodo_title(cursor.getString(1));
                toDoModel.setTodo_max_count(cursor.getLong(2));
                toDoModel.setTodo_now_count(cursor.getLong(3));
                toDoModel.setLast_update_date(cursor.getLong(4));
                toDoModel.setAdd_date(cursor.getLong(5));
                toDoModel.setTodo_type(cursor.getInt(6));
                toDoModel.setTodo_tag(cursor.getString(7));
                toDoModel.setTodo_note(cursor.getString(8));
                list.add(toDoModel);
            }

            _db.close();

            return list;

        }catch (Exception e){
            Log.d("today" , "select Error");
        }

        return list;
    }
}
