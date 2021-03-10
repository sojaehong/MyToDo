package com.ssostudio.mytodo.todo;

import com.ssostudio.mytodo.model.ToDoModel;
import com.ssostudio.mytodo.model.ToDoModelList;

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

    public boolean[] decoratorChecks(long timestamp){
        ArrayList<ToDoModel> list = ToDoModelList.allToDoModels;

        boolean[] checks = {false, false};

        for (ToDoModel todo:
                list) {
            if (todo.getStart_date() <= timestamp && todo.getDeadline_date() >= timestamp){
                checks[0] = true;
                if (todo.getTodo_now_count() < todo.getTodo_max_count()){
                    return checks;
                }
            }
        }

        checks[1] = true;

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
