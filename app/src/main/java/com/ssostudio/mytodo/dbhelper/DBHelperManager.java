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
        db.execSQL("CREATE TABLE todo (todo_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "todo_title TEXT," +
                "todo_max_count INTEGER," +
                "todo_now_count INTEGER," +
                "last_update_date INTEGER," +
                "add_date INTEGER," +
                "start_date INTEGER," +
                "deadline_date INTEGER," +
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
                    "last_update_date, add_date, start_date, deadline_date, todo_type, todo_tag" +
                    ") VALUES (?,?,?,?,?,?,?,?,?)";

            _statement = _db.compileStatement(sql);

            _statement.bindString(1, toDoModel.getTodo_title());
            _statement.bindLong(2, toDoModel.getTodo_max_count());
            _statement.bindLong(3, 0);
            _statement.bindLong(4, toDoModel.getLast_update_date());
            _statement.bindLong(5, toDoModel.getAdd_date());
            _statement.bindLong(6, toDoModel.getStart_date());
            _statement.bindLong(7, toDoModel.getDeadline_date());
            _statement.bindLong(8, toDoModel.getTodo_type());
            _statement.bindString(9, toDoModel.getTodo_tag());

            _statement.execute();

            _statement.close();
            _db.close();

        } catch (Exception e) {
            Log.d("databaseError", "add error " + e.getMessage());
        }
    }

    public ArrayList<ToDoModel> onTodayToDoSelect() {

        ArrayList<ToDoModel> list = new ArrayList<>();

        try {

            _db = getReadableDatabase();

            long todayStart = DateManager.dayStartTimestamp(DateManager.getTimestamp());
            long todayEnd = DateManager.dayEndTimestamp(DateManager.getTimestamp());

            String sql = "SELECT * FROM todo WHERE todo_type = 0 AND start_date BETWEEN " + todayStart + " AND " + todayEnd;

            Cursor cursor = _db.rawQuery(sql, null);

            while (cursor.moveToNext()) {
                ToDoModel toDoModel = new ToDoModel();
                toDoModel.setTodo_id(cursor.getLong(0));
                toDoModel.setTodo_title(cursor.getString(1));
                toDoModel.setTodo_max_count(cursor.getLong(2));
                toDoModel.setTodo_now_count(cursor.getLong(3));
                toDoModel.setLast_update_date(cursor.getLong(4));
                toDoModel.setAdd_date(cursor.getLong(5));
                toDoModel.setStart_date(cursor.getLong(6));
                toDoModel.setDeadline_date(cursor.getLong(7));
                toDoModel.setTodo_type(cursor.getInt(8));
                toDoModel.setTodo_tag(cursor.getString(9));
                toDoModel.setTodo_note(cursor.getString(10));
                list.add(toDoModel);
            }

            _db.close();

            return list;

        } catch (Exception e) {
            Log.d("databaseError", "today select error");
        }
        return list;
    }

    public ArrayList<ToDoModel> onToDoSelect(int type, long selectDate) {

        ArrayList<ToDoModel> list = new ArrayList<>();

        try {

            _db = getReadableDatabase();

            String sql = "SELECT * FROM todo WHERE todo_type = " + type + " AND start_date <= "
                    + selectDate + "  AND deadline_date >= " + selectDate;

            Cursor cursor = _db.rawQuery(sql, null);

            while (cursor.moveToNext()) {
                ToDoModel toDoModel = new ToDoModel();
                toDoModel.setTodo_id(cursor.getLong(0));
                toDoModel.setTodo_title(cursor.getString(1));
                toDoModel.setTodo_max_count(cursor.getLong(2));
                toDoModel.setTodo_now_count(cursor.getLong(3));
                toDoModel.setLast_update_date(cursor.getLong(4));
                toDoModel.setAdd_date(cursor.getLong(5));
                toDoModel.setStart_date(cursor.getLong(6));
                toDoModel.setDeadline_date(cursor.getLong(7));
                toDoModel.setTodo_type(cursor.getInt(8));
                toDoModel.setTodo_tag(cursor.getString(9));
                toDoModel.setTodo_note(cursor.getString(10));
                list.add(toDoModel);
            }

            _db.close();

            return list;

        } catch (Exception e) {
            Log.d("databaseError", "todo select error");
        }
        return list;
    }

    public ArrayList<ToDoModel> onIncompleteToDoSelect() {

        ArrayList<ToDoModel> list = new ArrayList<>();

        try {

            _db = getReadableDatabase();

            String sql = "SELECT * FROM todo WHERE todo_type = 0 AND todo_max_count > todo_now_count";

            Cursor cursor = _db.rawQuery(sql, null);

            while (cursor.moveToNext()) {
                ToDoModel toDoModel = new ToDoModel();
                toDoModel.setTodo_id(cursor.getLong(0));
                toDoModel.setTodo_title(cursor.getString(1));
                toDoModel.setTodo_max_count(cursor.getLong(2));
                toDoModel.setTodo_now_count(cursor.getLong(3));
                toDoModel.setLast_update_date(cursor.getLong(4));
                toDoModel.setAdd_date(cursor.getLong(5));
                toDoModel.setStart_date(cursor.getLong(6));
                toDoModel.setDeadline_date(cursor.getLong(7));
                toDoModel.setTodo_type(cursor.getInt(8));
                toDoModel.setTodo_tag(cursor.getString(9));
                toDoModel.setTodo_note(cursor.getString(10));
                list.add(toDoModel);
            }

            _db.close();

            return list;

        } catch (Exception e) {
            Log.d("databaseError", "todo select error");
        }
        return list;
    }

    public ArrayList<ToDoModel> onToDoAllSelect() {

        ArrayList<ToDoModel> list = new ArrayList<>();

        try {

            _db = getReadableDatabase();

            String sql = "SELECT * FROM todo WHERE todo_type = 0 ";

            Cursor cursor = _db.rawQuery(sql, null);

            while (cursor.moveToNext()) {
                ToDoModel toDoModel = new ToDoModel();
                toDoModel.setTodo_id(cursor.getLong(0));
                toDoModel.setTodo_max_count(cursor.getLong(2));
                toDoModel.setTodo_now_count(cursor.getLong(3));
                toDoModel.setAdd_date(cursor.getLong(5));
                toDoModel.setStart_date(cursor.getLong(6));
                toDoModel.setDeadline_date(cursor.getLong(7));
                toDoModel.setTodo_type(cursor.getInt(8));
                list.add(toDoModel);
            }

            _db.close();

            return list;

        } catch (Exception e) {
            Log.d("databaseError", "todo select error");
        }
        return list;
    }

    public int onToDoCount(long selectDate) {

        int size = 0;

        try {

            _db = getReadableDatabase();

            String sql = "SELECT * FROM todo WHERE todo_type = 0 AND start_date <= "
                    + selectDate + "  AND deadline_date >= " + selectDate;

            size = _db.rawQuery(sql, null).getCount();

            _db.close();

            return size;

        } catch (Exception e) {
            Log.d("databaseError", "todo select error");
        }
        return size;
    }

    public void onToDoUpdate(ToDoModel toDoModel){
        try {
            String sql = "";

            _db = getWritableDatabase();

            sql = "UPDATE todo SET todo_title = ?, todo_max_count = ?, start_date = ?, deadline_date = ? WHERE todo_id = ?";
            _statement = _db.compileStatement(sql);

            _statement.bindString(1, toDoModel.getTodo_title());
            _statement.bindLong(2, toDoModel.getTodo_max_count());
            _statement.bindLong(3, toDoModel.getStart_date());
            _statement.bindLong(4, toDoModel.getDeadline_date());
            _statement.bindLong(5, toDoModel.getTodo_id());

            _statement.execute();
            _statement.close();
            _db.close();

        }catch (Exception e){

        }
    }

    public void onCountUpdate(long id, int type) {
        try {
            String sql = "";
            int count = 0;
            int max = 0;

            _db = getReadableDatabase();

            sql = "SELECT todo_now_count, todo_max_count FROM todo WHERE todo_id = " + id;

            Cursor cursor = _db.rawQuery(sql, null);

            if (cursor.moveToNext()) {
                count = cursor.getInt(0);
                max = cursor.getInt(1);
            }

            _db.close();

            if ((type == 0 && max <= count) || (type == 1 && count <= 0))
                return;

            _db = getWritableDatabase();
            sql = "UPDATE todo SET todo_now_count = ? WHERE todo_id = ?";
            _statement = _db.compileStatement(sql);

            if (type == 0) {
                count = count++;
                _statement.bindLong(1, count + 1);
            } else if (type == 1) {
                count = count--;
                _statement.bindLong(1, count - 1);
            }

            _statement.bindLong(2, id);

            _statement.execute();

            _statement.close();
            _db.close();

        } catch (Exception e) {

        }
    }

    public void onToDoDelete(ToDoModel toDoModel) {
        try {
            long todoId = toDoModel.getTodo_id();

            _db = getWritableDatabase();

            String sql = "DELETE FROM todo WHERE todo_id = ?";

            _statement = _db.compileStatement(sql);
            _statement.bindLong(1,todoId);
            _statement.execute();

            _statement.close();
            _db.close();

        } catch (Exception e) {

        }
    }

}
