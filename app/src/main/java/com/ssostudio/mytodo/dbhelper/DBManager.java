package com.ssostudio.mytodo.dbhelper;

import android.content.Context;
import android.util.Log;

import com.ssostudio.mytodo.ToDoActivity;
import com.ssostudio.mytodo.fragment.CalendarFragment;
import com.ssostudio.mytodo.fragment.TodayFragment;
import com.ssostudio.mytodo.model.ToDoModel;
import com.ssostudio.mytodo.model.ToDoModelList;
import com.ssostudio.mytodo.todo.ToDoDataManager;
import com.ssostudio.mytodo.utility.DateManager;

import java.util.ArrayList;

public class DBManager {
    DBHelperManager _db;
    Context _context;

    public DBManager(Context context) {
        _context = context;

        _db = new DBHelperManager(_context);
    }

    public void addTodoDB(ToDoModel toDoModel) {
        toDoModel.setAdd_date(DateManager.getTimestamp());
        toDoModel.setLast_update_date(DateManager.getTimestamp());
        toDoModel.setTodo_tag("");
        _db.onToDoAdd(toDoModel);

        switch (toDoModel.getTodo_type()) {
            case 0:
                new TodayFragment().listVIewRefresh();
                if (ToDoActivity.class == _context.getClass()) {
                    ((ToDoActivity) _context).refresh();
                } else {
                    new DBManager(_context).todoAllSelect();
                    new CalendarFragment().calendarDecoratorsRefresh();
                }
                break;
        }
    }

    public void selectTodayToDo() {
        ArrayList<ToDoModel> list = _db.onToDoSelect(DateManager.getTimestamp());
        ToDoModelList.todayToDoModels = new ToDoDataManager().toDoCompletedSortToMap(list);
    }

    public void selectToDo(long timestamp) {
        ArrayList<ToDoModel> list = _db.onToDoSelect(timestamp);
        ToDoModelList.selectToDoModels = new ToDoDataManager().toDoCompletedSortToMap(list);
    }

    public void todoAllSelect() {
        ToDoModelList.allToDoModels = _db.onToDoAllSelect();
    }

    public void todoCountUp(long id){
        _db.onCountUpdate(id, 0);
        viewRefresh();
    }

    public void todoCountDown(long id){
        _db.onCountUpdate(id, 1);
        viewRefresh();
    }

    private void viewRefresh(){
        new TodayFragment().listVIewRefresh();
        if (ToDoActivity.class == _context.getClass()) {
            ((ToDoActivity) _context).refresh();
        }
    }

}
