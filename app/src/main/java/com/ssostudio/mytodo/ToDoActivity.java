package com.ssostudio.mytodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ssostudio.mytodo.adapter.ToDoListVIewAdapter;
import com.ssostudio.mytodo.dbhelper.DBManager;
import com.ssostudio.mytodo.dialog.ToDoAddDialog;
import com.ssostudio.mytodo.fragment.CalendarFragment;
import com.ssostudio.mytodo.fragment.TodayFragment;
import com.ssostudio.mytodo.model.ToDoModelList;
import com.ssostudio.mytodo.utility.DateManager;

public class ToDoActivity extends AppCompatActivity implements View.OnClickListener {

    private int[] _dates;
    private TextView dateTextView;
    private ListView listView;
    private ToDoListVIewAdapter adapter;
    private ImageView closeImageView, beforeImageView, nextImageView;
    private FloatingActionButton addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        init();
    }

    private void init(){
        Intent intent = getIntent();
        _dates = intent.getIntArrayExtra("dates");

        new DBManager(getApplicationContext()).selectToDo(DateManager.intArrayToTimestamp(_dates));

        setAddBtn();
        setDateTextView();
        setToDoListView();
        setCloseImageView();
        setBeforeImageView();
        setNextImageView();
    }

    private void setAddBtn() {
        addBtn = findViewById(R.id.add_button);
        addBtn.setOnClickListener(this);
    }

    public void refresh(){
        if (getApplicationContext() == null)
            return;

        new DBManager(getApplicationContext()).selectToDo(DateManager.intArrayToTimestamp(_dates));
        setDateTextView();
        setToDoListView();
    }

    private void setNextImageView() {
        nextImageView = findViewById(R.id.next_date_image);
        nextImageView.setOnClickListener(this);
    }

    private void setBeforeImageView() {
        beforeImageView = findViewById(R.id.before_date_image);
        beforeImageView.setOnClickListener(this);
    }

    private void setCloseImageView() {
        closeImageView = findViewById(R.id.close_image);
        closeImageView.setOnClickListener(this);
    }

    private void setDateTextView(){
        dateTextView = findViewById(R.id.date_text);
        dateTextView.setText(DateManager.dateTimeZoneFormat(_dates));
    }

    private void setToDoListView(){
        listView = findViewById(R.id.to_do_list);
        adapter = new ToDoListVIewAdapter(this, ToDoModelList.selectToDoModels);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.close_image:
                onCloseImageViewClick();
                break;
            case R.id.before_date_image:
                onBeforeImageViewClick();
                break;
            case R.id.next_date_image:
                onNextImageViewClick();
                break;
            case R.id.add_button:
                onAddBtnClick();
                break;
        }
    }

    private void onAddBtnClick() {
        new ToDoAddDialog(this).onShowDialog(0, DateManager.intArrayToTimestamp(_dates), false);
    }

    private void onNextImageViewClick() {
        dateChange(1);
    }

    private void onBeforeImageViewClick() {
        dateChange(-1);
    }

    private void onCloseImageViewClick() {
        finish();
    }

    private void dateChange(int changeDate){
        long date = DateManager.changeDate(_dates, changeDate);
        _dates = DateManager.timestampToIntArray(date);
        refresh();
    }

    @Override
    public void finish() {
        super.finish();
    }
}
