package com.ssostudio.mytodo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.ssostudio.mytodo.R;
import com.ssostudio.mytodo.decorator.SaturdayDecorator;
import com.ssostudio.mytodo.decorator.SundayDecorator;
import com.ssostudio.mytodo.decorator.TodayDecorator;
import com.ssostudio.mytodo.model.ToDoModel;
import com.ssostudio.mytodo.utility.DateManager;

public class DateRangeVIewDialog implements View.OnClickListener {
    private ToDoModel _toDoModel = new ToDoModel();
    private Boolean _isFailed;
    private Dialog _dialog;
    private Context _context;
    private MaterialCalendarView calendarView;
    private MaterialButton cancelButton, backButton;

    public DateRangeVIewDialog(Context context) {
        _context = context;
    }

    public void onShowDialog(ToDoModel toDoModel, boolean isFailed) {
        _toDoModel = toDoModel;
        _isFailed = isFailed;
        init();
    }

    private void init(){
        _dialog = new Dialog(_context);

        _dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        _dialog.setContentView(R.layout.dialog_date_range_view);

        cancelButton = _dialog.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(this);

        backButton = _dialog.findViewById(R.id.back_button);
        backButton.setOnClickListener(this);

        calendarView = _dialog.findViewById(R.id.calendarView);
        calendarView.post(new Runnable() {
            @Override
            public void run() {
                calendarView.addDecorators(new SundayDecorator(),
                        new SaturdayDecorator(),
                        new TodayDecorator());

                setSelectRange();

                calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                        calendarView.clearSelection();

                        setSelectRange();
                    }
                });

            }
        });

        _dialog.show();

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(_dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        Window window = _dialog.getWindow();
        window.setAttributes(layoutParams);
    }

    private void setSelectRange(){
        long startDateTimestamp = DateManager.dayStartTimestamp(_toDoModel.getStart_date());
        long deadlineDateTimestamp = DateManager.dayEndTimestamp(_toDoModel.getDeadline_date());

        int[] startDates = DateManager.timestampToIntArray(startDateTimestamp);
        int[] deadlineDates = DateManager.timestampToIntArray(deadlineDateTimestamp);

        calendarView.selectRange(CalendarDay.from(startDates[0],startDates[1] - 1,startDates[2]),
                CalendarDay.from(deadlineDates[0], deadlineDates[1] - 1, deadlineDates[2]));

        calendarView.setCurrentDate(CalendarDay.from(startDates[0],startDates[1] - 1,startDates[2]), true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancel_button:
                onCancelBtnClick();
                break;
            case R.id.back_button:
                onBackBtnClick();
                break;
        }
    }

    private void onBackBtnClick() {
        new ToDoSelectDialog(_context).onShowDialog(_toDoModel, _isFailed);
        _dialog.dismiss();
    }

    private void onCancelBtnClick() {
        _dialog.dismiss();
    }
}
