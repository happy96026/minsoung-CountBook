package com.example.minsoung_countbook;

import android.os.Parcelable;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mchoi on 2017-10-02.
 */

public class Counter implements Serializable {

    private String name;
    private String comment;
    private int initial_value;
    private int current_value;
    private Date date;

    public Counter(String name, int initial_value, String comment) {
        this.name = name;
        this.comment = comment;
        this.initial_value = initial_value;
        current_value = initial_value;
        date = new Date();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String description) {
        this.comment = description;
    }

    public int getInitialValue() {
        return initial_value;
    }

    public void setInitialValue(int initial_value) throws NegativeNumber {
        if (initial_value < 0) {
            this.initial_value = initial_value;
        } else {
            throw new NegativeNumber("Cannot set to a negative number!");
        }
    }

    public int getCurrentValue() {
        return current_value;
    }

    public void setCurrentValue(int current_value) throws NegativeNumber {
        if (current_value < 0) {
            this.current_value = current_value;
        } else {
            throw new NegativeNumber("Cannot set to a negative number!");
        }
    }

    public String date_toString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        return dateFormat.format(date);
    }

    public void increment() {
        date = new Date();
        current_value++;
    }

    public void decrement() throws NegativeNumber {
        if (current_value > 0) {
            date = new Date();
            current_value--;
        } else {
            throw new NegativeNumber("Cannot Increment a negative Number!");
        }
    }
}

