package com.ssostudio.mytodo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.ssostudio.mytodo.R;
import com.ssostudio.mytodo.dbhelper.DBManager;
import com.ssostudio.mytodo.dialog.ToDoSelectDialog;
import com.ssostudio.mytodo.model.ToDoModel;
import com.ssostudio.mytodo.todo.ToDoDataManager;
import com.ssostudio.mytodo.utility.AppUtility;
import com.ssostudio.mytodo.utility.DateManager;

import java.util.ArrayList;
import java.util.Map;

public class ToDoListVIewAdapter extends BaseAdapter {

    private Context _context;
    private int _size;
    private LayoutInflater inflater;
    private ArrayList<ToDoModel> _list;
    private TextView toDoTitleTextVIew, countTextView, itemDateTextVIew;
    private MaterialButton upImageBtn, downImageBtn;
    private LinearLayout completedLayout, failedLayout;
    private int completedFirst = 0;

    public ToDoListVIewAdapter(Context context, Map<String, ArrayList<ToDoModel>> listMap) {
        _context = context;
        inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        _list = new ArrayList<>();
        ArrayList<ToDoModel> failList = listMap.get("failList");
        ArrayList<ToDoModel> completedList = listMap.get("completedList");
        _list.addAll(failList);
        _list.addAll(completedList);
        completedFirst = failList.size();

        _size = _list.size();
    }

    @Override
    public int getCount() {
        return _size;
    }

    @Override
    public Object getItem(int i) {
        return _list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (new ToDoDataManager().toDoCompletedCheck(_list.get(i)) && i == completedFirst) {
            view = inflater.inflate(R.layout.to_do_title_item, viewGroup, false);
        } else {
            view = inflater.inflate(R.layout.to_do_item, viewGroup, false);
        }

        final ToDoModel toDoModel = _list.get(i);

        toDoTitleTextVIew = view.findViewById(R.id.to_do_title_text_view);
        toDoTitleTextVIew.setText(toDoModel.getTodo_title());

        final long nowCount = toDoModel.getTodo_now_count();
        final long maxCount = toDoModel.getTodo_max_count();
        String countText = nowCount + " / " + maxCount;

        countTextView = view.findViewById(R.id.count_text);
        countTextView.setText(countText);

        if (toDoModel.getTodo_type() == 0){
            String dateText = " ~ " + DateManager.dateTimeZoneFormat(toDoModel.getDeadline_date());
            itemDateTextVIew = view.findViewById(R.id.item_date_text);
            itemDateTextVIew.setText(dateText);
        }

        upImageBtn = view.findViewById(R.id.count_up_button);
        upImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nowCount < maxCount) {
                    onClickVibrator();
                    new DBManager(_context).todoCountUp(toDoModel.getTodo_id(), toDoModel.getTodo_type());
                }
            }
        });

        downImageBtn = view.findViewById(R.id.count_down_button);
        downImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nowCount > 0) {
                    onClickVibrator();
                    new DBManager(_context).todoCountDown(toDoModel.getTodo_id(), toDoModel.getTodo_type());
                }
            }
        });

        completedLayout = view.findViewById(R.id.completed_ll);
        failedLayout = view.findViewById(R.id.failed_ll);

//        view.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });

        completedToDo(nowCount, maxCount, toDoModel);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ToDoSelectDialog(_context).onShowDialog(toDoModel);
            }
        });

        return view;
    }

    private void onClickVibrator() {
        AppUtility.onVibrator(_context, 13);
    }

    // 완료된 할일 체크 후 완료된 할일 표시
    private void completedToDo(long nowCount, long maxCount ,ToDoModel toDoModel){
        failedLayout.setVisibility(View.INVISIBLE);

        if (nowCount >= maxCount){
            upImageBtn.setVisibility(View.INVISIBLE);
            completedLayout.setVisibility(View.VISIBLE);
        }else{
            upImageBtn.setVisibility(View.VISIBLE);
            completedLayout.setVisibility(View.INVISIBLE);
            failedToDo(toDoModel);
        }
    }

    // 오늘이 지난 할일중 완료되지 않은 할일 체크 후 표시
    private void failedToDo(ToDoModel toDoModel){
        // 0:일간, 1:연간, 2:버킷리스트, 3:월간
        int type = toDoModel.getTodo_type();
        long deadline = toDoModel.getDeadline_date();
        int[] thisDates = null;
        int thisYear = 0;
        int thisMonth = 0;
        int[] deadlineDates = null;
        int deadlineYear = 0;
        int deadlineMonth = 0;

        switch (type){
            case 0:
                long todayStartTimestamp = DateManager.dayStartTimestamp(DateManager.getTimestamp());

                if (deadline < todayStartTimestamp)
                    failedLayout.setVisibility(View.VISIBLE);

                break;
            case 1:
                thisDates = DateManager.timestampToIntArray(DateManager.getTimestamp());
                thisYear = thisDates[0];
                deadlineDates = DateManager.timestampToIntArray(deadline);
                deadlineYear = deadlineDates[0];

                if (thisYear > deadlineYear)
                    failedLayout.setVisibility(View.VISIBLE);

                break;
            case 3:
                thisDates = DateManager.timestampToIntArray(DateManager.getTimestamp());
                thisYear = thisDates[0];
                thisMonth = thisDates[1];
                deadlineDates = DateManager.timestampToIntArray(deadline);
                deadlineYear = deadlineDates[0];
                deadlineMonth = deadlineDates[1];

                if (thisYear > deadlineYear || (thisYear == deadlineYear && thisMonth > deadlineMonth))
                    failedLayout.setVisibility(View.VISIBLE);

                break;
        }
    }

}
