package com.example.minsoung_countbook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * CounterAdapter is an adapter required to convert an array list
 * consisting of Counter object to a View. CounterAdapter reads the
 * objects in the array and displays the object as a view onto an activity
 * without specifying the internal properties of the view. CounterAdapter is
 * responsible for giving the button functionalities for incrementing, decrementing,
 * resetting and viewing details of each Counter object. This class is extended
 * from ArrayAdapter class in order to customize the visual of the view of each
 * element.
 */
public class CounterAdapter extends ArrayAdapter<Counter> {

    private Counter      counter;
    private View         counterView;
    private MainActivity activity;

    public CounterAdapter(Context context, List<Counter> counters) {
        super(context, R.layout.counter_row, counters);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater counterInflater = LayoutInflater.from(getContext());
        counter     = getItem(position);
        counterView = counterInflater.inflate(R.layout.counter_row, parent, false);
        activity    = (MainActivity)  counterView.getContext();

        TextView    name       = (TextView)   counterView.findViewById(R.id.name);
        TextView    date       = (TextView)   counterView.findViewById(R.id.date);
        TextView    value      = (TextView)   counterView.findViewById(R.id.current_value);
        ImageButton decrement  = (ImageButton)counterView.findViewById(R.id.decrement);
        ImageButton increment  = (ImageButton)counterView.findViewById(R.id.increment);

        name.setText(counter.getName());
        date.setText(counter.date_toString());
        value.setText(Integer.toString(counter.getCurrentValue()));

        handle_decrement(decrement);
        handle_increment(increment);
        handle_value    (value);

        return counterView;
    }

    private void handle_decrement(ImageButton decrement) {
        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    counter.decrement();
                } catch (NegativeNumber e) {
                    Toast.makeText(getContext(), "Negative numbers are not allowed.", Toast.LENGTH_SHORT).show();
                }
                notifyDataSetChanged();
                activity.saveInFile();
            }
        });
    }

    private void handle_increment(ImageButton increment) {
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter.increment();
                notifyDataSetChanged();
                activity.saveInFile();
            }
        });
    }

    private void handle_value(TextView value) {
        value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(counterView.getContext(), RecordActivity.class);
                intent.putExtra("Counter", counter);
                activity.startActivityForResult(intent, 1);
            }
        });

        value.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                View view = activity.getLayoutInflater().inflate(R.layout.reset_dialog, null);
                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.show();

                Button yes = (Button) view.findViewById(R.id.yes);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        counter.reset();
                        notifyDataSetChanged();
                        activity.saveInFile();
                        dialog.dismiss();
                    }
                });

                Button no  = (Button) view.findViewById(R.id.no);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                return true;
            }
        });
    }
}
