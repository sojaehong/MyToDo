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

import java.util.ArrayList;
import java.util.Map;

public class ToDoListVIewAdapter extends BaseAdapter {

    private Context _context;
    private int _size;
    private LayoutInflater inflater;
    private ArrayList<ToDoModel> _list;
    private TextView toDoTitleTextVIew, countTextView;
    private MaterialButton upImageBtn, downImageBtn;
    private LinearLayout completedLayout;
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

//        view.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });

        if (nowCount >= maxCount){
            upImageBtn.setVisibility(View.GONE);
            completedLayout.setVisibility(View.VISIBLE);
        }else{
            upImageBtn.setVisibility(View.VISIBLE);
            completedLayout.setVisibility(View.GONE);
        }

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


}
