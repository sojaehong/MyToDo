package com.ssostudio.mytodo.decorator;

import android.graphics.Color;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;
import com.ssostudio.mytodo.todo.ToDoDataManager;
import com.ssostudio.mytodo.utility.DateManager;

public class CompletedEventDecorator implements DayViewDecorator {
    int checks;

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        int[] date = DateManager.calendarDayToIntArray(day);
        long timestamp = DateManager.intArrayToTimestamp(date);
        checks =  new ToDoDataManager().decoratorChecks(timestamp);

        if (checks == 1)
            return true;
        else
            return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(10, Color.argb(95,24,118,251)));
    }
}
