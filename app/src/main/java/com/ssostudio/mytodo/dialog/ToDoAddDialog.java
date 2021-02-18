package com.ssostudio.mytodo.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.ssostudio.mytodo.R;
import com.ssostudio.mytodo.dbhelper.DBManager;
import com.ssostudio.mytodo.model.ToDoModel;
import com.ssostudio.mytodo.utility.DateManager;

import java.util.Calendar;
import java.util.Date;

public class ToDoAddDialog implements View.OnClickListener {

    private Context _context;
    private ToDoModel _toDoModel = new ToDoModel();
    private int _type;
    private long _startDate = 0;
    private int _year;
    private int _month;
    private boolean _isToday = false;
    private boolean _isUpdate = false;

    private Dialog _dialog;
    private TextView titleTextVIew;
    private MaterialButton cancelBtn, okBtn, plusBtn, subBtn, deadlineBtn;
    private TextInputEditText todoText, countText;
    private InputMethodManager imm;

    public ToDoAddDialog(Context context) {
        _context = context;
    }

    public void onShowDialog(int type, long startDate, boolean isToday) {
        _type = type;
        _startDate = startDate;
        _isToday = isToday;

        init();
    }

    public void onShowDialog(int type, int year) {
        _type = type;
        _startDate = year;

        init();
    }

    public void onShowDialog(int type, int year, int month) {
        _type = type;
        _year = year;
        _month = month;

        init();
    }

    public void onShowDialog(int type) {
        _type = type;
        _startDate = 0;

        init();
    }

    public void onShowUpdateDialog(ToDoModel toDoModel) {
        _isUpdate = true;
        _toDoModel = toDoModel;
        _type = _toDoModel.getTodo_type();

        if (_type == 0) {
            _startDate = _toDoModel.getStart_date();
        } else if (_type == 1 || _type == 2) {
            _startDate = DateManager.timestampToIntArray(_toDoModel.getStart_date())[0];
        }

        init();
    }

    private void init() {
        _dialog = new Dialog(_context);

        _dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        _dialog.setContentView(R.layout.dialog_to_do_add);

        titleTextVIew = _dialog.findViewById(R.id.title_text_view);
        titleTextVIew.setText(getTitleText());

        deadlineBtn = _dialog.findViewById(R.id.deadline_button);
        if (_type == 0) {
            deadlineBtn.setOnClickListener(this);
        } else {
            deadlineBtn.setVisibility(View.GONE);
        }

        cancelBtn = _dialog.findViewById(R.id.cancel_button);
        cancelBtn.setOnClickListener(this);

        okBtn = _dialog.findViewById(R.id.ok_button);
        okBtn.setOnClickListener(this);

        plusBtn = _dialog.findViewById(R.id.plus_button);
        plusBtn.setOnClickListener(this);

        subBtn = _dialog.findViewById(R.id.sub_button);
        subBtn.setOnClickListener(this);

        todoText = _dialog.findViewById(R.id.to_do_text);

        countText = _dialog.findViewById(R.id.count_text);

        if (_isUpdate) {
            todoText.setText(_toDoModel.getTodo_title());
            countText.setText("" + _toDoModel.getTodo_max_count());
        }

        showKeyboard();

        _dialog.show();

        _dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                hideKeyboard();
            }
        });

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(_dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        Window window = _dialog.getWindow();
        window.setAttributes(layoutParams);
    }

    private void showKeyboard() {
        todoText.post(new Runnable() {
            @Override
            public void run() {
                todoText.setFocusableInTouchMode(true);
                todoText.requestFocus();
                imm = (InputMethodManager) _context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        });
    }

    private void hideKeyboard() {
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    private String getTitleText() {
        String titleText = "null";

        switch (_type) {
            case 0:
                if (_isToday) {
                    titleText = _context.getString(R.string.today);
                } else {
                    int[] dates = DateManager.timestampToIntArray(_startDate);
                    titleText = DateManager.dateTimeZoneFormat(dates);
                }
                break;
            case 1:
                titleText = "" + _startDate;
                break;
            case 2:
                titleText = "버킷리스트";
                break;
            case 3:
                titleText = _month + "/" + _year;
                break;
        }

        return titleText;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_button:
                onClickCancelBtn();
                break;
            case R.id.ok_button:
                onClickOkBtn();
                break;
            case R.id.plus_button:
                onClickPlusBtn();
                break;
            case R.id.sub_button:
                onClickSubBtn();
                break;
            case R.id.deadline_button:
                onClickDeadlineBtn();
                break;
        }
    }

    private void onClickDeadlineBtn() {
        if (inputCheck()){
            onDatePickerShow();
            _dialog.hide();
        }
    }

    private void onDatePickerShow() {
        int[] date = DateManager.timestampToIntArray(DateManager.getTimestamp());

        DatePickerDialog dateDialog = new DatePickerDialog(_context, listener, date[0], date[1] - 1, date[2]);

        dateDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                hideKeyboard();
            }
        });

        dateDialog.setButton(DialogInterface.BUTTON_NEGATIVE, _context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                    _dialog.show();
                    showKeyboard();
                }
            }
        });

        int[] startDates = DateManager.timestampToIntArray(_startDate);

        Calendar minDate = Calendar.getInstance();
        minDate.set(startDates[0], startDates[1] - 1, startDates[2] + 1);
        dateDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());

        dateDialog.show();
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int[] date = {year, monthOfYear + 1, dayOfMonth};

            String todo = todoText.getText().toString();
            String countString = countText.getText().toString();
            int count = 0;

            if (!countString.equals("")) {
                count = Integer.parseInt(countString);
            }

            long timestamp = DateManager.intArrayToTimestamp(date);

            if (_isUpdate)
                updateToDo(todo, count, timestamp);
            else
                addTodo(todo, count, timestamp);

            _dialog.dismiss();
        }
    };

    private void onClickCancelBtn() {
        _dialog.dismiss();
    }

    private void onClickOkBtn() {
        if (inputCheck()) {

            String todo = todoText.getText().toString();
            String countString = countText.getText().toString();
            int count = 0;

            if (!countString.equals("")) {
                count = Integer.parseInt(countString);
            }

            if (_isUpdate)
                updateToDo(todo, count, 0);
            else
                addTodo(todo, count, 0);

            _dialog.dismiss();
        }
    }

    private boolean inputCheck() {
        String todo = todoText.getText().toString();
        String countString = countText.getText().toString();
        int count = 0;

        if (!countString.equals("")) {
            count = Integer.parseInt(countString);
        }

        if (todo.equals("")) {
            Toast.makeText(_context, "할일을 입력해주세요", Toast.LENGTH_SHORT).show();
            return false;
        } else if (count < 1) {
            Toast.makeText(_context, "카운트는 최소 1입니다", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void addTodo(String todo, int count, long deadline) {
        _toDoModel.setTodo_title(todo);
        _toDoModel.setTodo_max_count(count);
        _toDoModel.setTodo_type(_type);

        switch (_type) {
            case 0:
                _toDoModel.setStart_date(DateManager.dayStartTimestamp(_startDate));
                if (deadline == 0){
                    _toDoModel.setDeadline_date(DateManager.dayEndTimestamp(_startDate));
                }else{
                    _toDoModel.setDeadline_date(DateManager.dayEndTimestamp(deadline));
                }
                break;
            case 1:
                _toDoModel.setStart_date(DateManager.yearStartTimestamp((int) _startDate));
                _toDoModel.setDeadline_date(DateManager.yearEndTimestamp((int) _startDate));
                break;
            case 2:
                _toDoModel.setStart_date(0);
                _toDoModel.setDeadline_date(0);
                break;
            case 3:
                _toDoModel.setStart_date(DateManager.monthStartTimestamp(_year, _month));
                _toDoModel.setDeadline_date(DateManager.monthEndTimestamp(_year, _month));
                break;
        }

        new DBManager(_context).addTodoDB(_toDoModel);
    }

    private void updateToDo(String todo, int count, long deadline) {
        _toDoModel.setTodo_title(todo);
        _toDoModel.setTodo_max_count(count);
        if (deadline != 0){
            _toDoModel.setDeadline_date(DateManager.dayEndTimestamp(deadline));
        }
        new DBManager(_context).updateTodoDB(_toDoModel);
    }

    private void onClickPlusBtn() {
        int count = Integer.parseInt(countText.getText().toString());

        if (count < 999) {
            count += 1;

            countText.setText("" + count);
        }
    }

    private void onClickSubBtn() {
        int count = Integer.parseInt(countText.getText().toString());

        if (count > 1) {
            count -= 1;

            countText.setText("" + count);
        }
    }
}
