package com.ssostudio.mytodo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.ssostudio.mytodo.R;
import com.ssostudio.mytodo.ToDoActivity;
import com.ssostudio.mytodo.dbhelper.DBManager;
import com.ssostudio.mytodo.decorator.CompletedEventDecorator;
import com.ssostudio.mytodo.decorator.IncompleteEventDecorator;
import com.ssostudio.mytodo.decorator.InporcessEventDecorator;
import com.ssostudio.mytodo.decorator.SaturdayDecorator;
import com.ssostudio.mytodo.decorator.SundayDecorator;
import com.ssostudio.mytodo.decorator.TodayDecorator;
import com.ssostudio.mytodo.utility.DateManager;

public class CalendarFragment extends Fragment {

    private static View view;
    private Context _context;
    private MaterialCalendarView materialCalendarView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calendar, container, false);
        _context = getContext();
        setCalendarView();

        return view;
    }

    private void setCalendarView() {
        materialCalendarView = view.findViewById(R.id.calendarView);
        initCalendarDecorators();
    }

    private void initCalendarDecorators() {
        if (view == null)
            return;

        new DBManager(_context).todoAllSelect();
        materialCalendarView = view.findViewById(R.id.calendarView);
        materialCalendarView.addDecorators(new SundayDecorator(),
                new SaturdayDecorator(),
                new TodayDecorator(),
                new IncompleteEventDecorator(),
                new CompletedEventDecorator(),
                new InporcessEventDecorator()
        );

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int[] dates = DateManager.calendarDayToIntArray(date);
                Intent intent = new Intent(getActivity(), ToDoActivity.class);
                intent.putExtra("dates", dates);
                startActivity(intent);
            }
        });
    }

    public void calendarDecoratorsRefresh(){
        if (view == null)
            return;
        materialCalendarView = view.findViewById(R.id.calendarView);

        materialCalendarView.post(new Runnable() {
            @Override
            public void run() {
                materialCalendarView.removeDecorator(new IncompleteEventDecorator());
                materialCalendarView.removeDecorator(new CompletedEventDecorator());
                materialCalendarView.removeDecorator(new InporcessEventDecorator());

                materialCalendarView.addDecorators(new IncompleteEventDecorator(),new CompletedEventDecorator(), new InporcessEventDecorator());
            }
        });
    }

    @Override
    public void onResume() {
        new DBManager(_context).todoAllSelect();
        calendarDecoratorsRefresh();
        super.onResume();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
