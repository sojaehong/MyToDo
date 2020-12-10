package com.ssostudio.mytodo.model;

import java.io.Serializable;

public class ToDoModel implements Serializable {
    // 고유 아이디
    private long todo_id;
    // 할일 타이틀
    private String todo_title;
    // 목표 카운트
    private long todo_max_count;
    // 현재 카운트
    private long todo_now_count;
    // 마지막 수정 날짜
    private long last_update_date;
    // 추가 한 날짜
    private long add_date;
    // 시작일
    private long start_date;
    // 종료일
    private long deadline_date;
    // 0:일간, 1:주간, 2:월간, 3:연간, 4:버킷리스트
    private int todo_type;
    // 할일 테그
    private String todo_tag;
    //할일 간단 노트
    private String todo_note;

    public long getTodo_id() {
        return todo_id;
    }

    public void setTodo_id(long todo_id) {
        this.todo_id = todo_id;
    }

    public String getTodo_title() {
        return todo_title;
    }

    public void setTodo_title(String todo_title) {
        this.todo_title = todo_title;
    }

    public long getTodo_max_count() {
        return todo_max_count;
    }

    public void setTodo_max_count(long todo_max_count) {
        this.todo_max_count = todo_max_count;
    }

    public long getTodo_now_count() {
        return todo_now_count;
    }

    public void setTodo_now_count(long todo_now_count) {
        this.todo_now_count = todo_now_count;
    }

    public long getLast_update_date() {
        return last_update_date;
    }

    public void setLast_update_date(long last_update_date) {
        this.last_update_date = last_update_date;
    }

    public long getAdd_date() {
        return add_date;
    }

    public void setAdd_date(long add_date) {
        this.add_date = add_date;
    }

    public long getStart_date() {
        return start_date;
    }

    public void setStart_date(long start_date) {
        this.start_date = start_date;
    }

    public long getDeadline_date() {
        return deadline_date;
    }

    public void setDeadline_date(long deadline_date) {
        this.deadline_date = deadline_date;
    }

    public int getTodo_type() {
        return todo_type;
    }

    public void setTodo_type(int todo_type) {
        this.todo_type = todo_type;
    }

    public String getTodo_tag() {
        return todo_tag;
    }

    public void setTodo_tag(String todo_tag) {
        this.todo_tag = todo_tag;
    }

    public String getTodo_note() {
        return todo_note;
    }

    public void setTodo_note(String todo_note) {
        this.todo_note = todo_note;
    }
}
