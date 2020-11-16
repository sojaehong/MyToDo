package com.ssostudio.mytodo.dbhelper;

import android.content.Context;
import android.util.Log;

import com.ssostudio.mytodo.model.ToDoModel;
import com.ssostudio.mytodo.model.ToDoModelList;
import com.ssostudio.mytodo.todo.ToDoDataManager;
import com.ssostudio.mytodo.utility.DateManager;

import java.util.ArrayList;

public class DBManager {
    DBHelperManager _db;
    Context _context;

    public DBManager(Context context){
        _context = context;

        _db = new DBHelperManager(_context);
    }

    public void addTodoDB(ToDoModel toDoModel){
        toDoModel.setAdd_date(DateManager.getTimestamp());
        toDoModel.setLast_update_date(DateManager.getTimestamp());
        toDoModel.setTodo_tag("");
        _db.onToDoAdd(toDoModel);
    }

    public void selectTodayTodo(){
        ArrayList<ToDoModel> list = _db.onTodayToDoSelect();
        ToDoModelList.todayToDoModels = new ToDoDataManager().toDoCompletedSort(list);

//        for (ToDoModel toDoModel : ToDoModelList.todayToDoModels) {
//                Log.d("today", ""
//                        + toDoModel.getTodo_title() + " :"
//                        + DateManager.dateTimeZoneSimpleFormat(toDoModel.getAdd_date()) + " :"
//                        + toDoModel.getTodo_max_count() + " :"
//                        + toDoModel.getTodo_now_count() + " :"
//                        + toDoModel.getTodo_id() + " :"
//                        + toDoModel.getTodo_type() + " :"
//                );
//        }


    }

}
