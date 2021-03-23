package com.ssostudio.mytodo.dbhelper;

import android.content.Context;

import com.ssostudio.mytodo.ToDoActivity;
import com.ssostudio.mytodo.BucketListActivity;
import com.ssostudio.mytodo.fragment.CalendarFragment;
import com.ssostudio.mytodo.fragment.MonthFragment;
import com.ssostudio.mytodo.fragment.TodayFragment;
import com.ssostudio.mytodo.fragment.YearFragment;
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
        viewRefresh(toDoModel.getTodo_type());
    }

    public void updateTodoDB(ToDoModel toDoModel){
        _db.onToDoUpdate(toDoModel);
        viewRefresh(toDoModel.getTodo_type());
    }

    public void deleteTodoDB(ToDoModel toDoModel){
        _db.onToDoDelete(toDoModel);
        viewRefresh(toDoModel.getTodo_type());
    }

    public void selectBucketList(){
        ArrayList<ToDoModel> list = _db.onToDoSelect(2, 0);
        ToDoModelList.bucketListToDoModels = new ToDoDataManager().toDoCompletedSortToMap(list);
    }

    public void selectYearToDo(int year) {
        ArrayList<ToDoModel> list = _db.onToDoSelect(1, DateManager.yearSelectTimestamp(year));
        ToDoModelList.yearToDoModels = new ToDoDataManager().toDoCompletedSortToMap(list);
    }

    public void selectMonthToDo(int year, int month){
        ArrayList<ToDoModel> list = _db.onToDoSelect(3, DateManager.monthSelectTimestamp(year, month));
        ToDoModelList.monthToDoModels = new ToDoDataManager().toDoCompletedSortToMap(list);
    }

    public void selectTodayToDo() {
        ArrayList<ToDoModel> list = _db.onToDoSelect(0, DateManager.getTimestamp());
        ToDoModelList.todayToDoModels = new ToDoDataManager().toDoCompletedSortToMap(list);
    }

    public void selectToDo(long timestamp) {
        ArrayList<ToDoModel> list = _db.onToDoSelect(0, timestamp);
        ToDoModelList.selectToDoModels = new ToDoDataManager().toDoCompletedSortToMap(list);
    }

    public void selectIncompleteToDo(){
        ArrayList<ToDoModel> list = _db.onIncompleteToDoSelect();
    }

    public void todoAllSelect() {
        ToDoModelList.allToDoModels = _db.onToDoAllSelect();
    }

    public void todoCountUp(long id, int type) {
        _db.onCountUpdate(id, 0);
        viewRefresh(type);
    }

    public void todoCountDown(long id, int type) {
        _db.onCountUpdate(id, 1);
        viewRefresh(type);
    }

    private void viewRefresh(int type) {
        switch (type) {
            case 0:
                new TodayFragment().listVIewRefresh();
                new TodayFragment().setSimpleStatisticsVIew();
                if (ToDoActivity.class == _context.getClass()) {
                    ((ToDoActivity) _context).refresh();
                    ((ToDoActivity) _context).setSimpleStatisticsVIew();
                } else {
                    new DBManager(_context).todoAllSelect();
                    new CalendarFragment().calendarDecoratorsRefresh();
                }
                break;
            case 1:
                new YearFragment().listRefresh();
                new YearFragment().setStatisticsView();
                break;
            case 2:
                ((BucketListActivity) _context).listRefresh();
                ((BucketListActivity) _context).setStatisticsView();
                break;
            case 3:
                new MonthFragment().listRefresh();
                new MonthFragment().setStatisticsView();
                break;
        }
    }

}
