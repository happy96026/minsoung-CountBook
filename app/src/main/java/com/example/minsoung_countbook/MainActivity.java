package com.example.minsoung_countbook;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";

    private ArrayList<Counter> counters = new ArrayList<Counter>();
    CounterAdapter counterAdapter;
    ListView counterListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.main_name));

        counterAdapter = new CounterAdapter(this, counters);
        counterListView = (ListView) findViewById(R.id.counter_list);
        counterListView.setAdapter(counterAdapter);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        counterAdapter = new CounterAdapter(this, counters);
        counterListView = (ListView) findViewById(R.id.counter_list);
        counterListView.setAdapter(counterAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add:
                onclick_add();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onclick_add() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.add_dialog, null);
        final EditText name = (EditText) view.findViewById(R.id.et_name);
        final EditText value = (EditText) view.findViewById(R.id.et_value);
        final EditText comment = (EditText) view.findViewById(R.id.et_comments);
        final Button ok = (Button) view.findViewById(R.id.ok);
        Button cancel = (Button) view.findViewById(R.id.cancel);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (name.getText().toString().equals("") || value.getText().toString().equals("")) {
                    ok.setEnabled(false);
                } else {
                    ok.setEnabled(true);
                }
            }
        };
        name.addTextChangedListener(textWatcher);
        value.addTextChangedListener(textWatcher);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Counter counter = new Counter(name.getText().toString(),
                                Integer.parseInt(value.getText().toString()), comment.toString());
                counters.add(counter);
                dialog.dismiss();
                counterAdapter.notifyDataSetChanged();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
