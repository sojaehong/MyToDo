package com.ssostudio.mytodo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ssostudio.mytodo.R;
import com.ssostudio.mytodo.adapter.ToDoListVIewAdapter;
import com.ssostudio.mytodo.dbhelper.DBManager;
import com.ssostudio.mytodo.dialog.ToDoAddDialog;
import com.ssostudio.mytodo.model.ToDoModelList;
import com.ssostudio.mytodo.utility.DateManager;

public class TodayFragment extends Fragment implements View.OnClickListener {

    private static View view;
    private static Context _context;
    private FloatingActionButton addBtn;
    private ToDoListVIewAdapter adapter;
    private ListView listView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_today, container, false);

        init();

        return view;
    }

    private void init() {
        _context = getContext();

        setAddBtn();

        new DBManager(_context).selectTodayToDo();

        setToDoListVIew();
    }

    private void setToDoListVIew(){
        listView = view.findViewById(R.id.to_do_list);
        adapter = new ToDoListVIewAdapter(getContext(), ToDoModelList.todayToDoModels);
        listView.setAdapter(adapter);
        listView.setOnTouchListener(new View.OnTouchListener() {
            boolean firstDragFlag = true;
            boolean dragFlag = false;   //현재 터치가 드래그 인지 확인
            float startYPosition = 0;       //터치이벤트의 시작점의 Y(세로)위치
            float endYPosition = 0;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_MOVE:       //터치를 한 후 움직이고 있으면
                        dragFlag = true;
                        if (firstDragFlag) {     //터치후 계속 드래그 하고 있다면 ACTION_MOVE가 계속 일어날 것임으로 무브를 시작한 첫번째 터치만 값을 저장함
                            startYPosition = motionEvent.getY(); //첫번째 터치의 Y(높이)를 저장
                            firstDragFlag = false;   //두번째 MOVE가 실행되지 못하도록 플래그 변경
                        }

                        break;

                    case MotionEvent.ACTION_UP:
                        endYPosition = motionEvent.getY();
                        firstDragFlag = true;

                        if (dragFlag) {  //드래그를 하다가 터치를 실행
                            // 시작Y가 끝 Y보다 크다면 터치가 아래서 위로 이루어졌다는 것이고, 스크롤은 아래로내려갔다는 뜻이다.
                            // (startYPosition - endYPosition) > 10 은 터치로 이동한 거리가 10픽셀 이상은 이동해야 스크롤 이동으로 감지하겠다는 뜻임으로 필요하지 않으면 제거해도 된다.
                            if ((startYPosition > endYPosition) && (startYPosition - endYPosition) > 10) {
//                                addBtn.hide();
                            }
                            //시작 Y가 끝 보다 작다면 터치가 위에서 아래로 이러우졌다는 것이고, 스크롤이 올라갔다는 뜻이다.
                            else if ((startYPosition < endYPosition) && (endYPosition - startYPosition) > 10) {
//                                addBtn.show();
                            }
                        }

                        startYPosition = 0.0f;
                        endYPosition = 0.0f;
                        break;
                }
                return false;
            }
        });

    }

    public void listVIewRefresh(){
        if (view == null)
            return;

        listView = view.findViewById(R.id.to_do_list);

        new DBManager(_context).selectTodayToDo();

        adapter = new ToDoListVIewAdapter(_context, ToDoModelList.todayToDoModels);
        listView.setAdapter(adapter);
    }

    private void setAddBtn() {
        addBtn = view.findViewById(R.id.add_button);
        addBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_button:
                onAddButtonClick();
                break;
        }
    }

    private void onAddButtonClick() {
        new ToDoAddDialog(_context).onShowDialog(0, DateManager.getTimestamp(), true);
    }
}
