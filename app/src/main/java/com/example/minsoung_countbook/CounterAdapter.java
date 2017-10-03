package com.example.minsoung_countbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minsoung_countbook.R;

import java.util.List;

/**
 * Created by mchoi on 2017-10-02.
 */

public class CounterAdapter extends ArrayAdapter<Counter> {

    public CounterAdapter(Context context, List<Counter> counters) {
        super(context, R.layout.counter_row, counters);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater counterInflater = LayoutInflater.from(getContext());
        final View counterView = counterInflater.inflate(R.layout.counter_row, parent, false);

        final Counter counter = getItem(position);
        TextView name = (TextView) counterView.findViewById(R.id.name);
        TextView count = (TextView) counterView.findViewById(R.id.current_value);
        ImageButton decrement = (ImageButton) counterView.findViewById(R.id.decrement);
        ImageButton increment = (ImageButton) counterView.findViewById(R.id.increment);

        name.setText(counter.getName());
        count.setText(Integer.toString(counter.getCurrentValue()));
        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    counter.decrement();
                } catch (NegativeNumber e) {
                    Toast.makeText(getContext(), "Negative numbers are not allowed.", Toast.LENGTH_SHORT).show();
                }
                notifyDataSetChanged();
                MainActivity activity = (MainActivity)  counterView.getContext();
                activity.saveInFile();
            }
        });
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter.increment();
                notifyDataSetChanged();
                MainActivity activity = (MainActivity)  counterView.getContext();
                activity.saveInFile();
            }
        });
        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(counterView.getContext(), RecordActivity.class);
                intent.putExtra("Counter", counter);
                Activity activity = (Activity) counterView.getContext();
                activity.startActivityForResult(intent, 1);
            }
        });

        return counterView;
    }
}
