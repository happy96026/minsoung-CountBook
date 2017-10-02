package com.example.minsoung_countbook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.minsoung_countbook.R;

/**
 * Created by mchoi on 2017-10-02.
 */

public class CounterAdapter extends ArrayAdapter<String> {

    CounterAdapter(Context context, String[] counter) {
        super(context, R.layout.counter_row, counter);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater counterInflater = LayoutInflater.from(getContext());
        View counterView = counterInflater.inflate(R.layout.counter_row, parent, false);

        String counter = getItem(position);
        TextView count = (TextView) counterView.findViewById(R.id.count);
        TextView descriptor = (TextView) counterView.findViewById(R.id.descriptor);
        ImageButton decrement = (ImageButton) counterView.findViewById(R.id.decrement);
        ImageButton increment = (ImageButton) counterView.findViewById(R.id.increment);

        count.setText("1");
        descriptor.setText("Hello World!");

        return counterView;
    }
}
