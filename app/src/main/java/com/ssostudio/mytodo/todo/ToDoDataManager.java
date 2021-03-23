package com.ssostudio.mytodo.todo;

import com.ssostudio.mytodo.model.ToDoModel;
import com.ssostudio.mytodo.model.ToDoModelList;
import com.ssostudio.mytodo.utility.DateManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ToDoDataManager {

    // to-do 리스트를 완료, 실패로 분류
    public Map<String,ArrayList<ToDoModel>> toDoCompletedSortToMap(ArrayList<ToDoModel> list){
        if (list == null)
            return null;

        Map<String,ArrayList<ToDoModel>> map = new HashMap<>();
        ArrayList<ToDoModel> completedList = new ArrayList<>();
        ArrayList<ToDoModel> failList = new ArrayList<>();

        for (ToDoModel toDoModel: list) {
            if (toDoCompletedCheck(toDoModel)){
                completedList.add(toDoModel);
            }else{
                failList.add(toDoModel);
            }
        }

        Collections.sort(completedList);
        Collections.sort(failList);

        map.put("completedList", completedList);
        map.put("failList", failList);

        return map;
    }

    // 할일 완료 체크 return True : 완료 false: 진행중
    public Boolean toDoCompletedCheck(ToDoModel toDoModel){
        Boolean isCompleted = false;

        if (toDoModel.getTodo_max_count() <= toDoModel.getTodo_now_count()){
            isCompleted = true;
        }else{
            isCompleted = false;
        }

        return isCompleted;
    }

    public boolean decoratorCheck(long timestamp){
        ArrayList<ToDoModel> list = ToDoModelList.allToDoModels;

        for (ToDoModel todo:
                list) {
            if (todo.getStart_date() <= timestamp && todo.getDeadline_date() >= timestamp)
                return true;
        }

        return false;
    }

    boolean isIncomplete = false;
    boolean isToDoZero = true;
    public int decoratorChecks(long timestamp){
        ArrayList<ToDoModel> list = ToDoModelList.allToDoModels;
        long todayStartTimestamp = DateManager.dayStartTimestamp(DateManager.getTimestamp());

        // 1: 완료, 2: 진행중, 3: 실패한 투두가 존재
        int checks = 0;

        for (ToDoModel todo : list) {
            if (todo.getStart_date() <= timestamp && todo.getDeadline_date() >= timestamp){
                isToDoZero = false;
                if (todo.getTodo_now_count() < todo.getTodo_max_count()){
                    isIncomplete = true;
                    if (todo.getDeadline_date() < todayStartTimestamp){
                        return 3;
                    }
                }
            }
        }

        if (isToDoZero)
            return checks;

        if (isIncomplete)
            checks = 2;
        else
            checks = 1;

        return checks;
    }

//    public ArrayList<ToDoModel> toDoCompletedSortToList(ArrayList<ToDoModel> list){
//        if (list == null)
//            return null;
//
//        Map<String,ArrayList<ToDoModel>> map = new HashMap<>();
//        ArrayList<ToDoModel> completedList = new ArrayList<>();
//        ArrayList<ToDoModel> failList = new ArrayList<>();
//
//        for (ToDoModel toDoModel: list) {
//            if (toDoModel.getTodo_max_count() <= toDoModel.getTodo_now_count()){
//                completedList.add(toDoModel);
//            }else{
//                failList.add(toDoModel);
//            }
//        }
//
//        map.put("completedList", completedList);
//        map.put("failList", failList);
//
//        return failList;
//    }

}
